package validate.rule.result

case class ValidatorResult(valid: Boolean,fieldName:String,value:String, rule: String, desc: String) extends Result {
  override def toString: String = {
    s"""valid:$valid
       |rule:$rule
       |desc:$desc
       |""".stripMargin
  }

  override def isValid: Boolean = valid
}
