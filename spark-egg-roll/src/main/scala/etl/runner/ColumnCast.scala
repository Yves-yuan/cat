package etl.runner

import etl.env.CatEnv
import org.apache.spark.sql.functions.col

import scala.collection.mutable

class ColumnCast(sourceConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val source = sourceConfig.get("source") match {
      case Some(d) => d
      case None => throw new Exception("source must be assigned in config")
    }
    val column = sourceConfig.get("column") match {
      case Some(d) => d
      case None => throw new Exception("column must be assigned in config")
    }
    val castType = sourceConfig.get("cast_type") match {
      case Some(d) => d
      case None => throw new Exception("cast_type must be assigned in config")
    }
    val sink = sourceConfig.get("sink") match {
      case Some(d) => d
      case None => throw new Exception("sink must be assigned in config")
    }
    val df = env.spark.sql(source)
    df.withColumn(column, col(column).cast(castType))
      .createOrReplaceTempView(sink)
  }
}
