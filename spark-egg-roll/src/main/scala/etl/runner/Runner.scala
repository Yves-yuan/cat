package etl.runner

import org.apache.spark.sql.SparkSession

trait Runner {
  def run(spark: SparkSession): Unit
}
