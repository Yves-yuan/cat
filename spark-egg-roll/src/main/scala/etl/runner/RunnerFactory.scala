package etl.runner

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode

object RunnerFactory {
  def createRunner(json: JsonNode): Runner = {
    val typ = json.get("type").asText()
    typ match {
      case "union" => createUnion(json)
      case "custom_sql" => createCustomSql(json)
      case "drop_column" => createDropColumns(json)
      case x => throw new Exception(s"runner type $x not supported now")
    }
  }

  def createCustomSql(json: JsonNode): Runner = {
    val sql = json.get("sql").asText()
    val sink = json.get("sink").asText()
    new CustomSql(sql, sink)
  }

  def createDropColumns(json: JsonNode): Runner = {
    val sql = json.get("sql").asText()
    val sink = json.get("sink").asText()
    val columns = arrayNodeToStringArray(json.get("drop_columns").asInstanceOf[ArrayNode])
    new DropColumns(sql, sink, columns)
  }

  private def arrayNodeToStringArray(node: ArrayNode): Array[String] = {
    val size = node.size()
    val builder = scala.collection.mutable.ArrayBuilder.make[String]()
    for (i <- 0 until size) {
      builder += node.get(i).asText()
    }
    builder.result()
  }

  def createUnion(json: JsonNode): Runner = {
    val sqls = arrayNodeToStringArray(json.get("sqls").asInstanceOf[ArrayNode])
    val sink = json.get("sink").asText()
    new Union(sqls,sink)
  }
}
