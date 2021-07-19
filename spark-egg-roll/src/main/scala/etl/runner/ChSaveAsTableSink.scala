package etl.runner

import etl.env.CatEnv
import org.apache.spark.sql.SaveMode

import java.sql.DriverManager
import java.util.Properties
import scala.collection.mutable


class ChSaveAsTableSink(sinkConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val sql = sinkConfig.get("sql") match {
      case Some(from) => from
      case None => throw new Exception("sql must be assigned")
    }
    val df = env.spark.sql(sql)
    val chTableName = sinkConfig.get("name") match {
      case Some(name) => name
      case None => throw new Exception("Ch table name must be assigned")
    }
    val columnsSqlBuilder = scala.collection.mutable.ArrayBuilder.make[String]()
    df.schema.foreach(sf => {
      val typeOri = sf.dataType.typeName
      val chType = typeOri.charAt(0).toUpper + typeOri.substring(1, typeOri.length)
      columnsSqlBuilder += s"${sf.name} ${chType}"
    })
    val columns = columnsSqlBuilder.result().mkString(",")
    val engine = env.args.getOrElse('engine, "MergeTree")
    val orderByKey = sinkConfig.get("order_by") match {
      case Some(key) => Some(key)
      case None => env.args.get('order_by) match {
        case Some(key) => Some(key.toString)
        case None => None
      }
    }
    val partitionByKey = sinkConfig.get("partition_key") match {
      case Some(key) => Some(key)
      case None => env.args.get('partition_key) match {
        case Some(key) => Some(key.toString)
        case None => None
      }
    }
    val ddl =
      s"""
         |create table if not exists $chTableName
         |(
         | $columns
         |)
         |ENGINE = $engine
         |""".stripMargin + orderByKey.flatMap(x => {
        Some(
          s"""ORDER BY $x
             |""".stripMargin)
      }).getOrElse("") + partitionByKey.flatMap(x => {
        Some(
          s"""PARTITION BY $x
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
      .mode(SaveMode.Append)
      .jdbc(url, chTableName, prop)
  }
}
