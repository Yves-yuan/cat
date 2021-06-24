package validate

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{count, lit}

import java.util.Properties
import scala.annotation.tailrec
import scala.sys.exit

object Uniqueness {
  val usage =
    """
    Usage: Uniqueness [--dt '20210610' tbl1,k1,k2 tbl2,k1,k2 tbl3,k1,k2]
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
        case "--table" :: value :: tail =>
          nextOption(map ++ Map('table -> value), tail)
        case "--pk" :: value :: tail =>
          nextOption(map ++ Map('pk -> value), tail)
        case "--savepath" :: value :: tail =>
          nextOption(map ++ Map('savepath -> value), tail)
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
    val tblAndPks = tblBuilder.result()
    val spark = SparkSession.builder().appName("Wanmei")
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    val resBuilder = Array.newBuilder[(String, String, String)]
    tblAndPks.foreach(tblAndPk => {
      val tblAndPkArr = tblAndPk.split(',')
      val tblName = tblAndPkArr.head
      val pks = tblAndPkArr.tail
      println(tblAndPk)
      val tableSql = s"(select * from $tblName where dt='$dt') as src"
      val df = spark.read
        .format("jdbc")
        .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
        .option("url", "jdbc:clickhouse://10.6.8.39:8123/ods")
        .option("dbtable", tableSql)
        .option("user", "root")
        .option("password", "7rgbbg93lt67s3sn")
        .load()
      val cntNum = df.groupBy(pks.head, pks.tail: _*)
        .agg(count(lit(1)) as "cnt")
        .filter($"cnt" > 1)
        .count()
      if (cntNum > 0) {
        resBuilder += ((tblName, "not unique", pks.mkString(",")))
      } else {
        resBuilder += ((tblName, "unique", pks.mkString(",")))
      }
    })
    val res = resBuilder.result().map { (tblAndResult) => {
      (tblAndResult._1, "uniqueness", tblAndResult._3, tblAndResult._2, dt)
    }
    }
    val resDf = spark.createDataFrame(res)
      .toDF("table_name", "validate_type", "validate_target", "validate_result", "dt")
    val prop = new Properties()
    val ckDriver = "ru.yandex.clickhouse.ClickHouseDriver"
    prop.put("driver", ckDriver)
    prop.put("user", "root")
    prop.put("password", "7rgbbg93lt67s3sn")
    resDf.write
      .mode("append")
      .format("jdbc")
      .jdbc("jdbc:clickhouse://10.6.8.39:8123/ods", "ods.ods_data_validate_result", prop)
  }
}
