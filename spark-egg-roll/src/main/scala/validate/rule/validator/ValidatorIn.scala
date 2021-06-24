package validate.rule.validator

import com.google.protobuf.Descriptors.FieldDescriptor
import org.apache.spark.sql.Row
import validate.rule.result.{Result, ValidatorResult}

import scala.util.{Failure, Success, Try}

class ValidatorIn[T](fieldName: String, equalValue: Array[T]) extends Validator with Serializable {
  var myEqualValue: Array[T] = equalValue
  var myFieldName: String = fieldName

  override def validate(data: Row): Result = {
    Try {
      val ft = data.getAs[T](myFieldName)
      val inRange = myEqualValue.map(x => {
        x
      }).exists(_.equals(ft))
      ValidatorResult(inRange, myFieldName,ft.toString, toString, "枚举规则校验结果")
    } match {
      case Success(value) => value
      case Failure(exception) => ValidatorResult(valid = false,myFieldName, "", toString, exception.toString)
    }

  }

  override def toString: String = {
    s"""$myFieldName in ${myEqualValue.mkString(",")}"""
  }

  override def fieldName(): String = myFieldName
}
