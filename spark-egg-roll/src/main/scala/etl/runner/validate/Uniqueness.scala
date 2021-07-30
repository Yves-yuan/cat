package etl.runner.validate

import etl.env.CatEnv
import etl.runner.Runner
import org.apache.spark.sql.functions._

import scala.collection.mutable

class Uniqueness(jsonConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    import env.spark.implicits._
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
    val pks = jsonConfig.get("pks") match {
      case Some(d) => d
      case None => throw new Exception("pks must be assigned,like pk1,pk2,pk3")
    }

    val pksArr = pks.split(',')
    val resBuilder = Array.newBuilder[(String, String, String)]
    val df = env.spark.sql(sql)
    val cntNum = df.groupBy(pksArr.head, pksArr.tail: _*)
      .agg(count(lit(1)) as "cnt")
      .filter($"cnt" > 1)
      .count()
    if (cntNum > 0) {
      resBuilder += ((table, "not unique", pks.mkString(",")))
    } else {
      resBuilder += ((table, "unique", pks.mkString(",")))
    }
    val res = resBuilder.result().map { (tblAndResult) => {
      (tblAndResult._1, "uniqueness", tblAndResult._3, tblAndResult._2)
    }
    }
    val resDf = env.spark.createDataFrame(res)
      .toDF("table_name", "validate_type", "validate_target", "validate_result")
    resDf.createOrReplaceTempView(sink)
  }
}
