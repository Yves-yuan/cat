source /etc/profile
basedir=`dirname $0`
cd $basedir
from=$1
to=$2
#if [ ! $path ];then
#from=ods.ods_mo_order_detail_di
#to=dwd.dwd_mo_order_detail_df
#fi
libs=`echo $basedir/dependency/* |tr ' ' ','`
spark-submit --class ddl.CreateTableLike \
    --master yarn \
    --deploy-mode client \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --num-executors 1 \
    --jars $libs \
 $basedir/spark-egg-roll-0.5.jar \
 --from $from \
 --to $to 
