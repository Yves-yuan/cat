package etl.runner

import etl.env.CatEnv

import scala.collection.mutable

class ChSource (sourceConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val table = sourceConfig.get("table") match {
      case Some(d) => d
      case None => throw new Exception("table must be assigned in config")
    }
    val url = sourceConfig.get("url") match {
      case Some(d) => d
      case None => throw new Exception("url must be assigned in config")
    }
    val user = sourceConfig.get("user") match {
      case Some(d) => d
      case None => throw new Exception("user must be assigned in config")
    }
    val password = sourceConfig.get("password") match {
      case Some(d) => d
      case None => throw new Exception("password must be assigned in config")
    }
    val sink = sourceConfig.get("sink") match {
      case Some(d) => d
      case None => throw new Exception("sink must be assigned in config")
    }
    val df = env.spark.read
      .format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", url)
      .option("dbtable", table)
      .option("user", user)
      .option("password", password)
      .load()
    df.createOrReplaceTempView(sink)
  }
}
