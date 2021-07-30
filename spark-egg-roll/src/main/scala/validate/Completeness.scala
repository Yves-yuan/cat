package validate

import org.apache.spark.sql.SparkSession

import java.util.Properties
import scala.annotation.tailrec
import scala.sys.exit

object Completeness {
  val usage =
    """
    Usage: Completeness [--dt '20210610'  tbl1 tbl2]
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
    val spark = SparkSession.builder().appName("Wanmei")
      .enableHiveSupport()
      .getOrCreate()
    val tbls = tblBuilder.result()
    println(
      s"""
         |----------------------------------------------------
         |validate tables: ${tbls.mkString(",")}""".stripMargin)
    tbls.foreach(tbl => {
      val df = spark.read
        .format("jdbc")
        .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
        .option("url", "jdbc:clickhouse://10.6.8.39:8123/ods")
        .option("dbtable", tbl)
        .option("user", "root")
        .option("password", "7rgbbg93lt67s3sn")
        .load()
      df.createTempView(tbl)
      df.printSchema()
      val st = df.schema
      val concatSql = st.map(sf => {
        s"(count(${sf.name})-count(case when ${sf.name} is null or ${sf.name}='' then 1 end))/count(${sf.name}) as ${sf.name}"
      }).mkString(",")
        .dropRight(1)
      val exeCompleteNessSql =
        s"""
           |select ${concatSql} from $tbl where dt = '$dt'
           |""".stripMargin
      val completenessRes = spark.sql(exeCompleteNessSql)
      completenessRes.createTempView(s"${tbl}_res")
      val stackStr = s"stack(${completenessRes.schema.fieldNames.length}," + completenessRes.schema.fieldNames.map { x => {
        s"'$x',`$x`,"
      }
      }.mkString.dropRight(1) + s") as (`validate_target`,`validate_result`)"
      val chRes = spark.sql(
        s"""
           |select '$tbl' as table_name,
           |'completeness' as validate_type,
           |$stackStr,
           |$dt as `dt`
           | from ${tbl}_res
           |""".stripMargin)
      val prop = new Properties()
      val ckDriver = "ru.yandex.clickhouse.ClickHouseDriver"
      prop.put("driver", ckDriver)
      prop.put("user", "root")
      prop.put("password", "7rgbbg93lt67s3sn")
      chRes.write
        .mode("append")
        .format("jdbc")
        .jdbc("jdbc:clickhouse://10.6.8.39:8123/ods", "ods.ods_data_validate_result", prop)
      //      completenessRes.repartition(1).write.option("header", "true").csv(
      //        s"$savepath/$tbl"
      //      )
    })

  }


}
