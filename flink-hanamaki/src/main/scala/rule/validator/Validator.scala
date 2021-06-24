package rule.validator

import com.google.protobuf.GeneratedMessageV3
import rule.result.Result

trait Validator extends Serializable {
  def validate(data: GeneratedMessageV3): Result

  def toString: String
}
