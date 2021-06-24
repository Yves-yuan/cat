package source

import com.fasterxml.jackson.databind.ObjectMapper
import io.growing.validator.tunnel.protocol.CustomEventMessage
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import serde.ProtoDeserializationSchema

import java.util.Properties
import scala.reflect.io.{File, Path}

object KafkaSourceFactory {
  def genSourceCustomEvent(sourceKafka: String, env: StreamExecutionEnvironment): DataStream[CustomEventMessage] = {
    val path = KafkaSourceFactory.getClass.getClassLoader.getResource(sourceKafka).getFile
    val reader = File.apply(Path.apply(path)).bufferedReader()
    val objectMapper = new ObjectMapper
    val jsonNode = objectMapper.readTree(reader)
    val topic = jsonNode.get("topic").asText()
    val bootstrapServer = jsonNode.get("bootstrapServer").asText()

    val properties = new Properties()
    properties.setProperty("bootstrap.servers", bootstrapServer)
    properties.setProperty("group.id", "huajuan")
    val stream = env
      .addSource(new FlinkKafkaConsumer[CustomEventMessage](topic, new ProtoDeserializationSchema(), properties))
    stream
  }
}
