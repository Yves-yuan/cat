package sink

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer
import org.apache.flink.streaming.connectors.kafka.partitioner.FlinkFixedPartitioner
import source.KafkaSourceFactory

import java.util.Properties
import scala.reflect.io.{File, Path}

object KafkaSinkFactory {
  def genSourceCustomEvent(sinkKafka: String): FlinkKafkaProducer[String] = {
    val path = KafkaSourceFactory.getClass.getClassLoader.getResource(sinkKafka).getFile
    val reader = File.apply(Path.apply(path)).bufferedReader()
    val objectMapper = new ObjectMapper
    val jsonNode = objectMapper.readTree(reader)
    val topic = jsonNode.get("topic").asText()
    val bootstrapServer = jsonNode.get("bootstrapServer").asText()
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", bootstrapServer)
    val myProducer = new FlinkKafkaProducer(
      topic,
      new SimpleStringSchema(),
      properties,
      new FlinkFixedPartitioner[String](),
      FlinkKafkaProducer.Semantic.EXACTLY_ONCE,
      FlinkKafkaProducer.DEFAULT_KAFKA_PRODUCERS_POOL_SIZE
    )
    myProducer
  }
}
