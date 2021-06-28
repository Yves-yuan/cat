source /etc/profile
basedir=`dirname $0`
cd $basedir
path=$1
dt=$2

if [ ! $path ];then
path="$basedir/rules/validate_rule.json"
dt='20210622'
fi

libs=`echo $basedir/lib/* |tr ' ' ','`
echo $libs
spark-submit --class validate.rule.RuleValidateRunner \
    --master yarn \
    --deploy-mode client \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --num-executors 20 \
    --jars $libs \
/root/yuanyifei/spark-egg-roll-0.5.jar \
 --dt $dt \
 --path $path
