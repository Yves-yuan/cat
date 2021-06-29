package sink

import com.fasterxml.jackson.databind.ObjectMapper
import json.JsonReader
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer
import org.apache.flink.streaming.connectors.kafka.partitioner.FlinkFixedPartitioner
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

import java.net.URI
import java.util.Properties
import scala.io.Source

object KafkaSinkFactory {
  def genSourceCustomEvent(path: String): FlinkKafkaProducer[String] = {
    val reader = Source.fromFile(path).bufferedReader()
    //    val hdfs = FileSystem.get(URI.create("hdfs://growingFS"), new Configuration())
    //    val sourceStream = hdfs.open(new Path(path))
//    val objectMapper = new ObjectMapper
//    val jsonNode = objectMapper.readTree(reader)
    val jsonNode = JsonReader.readFromFile(path)
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
