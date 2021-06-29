package sql

import scala.util.matching.Regex

class ArgsParser(args: Map[Symbol, Any]) extends Parser {
  val regex1: Regex = "(\\$\\{[^ ]*})".r
  val regex2: Regex = "(\\$[^ ]*)".r

  override def parse(sql: String): String = {
    val r1 = regex1
    val r2 = regex2
    var res = sql
    val matchs1 = r1.findAllMatchIn(res)
    matchs1.foreach(m => {
      val g1 = m.group(1)
      val key = g1.substring(2, g1.length - 1)
      if (args.contains(Symbol(key))) {
        val s = Symbol(key)
        res = res.replace(g1, args(s).toString)
      }
    })
    val matchs2 = r2.findAllMatchIn(res)
    matchs2.foreach(m => {
      val g1 = m.group(1)
      val key = g1.substring(1, g1.length)
      if (args.contains(Symbol(key))) {
        res = res.replace(g1, args(Symbol(key)).toString)
      }
    })
    res
  }
}
