package etl.runner

import env.CatEnv

trait Runner {
  def run(env: CatEnv): Unit
}
