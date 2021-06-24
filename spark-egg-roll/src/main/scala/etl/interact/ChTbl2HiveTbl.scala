package etl.interact

import org.apache.spark.sql.SparkSession

import scala.annotation.tailrec
import scala.sys.exit

object ChTbl2HiveTbl {
  val usage =
    """
    Usage: WanmeiCompleteness [--dt '20210610' --savepath '/home/yuanyifei' tbl1 tbl2]
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
        case "--from" :: value :: tail =>
          nextOption(map ++ Map('from -> value), tail)
        case "--to" :: value :: tail =>
          nextOption(map ++ Map('to -> value), tail)
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
    val from = options('from).toString
    val to = options('to).toString
    val spark = SparkSession.builder().appName("Wanmei")
      .enableHiveSupport()
      .config("hive.exec.dynamici.partition", value = true)
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .getOrCreate()
    val tableSql = s"(select * from $from where dt='$dt') as src"
    val df = spark.read
      .format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://10.6.8.39:8123/ods")
      .option("dbtable", tableSql)
      .option("user", "root")
      .option("password", "7rgbbg93lt67s3sn")
      .load()
      .repartition(50)
    df.createOrReplaceTempView("res")
    df.printSchema()
    val columns = df.schema.fieldNames.dropRight(1).mkString(",")
    val todt = if(dt==""){
      "20210610"
    }else{
      dt
    }
    spark.sql(
      s"""
         |insert overwrite table $to partition(dt='$todt') select $columns from res
         |""".stripMargin)
  }
}
