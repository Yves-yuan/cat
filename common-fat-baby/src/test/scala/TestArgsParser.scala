import sql.ArgsParser

import scala.annotation.tailrec
import scala.sys.exit

object TestArgsParser {
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
    val parser = new ArgsParser(options)
    val sql = "select $myname from addada where ${dt} = ${dt_dt}"
    val s1 = parser.parse(sql)
    println(sql)
    println(s1)
  }
}
