#! /bin/bash
db_table=$1
dt=$2
if [ ! $dt ];then
dt=`date +%Y%m%d -d"-16hours"`
fi
echo dt $dt
# dt from airflow is utc time,so i add 8 to dt.
echo $dt
echo now is `date`
basedir=`dirname $0`
cd $basedir
db=`echo $db_table | cut  -d "." -f1`
table=`echo $db_table | cut  -d "." -f2`

libs=`echo $basedir/../dependency/* |tr ' ' ','`
/data/apps/lib/spark/bin/spark-submit --class bin.EtlRunnerGo \
    --master yarn \
    --deploy-mode client \
    --driver-memory 2g \
    --executor-memory 2g \
    --executor-cores 1 \
    --name load_ch_from_parquet \
    --num-executors 1 \
    --jars $libs \
    --conf spark.sql.shuffle.partitions=20 \
    --conf spark.default.parallelism=20 \
 $basedir/../bin/spark-egg-roll-0.5.jar \
 --runner_config $basedir/../config/ch_2_hive.json \
 --ch_table $db_table  \
 --hive_table $db_table \
 --dt $dt
