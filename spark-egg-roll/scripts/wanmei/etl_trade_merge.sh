source /etc/profile
basedir=`dirname $0`
cd $basedir
dt=$1
if [ ! dt ];then
  echo input dt
  exit
fi
libs=`echo $basedir/dependency/* |tr ' ' ','`
spark-submit --class bin.EtlRunnerGo \
    --master yarn \
    --deploy-mode client \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --name table_merge \
    --num-executors 100 \
    --jars $libs \
    --conf spark.sql.shuffle.partitions=200 \
    --conf spark.defalut.parallelism=200 \
 $basedir/spark-egg-roll-0.5.jar \
 --runner_config $basedir/etl_config/merge_trade.json \
 --dt $dt
