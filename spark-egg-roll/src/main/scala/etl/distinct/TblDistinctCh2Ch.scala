package etl.distinct

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{lit, row_number}

import java.util.Properties
import scala.annotation.tailrec
import scala.sys.exit

object TblDistinctCh2Ch {
  val usage =
    """
    Usage: TblDistinct [--src 'src' --des 'des' --pks 'pks']
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
        case "--src" :: value :: tail =>
          nextOption(map ++ Map('src -> value), tail)
        case "--des" :: value :: tail =>
          nextOption(map ++ Map('des -> value), tail)
        case "--pks" :: value :: tail =>
          nextOption(map ++ Map('pks -> value), tail)
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
    val src = options.apply('src).toString
    val des = options.apply('des).toString
    val pks = options.apply('pks).toString
    val pkArr = pks.split(',')
    val spark = SparkSession.builder().appName("Wanmei")
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    val df = spark.read
      .format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://10.6.8.39:8123/dws")
      .option("dbtable", src)
      .option("user", "root")
      .option("password", "7rgbbg93lt67s3sn")
      .load()
      .repartition(50)
    df.printSchema()
    val window = Window.partitionBy(pkArr.head, pkArr.tail: _*).orderBy($"dt".desc)
    df.withColumn("rank", row_number() over (window))
      .filter($"rank" === lit(1))

    val prop = new Properties()
    val ckDriver = "ru.yandex.clickhouse.ClickHouseDriver"
    prop.put("driver", ckDriver)
    prop.put("user", "root")
    prop.put("password", "7rgbbg93lt67s3sn")
    prop.put("ENGINE", "MergeTree()")
    df
      .drop($"rank")
      .drop($"dt")
      .write
      .mode("append")
      .format("jdbc")
      .jdbc("jdbc:clickhouse://10.6.8.39:8123/dws", des, prop)
  }

}
