package sql

import scala.util.matching.Regex

class ArgsParser(args: Map[Symbol, Any]) extends Parser {
  val regex1: Regex = "(\\$\\{[^ ]*})".r
  val regex2: Regex = "(\\$[a-zA-Z0-9|_]*)".r

  override def parse(sql: String): String = {
    var res = sql
    val matches1 = regex1.findAllMatchIn(res)
    matches1.foreach(m => {
      val g1 = m.group(1)
      val key = g1.substring(2, g1.length - 1)
      if (args.contains(Symbol(key))) {
        val s = Symbol(key)
        res = res.replace(g1, args(s).toString)
      }
    })
    val matches2 = regex2.findAllMatchIn(res)
    matches2.foreach(m => {
      val g1 = m.group(1)
      val key = g1.substring(1, g1.length)
      if (args.contains(Symbol(key))) {
        res = res.replace(g1, args(Symbol(key)).toString)
      }
    })
    res
  }

  def matches(sql: String): Array[String] = {
    val matches1 = regex1.findAllMatchIn(sql)
    val matches2 = regex2.findAllMatchIn(sql)
    val matchValues = scala.collection.mutable.ArrayBuilder.make[String]()
    matches1.foreach(m => {
      val g1 = m.group(1)
      matchValues += g1
    })
    matches2.foreach(m => {
      val g1 = m.group(1)
      matchValues += g1
    })
    matchValues.result()
  }

}
