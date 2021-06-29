package etl.runner

import org.apache.spark.sql.SparkSession

class Union(sqls: Array[String], sink: String) extends Runner {
  def run(spark: SparkSession): Unit = {
    val df = sqls.map { s => {
      spark.sql(s)
    }
    }.reduce((left, right) => {
      left.union(right)
    })
    sink match {
      case "null" => "do nothing"
      case anyOther => df.createTempView(anyOther)
    }
  }
}
