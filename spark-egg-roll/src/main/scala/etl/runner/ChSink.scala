package etl.runner

import etl.env.CatEnv

import java.sql.DriverManager
import java.text.DateFormat
import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter
import java.util.{Date, Properties}
import scala.collection.mutable

class ChSink(sinkConfig: mutable.HashMap[String, String]) extends Runner {
  def run(env: CatEnv): Unit = {
    val from = sinkConfig.get("from") match {
      case Some(from) => from
      case None => throw new Exception("from must be assigned")
    }
    val from_dt = env.args.get('from_dt)
    val to_dt = env.args.get('to_dt)
    println(from)
    val df = env.spark.sql(from)

    val chTableName = sinkConfig.get("name") match {
      case Some(name) => name
      case None => throw new Exception("Ch table name must be assigned")
    }
    val engine = sinkConfig.getOrElse("engine", "MergeTree")
    val partitionKey = sinkConfig.get("partition_key")
    val orderByKey = sinkConfig.get("order_by") match {
      case Some(key) => key
      case None => throw new Exception("Ch order_by key must be assigned")
    }
    val columnsSqlBuilder = scala.collection.mutable.ArrayBuilder.make[String]()
    df.schema.foreach(sf => {
      val typeOri = sf.dataType.typeName
      val chType = typeOri.charAt(0).toUpper + typeOri.substring(1, typeOri.length)
      columnsSqlBuilder += s"${sf.name} ${chType}"
    })
    val columns = columnsSqlBuilder.result().mkString(",")
    val ddl =
      s"""
         |create table if not exists $chTableName
         |(
         | $columns
         |)
         |ENGINE = $engine
         |ORDER BY $orderByKey
         |""".stripMargin + partitionKey.flatMap(x => {
        Some(s"""
           | PARTITION BY $x
           |""".stripMargin)
      }).getOrElse("")
    val dbTable = chTableName.split('.')
    if (dbTable.length != 2) {
      throw new Exception(s"db and table must be assigned in name but got $chTableName")
    }
    val db = dbTable(0)
    val name = dbTable(1)
    if (db.length <= 0) {
      throw new Exception(s"db name is not legal $db")
    }
    if (name.length <= 0) {
      throw new Exception(s"table name is not legal $name")
    }
    val cdDdl =
      s"""
         |create database if not exists $db
         |""".stripMargin
    Class.forName("ru.yandex.clickhouse.ClickHouseDriver")
    val url = sinkConfig.get("url") match {
      case Some(url) => url
      case None => throw new Exception("Ch url must be assigned")
    }
    val user = sinkConfig.get("user") match {
      case Some(user) => user
      case None => throw new Exception("Ch user must be assigned")
    }
    val password = sinkConfig.get("password") match {
      case Some(password) => password
      case None => throw new Exception("Ch password must be assigned")
    }
    val conn = DriverManager.getConnection(url, user, password)
    val stmt = conn.createStatement()
    println(s"执行 $cdDdl")
    stmt.execute(cdDdl)
    println(s"执行 $ddl")
    stmt.execute(ddl)
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    if (from_dt.isDefined && to_dt.isDefined){
      val fromDt = LocalDate.parse(from_dt.get.toString, formatter)
      val toDt = LocalDate.parse(to_dt.get.toString, formatter)
      var iteratorDt = fromDt
      while (!iteratorDt.isAfter(toDt)) {
        val iDt = iteratorDt.format(formatter)
        val dmlDropPartition =
          s"""
             |alter table $chTableName drop partition '$iDt'
             |""".stripMargin
        println(s"执行 $dmlDropPartition")
        stmt.execute(dmlDropPartition)
        iteratorDt = iteratorDt.plusDays(1)
      }
    }
    stmt.close()
    conn.commit()
    conn.close()
    val prop = new Properties()
    val ckDriver = "ru.yandex.clickhouse.ClickHouseDriver"
    prop.put("driver", ckDriver)
    prop.put("user", user)
    prop.put("password", password)
    prop.put("batchsize", "100000")
    prop.put("rewriteBatchedStatements", "true")
    val p = env.args.getOrElse('write_parallelism, "10")
    println("write_parallelism:" + p)
    prop.put("numPartitions", s"$p")
    prop.put("socket_timeout", "300000")
    df
      .write
      .mode("append")
      .format("jdbc")
      .jdbc(url, chTableName, prop)
  }
}
