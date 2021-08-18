{
  "runners": [
    {
      "type": "ch_source",
      "table": "(select * from tmp.user ) as src",
      "url": "jdbc:clickhouse://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}",
      "user": "root",
      "aws_key": "gio_lego_secret",
      "password_key_name": "ch_password",
      "aws_region": "cn-northwest-1",
      "sink": "tmp1"
    },
    {
      "type": "custom_sql",
      "sql": "select userId,dataSourceId,eventType,`timestamp`,map_concat(attributes,scoreAttributes) as attributes from (select * from tmp.user) a left join (select userId,map('label',score) as scoreAttributes model.label) b on a.userId=b.userId",
      "sink": "tmp2"
    },
    {
      "type": "to_cdp_using_http",
      "sql": "select * from tmp2",
      "url": "http://{{ resources.cdp_clickhouse.default.http.addr.split('-')[0] }}-cdp-collect-ec2-1:8080/v3/projects/af0933540dd85550/collect"
    }
  ]
}

