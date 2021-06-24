package ddl

import org.apache.spark.sql.SparkSession

object CreateTable {
  def main(input: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("CreateTable")
      .enableHiveSupport()
      .getOrCreate()
    spark.sql("create table if not exist t1(id string)")
    spark.sql("create table if not exist t2(id string)")
    spark.sql("create table if not exist t2(id string)")
  }
}
