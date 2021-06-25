package rule.validator

import com.google.protobuf.Descriptors.FieldDescriptor
import com.google.protobuf.GeneratedMessageV3
import io.growing.collector.tunnel.protocol.EventDto
import rule.ProtobufBase
import rule.result.{Result, ValidatorResult}

import scala.util.{Failure, Success, Try}

class ValidatorIn[T](fieldName: String, equalValue: Array[T]) extends Validator with ProtobufBase with Serializable {
  var myEqualValue: Array[T] = equalValue
  var myFieldName: String = fieldName

  override def validate(data: AnyRef): Option[Result] = {
    Try {
      val myData = data.asInstanceOf[EventDto]
      val valid = if (myFieldName.startsWith("attributes")) {
        val attrName = myFieldName.split('.')(1)
        val v = myData.getAttributesMap.get(attrName)
        val valid = myEqualValue.exists(_.equals(v))
        val msg =
          s"""枚举规则校验结果:$valid
             |$myFieldName:$v
             |rule:${toString}""".stripMargin
        ValidatorResult(valid, msg)
      } else {
        throw new Exception("暂时不支持非attribute校验")
      }
      valid
    } match {
      case Success(value) => Some(value)
      case Failure(exception) => Some(ValidatorResult(valid = false, exception.toString))
    }

  }

  override def toString: String = {
    s"""$myFieldName in ${myEqualValue.mkString(",")}"""
  }
}
