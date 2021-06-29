package json

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

import java.io.File
import java.net.URI

object JsonReader {
  def readFromFile(path: String): JsonNode = {
    path match {
      case p if (path.startsWith("file://")) =>
        val file = new File(p)
        val objectMapper = new ObjectMapper
        objectMapper.readTree(file)
      case p if (path.startsWith("hdfs://")) =>
        val hdfs = FileSystem.get(URI.create("hdfs://growingFS"), new Configuration())
        val sourceStream = hdfs.open(new Path(path))
        val objectMapper = new ObjectMapper
        objectMapper.readTree(sourceStream)
      case _ => throw new Exception(s"unsupported file format $path")
    }
  }
}
