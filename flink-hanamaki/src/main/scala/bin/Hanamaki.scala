package bin

import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import rule.RuleFactory
import sink.KafkaSinkFactory
import source.KafkaSourceFactory

import scala.annotation.tailrec
import scala.sys.exit

object Hanamaki {
  val usage =
    """
    Usage: Hanamaki [--rule custom_event_rule.json --source source.json --sink sink.json]
  """

  def main(args: Array[String]): Unit = {
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
    println(options)
    val sourceName = options.getOrElse('source, "source.json").toString
    val sinkName = options.getOrElse('sink, "sink.json").toString
    val ruleName = options.getOrElse('rule, "custom_event_rule.json").toString
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val source = KafkaSourceFactory.genSourceCustomEvent(sourceName, env)
    val rule = RuleFactory.generateRule("customEvent",ruleName)
    val sink = KafkaSinkFactory.genSourceCustomEvent(sinkName)
    source.map { x => {
      rule.validate(x)
        .mkString
    }
    }.addSink(sink)
    env.execute("huajuan")
  }
}
