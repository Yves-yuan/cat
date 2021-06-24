package rule.result

trait Result {
  def toString: String

  def isValid: Boolean
}
