package etl.runner

import etl.env.CatEnv
import org.apache.spark.sql.SaveMode

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
