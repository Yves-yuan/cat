package rule

import com.fasterxml.jackson.databind.node.ArrayNode
import json.JsonReader
import org.slf4j.{Logger, LoggerFactory}
import rule.validator.{Validator, ValidatorFactory}

object RuleFactory {
  val logger: Logger = LoggerFactory.getLogger("RuleFactory")

  def generateRule(ruleType: String, rulePath: String): Validator = {
    ruleType match {
      case "customEvent" =>
        customEventRuleGen(rulePath)
      case _ => throw new Exception("未知的规则")
    }
  }

  private def customEventRuleGen(path: String) = {
    //    val hdfs = FileSystem.get(URI.create("hdfs://growingFS"),new Configuration())
    //    val sourceStream = hdfs.open(new Path(path))
    val jsonNode = JsonReader.readFromFile(path)
    val messageRuleNodes = jsonNode.get("messageRules") match {
      case node if (node.isInstanceOf[ArrayNode]) => node
      case x => throw new Exception(s"messageRules 节点必须是数组${x.toString}")
    }
    val msgRuleBuilder = Array.newBuilder[CustomEventMessageRule]
    for (i <- 0 until messageRuleNodes.size()) {
      val msgNode = messageRuleNodes.get(i)
      val validatorNodes = msgNode.get("validators") match {
        case node if (node.isInstanceOf[ArrayNode]) => node
        case x => throw new Exception(s"validators 节点必须是数组${x.toString}")
      }
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
