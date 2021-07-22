package runner

import org.slf4j.LoggerFactory

import scala.collection.mutable

class CustomQuerySql(jsonConfig: mutable.HashMap[String, String]) extends Runner{
  val logger = LoggerFactory.getLogger("CustomQuerySql")

  override def run(cat: CatEnv): Unit = {

    val sql = jsonConfig.get("sql") match {
      case Some(from) => from
      case None => throw new Exception("sql must be assigned")
    }
    val sink = jsonConfig.get("sink") match {
      case Some(from) => from
      case None => throw new Exception("sink must be assigned")
    }
    logger.info(sql)
    val tbl = cat.tEnv.sqlQuery(sql)
    sink match {
      case "null" => "do nothing"
      case anyOther => cat.tEnv.createTemporaryView(sink,tbl)
    }
  }
}
