{
  "runners": [
    {
      "type": "ch_source",
      "table": "(select * from $ch_table ) as src",
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
      "table": "$hive_table",
      "partition_key": "dt"
    },
    {
      "type": "repartition",
      "sql": "select * from tmp1",
      "partition_key": "",
      "sink": "tmp2"
    },
    {
      "type": "custom_sql",
      "sql": "truncate table $hive_table ",
      "sink": "null"
    },
    {
      "type": "custom_sql",
      "sql": "insert overwrite table $hive_table partition(dt=$dt) select * from tmp2",
      "sink": "null"
    }
  ]
}

