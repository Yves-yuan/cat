#! /bin/bash
basedir=`dirname $0`


table=$1
dt=$2

if [ ! $dt ];then
echo input $dt
exit
fi

sql="ALTER TABLE $table DROP PARTITION '$dt'"
password=`sh get-secret-new.sh`
echo "$sql" | curl 'http://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}' -d @-
