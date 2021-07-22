package bin

import input.InputPool
import json.JsonReader
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.EnvironmentSettings
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.slf4j.{Logger, LoggerFactory}
import runner.CatEnv

import java.net.{InetAddress, NetworkInterface}
import scala.annotation.tailrec
import scala.collection.JavaConverters.enumerationAsScalaIteratorConverter
import scala.sys.exit

object Hanamaki {
  val usage =
    """
    Usage: Hanamaki [--runner_config config.json ]
  """
  val logger: Logger = LoggerFactory.getLogger("Hanamaki")

  def main(args: Array[String]): Unit = {
    logger.info("start hanamaki")

    val enumeration = NetworkInterface.getNetworkInterfaces.asScala.toSeq
    val ipAddresses = enumeration.flatMap(p =>
      p.getInetAddresses.asScala.toSeq
    )
    val address = ipAddresses.find { address =>
      val host = address.getHostAddress
      host.contains(".") && !address.isLoopbackAddress && !address.isAnyLocalAddress && !address.isLinkLocalAddress
    }.getOrElse(InetAddress.getLocalHost)
    logger.info("-----------------" + address)
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
    logger.info(options.toString())
    val path = options('runner_config).toString
    val jsonNode = JsonReader.readFromFile(path)
    val settings = EnvironmentSettings.newInstance().useBlinkPlanner().build()
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = StreamTableEnvironment.create(env, settings)
    val catEnv = CatEnv.builder()
      .arg(options)
      .build(tEnv, env)
    catEnv.addRunner(jsonNode)
    catEnv.run()
    env.execute("huajuan")
  }
}
