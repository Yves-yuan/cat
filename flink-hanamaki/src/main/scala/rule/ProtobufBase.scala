package rule

import com.google.protobuf.Descriptors.FieldDescriptor
import com.google.protobuf.GeneratedMessageV3

trait ProtobufBase {
  def findField(name: String, data: GeneratedMessageV3): Option[FieldDescriptor] = {
    val fields = data.getAllFields.keySet()
    import scala.collection.JavaConversions._
    fields.find(x => {
      x.getName == name
    })
  }

  def getField(name: String, data: GeneratedMessageV3): AnyRef = {
    findField(name, data) match {
      case None => throw new Exception("no name " + name + " found")
      case Some(x) => data.getField(x)
    }
  }

  def getFiledType(name: String, data: GeneratedMessageV3): FieldDescriptor.Type = {
    findField(name, data) match {
      case None => throw new Exception("no name " + name + " found")
      case Some(x) => x.getType
    }
  }

  def getFieldAndType(name: String, data: GeneratedMessageV3): (AnyRef, FieldDescriptor.Type) = {
    findField(name, data) match {
      case None => throw new Exception("no name " + name + " found")
      case Some(x) => (data.getField(x), x.getType)
    }
  }
}
