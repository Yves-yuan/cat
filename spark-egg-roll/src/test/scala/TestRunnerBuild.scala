import bin.EtlRunnerGo.usage
import etl.env.CatEnv
import input.InputPool
import json.JsonReader

import scala.annotation.tailrec
import scala.sys.exit

object TestRunnerBuild {
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
    InputPool.inputMap = Some(options)
    println(options)
    val path = "D:\\dev\\cat\\spark-egg-roll\\config\\etl\\wanmei\\interact_hive_2_ch.json"
    val jsonNode = JsonReader.readFromFile(path)
    val env = new CatEnv(null,options,Map.empty)
    env.addRunner(jsonNode)
  }
}
