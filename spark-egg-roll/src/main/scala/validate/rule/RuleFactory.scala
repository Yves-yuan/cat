package validate.rule

import com.fasterxml.jackson.databind.node.{ArrayNode, TextNode}
import json.JsonReader
import validate.rule.validator.{Validator, ValidatorFactory}

object RuleFactory {
  def getTableRule(path: String): TableRule = {
    val jsonNode = JsonReader.readFromFile(path)
    val tableName = jsonNode.get("table_name").asInstanceOf[TextNode].asText()
    val validatorNodes = jsonNode.get("validators").asInstanceOf[ArrayNode]
    val validatorBuilder = Array.newBuilder[Validator]
    for (j <- 0 until validatorNodes.size()) {
      val validatorNode = validatorNodes.get(j)
      val validator = ValidatorFactory.genValidator(validatorNode)
      validatorBuilder += validator
    }
    TableRule(tableName, validatorBuilder.result())
  }
}
