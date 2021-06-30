package env

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import etl.runner.{CustomSql, DropColumns, Runner, Union}
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
      case x => throw new Exception(s"runner type $x not supported now")
    }
  }

  private def validateSql(s: String): Unit = {
    val ms = argsParser.matches(s)
    if (ms.length > 0) {
      if (settings.getOrElse("validate_sql_force", "true") == "true") {
        throw new IllegalArgumentException(ms.mkString(",") + " must be assigned")
      }
    }
  }

  private def createCustomSql(json: JsonNode): Runner = {
    val sql = json.get("sql").asText()
    val sqlParsed = argsParser.parse(sql)
    validateSql(sqlParsed)
    val sink = json.get("sink").asText()
    new CustomSql(sqlParsed, sink)
  }

  private def createDropColumns(json: JsonNode): Runner = {
    val sql = json.get("sql").asText()
    val sqlParsed = argsParser.parse(sql)
    validateSql(sqlParsed)
    val sink = json.get("sink").asText()
    val columns = arrayNodeToStringArray(json.get("drop_columns").asInstanceOf[ArrayNode])
    new DropColumns(sqlParsed, sink, columns)
  }

  private def createUnion(json: JsonNode): Runner = {
    val sqls = arrayNodeToStringArray(json.get("sqls").asInstanceOf[ArrayNode])
    val sqlsParsed = sqls.map { sql => {
      val s1 = argsParser.parse(sql)
      validateSql(s1)
      s1
    }
    }
    val sink = json.get("sink").asText()
    new Union(sqlsParsed, sink)
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