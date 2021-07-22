package runner

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import sql.ArgsParser

import scala.collection.mutable

case class CatEnv(tEnv: StreamTableEnvironment, env: StreamExecutionEnvironment, args: Map[Symbol, Any], settings: Map[String, String]) {
  val argsParser = new ArgsParser(args)
  val runners = new scala.collection.mutable.MutableList[Runner]()

  def run(): Unit = {
    runners.foreach(r => {
      r.run(this)
    })
  }

  def addRunner(runner: Runner): Unit = {
    runners += runner
  }

  def addRunner(jsonNode: JsonNode): Unit = {
    val runnerNodes = jsonNode.get("runners").asInstanceOf[ArrayNode]
    for (i <- 0 until runnerNodes.size()) {
      val runnerNode = runnerNodes.get(i)
      val runner = createRunner(runnerNode)
      runners += runner
    }
  }

  private def createRunner(json: JsonNode): Runner = {
    val jsonConfig = new scala.collection.mutable.HashMap[String, String]()
    val fs = json.fields()
    while (fs.hasNext) {
      val n = fs.next()
      val v = n.getValue.asText()
      val parsedValue = argsParser.parse(v)
      jsonConfig.put(n.getKey, parsedValue)
    }
    jsonConfig.foreach(x => {
      validateArgs(x._2)
    })
    val typ = json.get("type").asText()
    typ match {
      case "gio_event_source" => new GioEventSource(jsonConfig)
      case "custom_query_sql" => new CustomQuerySql(jsonConfig)
      case "custom_execute_sql" => new CustomExecuteSql(jsonConfig)
      case "event_validator" => new EventValidator(jsonConfig)
      case "hive_connect" => new HiveConnect(jsonConfig)
      case x => throw new Exception(s"runner type $x not supported now")
    }
  }

  private def validateArgs(s: String): Unit = {
    val ms = argsParser.matches(s)
    if (ms.length > 0) {
      if (settings.getOrElse("validate_args_force", "true") == "true") {
        throw new IllegalArgumentException(ms.mkString(",") + " must be assigned by input args")
      }
    }
  }

}

object CatEnv {

  class Builder {
    private val args = new mutable.HashMap[Symbol, Any]()
    private val settings = new mutable.HashMap[String, String]()

    def config(key: String, value: String): Builder = {
      settings.put(key, value)
      this
    }

    def arg(symbol: Symbol, value: Any): Builder = {
      args.put(symbol, value)
      this
    }

    def arg(arg: Map[Symbol, Any]): Builder = {
      args ++= arg
      this
    }

    def build(tEnv: StreamTableEnvironment, env: StreamExecutionEnvironment): CatEnv = {
      new CatEnv(tEnv, env, args.toMap, settings.toMap)
    }
  }

  def builder(): Builder = {
    new Builder()
  }
}