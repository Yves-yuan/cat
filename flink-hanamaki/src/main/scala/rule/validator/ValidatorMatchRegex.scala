package rule.validator

import com.google.protobuf.Descriptors.FieldDescriptor
import com.google.protobuf.GeneratedMessageV3
import rule.ProtobufBase
import rule.result.{Result, ValidatorResult}

import scala.util.{Failure, Success, Try}

class ValidatorMatchRegex(fieldName: String, equalValue: String) extends Validator with ProtobufBase with Serializable {
  var myEqualValue: String = equalValue
  var myFieldName: String = fieldName

  override def validate(data: GeneratedMessageV3): Result = {
    Try {
      val ft = getFieldAndType(myFieldName, data)
      val field = ft._1
      val fieldType = ft._2
      val valid = fieldType match {
        case FieldDescriptor.Type.STRING =>
          myEqualValue.r.findFirstIn(field.asInstanceOf[String]).isDefined
          myEqualValue.exists(_.equals(field))
        case _ => throw new Exception("expected value must be regex string")
      }
      ValidatorResult(valid, toString, "相等规则校验结果")
    } match {
      case Success(value) => value
      case Failure(exception) => ValidatorResult(valid = false, toString, exception.toString)
    }
  }

  override def toString: String = {
    s"""$myFieldName match ${equalValue}"""
  }
}
