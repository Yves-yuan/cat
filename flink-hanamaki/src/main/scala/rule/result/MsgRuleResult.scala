package rule.result

case class MsgRuleResult(valid: Boolean, msg: String, desc: String, childrenResults: Array[Result]) extends Result {
  override def isValid: Boolean = {
    valid
  }

  override def toString: String = {
    s"""${childrenResults.map(_.toString).mkString("\n")}""".stripMargin
  }
}
