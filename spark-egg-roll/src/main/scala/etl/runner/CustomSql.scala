package etl.runner

import etl.env.CatEnv

import scala.collection.mutable

class CustomSql(sinkConfig: mutable.HashMap[String, String]) extends Runner {
  def run(env: CatEnv): Unit = {
    val sql = sinkConfig.get("sql") match {
      case Some(from) => from
      case None => throw new Exception("sql must be assigned")
    }
    val sink = sinkConfig.get("sink") match {
      case Some(from) => from
      case None => throw new Exception("sink must be assigned")
    }
    val df = env.spark.sql(sql)
    sink match {
      case "null" => "do nothing"
      case anyOther => df.createTempView(anyOther)
    }
  }
}
