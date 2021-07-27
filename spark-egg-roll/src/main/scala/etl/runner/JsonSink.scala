package etl.runner

import etl.env.CatEnv

import scala.collection.mutable

class JsonSink(jsonConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val sink = jsonConfig.get("sink") match {
      case Some(from) => from
      case None => throw new Exception("from must be assigned")
    }
    val sql = jsonConfig.get("sql") match {
      case Some(sql) => sql
      case None => throw new Exception("sql must be assigned")
    }
    val mode = jsonConfig.get("mode")
    val df = env.spark.sql(sql)
    df.write
      .options(jsonConfig)
      .mode(mode.getOrElse("error"))
      .json(sink)
  }
}
