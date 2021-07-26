package etl.runner

import etl.env.CatEnv

import scala.collection.mutable

class PhoenixSource(jsonConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val table = jsonConfig.get("table") match {
      case Some(d) => d
      case None => throw new Exception("table must be assigned in config")
    }
    val zk = jsonConfig.get("zk") match {
      case Some(d) => d
      case None => throw new Exception("zk must be assigned in config")
    }
    val sink = jsonConfig.get("sink") match {
      case Some(d) => d
      case None => throw new Exception("sink must be assigned in config")
    }
    val df = env.spark.read
      .format("phoenix")
      .options(Map("table" -> table, "zookeeper" -> zk))
      .load()
    df.createOrReplaceTempView(sink)
  }
}
