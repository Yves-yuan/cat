package rule.validator

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.{ArrayNode, NumericNode, TextNode}

import java.math.BigDecimal
import scala.util.{Failure, Success, Try}

object ValidatorFactory {
  def genValidator(jsonNode: JsonNode): Validator = {
    Try {
      jsonNode.get("operator").asText() match {
        case "equal" => equalValidator(jsonNode)
        case "=" => equalValidator(jsonNode)
        case "==" => equalValidator(jsonNode)
        case "in" => inValidator(jsonNode)
      }
    } match {
      case Success(value) => value
      case Failure(exception) => throw exception
    }
  }

  def equalValidator(jsonNode: JsonNode): Validator = {
    val key = jsonNode.get("key").asText()
    val value = jsonNode.get("value") match {
      case n: TextNode => n.asText()
      case n: NumericNode => n.decimalValue
    }
    new ValidatorEqual(key, value)
  }

  def inValidator(jsonNode: JsonNode): Validator = {
    val key = jsonNode.get("key").asText()
    jsonNode.get("value") match {
      case arrayNode: ArrayNode =>
        if (arrayNode.size() == 0) {
          return new ValidatorEqual(key, Array.empty)
        }
        val node1 = arrayNode.get(0)
        node1 match {
          case n: TextNode =>
            val arrBuilder = Array.newBuilder[String]
            for (i <- 0 until arrayNode.size()) {
              arrBuilder += arrayNode.get(i).asText()
            }
            new ValidatorIn(key, arrBuilder.result())
          case n: NumericNode =>
            val arrBuilder = Array.newBuilder[BigDecimal]
            for (i <- 0 until arrayNode.size()) {
              arrBuilder += arrayNode.get(i).decimalValue
            }
            new ValidatorIn(key, arrBuilder.result())
          case _ => throw new Exception(s"unsupported array value type in 'in' operator ${arrayNode.toString}")
        }
      case _ =>
        throw new Exception("in 操作符必须匹配数组元素例如:[1,2,3]或者['12','23','34']")
    }
  }
}
