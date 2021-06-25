# 项目地址

项目目前托管在github私有仓库，地址 git@github.com:Yves-yuan/cat.git

# 部署

## 准备工作

* 从flink官网Apache Flink: Downloads下载flink的安装包flink-1.13.1-bin-scala_2.11.tgz，解压后是flink-1.13.1文件夹
* 使用maven对hanamaki进行打包生成flink-hanamaki-0.5.jar
* 使用mvn dependency:copy-dependencies 生成hanamaki的依赖jar包保存在target目录下的dependency目录中
* 一个可以连接使用的cdp计算节点服务器
* 找到源码cat/flink-hanamaki/scrips 目录下的 validator_submit.sh
* 找到源码cat/flink-hanamaki 目录下的 config 目录
* 在config目录中配置自己的校验规则xxx_rule.json

## 开始部署

1. 进入服务器x，创建一个目录hanamaki

2. 将flink-hanamaki-0.5.jar，flink-1.13.1文件夹，validator_submit.sh，config目录，dependency目录，5个东西拷贝到hanamaki目录下

3. 将dependency目录中的jar包全部拷贝到flink-1.13.1/lib 目录中

4. 修改或增加自己的kafka配置文件，在config目录下的source.json中bootstrapServer指定了目标kafka集群地址，topic指定了输入的kafka
   topic，sink.json指定的输出的kafka配置信息，记得创建好配置的topic

5. 修改validator_submit.sh,通过--rule 指定自己的规则配置文件，--source 指定kafka配置文件地址，--sink 指定输出kafka配置文件地址

6. 运行sh validator_submit.sh 启动hanamaki

7. 使用kafka控制台消费器查看hanamaki的校验结果输出 /data/apps/lib/kafka/bin//kafka-console-consumer.sh --topic ${your sink kafka topic}
   --bootstrap-server 10-10-9-148:9092,10-10-9-65:9092,10-10-2-113:9092
   将topic修改为sink.json中配置的topic，地址修改为sink.json中配置的地址。

8. 以上就是部署全部流程

## 校验规则配置方式

hanamaki在启动的时候可以通过命令行参数指定规则配置文件的路径，下面我根据一个配置样例来说明如何配置校验规则。  
克隆下来项目过后，进入cat/flink-hanamaki/config目录，打开样例custom_event_rule.json

```json
{
  "projectKey": "9c51446da23d44a0",
  "messageRules": [
    {
      "eventKey": "homeFlowLocationClick",
      "dataSourceId": "b97ddf7b836326fb",
      "validators": [
        {
          "key": "attributes.channelType_var",
          "operator": "in",
          "value": [
            "安卓",
            "iOS"
          ]
        },
        {
          "key": "attributes.floor_var",
          "operator": "in",
          "value": [
            "职业培训",
            "为您推荐",
            "最新课程"
          ]
        }
      ]
    },
    {
      "eventKey": "tabbarClick",
      "dataSourceId": "b97ddf7b836326fb",
      "validators": [
        {
          "key": "attributes.tabbarName_var",
          "operator": "in",
          "value": [
            "首页",
            "分类",
            "我的"
          ]
        }
      ]
    }
  ]
}
```

+ 每个规则文件指定了校验的projectKey,hanamaki接收到kafka中的消息后只对与配置文件中projectKey相等的消息进行校验。
+ messageRules配置了一系列的消息规则，每条消息规则通过eventKey（埋点事件key）和dataSourceId（数据来源，例如安卓，苹果，网页）进行指定。  
+ 每条rule下面的validators配置了一系列校验器，每个校验器的key指定了校验的字段，例如attributes.channelType_var指定校验attributes中的channelType_var属性。
+ operator指定了校验器的类型，在源代码rule.validator.ValidatorFactory中定义了支持的校验器类型，目前有in，equal，match三种校验器类型。
+ in代表枚举类型，表示属性值必须在配置的枚举列表中。
+ equal代表相等类型，表示属性值必须与配置值相等。
+ match代表正则表达式匹配类型，表示属性值必须与配置的正则表达式匹配。

# 运维
在运行了validator_submit.sh脚本后会向yarn集群提交一个yarn application，  
在控制台退出后，yarn application并不会结束运行，需要手动kill，  
通过yarn application -list 找到所有正在运行的application，  
通过name找到名为“Flink per-job cluster”的application，  
使用yarn application -kill ${appid}命令杀死对应application。