package rule.validator

import io.growing.collector.tunnel.protocol.EventDto
import rule.ProtobufBase
import rule.result.{Result, ValidatorResult}

import scala.util.{Failure, Success, Try}

class ValidatorMatchRegex(fieldName: String, equalValue: String) extends Validator with ProtobufBase with Serializable {
  var myEqualValue: String = equalValue
  var myFieldName: String = fieldName

  override def validate(data: AnyRef): Option[Result] = {
    Try {
      val myData = data.asInstanceOf[EventDto]
      if (!myFieldName.startsWith("attributes")) {
        throw new Exception("暂时不支持非attributes校验")
      } else {
        val fn = myFieldName.split('.')(1)
        val v = myData.getAttributesMap.get(fn)
        val valid = myEqualValue.r.findFirstIn(v).isDefined
        val msg =
          s"""正则表达式规则校验结果:$valid
             |$myFieldName:$v
             |rule:${toString}""".stripMargin
        ValidatorResult(valid, msg)
      }

    } match {
      case Success(value) => Some(value)
      case Failure(exception) => Some(ValidatorResult(valid = false, exception.toString))
    }
  }

  override def toString: String = {
    s"""$myFieldName match ${equalValue}"""
  }
}
