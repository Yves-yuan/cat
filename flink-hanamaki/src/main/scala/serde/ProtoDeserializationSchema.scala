package serde

import io.growing.validator.tunnel.protocol.CustomEventMessage
import org.apache.flink.api.common.serialization.DeserializationSchema
import org.apache.flink.api.common.typeinfo.{TypeHint, TypeInformation}

class ProtoDeserializationSchema extends DeserializationSchema[CustomEventMessage] {

  override def deserialize(bytes: Array[Byte]): CustomEventMessage = {
    CustomEventMessage.parseFrom(bytes)
  }

  override def isEndOfStream(t: CustomEventMessage): Boolean = {
    false
  }

  override def getProducedType: TypeInformation[CustomEventMessage] = TypeInformation.of(new TypeHint[CustomEventMessage] {})
}
