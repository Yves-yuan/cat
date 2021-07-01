package etl.runner

import env.CatEnv

import scala.util.Try

case class TableWithColumnPrefix(tableName: String, columnPrefix: String)

class TableMerge(table: String, tables: Array[TableWithColumnPrefix]) extends Runner {

  override def run(env: CatEnv): Unit = {
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
    var sqlBuilder = head
    val columnsSqlBuilder = scala.collection.mutable.ArrayBuilder.make[String]()
    tables.foreach(tableWithColumnPrefix => {
      val df = env.spark.sql(s"select * from ${tableWithColumnPrefix.tableName}")
      df.schema.foreach(sf => {
        columnsSqlBuilder += s"${tableWithColumnPrefix.columnPrefix + sf.name} ${sf.dataType.typeName}"

      })
    })
    sqlBuilder += columnsSqlBuilder.result().mkString(",\n")
    sqlBuilder += tail
    println(sqlBuilder)
    Try{
      env.spark.sql(sqlBuilder)
    }
  }

}
