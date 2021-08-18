#! /bin/bash
file=$1
table=$2
dt=$3
if [ ! $table ];then
echo please input table
fi
if [ ! $dt ];then
dt=`date +%Y%m%d -d"-16hours"`
fi
echo dt=$dt
# dt from airflow is utc time,so i add 8 to dt.
echo $dt
echo now is `date`
basedir=`dirname $0`

sh $basedir/ch-drop-partition.sh $table $dt
libs=`echo $basedir/../dependency/* |tr ' ' ','`
/data/apps/lib/spark/bin/spark-submit --class bin.EtlRunnerGo \
    --master yarn \
    --deploy-mode client \
    --driver-memory 2g \
    --executor-memory 2g \
    --executor-cores 1 \
    --name load_ch_from_parquet \
    --num-executors 5 \
    --jars $libs \
    --conf spark.sql.shuffle.partitions=20 \
    --conf spark.default.parallelism=20 \
 $basedir/../bin/spark-egg-roll-0.5.jar \
 --runner_config $basedir/../config/parquet_2_ch.json \
 --path /data/redshift-data/$file/$dt/*.parquet \
 --name $table \
 --dt $dt

table_count=`echo "select count(*) from ods.ods_$table" | curl 'http://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}' --data-binary @-`
echo count in table=$table_count
file_count=`/data/apps/lib/hadoop/bin/hadoop fs -cat /data/redshift-data/$file/$dt/*_manifest | grep -E ".*record_count\": ([0-9]*)$" | sed -r "s/.*record_count\": ([0-9]*)$/\1/"`
echo count in file=$file_count
if [ $table_count -eq $file_count ];then
flag='ok'
else
flag='bad'
fi
echo "insert into meta.table_size_validate values ('file_$file',$file_count,'table_$table',$table_count,'$flag','$dt')" \
| curl 'http://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}/' --data-binary @-
echo "optimize table meta.table_size_validate" \
| curl 'http://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}/' --data-binary @-