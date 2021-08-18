{
  "runners": [
    {
      "type": "ch_source",
      "table": "(select * from system.columns ) as src",
      "url": "jdbc:clickhouse://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}",
      "user": "root",
      "aws_key": "gio_lego_secret",
      "password_key_name": "ch_password",
      "aws_region": "cn-northwest-1",
      "sink": "tmp1"
    },
    {
      "type": "custom_sql",
      "sql": "select database,table,name,now() as create_date from tmp1",
      "sink": "tmp2"
    },
    {
      "type": "ch_save_table_sink",
      "sql": "select * from tmp2",
      "name": "meta.tech_meta_table",
      "order_by": "(database,table)",
      "url": "jdbc:clickhouse://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}",
      "user": "root",
      "aws_key": "gio_lego_secret",
      "password_key_name": "ch_password",
      "aws_region": "cn-northwest-1"
    },
    {
      "type": "ch_source",
      "table": "(select database,table,rows,data_compressed_bytes,data_uncompressed_bytes from system.parts ) as src",
      "url": "jdbc:clickhouse://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}",
      "user": "root",
      "aws_key": "gio_lego_secret",
      "password_key_name": "ch_password",
      "aws_region": "cn-northwest-1",
      "sink": "tmp3"
    },
    {
      "type": "ch_save_table_sink",
      "sql": "select database,table,sum(rows) as size,sum(data_compressed_bytes) as data_compressed_bytes,sum(data_uncompressed_bytes) as data_uncompressed_bytes,'$pt' as pt from tmp3 group by database,table",
      "name": "meta.tables_information_table",
      "partition_key": "pt",
      "order_by": "(database,table)",
      "url": "jdbc:clickhouse://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}",
      "user": "root",
      "aws_key": "gio_lego_secret",
      "password_key_name": "ch_password",
      "aws_region": "cn-northwest-1"
    }
  ]
}
