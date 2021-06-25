package serde

import io.growing.collector.tunnel.protocol.EventDto
import org.apache.flink.api.common.typeinfo.{TypeHint, TypeInformation}
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema
import org.apache.kafka.clients.consumer.ConsumerRecord

class ProtoDeserializationSchema extends KafkaDeserializationSchema[EventDto] {


  override def isEndOfStream(t: EventDto): Boolean = {
    false
  }
  override def getProducedType: TypeInformation[EventDto] = TypeInformation.of(new TypeHint[EventDto] {})

  override def deserialize(consumerRecord: ConsumerRecord[Array[Byte], Array[Byte]]): EventDto = {
    EventDto.parseFrom(consumerRecord.value())
  }
}
