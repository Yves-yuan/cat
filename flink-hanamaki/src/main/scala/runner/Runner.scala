package runner


trait Runner {
  def run(cat: CatEnv): Unit
}
