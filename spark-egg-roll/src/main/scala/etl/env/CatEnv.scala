package etl.env

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.{ArrayNode, TextNode}
import etl.runner._
import org.apache.spark.sql.SparkSession
import sql.ArgsParser

import scala.collection.mutable

case class CatEnv(spark: SparkSession, args: Map[Symbol, Any], settings: Map[String, String]) {
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
    val typ = json.get("type").asText()
    typ match {
      case "union" => createUnion(json)
      case "custom_sql" => createCustomSql(json)
      case "drop_column" => createDropColumns(json)
      case "table_merge" => createMerge(json)
      case "ch_sink" => createChSink(json)
      case "ch_source" => createChSource(json)
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

  private def createCustomSql(json: JsonNode): Runner = {
    val sql = json.get("sql").asText()
    val sqlParsed = argsParser.parse(sql)
    validateArgs(sqlParsed)
    val sink = json.get("sink").asText()
    new CustomSql(sqlParsed, sink)
  }

  private def createDropColumns(json: JsonNode): Runner = {
    val sql = json.get("sql").asText()
    val sqlParsed = argsParser.parse(sql)
    validateArgs(sqlParsed)
    val sink = json.get("sink").asText()
    val columns = arrayNodeToStringArray(json.get("drop_columns").asInstanceOf[ArrayNode])
    new DropColumns(sqlParsed, sink, columns)
  }

  private def createUnion(json: JsonNode): Runner = {
    val sqls = arrayNodeToStringArray(json.get("sqls").asInstanceOf[ArrayNode])
    val sqlsParsed = sqls.map { sql => {
      val s1 = argsParser.parse(sql)
      validateArgs(s1)
      s1
    }
    }
    val sink = json.get("sink").asText()
    new Union(sqlsParsed, sink)
  }

  private def createChSink(json: JsonNode): Runner = {
    val config = new scala.collection.mutable.HashMap[String, String]()
    val fs = json.fields()
    while (fs.hasNext) {
      val n = fs.next()
      val v = n.getValue.asText()
      val parsedValue = argsParser.parse(v)
      config.put(n.getKey, parsedValue)
    }
    config.foreach(x=>{
      validateArgs(x._2)
    })
    new ChSink(config)
  }
  private def createChSource(json: JsonNode): Runner = {
    val config = new scala.collection.mutable.HashMap[String, String]()
    val fs = json.fields()
    while (fs.hasNext) {
      val n = fs.next()
      val v = n.getValue.asText()
      val parsedValue = argsParser.parse(v)
      config.put(n.getKey, parsedValue)
    }
    config.foreach(x=>{
      validateArgs(x._2)
    })
    new ChSource(config)
  }

  private def createMerge(json: JsonNode): Runner = {
    val table = json.get("table") match {
      case node if (node.isInstanceOf[TextNode]) =>
        node.asText()
      case _ => throw new Exception("table 节点必须是string 类型")
    }
    val tablesBuilder = scala.collection.mutable.ArrayBuilder.make[TableWithColumnPrefix]()
    json.get("tables") match {
      case nodes if (nodes.isInstanceOf[ArrayNode]) =>
        for (i <- 0 until nodes.size()) {
          val node = nodes.get(i)
          val tableName = node.get("name").asText()
          val columnPrefix = node.get("column_prefix").asText()
          tablesBuilder += TableWithColumnPrefix(tableName, columnPrefix)
        }

      case _ => throw new Exception("tables 节点必须是数组节点")
    }
    new TableMerge(table, tablesBuilder.result())
  }

  private def arrayNodeToStringArray(node: ArrayNode): Array[String] = {
    val size = node.size()
    val builder = scala.collection.mutable.ArrayBuilder.make[String]()
    for (i <- 0 until size) {
      builder += node.get(i).asText()
    }
    builder.result()
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

    def build(spark: SparkSession): CatEnv = {
      new CatEnv(spark, args.toMap, settings.toMap)
    }
  }

  def builder(): Builder = {
    new Builder()
  }
}