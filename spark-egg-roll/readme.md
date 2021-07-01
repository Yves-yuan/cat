# Egg-roll
## About
Egg-roll 包括etl模块，数据校验模块，数据仓库ddl模块

etl模块通过runner_config配置runner列表，runner会串行运行
runner支持扩展，通过继承Runner特质，以及在CatEnv中解析配置生成runner实现runner扩展

数据校验模块通过配置文件定义表中字段的校验类型和校验值，通过RuleFactory生成校验规则，  
在RuleValidateRunner中对目标表进行校验，将校验结果写入数据库

ddl模块包括一些自动化ddl操作，例如自动化建表
## Deploy
+ 使用 maven 进行 compile package 生成jar包 spark-egg-roll-0.5.jar
+ 使用 mvn dependency:copy-dependencies 生成依赖包 dependency 文件夹
+ 进入服务器创建目录，将spark-egg-roll-0.5.jar，dependency 文件夹，拷入目录
+ 创建shell脚本，编辑任务提交命令例如：
```shell
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
```
具体请参考scripts下的脚本。

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