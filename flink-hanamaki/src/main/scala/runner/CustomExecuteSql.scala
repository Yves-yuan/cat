package runner

import scala.collection.mutable

class CustomExecuteSql(jsonConfig: mutable.HashMap[String, String]) extends Runner{
  override def run(cat: CatEnv): Unit = {
    val sql = jsonConfig.get("sql") match {
      case Some(from) => from
      case None => throw new Exception("sql must be assigned")
    }
    val result = cat.tEnv.executeSql(sql)
    result.print()
  }
}
