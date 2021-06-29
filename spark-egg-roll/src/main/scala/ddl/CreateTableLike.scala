package ddl

import env.CatEnv
import input.InputPool
import json.JsonReader
import org.apache.spark.sql.SparkSession

import scala.annotation.tailrec
import scala.sys.exit

object CreateTableLike {
  val usage =
    """
    Usage: CreateTableLike [--from 'tbl1' --to 'tbl2']
  """

  def main(args: Array[String]): Unit = {
    if (args.length == 0) println(usage)
    val tblBuilder = Array.newBuilder[String]
    type OptionMap = Map[Symbol, Any]

    @tailrec
    def nextOption(map: OptionMap, list: List[String]): OptionMap = {
      def isSwitch(s: String) = (s(0) == '-')

      list match {
        case Nil => map
        case "--from" :: value :: tail =>
          nextOption(map ++ Map('from -> value), tail)
        case "--to" :: value :: tail =>
          nextOption(map ++ Map('to -> value), tail)
        case option :: tail => println("Unknown option " + option)
          exit(1)
      }
    }

    val options = nextOption(Map(), args.toList)
    InputPool.inputMap = Some(options)
    println(options)
    val from = options('from)
    val to = options('to)
    val spark = SparkSession
      .builder()
      .appName("EtlRunnerGo")
      .enableHiveSupport()
      .getOrCreate()
    val s = spark.sql(s"show create table $from")
    val stmt = s.collect()(0).getAs[String]("createtab_stmt")
    var new_stmt = stmt
    val tableRegex = "CREATE TABLE ([^(]+)".r
    tableRegex.findAllMatchIn(stmt).foreach { m => {
      val s = m.group(1)
      new_stmt = new_stmt.replace(s, to.toString)
    }
    }
    println(new_stmt)
    spark.sql(new_stmt)
  }

}
