package validate.rule.validator

import org.apache.spark.sql.Row
import validate.rule.result.{Result, ValidatorResult}

import scala.util.{Failure, Success, Try}

class ValidatorEqual[T](fieldName: String, equalValue: T) extends Validator {
  var myEqualValue: T = equalValue
  var myFieldName: String = fieldName

  override def validate(data: Row): Result = {
    Try {
      val ft = data.getAs[T](myFieldName)
      val valid = myEqualValue.equals(ft)
      ValidatorResult(valid, fieldName, ft.toString, toString, "相等规则校验结果")
    } match {
      case Success(value) => value
      case Failure(exception) => ValidatorResult(valid = false, fieldName, "", toString, exception.toString)
    }

  }

  override def toString: String = {
    s"$myFieldName = $equalValue"
  }

  override def fieldName(): String = myFieldName
}
