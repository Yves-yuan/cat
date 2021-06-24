package validate.rule.validator

import org.apache.spark.sql.Row
import validate.rule.result.{Result, ValidatorResult}

import scala.util.{Failure, Success, Try}

class ValidatorMatchRegex(fieldName: String, equalValue: String) extends Validator with Serializable {
  var myEqualValue: String = equalValue
  var myFieldName: String = fieldName

  override def validate(data: Row): Result = {
    Try {
      val ft = data.getAs[String](myFieldName)
      val valid = myEqualValue.r.findFirstIn(ft).isDefined
      ValidatorResult(valid, myFieldName, ft, toString, "正则表达式规则校验结果")
    } match {
      case Success(value) => value
      case Failure(exception) => ValidatorResult(valid = false, myFieldName, "", toString, exception.toString)
    }
  }

  override def toString: String = {
    s"""$myFieldName match ${equalValue}"""
  }

  override def fieldName(): String = myFieldName
}
