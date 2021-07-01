source /etc/profile
basedir=`dirname $0`
cd $basedir
libs=`echo $basedir/dependency/* |tr ' ' ','`
echo $libs
from=$1
to=$2
pks=$3
dt=$4
if [ ! $from ];then
from='ods.ods_wechat_mall_event_di'
to='model.model_wechat_mall_event_di'
pks='id,order_id'
dt='20210621'
fi
spark-submit --class  etl.distinct.TblDistinctCh2Ch \
    --master yarn \
    --deploy-mode client \
    --driver-memory 8g \
    --executor-memory 2g \
    --executor-cores 1 \
    --num-executors 50 \
    --jars $libs \
/root/yuanyifei/spark-egg-roll-0.5.jar \
--src $from \
--des $to \
--pks $pks \
--dt $dt

