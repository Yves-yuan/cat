package etl.runner.validate

import etl.env.CatEnv
import etl.runner.Runner

import scala.collection.mutable

class TableRowNumber(jsonConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val sql = jsonConfig.get("sql") match {
      case Some(d) => d
      case None => throw new Exception("sql must be assigned")
    }
    val table = jsonConfig.get("table") match {
      case Some(d) => d
      case None => throw new Exception("table must be assigned")
    }
    val sink = jsonConfig.get("sink") match {
      case Some(d) => d
      case None => throw new Exception("sink must be assigned")
    }
    val resBuilder = Array.newBuilder[(String, String, String)]
    val df = env.spark.sql(sql)
    val cnt = df.count()
    resBuilder += ((table, cnt.toString, "row_number"))
    val res = resBuilder.result().map { (tblAndResult) => {
      (tblAndResult._1, "row_number", tblAndResult._3, tblAndResult._2)
    }
    }
    val resDf = env.spark.createDataFrame(res)
      .toDF("table_name", "validate_type", "validate_target", "validate_result", "dt")
    resDf.createOrReplaceTempView(sink)
  }
}
