package source

import com.fasterxml.jackson.databind.ObjectMapper
import io.growing.collector.tunnel.protocol.EventDto
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import serde.ProtoDeserializationSchema

import java.util.Properties
import scala.io.Source

object KafkaSourceFactory {
  def genSourceCustomEvent(path: String, env: StreamExecutionEnvironment): DataStream[EventDto] = {
    //    val hdfs = FileSystem.get(URI.create("hdfs://growingFS"),new Configuration())
    //    val sourceStream = hdfs.open(new Path(path))
    val file = Source.fromFile(path)
    val reader = file.bufferedReader()
    val objectMapper = new ObjectMapper
    val jsonNode = objectMapper.readTree(reader)
    val topic = jsonNode.get("topic").asText()
    val bootstrapServer = jsonNode.get("bootstrapServer").asText()

    val properties = new Properties()
    properties.setProperty("bootstrap.servers", bootstrapServer)
    properties.setProperty("group.id", "huajuan")
    val stream = env
      .addSource(new FlinkKafkaConsumer[EventDto](topic, new ProtoDeserializationSchema(), properties))
    stream
  }
}
