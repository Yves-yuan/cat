package rule

import com.google.protobuf.GeneratedMessageV3
import rule.result.Result

trait Rule extends Serializable {
  def validate(data: GeneratedMessageV3): Option[Result]
}
