# cat
This repository is about data processing using spark and flink

## hanamaki
hanamaki is a validator using flink to validate event in kafka.

## egg-roll
egg-roll is a spark etl project.

## developer
每个developer建立自己的分支，公共特性可以提交到自己分支然后合入master。
如果跟具体业务项目有关的一些配置文件的新增修改，需要新建业务项目分支，提交修改到业务项目分支上。  
业务分支下不建议修改master分支下的内容，因为这样从master同步会冲突，  
建议业务分支在config目录下新增项目相关文件夹，放入文件夹内，业务分支的修改理论上只应该有配置文件。