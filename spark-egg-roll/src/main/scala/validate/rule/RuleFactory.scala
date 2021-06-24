package validate.rule

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import org.codehaus.jackson.node.TextNode
import validate.rule.validator.{Validator, ValidatorFactory}

import java.io.File

object RuleFactory {
  def getTableRule(path: String): TableRule = {
    val file = new File(path)
    val objectMapper = new ObjectMapper
    val jsonNode = objectMapper.readTree(file)
    val tableName = jsonNode.get("validators").asInstanceOf[TextNode].asText()
    val validatorNodes = jsonNode.get("validators").asInstanceOf[ArrayNode]
    val validatorBuilder = Array.newBuilder[Validator]
    for (j <- 0 until validatorNodes.size()) {
      val validatorNode = validatorNodes.get(j)
      val validator = ValidatorFactory.genValidator(validatorNode)
      validatorBuilder += validator
    }
    TableRule(tableName,validatorBuilder.result())
  }
}
