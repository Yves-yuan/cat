package rule.result

case class MsgRuleResult(valid: Boolean, msg: String, desc: String, childrenResults: Array[Result]) extends Result {
  override def isValid: Boolean = {
    valid
  }

  override def toString: String = {
    s"""
       |valid:$valid
       |msg:$msg
       |desc:$desc
       |----------
       |${childrenResults.map(_.toString).mkString("\n")}
       |""".stripMargin
  }
}
