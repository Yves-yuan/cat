# Egg-roll
## about
Egg-roll 包括etl模块，数据校验模块，数据仓库ddl模块

etl模块通过runner_config配置runner列表，runner会串行运行
runner支持扩展，通过继承Runner特质，以及在CatEnv中解析配置生成runner实现runner扩展

数据校验模块通过配置文件定义表中字段的校验类型和校验值，通过RuleFactory生成校验规则，  
在RuleValidateRunner中对目标表进行校验，将校验结果写入数据库

ddl模块包括一些自动化ddl操作，例如自动化建表
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

## ETL 标准化
1.  数据接入hive ods  
    从外部数据库接入hive ods仓库，数据按照dt分区，每日接入
2.  ods表去重生成dwd全量快照表  
    每日对ods表根据id取最新dt去重
3.  主题域内dwd表关联生成dws表，字段保留，名字修改为表名加字段名  
    主体域内dwd表选择关联字段，每日关联后生成dws分区表
4.  dws表根据主键去重生成model表
    dws主题域宽表选择复合主键，根据主键去重取最新dt数据，生成model表
5.  hive 数据回写 clickhouse 数据库
    每日回写分区表到clickhouse 数据库