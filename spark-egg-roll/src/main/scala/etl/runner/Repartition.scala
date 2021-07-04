package etl.runner

import etl.env.CatEnv
import org.apache.spark.sql.functions.{col, split}

import scala.collection.mutable

class Repartition(sinkConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val sql = sinkConfig.get("sql") match {
      case Some(from) => from
      case None => throw new Exception("sql must be assigned")
    }
    val partitionKey = sinkConfig.get("partition_key") match {
      case Some(from) => from
      case None => throw new Exception("partition_key must be assigned")
    }
    val sink = sinkConfig.get("sink") match {
      case Some(from) => from
      case None => throw new Exception("sink must be assigned")
    }
    val df = env.spark.sql(sql)
    df.repartition(partitionKey.split(',').map(col): _*)
      .createOrReplaceTempView(sink)
  }
}
