{
  "runners": [
    {
      "type": "ch_source",
      "table": "(select * from $ch_table where pt='$dt') as src",
      "url": "jdbc:clickhouse://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}",
      "user": "root",
      "aws_key": "gio_lego_secret",
      "password_key_name": "ch_password",
      "aws_region": "cn-northwest-1",
      "sink": "tmp1"
    },
    {
      "type": "ddl_create_table_from_df",
      "sql": "select * from tmp1",
      "table": "$ch_table",
      "partition_key": "dt"
    },
    {
      "type": "hdfs_sink",
      "sql": "select * from tmp1",
      "mode": "overwrite",
      "format": "orc",
      "path": "$path"
    }
  ]
}

