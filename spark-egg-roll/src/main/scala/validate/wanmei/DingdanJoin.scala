package validate.wanmei

import org.apache.spark.sql.SparkSession

import java.util.Properties
import scala.annotation.tailrec
import scala.sys.exit

object DingdanJoin {
  val usage =
    """
    Usage: WanmeiCompleteness [--dt '20210610']
  """

  def main(input: Array[String]): Unit = {
    if (input.length == 0) println(usage)
    val tblBuilder = Array.newBuilder[String]
    type OptionMap = Map[Symbol, Any]

    @tailrec
    def nextOption(map: OptionMap, list: List[String]): OptionMap = {
      def isSwitch(s: String) = (s(0) == '-')

      list match {
        case Nil => map
        case "--dt" :: value :: tail =>
          nextOption(map ++ Map('dt -> value), tail)
        case string :: opt2 :: tail if isSwitch(opt2) =>
          tblBuilder += string
          nextOption(map ++ Map('rule -> string), list.tail)
        case string1 :: string2 =>
          tblBuilder += string1
          nextOption(map, list.tail)
        case string :: Nil =>
          tblBuilder += string
          nextOption(map, list.tail)
        case option :: tail => println("Unknown option " + option)
          exit(1)
      }
    }

    val options = nextOption(Map(), input.toList)
    println(options)
    val dt = options.getOrElse('dt, "20210611").toString
    val spark = SparkSession.builder().appName("Wanmei")
      .enableHiveSupport()
      .getOrCreate()
    val tableOrderInfo = s"(select * from ods.ods_mo_order_info_di where dt='$dt') as ods_mo_order_info_di"
    spark.read
      .format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://10.6.8.39:8123/ods")
      .option("dbtable", tableOrderInfo)
      .option("user", "root")
      .option("password", "7rgbbg93lt67s3sn")
      .load()
      .createOrReplaceTempView("ods.ods_mo_order_info_di")
    val tableOrderExtend = s"(select * from ods.ods_mo_order_extend_di where dt='$dt') as ods_mo_order_extend_di"
    spark.read
      .format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://10.6.8.39:8123/ods")
      .option("dbtable", tableOrderExtend)
      .option("user", "root")
      .option("password", "7rgbbg93lt67s3sn")
      .load()
      .createOrReplaceTempView("ods.ods_mo_order_extend_di")
    val tableOrderDetail = s"(select * from ods.ods_mo_order_detail_di where dt='$dt') as ods_mo_order_detail_di"
    spark.read
      .format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://10.6.8.39:8123/ods")
      .option("dbtable", tableOrderDetail)
      .option("user", "root")
      .option("password", "7rgbbg93lt67s3sn")
      .load()
      .createOrReplaceTempView("ods.ods_mo_order_detail_di")
    val res = spark.sql(
      s"""
         |select
         |a.id
         |,a.customer_id
         |,a.uid
         |,a.order_status
         |,a.pay_status
         |,a.pay_time
         |,a.pay_id
         |,a.payment_scene
         |,a.shipping_status
         |,a.refund_status
         |,a.order_type
         |,a.sale_mode
         |,a.is_group
         |,a.is_change
         |,a.pay_type
         |,a.total_amount
         |,a.sku_amount
         |,a.freight_amount
         |,a.promotion_amount
         |,a.coupon_amount
         |,a.voucher_amount
         |,a.points_amount
         |,a.pay_amount
         |,a.down_pay_amount
         |,a.final_pay_amount
         |,a.unpaid_amt
         |,a.order_split
         |,a.parent_order_id
         |,b.deducted_points
         |,b.freight_deducted_points
         |,b.binder_points
         |,b.config_points_amount
         |,b.config_points
         |,b.settle_amount
         |,b.settle_points
         |,b.real_freight
         |,b.coupon_user_group
         |,b.is_virtual
         |,b.coupon_account_id
         |,b.terminal_type
         |,b.delivery_status
         |,b.order_source
         |,b.source_type
         |,h.id as order_detail_id
         |,h.product_id
         |,h.product_code
         |,h.sku_id
         |,h.sku_code
         |,h.brand_sku_code
         |,h.sku_name
         |,h.sku_quantity
         |,h.promotion_num
         |,h.promotion_group
         |,h.origin_price
         |,h.sku_price
         |,h.display_price
         |,h.binder_points_amount
         |,h.real_amount
         |,h.detail_type
         |,h.is_canceled
         |,h.brand_id
         |,h.weight_gram
         |,now() as etl_dt
         |,b.dt as dt
         |from (select * from ods.ods_mo_order_info_di where dt = '$dt') a
         |left join (select * from ods.ods_mo_order_extend_di where dt = '$dt') b
         |on a.id = b.order_id
         |left join (select * from ods.ods_mo_order_detail_di where dt = '$dt') h
         |on a.id = h.order_id;
         |""".stripMargin)
    val prop = new Properties()
    val ckDriver = "ru.yandex.clickhouse.ClickHouseDriver"
    prop.put("driver", ckDriver)
    prop.put("user", "root")
    prop.put("password", "7rgbbg93lt67s3sn")
    res.write
      .mode("append")
      .format("jdbc")
      .jdbc("jdbc:clickhouse://10.6.8.39:8123/dws", "dws.dws_trs_info_df", prop)
  }
}
