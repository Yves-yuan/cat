package etl.runner

import etl.env.CatEnv

trait Runner {
  def run(env: CatEnv): Unit
}
