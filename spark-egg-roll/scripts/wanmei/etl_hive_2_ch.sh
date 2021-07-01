source /etc/profile
basedir=`dirname $0`
cd $basedir
table=$1
dt=$2
order_by=$3

if [ ! $order_by ];then
table=model.model_trade_merge_df
dt=20210630
order_by=mo_order_info_customer_id
fi

libs=`echo $basedir/dependency/* |tr ' ' ','`
spark-submit --class bin.EtlRunnerGo \
    --master yarn \
    --deploy-mode client \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --name table_merge \
    --num-executors 10 \
    --jars $libs \
    --conf spark.sql.shuffle.partitions=200 \
    --conf spark.defalut.parallelism=200 \
 $basedir/spark-egg-roll-0.5.jar \
 --runner_config $basedir/etl_config/ch_sink.json \
 --table $table \
 --dt $dt \
 --order_by $order_by \
 --write_parallelism 10
