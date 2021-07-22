package runner

import com.twitter.chill.protobuf.ProtobufSerializer
import io.growing.collector.tunnel.protocol.EventDto
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.table.api.FieldExpression
import rule.RuleFactory

import scala.collection.mutable

class EventValidator(jsonConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(cat: CatEnv): Unit = {
    val sql = jsonConfig.get("sql") match {
      case Some(from) => from
      case None => throw new Exception("sql must be assigned")
    }
    val rulePath = jsonConfig.get("rulePath") match {
      case Some(from) => from
      case None => throw new Exception("rulePath must be assigned")
    }
    val sink = jsonConfig.get("sink") match {
      case Some(from) => from
      case None => throw new Exception("sink must be assigned")
    }
    cat.env.getConfig.registerTypeWithKryoSerializer(classOf[EventDto], classOf[ProtobufSerializer])
    val t = cat.tEnv.sqlQuery(sql)
    val rule = RuleFactory.generateRule("customEvent", rulePath)
    val res = cat.tEnv.toDataStream(t).map { x => {
      rule.validate(x)
        .mkString
    }
    }
    cat.tEnv.createTemporaryView(sink, res, $"result")
  }
}
