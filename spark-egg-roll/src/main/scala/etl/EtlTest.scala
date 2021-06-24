package etl

import org.apache.spark.sql.SparkSession

object EtlTest {
  def main(input: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("EtlTest")
      .enableHiveSupport()
      .getOrCreate()
//    spark.sql(
//      s"""create table if not exists tmp.test(
//         |`user_id` string,
//         |`gender` string,
//         |`age` int
//         |) stored as orc tblproperties ("orc.compress"="NONE")
//         |""".stripMargin)
    val df = spark.read
      .format("jdbc")
      .option("driver","ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://10.6.8.39:8123/ods")
      .option("dbtable", "ods_mo_order_info_di")
      .option("user", "root")
      .option("password", "7rgbbg93lt67s3sn")
      .load()
//    df.createTempView("t1")
    df.write.mode("overwrite")
      .saveAsTable("tmp.ods_mo_order_info_di")

  }
}
