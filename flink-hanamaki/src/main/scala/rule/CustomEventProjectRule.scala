package rule

import com.fasterxml.jackson.databind.JsonNode
import io.growing.collector.tunnel.protocol.EventDto
import org.slf4j.LoggerFactory
import rule.result.{ProjectRuleResult, Result}
import rule.validator.Validator

import java.util
import scala.util.{Failure, Success, Try}

class CustomEventProjectRule(jsonNode: JsonNode, rules: Array[CustomEventMessageRule]) extends ProtobufBase with Validator {
  val myProjectKey: String = jsonNode.get("projectKey").asText()
  val logger = LoggerFactory.getLogger("CustomEventProjectRule")

  def validate(data: AnyRef): Option[Result] = {
    Try {
      val myData = data.asInstanceOf[EventDto]
      val projectKey = myData.getProjectKey
      logger.info(projectKey)
      logger.info(myProjectKey)
      if (projectKey == myProjectKey) {
        logger.info("开始校验")
        val msgRuleResults = rules.flatMap(r => {
          r.validate(data)
        })
        var valid = true
        if (msgRuleResults.exists(_.isValid == false)) {
          valid = false
        }
        val msgStr = myData.getAttributesMap.entrySet().toArray().map(_.asInstanceOf[util.Map.Entry[String, String]]).map(x => {
          (x.getKey, x.getValue)
        }).mkString(",")
        val dec =
          s"""project key:$projectKey
             |event key:${myData.getEventKey}
             |data source id:${myData.getDataSourceId}
             |user id:${myData.getUserId}
             |attributes:$msgStr""".stripMargin
        Some(ProjectRuleResult(valid, projectKey, dec, msgRuleResults))
      } else {
        logger.info("不校验")
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
