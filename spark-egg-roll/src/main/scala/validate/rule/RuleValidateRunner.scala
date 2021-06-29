package validate.rule

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import validate.rule.result.ValidatorResult

import java.util.Properties
import scala.annotation.tailrec
import scala.sys.exit

object RuleValidateRunner {
  val usage =
    """
    Usage: RuleValidateRunner [--path 'path' --dt'20210622']
  """

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Wanmei")
      .enableHiveSupport()
      .getOrCreate()
    if (args.length == 0) println(usage)
    val tblBuilder = Array.newBuilder[String]
    type OptionMap = Map[Symbol, Any]

    @tailrec
    def nextOption(map: OptionMap, list: List[String]): OptionMap = {
      def isSwitch(s: String) = (s(0) == '-')

      list match {
        case Nil => map
        case "--path" :: value :: tail =>
          nextOption(map ++ Map('path -> value), tail)
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
    val options = nextOption(Map(), args.toList)
    println(options)
    val path = options('path).toString
    val dt = options('dt).toString
    val rule = RuleFactory.getTableRule(path)
    val validators = rule.validators
    val tblName = rule.tblName
    val tableSql = s"(select * from $tblName where dt='$dt') as src"
    val df = spark.read
      .format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://10.6.8.39:8123/ods")
      .option("dbtable", tableSql)
      .option("user", "root")
      .option("password", "7rgbbg93lt67s3sn")
      .load()
    import spark.implicits._
    val totalFields = spark.createDataFrame(validators.map(v => {
      Tuple2(v.fieldName, true)
    }).toList).toDF("validate_target", "validate_result")
      .distinct()
    val resDf = df.rdd.flatMap(r => {
      validators.map(v => {
        v.validate(r).asInstanceOf[ValidatorResult]
      })
    }).toDF
      .withColumnRenamed("valid", "validate_result")
      .filter($"validate_result" === lit(false))
      .select($"fieldName")
      .withColumnRenamed("fieldName", "validate_target")
      .withColumn("is_false",lit(true))
      .distinct()
      .join(totalFields, Array("validate_target"), "right")
      .withColumn("validate_result", when($"is_false".isNull, lit(true)).otherwise(false))
      .withColumn("table_name", lit(tblName))
      .withColumn("validate_type", lit("rule"))
      .withColumn("dt", lit(dt))
      .select("table_name", "validate_type", "validate_target", "validate_result", "dt")
      .withColumn("validate_result",$"validate_result".cast("string"))
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
