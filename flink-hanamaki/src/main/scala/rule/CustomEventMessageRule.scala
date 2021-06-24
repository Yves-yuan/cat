package rule

import com.fasterxml.jackson.databind.JsonNode
import com.google.protobuf.GeneratedMessageV3
import rule.result.{MsgRuleResult, Result}
import rule.validator.Validator

import scala.util.{Failure, Success, Try}

class CustomEventMessageRule(jsonNode: JsonNode, validators: Array[Validator]) extends Rule with ProtobufBase with Serializable {
  var myEventKey: String = jsonNode.get("eventKey").asText()
  var myUe: String = jsonNode.get("ue").asText()

  def validate(data: GeneratedMessageV3): Option[Result] = {
    Try {
      val eventKey = getField("eventKey", data)
      val ue = getField("ue", data)
      if (eventKey == myEventKey && ue == myUe) {
        val validatorResults = validators.map { v => {
          v.validate(data)
        }
        }
        var valid = true
        if (validatorResults.exists(_.isValid == false)) {
          valid = false
        }
        Some(MsgRuleResult(valid, data.toString, s"消息级别校验", validatorResults))
      } else {
        None
      }
    } match {
      case Success(value) => value
      case Failure(e) => throw e
    }
  }


}
