package etl.runner

import etl.env.CatEnv

import scala.collection.mutable

class HdfsSink(jsonConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val sql = jsonConfig.get("sql") match {
      case Some(d) => d
      case None => throw new Exception("sql must be assigned")
    }
    val mode = jsonConfig.get("mode") match {
      case Some(d) => d
      case None => throw new Exception("mode must be assigned")
    }
    val format = jsonConfig.get("format") match {
      case Some(d) => d
      case None => throw new Exception("format must be assigned")
    }
    val path = jsonConfig.get("path") match {
      case Some(d) => d
      case None => throw new Exception("path must be assigned")
    }
    val df = env.spark.sql(sql)
    df.write.mode(mode)
      .format(format)
      .save(path)
  }
}
