package etl.runner

import org.apache.spark.sql.{DataFrame, SparkSession}

class DropColumns(sql: String, sink: String, columns: Array[String]) extends Runner {
  override def run(spark: SparkSession): Unit = {
    val df = spark.sql(sql)
    val res = df.drop(columns: _*)
    sink match {
      case "null" => "do nothing"
      case anyOther => res.createTempView(anyOther)
    }
  }
}
