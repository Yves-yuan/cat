package rule.validator

import com.google.protobuf.Descriptors.FieldDescriptor
import com.google.protobuf.GeneratedMessageV3
import rule.ProtobufBase
import rule.result.{Result, ValidatorResult}

import scala.util.{Failure, Success, Try}

class ValidatorEqual[T](fieldName: String, equalValue: T) extends Validator with ProtobufBase {
  var myEqualValue: T = equalValue
  var myFieldName: String = fieldName

  override def validate(data: GeneratedMessageV3): Result = {
    Try {
      val ft = getFieldAndType(myFieldName, data)
      val field = ft._1
      val t = ft._2

      val valid = t match {
        case FieldDescriptor.Type.STRING => myEqualValue.equals(field)
        case _ => myEqualValue.equals(field)
      }
      ValidatorResult(valid, toString, "相等规则校验结果")
    } match {
      case Success(value) => value
      case Failure(exception) => ValidatorResult(valid = false, toString, exception.toString)
    }

  }

  override def toString: String = {
    s"$myFieldName = $equalValue"
  }
}
