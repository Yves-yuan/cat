package etl.runner

import env.CatEnv

import java.sql.DriverManager
import java.util.Properties
import scala.collection.mutable

class ChSink(sinkConfig: mutable.HashMap[String, String]) extends Runner {
  def run(env: CatEnv): Unit = {
    val parsedConfig = sinkConfig
    val from = parsedConfig.get("from") match {
      case Some(from) => from
      case None => throw new Exception("from must be assigned")
    }
    println(from)
    val df = env.spark.sql(from)
    val chName = parsedConfig.get("name") match {
      case Some(name) => name
      case None => throw new Exception("Ch table name must be assigned")
    }
    val engine = parsedConfig.getOrElse("engine", "MergeTree")
    val partitionKey = parsedConfig.getOrElse("partition_key", "dt")
    val orderByKey = parsedConfig.get("order_by") match {
      case Some(key) => key
      case None => throw new Exception("Ch order_by key must be assigned")
    }
    val columnsSqlBuilder = scala.collection.mutable.ArrayBuilder.make[String]()
    df.schema.foreach(sf => {
      columnsSqlBuilder += s"${sf.name} ${sf.dataType.typeName}"
    })
    val columns = columnsSqlBuilder.result().mkString(",")
    val ddl =
      s"""
                 |create table if not exists $chName
                 |(
                 | $columns
                 |)
                 |ENGINE = $engine
                 |PARTITION BY $partitionKey
                 |ORDER BY $orderByKey
                 |
                 |""".stripMargin
    Class.forName("ru.yandex.clickhouse.ClickHouseDriver")
    val url = parsedConfig.get("url") match {
      case Some(url) => url
      case None => throw new Exception("Ch url must be assigned")
    }
    val user = parsedConfig.get("user") match {
      case Some(user) => user
      case None => throw new Exception("Ch user must be assigned")
    }
    val password = parsedConfig.get("password") match {
      case Some(password) => password
      case None => throw new Exception("Ch password must be assigned")
    }
    val conn = DriverManager.getConnection(url, user, password)
    val stmt = conn.createStatement()
    stmt.execute(ddl)
    stmt.close()
    conn.commit()
    conn.close()
    val prop = new Properties()
    val ckDriver = "ru.yandex.clickhouse.ClickHouseDriver"
    prop.put("driver", ckDriver)
    prop.put("user", user)
    prop.put("password", password)
    df.write
      .mode("append")
      .format("jdbc")
      .jdbc(url, chName, prop)
  }
}
