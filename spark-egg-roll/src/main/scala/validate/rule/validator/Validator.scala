package validate.rule.validator

import org.apache.spark.sql.Row
import validate.rule.result.Result

trait Validator extends Serializable {
  def validate(data: Row): Result

  def toString: String
  def fieldName:String
}
