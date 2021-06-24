package rule

import com.fasterxml.jackson.databind.JsonNode
import com.google.protobuf.GeneratedMessageV3
import rule.result.{ProjectRuleResult, Result}

import scala.util.{Failure, Success, Try}

class CustomEventProjectRule(jsonNode: JsonNode, rules: Array[CustomEventMessageRule]) extends ProtobufBase with Rule {
  val myProjectKey: String = jsonNode.get("projectKey").asText()

  def validate(data: GeneratedMessageV3): Option[Result] = {
    Try {
      val projectKey = getField("projectKey", data).toString
      if (projectKey == myProjectKey) {
        val msgRuleResults = rules.flatMap(r => {
          r.validate(data)
        })
        var valid = true
        if (msgRuleResults.exists(_.isValid == false)) {
          valid = false
        }
        Some(ProjectRuleResult(valid, projectKey, s"工程级别校验:$projectKey", msgRuleResults))
      } else {
        None
      }
    } match {
      case Success(value) => value
      case Failure(exception) =>
        val error =
          s"""
             |${exception.getMessage}
             |-----------------------
             |${data.toString}
             |""".stripMargin
        Some(ProjectRuleResult(valid = false, myProjectKey, error, Array.empty[Result]))
    }
  }
}
