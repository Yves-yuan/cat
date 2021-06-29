package input

object InputPool {
  var inputMap: Option[Map[Symbol, Any]] = None

  def get(): Map[Symbol, Any] = {
    inputMap.getOrElse(Map.empty)
  }

  def getOrElse(key: Symbol, other: Any): Any = {
    if (inputMap.isDefined) {
      inputMap.getOrElse(key, other)
    } else {
      other
    }
  }

  def contains(key: Symbol): Boolean = {
    if (inputMap.isDefined) {
      inputMap.contains(key)
    } else {
      false
    }
  }
}
