package etl.runner

import env.CatEnv
import org.apache.spark.sql.SparkSession

trait Runner {
  def run(env: CatEnv): Unit
}
