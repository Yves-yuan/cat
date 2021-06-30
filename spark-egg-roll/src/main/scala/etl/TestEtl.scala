package etl

import org.apache.spark.sql.SparkSession

object TestEtl {
  def main(input: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("EtlTest")
      .master("local")
      //      .enableHiveSupport()
      .getOrCreate()
    val df = spark.read
      .format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
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
