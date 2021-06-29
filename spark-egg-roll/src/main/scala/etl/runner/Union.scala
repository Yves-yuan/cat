package etl.runner

import env.CatEnv
import org.apache.spark.sql.SparkSession

class Union(sqls: Array[String], sink: String) extends Runner {
  def run(env: CatEnv): Unit = {
    val df = sqls.map { s => {
      env.spark.sql(s)
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
