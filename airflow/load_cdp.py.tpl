#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import os
import pathlib
import sys

sys.path.append(str(pathlib.Path(os.path.abspath(__file__)).parent.parent))
from datetime import timedelta
from growing.function.task_process import *
from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from airflow.operators.bash_operator import BashOperator
from airflow.utils.dates import days_ago

# DAG 定义
# 调度时间 详见官网
# schedule_interval = '30 21 * * *'
schedule_interval = '0 4 * * *'
# 任务开始时间
start_date = days_ago(1)
# DAG参数
default_args = {
    'retry_delay': timedelta(seconds=5),
    'owner': "etl",
    'retries': 1
}
dag_id = 'load_cdp'
tags = ['load_cdp']
dag = DAG(
    dag_id=dag_id,
    schedule_interval=schedule_interval,
    default_args=default_args,
    start_date=start_date,
    tags=tags
)


load_user = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='load_user',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/loadcdp/shells/loadUser.sh ',
    dag=dag
)

user_2_ch = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='user_2_ch',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/loadcdp/shells/userToCh.sh ',
    dag=dag
)

event_load = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='event_load',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/loadcdp/shells/load.sh ',
    dag=dag
)
event_2_ch = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='event_2_ch',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/loadcdp/shells/eventToCh.sh ',
    dag=dag
)

start = BashOperator(
    task_id='start',
    bash_command='echo start',
    dag=dag
)

# pre check
pre_check = PythonOperator(
    task_id='pre_check',
    python_callable=python_operator_default_check_do,
    provide_context=True,
    op_kwargs={"pre_dag_id": "dwd_dim_dws_dag", "check_time_type": "dailyOnDaily", "check_execution_date": "None"}
)

start >> event_load >> event_2_ch
start >> load_user >> user_2_ch
pre_check >> start
