package rule.validator

import io.growing.collector.tunnel.protocol.EventDto
import rule.ProtobufBase
import rule.result.{Result, ValidatorResult}

import scala.util.{Failure, Success, Try}

class ValidatorEqual[T](fieldName: String, equalValue: T) extends Validator with ProtobufBase {
  var myEqualValue: T = equalValue
  var myFieldName: String = fieldName

  override def validate(data: AnyRef): Option[Result] = {
    Try {
      val myData = data.asInstanceOf[EventDto]
      if (myFieldName.startsWith("attributes")) {
        val fn = myFieldName.split(',')(1)
        val v = myData.getAttributesMap.get(fn)
        val valid = myEqualValue.equals(v)
        val msg =
          s"""相等规则校验结果:$valid
             |$myFieldName:$v
             |rule:${toString}""".stripMargin
        ValidatorResult(valid, msg)
      } else {
        throw new Exception("暂时不支持非attributes校验")
      }
    } match {
      case Success(value) => Some(value)
      case Failure(exception) => Some(ValidatorResult(valid = false, exception.toString))
    }

  }

  override def toString: String = {
    s"$myFieldName = $equalValue"
  }
}
