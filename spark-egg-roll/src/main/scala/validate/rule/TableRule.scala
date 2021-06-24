package validate.rule

import org.apache.spark.sql.Row
import validate.rule.result.Result
import validate.rule.validator.Validator

case class TableRule(tblName: String, validators: Seq[Validator]) extends Rule {
  override def validate(data: Row): Seq[Result] = {
    validators.map { v => {
      v.validate(data)
    }
    }
  }
}
