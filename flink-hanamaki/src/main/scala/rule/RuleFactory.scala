package rule

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import rule.validator.{Validator, ValidatorFactory}

object RuleFactory {
  def generateRule(ruleType: String, resource: String): Rule = {
    ruleType match {
      case "customEvent" =>
        customEventRuleGen(resource)
      case _ => throw new Exception("未知的规则")
    }
  }

  private def customEventRuleGen(resource: String) = {
    val url = RuleFactory.getClass.getClassLoader.getResource(resource)
    val objectMapper = new ObjectMapper
    val jsonNode = objectMapper.readTree(url)
    val messageRuleNodes = jsonNode.get("messageRules").asInstanceOf[ArrayNode]
    val msgRuleBuilder = Array.newBuilder[CustomEventMessageRule]
    for (i <- 0 until messageRuleNodes.size()) {
      val msgNode = messageRuleNodes.get(i)
      val validatorNodes = msgNode.get("validators").asInstanceOf[ArrayNode]
      val validatorBuilder = Array.newBuilder[Validator]
      for (j <- 0 until validatorNodes.size()) {
        val validatorNode = validatorNodes.get(j)
        val validator = ValidatorFactory.genValidator(validatorNode)
        validatorBuilder += validator
      }
      val msgRule = new CustomEventMessageRule(msgNode, validatorBuilder.result())
      msgRuleBuilder += msgRule
    }
    new CustomEventProjectRule(jsonNode, msgRuleBuilder.result())
  }
}
