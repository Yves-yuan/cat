object Test1 {
  def main(args: Array[String]): Unit = {
    def toInt(s: String): Option[Int] = {
      try {
        Some(Integer.parseInt(s.trim))
      } catch {
        case e: Exception => None
      }
    }

    val y = for {
      a <- toInt("1")
      b <- toInt("3")
      c <- toInt("2")
    } yield a + b + c
    println(y)
  }
}
