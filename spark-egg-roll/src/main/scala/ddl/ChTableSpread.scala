package ddl


import java.sql.DriverManager
import scala.annotation.tailrec
import scala.sys.exit
import scala.util.matching.Regex

object ChTableSpread {
  val usage =
    """
    Usage: ChTableSpread --from 'ods.ods_(.*)' --to 'dwd.dwd_$1' --url xxx
    --user xxx --password xxx
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
        case "--url" :: value :: tail =>
          nextOption(map ++ Map('url -> value), tail)
        case "--user" :: value :: tail =>
          nextOption(map ++ Map('user -> value), tail)
        case "--password" :: value :: tail =>
          nextOption(map ++ Map('password -> value), tail)
        case string :: Nil =>
          tblBuilder += string
          nextOption(map, list.tail)
        case string1 :: string2 =>
          tblBuilder += string1
          nextOption(map, list.tail)
      }
    }

    val options = nextOption(Map(), args.toList)
    println(options)
    val from = options('from).toString
    val to = options('to).toString
    val re = from.r
    val url = options('url).toString
    val user = options('user).toString
    val password = options('password).toString
    Class.forName("ru.yandex.clickhouse.ClickHouseDriver")
    val conn = DriverManager.getConnection(url, user, password)
    val metaData = conn.getMetaData

    val tables = metaData.getTables(null, "%", "%", Array("TABLE"))
    while (tables.next()) {
      val tblName = tables.getString("TABLE_NAME")
      val db = tables.getString("TABLE_SCHEM")
      val fullName = db+"."+tblName
      println(fullName)
      val spreadName = tblSpread(fullName, re, to)
      if (spreadName.isDefined) {
        val sctSql = s"show create table $fullName"
        val sctStatement = conn.createStatement()
        sctStatement.execute(sctSql)
        val sctRs = sctStatement.getResultSet
        while(sctRs.next()){
          val ctStatement = sctRs.getString("statement")
          println(ctStatement)
          val spreadCtStatement = ctStatement.replace(fullName,spreadName.get)
            .replace("CREATE TABLE","CREATE TABLE if not exists")
          println(spreadCtStatement)
        }

      }
    }
  }

  private def tblSpread(tblName: String, from: Regex, to: String) = {
    val matchList = from.findAllMatchIn(tblName).toList
    if (matchList.length == 1) {
      val m = matchList.head
      var buf = to
      for (i <- 1 to m.groupCount) {
        buf = buf.replace("$" + s"$i", m.group(i))
      }
      Some(buf)
    } else {
      None
    }
  }
}
