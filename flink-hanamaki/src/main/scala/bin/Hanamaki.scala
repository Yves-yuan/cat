package bin

import com.twitter.chill.protobuf.ProtobufSerializer
import io.growing.collector.tunnel.protocol.EventDto
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.slf4j.LoggerFactory
import rule.RuleFactory
import sink.KafkaSinkFactory
import source.KafkaSourceFactory

import java.net.{InetAddress, NetworkInterface, URI}
import scala.annotation.tailrec
import scala.collection.JavaConverters.enumerationAsScalaIteratorConverter
import scala.sys.exit

object Hanamaki {
  val usage =
    """
    Usage: Hanamaki [--rule custom_event_rule.json --source source.json --sink sink.json]
  """
  val logger = LoggerFactory.getLogger("Hanamaki")

  def main(args: Array[String]): Unit = {
    logger.info("start validate")

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
    val arglist = args.toList
    type OptionMap = Map[Symbol, Any]

    @tailrec
    def nextOption(map: OptionMap, list: List[String]): OptionMap = {
      def isSwitch(s: String) = (s(0) == '-')

      list match {
        case Nil => map
        case "--rule" :: value :: tail =>
          nextOption(map ++ Map('rule -> value), tail)
        case "--source" :: value :: tail =>
          nextOption(map ++ Map('source -> value), tail)
        case "--sink" :: value :: tail =>
          nextOption(map ++ Map('sink -> value), tail)
        case string :: Nil => nextOption(map ++ Map('rule -> string), list.tail)
        case option :: tail => println("Unknown option " + option)
          exit(1)
      }
    }

    val options = nextOption(Map(), arglist)
    logger.info(options.toString())
    val sourcePath = options.getOrElse('source, "D:\\dev\\cat\\flink-hanamaki\\config\\source.json").toString
    val sinkPath = options.getOrElse('sink, "D:\\dev\\cat\\flink-hanamaki\\config\\sink.json").toString
    val rulePath = options.getOrElse('rule, "D:\\dev\\cat\\flink-hanamaki\\config\\custom_event_rule.json").toString
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(3)
    env.getConfig.registerTypeWithKryoSerializer(classOf[EventDto], classOf[ProtobufSerializer])
    val source = KafkaSourceFactory.genSourceCustomEvent(sourcePath, env)
    val rule = RuleFactory.generateRule("customEvent", rulePath)
    val sink = KafkaSinkFactory.genSourceCustomEvent(sinkPath)
    source.map { x => {
      rule.validate(x)
        .mkString
    }
    }.addSink(sink)
    env.execute("huajuan")
  }
}
