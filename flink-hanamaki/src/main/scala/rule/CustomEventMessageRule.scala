package rule

import com.fasterxml.jackson.databind.JsonNode
import com.google.protobuf.GeneratedMessageV3
import io.growing.collector.tunnel.protocol.EventDto
import rule.result.{MsgRuleResult, Result}
import rule.validator.Validator

import scala.util.{Failure, Success, Try}

class CustomEventMessageRule(jsonNode: JsonNode, validators: Array[Validator]) extends Validator with ProtobufBase with Serializable {
  var myEventKey: String = jsonNode.get("eventKey").asText()
  var myDataSourceId: String = jsonNode.get("dataSourceId").asText()

  def validate(data: AnyRef): Option[Result] = {
    Try {
      val myData = data.asInstanceOf[EventDto]
      val eventKey = myData.getEventKey
      val dataSourceId = myData.getDataSourceId
      if (eventKey == myEventKey && dataSourceId == myDataSourceId) {
        val validatorResults = validators.map { v => {
          v.validate(data)
        }
        }.filter(_.isDefined).map(_.get)
        var valid = true
        if (validatorResults.exists(_.isValid == false)) {
          valid = false
        }
        Some(MsgRuleResult(valid, "", s"", validatorResults))
      } else {
        None
      }
    } match {
      case Success(value) => value
      case Failure(e) => throw e
    }
  }


}
