package etl.runner

import etl.env.CatEnv

import scala.collection.mutable
import scala.util.Try

class CreateTableFromDf(sourceConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val source = sourceConfig.get("source") match {
      case Some(d) => d
      case None => throw new Exception("source must be assigned in config")
    }
    val table = sourceConfig.get("table") match {
      case Some(d) => d
      case None => throw new Exception("table must be assigned in config")
    }
    val df = env.spark.sql(source)
    val head =
      s"""
         |create table if not exists $table
         |(""".stripMargin
    val tail =
      s"""|)
          |PARTITIONED BY (`dt` STRING)
          |ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.orc.OrcSerde'
          |WITH SERDEPROPERTIES (
          |  'serialization.format' = '1'
          |)
          |STORED AS
          |  INPUTFORMAT 'org.apache.hadoop.hive.ql.io.orc.OrcInputFormat'
          |  OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.orc.OrcOutputFormat'
          |TBLPROPERTIES (
          |  'transient_lastDdlTime' = '1624516829',
          |  'orc.compress' = 'NONE'
          |)
          |
          |""".stripMargin
    val columnsSqlBuilder = scala.collection.mutable.ArrayBuilder.make[String]()
    df.schema.foreach(sf => {
      columnsSqlBuilder += s"${sf.name} ${sf.dataType.typeName}"
    })
    val sql = head + columnsSqlBuilder.result().mkString(",\n") + tail
    println(sql)
    Try{
      env.spark.sql(sql)
    }
  }
}
