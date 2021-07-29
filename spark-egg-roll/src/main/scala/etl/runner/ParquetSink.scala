package etl.runner

import etl.env.CatEnv

import scala.collection.mutable

class ParquetSink (jsonConfig: mutable.HashMap[String, String]) extends Runner{
  override def run(env: CatEnv): Unit = {

  }
}
