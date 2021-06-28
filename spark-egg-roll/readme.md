# egg roll
## about
egg roll 主要包括数据etl，数据仓库ddl，数据校验等功能
## deploy
+ 使用 maven 进行 compile package 生成jar包 spark-egg-roll-0.5.jar
+ 使用 mvn dependency:copy-dependencies 生成依赖包 dependency 文件夹
+ 进入服务器创建目录，将spark-egg-roll-0.5.jar，dependency 文件夹，拷入目录
+ 创建shell脚本，编辑任务提交命令例如：
```shell
source /etc/profile
dt=$1
basedir=`dirname $0`
cd $basedir
libs=`echo $basedir/dependency/* |tr ' ' ','`
echo $libs
spark-submit --class validate.Uniqueness \
    --master yarn \
    --deploy-mode client \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --num-executors 4 \
    --jars $libs \
/root/yuanyifei/spark-egg-roll-0.5.jar --dt $dt \
ods_mo_order_info_di,id \
ods_mo_order_info_di,customer_id
```
+ 运行以上 shell 即可提交 validate.Uniqueness 任务