package bin

import com.fasterxml.jackson.databind.node.ArrayNode
import etl.runner.{CustomSql, Runner, RunnerFactory}
import json.JsonReader
import org.apache.spark.sql.SparkSession
import validate.wanmei.DingdanJoin.usage

import scala.annotation.tailrec
import scala.sys.exit

object EtlRunnerGo {
  val usage =
    """
    Usage: EtlRunnerGo [--runner_config 'path to runner config' --dt '20210610']
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
        case "--runner_config" :: value :: tail =>
          nextOption(map ++ Map('runner_config -> value), tail)
        case string :: opt2 :: tail if isSwitch(string) =>
          tblBuilder += string
          nextOption(map ++ Map(Symbol(string.dropWhile(_ == '-')) -> opt2), tail)
        case option :: tail => println("Unknown option " + option)
          exit(1)
      }
    }

    val options = nextOption(Map(), args.toList)
    println(options)
    val spark = SparkSession.builder().appName("EtlTest")
      .enableHiveSupport()
      .getOrCreate()
    val path = options('runner_config).toString
    val jsonNode = JsonReader.readFromFile(path)
    val runnerNodes = jsonNode.get("runners").asInstanceOf[ArrayNode]
    val runnersBuilder = scala.collection.mutable.ArrayBuilder.make[Runner]()
    for (i <- 0 until runnerNodes.size()) {
      val runnerNode = runnerNodes.get(i)
      val runner = RunnerFactory.createDropColumns(runnerNode)
      runnersBuilder += runner
    }
    val runners = runnersBuilder.result()
    runners.foreach(r => {
      r.run(spark)
    })
  }
}
