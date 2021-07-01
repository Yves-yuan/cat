source /etc/profile
basedir=`dirname $0`
cd $basedir
dt=$1
last_dt=$2
inc_table=$3
full_table=$4
path="$basedir/etl_config/union_and_distinct.json"
if [ ! $full_table ];then
path="$basedir/etl_config/union_and_distinct.json"
dt='20210522'
last_dt='20210521'
inc_table='ods.ods_mr_crm_customer_di'
full_table='dwd.dwd_mr_crm_customer_df'
fi

libs=`echo $basedir/dependency/* |tr ' ' ','`
spark-submit --class bin.EtlRunnerGo \
    --master yarn \
    --deploy-mode client \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --num-executors 20 \
    --jars $libs \
 $basedir/spark-egg-roll-0.5.jar \
 --runner_config $path \
 --dt $dt \
 --last_dt $last_dt \
 --inc_table $inc_table \
 --full_table $full_table
