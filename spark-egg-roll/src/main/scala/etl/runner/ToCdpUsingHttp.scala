package etl.runner

import etl.env.CatEnv
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.{PostMethod, StringRequestEntity}

import scala.collection.mutable

class ToCdpUsingHttp(jsonConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(env: CatEnv): Unit = {
    val sql = jsonConfig.get("sql") match {
      case Some(from) => from
      case None => throw new Exception("sql must be assigned")
    }
    val url = jsonConfig.get("url") match {
      case Some(from) => from
      case None => throw new Exception("url must be assigned")
    }
    val df = env.spark.sql(sql)
    df.toJSON
      .repartition(1)
      .foreachPartition(i => {
        var cnt = 0
        val ds = scala.collection.mutable.ArrayBuffer.newBuilder[String]

        i.foreach(d => {
          ds.+=(d)
          cnt += 1

          if (cnt % 1000 == 0 || !i.hasNext) {
            val method = new PostMethod(url)
            val jsonData = ds.result().mkString("[", ",", "]")
            val stringEntity = new StringRequestEntity(jsonData, null, "utf-8")
            method.setRequestEntity(stringEntity)
            val client = new HttpClient()
            client.executeMethod(method)
            ds.clear()
          }
        })
      })
  }
}
