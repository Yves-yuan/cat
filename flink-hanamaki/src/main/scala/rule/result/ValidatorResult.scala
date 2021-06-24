package rule.result

case class ValidatorResult(valid: Boolean, rule: String, desc: String) extends Result {
  override def toString: String = {
    s"""valid:$valid
       |rule:$rule
       |desc:$desc
       |""".stripMargin
  }

  override def isValid: Boolean = valid
}
