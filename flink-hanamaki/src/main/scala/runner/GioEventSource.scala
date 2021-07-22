package runner

import com.twitter.chill.protobuf.ProtobufSerializer
import io.growing.collector.tunnel.protocol.EventDto
import org.apache.flink.api.common.eventtime.{TimestampAssigner, TimestampAssignerSupplier, WatermarkStrategy}
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema
import org.apache.flink.table.api.{DataTypes, Schema}
import serde.ProtoEventDeserializationSchema

import java.time.Duration
import java.util.Properties
import scala.collection.mutable

class GioEventSource(jsonConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(cat: CatEnv): Unit = {
    cat.env.getConfig.registerTypeWithKryoSerializer(classOf[EventDto], classOf[ProtobufSerializer])
    val topic = jsonConfig.get("topic") match {
      case Some(d) => d
      case None => throw new Exception("topic must be assigned in config")
    }
    val bootstrapServer = jsonConfig.get("bootstrapServer") match {
      case Some(d) => d
      case None => throw new Exception("bootstrapServer must be assigned in config")
    }
    val sink = jsonConfig.get("sink") match {
      case Some(d) => d
      case None => throw new Exception("sink must be assigned in config")
    }
    val delaySecond = jsonConfig.get("delaySecond") match {
      case Some(d) => d.toLong
      case None => throw new Exception("delaySecond must be assigned in config")
    }
    val bounded = jsonConfig.get("bounded") match {
      case Some(d) => d.toBoolean
      case None => false
    }
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", bootstrapServer)
    properties.setProperty("group.id", "huajuan")
    properties.setProperty("scan.startup.mode", "earliest-offset")
    val s = new ProtoEventDeserializationSchema()
    val builder = KafkaSource.builder()
      .setBootstrapServers(bootstrapServer)
      .setGroupId("huajuan")
      .setTopics(topic)
      .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(s))
    if (bounded) {
      builder.setUnbounded(OffsetsInitializer.latest())
    }
    val kafkaSource = builder.build()
    val stream = cat.env
      .fromSource(kafkaSource, WatermarkStrategy
        .forBoundedOutOfOrderness(Duration.ofSeconds(delaySecond))
        .withTimestampAssigner(new TimestampAssignerSupplier[EventDto]{
          override def createTimestampAssigner(context: TimestampAssignerSupplier.Context): TimestampAssigner[EventDto] = {
            new TimestampAssigner[EventDto] {
              override def extractTimestamp(element: EventDto, recordTimestamp: Long): Long = {
                element.getSendTime
              }
            }
          }
        })
        , "kafka")
      .map(ed => {
        (ed.getProjectKey, ed.getAttributesMap)
      })
    val schema = Schema.newBuilder()
      .column("_1", DataTypes.STRING())
      .column("_2", DataTypes.MAP(DataTypes.STRING(), DataTypes.STRING()))
      .build()
    val table1 = cat.tEnv.fromDataStream(stream, schema).as("project_key", "attrs")
    table1.printSchema()
    cat.tEnv.createTemporaryView(sink, table1)
  }
}
