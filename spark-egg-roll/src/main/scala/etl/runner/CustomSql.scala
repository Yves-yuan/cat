package etl.runner

import env.CatEnv

class CustomSql(sql: String, sink: String) extends Runner {
  def run(env: CatEnv): Unit = {
    val df = env.spark.sql(sql)
    sink match {
      case "null" => "do nothing"
      case anyOther => df.createTempView(anyOther)
    }
  }
}
