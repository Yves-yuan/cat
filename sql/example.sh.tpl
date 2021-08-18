#!/bin/bash
echo '开始执行sql文件'
dt=$1
if [ ! $dt ];then
dt=`date +%Y%m%d -d"-16hours"`
fi
/data/apps/lib/cdp-clickhouse/bin/clickhouse client  --param_dt=$dt -h {{ resources.cdp_clickhouse.default.http.addr.split(':')[0] }}  --send_logs_level=trace  --multiquery < {{ app_deploy_dir }}/sql/example.sql
result_code=$?
echo '执行结束'
exit $result_code