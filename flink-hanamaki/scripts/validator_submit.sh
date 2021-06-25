source /etc/profile
basepath=`dirname $0`
cd $basepath

export HADOOP_CLASSPATH=`hadoop classpath`
$basepath/flink-1.13.1/bin/flink run  \
 --target yarn-per-job \
 -ynm "hanamaki" \
 -c bin.Hanamaki \
 $basepath/flink-hanamaki-0.5.jar \
--rule $basepath/config/custom_event_rule.json \
--source $basepath/config/source.json \
--sink $basepath/config/sink.json
#--rule /user/root/yuanyifei/validator/config/custom_event_rule.json \
#--source /user/root/yuanyifei/validator/config/source.json \
#--sink /user/root/yuanyifei/validator/config/sink.json
