package rule.result

case class ProjectRuleResult(valid: Boolean, projectKey: String, desc: String, childrenResults: Array[Result]) extends Result {
  override def isValid: Boolean = {
    valid
  }

  override def toString: String = {
    s"""
       |valid:$valid
       |projectKey:$projectKey
       |desc:$desc
       |----------
       |${childrenResults.map(_.toString).mkString("\n")}
       |""".stripMargin
  }
}
