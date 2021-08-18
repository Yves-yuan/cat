import json
import sys
import time

import requests



datas=[]
counts=0
num=0
with open(sys.argv[1],"r") as f:
    for line in f:
        counts+=1
        num+=1
        datas.append(line)
        if(counts%1000==0):
            #一条
            # values_json=json.dumps(datas).replace("\\","").replace("[\"","[").replace("n\"]","]")
            values_json=json.dumps(datas,ensure_ascii=False).replace("\\","").replace("[\"","[").replace("}n\", \"","},").replace("n\"]","]").replace("\"attributes\":\"{\"","\"attributes\":{\"").replace("\"}\",\"eventType\"","\"},\"eventType\"")
            # print(values_json)
            # requests库提交数据进行post请求
            req = requests.post(sys.argv[2],data=values_json.encode("utf-8"))
            datas.clear()
            print(f"当前批次成功推送{counts}条数据")

values_json=json.dumps(datas,ensure_ascii=False).replace("\\","").replace("[\"","[").replace("}n\", \"","},").replace("n\"]","]").replace("\"attributes\":\"{\"","\"attributes\":{\"").replace("\"}\",\"eventType\"","\"},\"eventType\"")
# requests库提交数据进行post请求
req = requests.post(sys.argv[2],data=values_json.encode("utf-8"))
print(f"当前批次成功推送{len(datas)}条数据")
print(f"本次程序一共成功推送{num}")
