# cat
This repository is about data processing using spark and flink

## hanamaki
hanamaki is a validator using flink to validate event in kafka.

## egg-roll
egg-roll is a spark etl project.

## developer
每个developer建立自己的分支，公共特性可以提交到自己分支然后合入master
如果跟具体业务项目有关的一些配置文件的新增修改，不能直接修改master分支下的内容，  
建议在config目录下新增项目相关文件夹，放入文件夹内，项目特定的修改理论上只应该有配置文件。