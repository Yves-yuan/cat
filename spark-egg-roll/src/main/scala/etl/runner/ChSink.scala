package etl.runner

import env.CatEnv

class ChSink (from: String, sinkConfig: Map[String,String])extends Runner{
  def run(env: CatEnv): Unit = {
    println(from)
    val df = env.spark.sql(from)
    val chName = sinkConfig.get("name") match {
      case Some(name)=>name
      case None=>throw new Exception("Ch table name must be assigned")
    }
    val engine = sinkConfig.getOrElse("engine","MergeTree")
    val partitionKey = sinkConfig.getOrElse("partition_key","dt")

    sink match {
      case "null" => "do nothing"
      case anyOther => df.createTempView(anyOther)
    }
  }
}
