package etl.distinct

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{lit, row_number}

import java.util.Properties
import scala.annotation.tailrec
import scala.sys.exit


object TblDistinctHive2Hive {
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
        case "--dt" :: value :: tail =>
          nextOption(map ++ Map('dt -> value), tail)
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
    val dt = options.getOrElse('dt, "20210611").toString
    val pkArr = pks.split(',')
    val spark = SparkSession.builder().appName("Wanmei")
      .enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    val df = spark.sql(s"select * from $src")
    val window = Window.partitionBy(pkArr.head, pkArr.tail: _*).orderBy($"dt".desc)
    val res = df.withColumn("rank", row_number() over (window))
      .filter($"rank" === lit(1))
      .drop($"rank")
      .drop($"dt")
    res.createTempView("res")
    val columns = res.schema.fieldNames.mkString(",")
    res.printSchema()
    spark.sql(
      s"""
         |insert overwrite table $des partition(dt='$dt') select $columns from res
         |""".stripMargin)
  }
}
