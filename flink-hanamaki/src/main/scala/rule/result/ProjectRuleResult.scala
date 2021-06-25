package rule.result

case class ProjectRuleResult(valid: Boolean, projectKey: String, desc: String, childrenResults: Array[Result]) extends Result {
  override def isValid: Boolean = {
    valid
  }

  override def toString: String = {
    val childStr = if (childrenResults.isEmpty) {
      "没有匹配的消息规则"
    } else {
      childrenResults.map(_.toString).mkString("\n")
    }
    s"""
       |----start----
       |消息验证结果:$valid
       |$desc
       |$childStr
       |----end----
       |""".stripMargin
  }
}
