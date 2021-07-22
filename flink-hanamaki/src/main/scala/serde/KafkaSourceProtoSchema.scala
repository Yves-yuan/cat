package serde

import io.growing.collector.tunnel.protocol.EventDto
import org.apache.flink.api.common.serialization.DeserializationSchema
import org.apache.flink.api.common.typeinfo.{TypeHint, TypeInformation}

class KafkaSourceProtoSchema extends DeserializationSchema[EventDto] {
  override def deserialize(bytes: Array[Byte]): EventDto = {
    EventDto.parseFrom(bytes)
  }

  override def isEndOfStream(t: EventDto): Boolean = {
    false
  }

  override def getProducedType: TypeInformation[EventDto] = {
    TypeInformation.of(new TypeHint[EventDto] {})
  }
}
