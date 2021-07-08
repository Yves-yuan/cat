package etl.runner

import etl.env.CatEnv

import scala.collection.mutable

class ParquetSource (sourceConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val path = sourceConfig.get("path") match {
      case Some(d) => d
      case None => throw new Exception("path must be assigned in config")
    }
    val df = env.spark.read.parquet(path)
    val sink = sourceConfig.get("sink") match {
      case Some(d) => d
      case None => throw new Exception("sink must be assigned in config")
    }
    df.createOrReplaceTempView(sink)
  }
}
