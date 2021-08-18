import json
import sys
import time

import requests

from clickhouse_driver import Client
class TimeOutErr(Exception):
        print("同步校验数据时间过长，结束任务，请自行去clickhouse手动检查数据")
        pass

#clickhouse
host='sys.argv[1]'
port='9000'
userName='root'
passWord='sys.argv[2]'
databaseName='olap'
end_receive_timeout = 5
sql_truncate2="truncate table olap.event_load on cluster cluster_gio_no_shard"
sql=f"select count(1) from olap.event_load where event_key in ('dwsChannelSalesMain','dwsChannelSalesItem','dwdCampaignPartInApply','dwdCampaignPartInJoin','dwdAgreementCouponVer','dwdAgreementPointOrder')"
cli=Client(host=sys.argv[1],port=port,user=userName,password=sys.argv[2],database=databaseName,send_receive_timeout=end_receive_timeout)
cli.execute(sql_truncate2)
print("数据清理中")
time.sleep(10)
countsTotalHis = cli.execute(sql)
print(f"目前数据库中event_load数据现有的数据量为{countsTotalHis}")
datas=[]
counts=0
num=0
# total=len(open("/Users/mhx/Downloads/events.json","r").readlines())
with open(sys.argv[3],"r") as f:
    for line in f:
        counts+=1
        num+=1
        datas.append(line)
        if(counts%1000==0):
            #一条
            # values_json=json.dumps(datas).replace("\\","").replace("[\"","[").replace("n\"]","]")
            # values_json=json.dumps(datas).replace("\\","").replace("[\"","[").replace("}n\", \"","},").replace("n\"]","]")
            values_json=json.dumps(datas,ensure_ascii=False).replace("\\","").replace("[\"","[").replace("}n\", \"","},").replace("n\"]","]").replace("\"attributes\":\"{\"","\"attributes\":{\"").replace("\"}\",\"eventType\"","\"},\"eventType\"")
            # print(values_json)
            # requests库提交数据进行post请求
            req = requests.post(sys.argv[4],data=values_json.encode("utf-8"))
            datas.clear()
            print(f"当前批次成功推送{counts}条数据")

# values_json = json.dumps(datas).replace("\\", "").replace("[\"", "[").replace("}n\", \"", "},").replace("n\"]", "]")
values_json=json.dumps(datas,ensure_ascii=False).replace("\\","").replace("[\"","[").replace("}n\", \"","},").replace("n\"]","]").replace("\"attributes\":\"{\"","\"attributes\":{\"").replace("\"}\",\"eventType\"","\"},\"eventType\"")
# print(values_json)
# requests库提交数据进行post请求
req = requests.post(sys.argv[4],data=values_json.encode("utf-8"))
print(f"当前批次成功推送{len(datas)}条数据")
print(f"本次程序一共成功推送{num}")
print("开始进行连接clickhouse进行数据同步校验-------------------------------------------------------------------------------")
trues=0
while True:
    trues+=1
    print(f"第{trues}次核验数据中")
    time.sleep(300)
    countsTotalNow = cli.execute(sql)
    if (num+countsTotalHis[0][0] - int(countsTotalNow[0][0]) <=10):
        print("恭喜你！！！数据同步成功！！！")
        break
    if(trues>=36):
        raise TimeOutErr

for  i in range(1,61):
    if(12<i<=24):
        year="2018"
        month=abs(i-12*1)
        if(month<10):
            month_small = f"0{month}"
            sql = f"insert into event_local select * from event_load where toStartOfMonth(event_time)='{year}-{month_small}-01'"
            cli.execute(sql)
        else:
            sql = f"insert into event_local select * from event_load where toStartOfMonth(event_time)='{year}-{month}-01'"
            #执行插入
            cli.execute(sql)
    elif(24<i<=36):
        year="2019"
        month=abs(i-12*2)
        if(month<10):
            month_small = f"0{month}"
            sql = f"insert into event_local select * from event_load where toStartOfMonth(event_time)='{year}-{month_small}-01'"
            cli.execute(sql)
        else:
            sql = f"insert into event_local select * from event_load where toStartOfMonth(event_time)='{year}-{month}-01'"
            #执行插入
            cli.execute(sql)
    elif(36<i<=48):
        year="2020"
        month=abs(i-12*3)
        if(month<10):
            month_small = f"0{month}"
            sql = f"insert into event_local select * from event_load where toStartOfMonth(event_time)='{year}-{month_small}-01'"
            cli.execute(sql)
        else:
            sql = f"insert into event_local select * from event_load where toStartOfMonth(event_time)='{year}-{month}-01'"
            #执行插入
            cli.execute(sql)
    elif(48<i<=60):
        year="2021"
        month=abs(i-12*4)
        if(month<10):
            month_small = f"0{month}"
            sql = f"insert into event_local select * from event_load where toStartOfMonth(event_time)='{year}-{month_small}-01'"
            cli.execute(sql)
        else:
            sql = f"insert into event_local select * from event_load where toStartOfMonth(event_time)='{year}-{month}-01'"
            #执行插入
            cli.execute(sql)
    elif(i<=12):
        year="2017"
        fu_time="201801"
        month=i
        if(month<10):
            month_small = f"0{month}"
            sql = f"insert into event_local select * from event_load where toStartOfMonth(event_time)='{year}-{month_small}-01'"
            cli.execute(sql)
        else:
            sql = f"insert into event_local select * from event_load where toStartOfMonth(event_time)='{year}-{month}-01'"
            #执行插入
            cli.execute(sql)
