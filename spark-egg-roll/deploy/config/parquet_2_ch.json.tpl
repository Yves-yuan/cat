{
  "runners": [
    {
      "type": "parquet_source",
      "path": "$path",
      "sink": "tmp1"
    },
    {
      "type": "ch_save_table_sink",
      "sql": "select *,now() as cdp_modify_time,'$dt' as pt from tmp1",
      "partition_key": "pt",
      "cluster": "cluster_gio_no_shard",
      "name": "$name",
      "url": "jdbc:clickhouse://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}",
      "user": "root",
      "aws_key": "gio_lego_secret",
      "password_key_name": "ch_password",
      "aws_region": "cn-northwest-1",
      "write_parallelism": "1"
    }
  ]
}
