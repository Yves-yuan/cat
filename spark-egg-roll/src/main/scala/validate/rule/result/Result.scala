package validate.rule.result

trait Result extends Serializable {
  def toString: String

  def isValid: Boolean
}
