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
    val partitionNum = sinkConfig.get("partition_num")
    val sink = sinkConfig.get("sink") match {
      case Some(from) => from
      case None => throw new Exception("sink must be assigned")
    }
    val df = env.spark.sql(sql)
    val res = if (partitionKey == "") {
      df.repartition(partitionNum.getOrElse(env.spark.conf.get("spark.sql.shuffle.partitions")).toInt)
    } else {
      df.repartition(partitionNum.getOrElse(env.spark.conf.get("spark.sql.shuffle.partitions")).toInt,
        partitionKey.split(',').map(col): _*)
    }
    res.createOrReplaceTempView(sink)
  }
}
