package etl.runner.validate

import etl.env.CatEnv
import etl.runner.Runner

import scala.collection.mutable

class Completeness(jsonConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {

    val sql = jsonConfig.get("sql") match {
      case Some(d) => d
      case None => throw new Exception("sql must be assigned")
    }
    val sink = jsonConfig.get("sink") match {
      case Some(d) => d
      case None => throw new Exception("sink must be assigned")
    }
    val table = jsonConfig.get("table") match {
      case Some(d) => d
      case None => throw new Exception("table must be assigned")
    }

    val df = env.spark.sql(sql)
    val tbl = "tmp_completeness_view"
    df.createTempView(tbl)
    df.printSchema()
    val st = df.schema
    val concatSql = st.map(sf => {
      s"(count(${sf.name})-count(case when ${sf.name} is null or ${sf.name}='' then 1 end))/count(${sf.name}) as ${sf.name}"
    }).mkString(",")
      .dropRight(1)
    val exeCompleteNessSql =
      s"""
         |select ${concatSql} from $tbl
         |""".stripMargin
    val completenessRes = env.spark.sql(exeCompleteNessSql)
    completenessRes.createTempView(s"${tbl}_res")
    val stackStr = s"stack(${completenessRes.schema.fieldNames.length}," + completenessRes.schema.fieldNames.map { x => {
      s"'$x',`$x`,"
    }
    }.mkString.dropRight(1) + s") as (`validate_target`,`validate_result`)"
    val chRes = env.spark.sql(
      s"""
         |select '$table' as table_name,
         |'completeness' as validate_type,
         |$stackStr,
         | from ${tbl}_res
         |""".stripMargin)
    chRes.createOrReplaceTempView(sink)
  }
}
