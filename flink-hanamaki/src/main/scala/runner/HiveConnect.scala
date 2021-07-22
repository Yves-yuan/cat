package runner

import org.apache.flink.table.api.SqlDialect
import org.apache.flink.table.catalog.hive.HiveCatalog

import scala.collection.mutable

class HiveConnect(jsonConfig: mutable.HashMap[String, String]) extends Runner {
  override def run(cat: CatEnv): Unit = {
    val catalogName = jsonConfig.get("catalogName") match {
      case Some(d) => d
      case None => throw new Exception("catalogName must be assigned in config")
    }
    val defaultDatabase = jsonConfig.get("defaultDatabase") match {
      case Some(d) => d
      case None => null
    }
    val hiveConfDir = jsonConfig.get("hiveConfDir") match {
      case Some(d) => d
      case None => throw new Exception("hiveConfDir must be assigned in config")
    }
    //    val catalogName            = "myhive"
    //    val defaultDatabase = "mydatabase"
    //    val hiveConfDir     = "/opt/hive-conf"
    val hive = new HiveCatalog(catalogName, defaultDatabase, hiveConfDir)
    cat.tEnv.registerCatalog("huajuan_hive", hive)
    cat.tEnv.useCatalog("huajuan_hive")
    cat.tEnv.getConfig.setSqlDialect(SqlDialect.HIVE)
  }
}
