package etl.runner

import etl.env.CatEnv

import scala.collection.mutable

class CsvSource(jsonConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val path = jsonConfig.get("path") match {
      case Some(d) => d
      case None => throw new Exception("path must be assigned")
    }
    val sink = jsonConfig.get("sink") match {
      case Some(d) => d
      case None => throw new Exception("sink must be assigned")
    }
    val df = env.spark.read
      .options(jsonConfig)
      .csv(path)
    df.createOrReplaceTempView(sink)
  }
}
