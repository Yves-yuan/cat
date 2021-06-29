package etl.runner

import org.apache.spark.sql.{DataFrame, SparkSession}

class CustomSql(sql: String, sink: String) extends Runner {
  def run(spark: SparkSession): Unit = {
    val df = spark.sql(sql)
    sink match {
      case "null" => "do nothing"
      case anyOther => df.createTempView(anyOther)
    }
  }
}
