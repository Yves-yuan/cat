#! /bin/bash
dt=$1
if [ ! $dt ];then
dt=`date +%Y%m%d -d"-16hours"`
fi
echo dt $dt
basedir=`dirname $0`

sql1=" \
alter table meta.tables_information_table drop partition '$dt' \
"
sql2="truncate table meta.tech_meta_table"
echo "$sql1" | curl 'http://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}' -d @-
echo "$sql2" | curl 'http://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}' -d @-
libs=`echo $basedir/../dependency/* |tr ' ' ','`
/data/apps/lib/spark/bin/spark-submit --class bin.EtlRunnerGo \
    --master yarn \
    --deploy-mode client \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --name table_merge \
    --num-executors 1 \
    --jars $libs \
    --conf spark.sql.shuffle.partitions=20 \
    --conf spark.default.parallelism=20 \
 $basedir/../bin/spark-egg-roll-0.5.jar \
 --runner_config $basedir/../config/metadata.json \
 --pt $dt
