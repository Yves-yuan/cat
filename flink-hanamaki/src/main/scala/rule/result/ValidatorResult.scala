package rule.result

case class ValidatorResult(valid: Boolean,s:String) extends Result {
  override def toString: String = {
    s
  }

  override def isValid: Boolean = valid
}
