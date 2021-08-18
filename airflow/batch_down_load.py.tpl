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

#DAG参数
default_args = {
        'retry_delay' : timedelta(seconds=5),
        'owner' : "etl",
        'retries' : 1
}


# DAG 定义
#调度时间 详见官网
schedule_interval = '0 4 * * *'
#DAG名称与webUI界面显示的名称相同
dag_id = "batch_down_load"
#人物开始时间
start_date=days_ago(1)
tags = ['batch_down_load']
dag = DAG(
        schedule_interval = schedule_interval ,
        dag_id = dag_id ,
        default_args = default_args ,
        start_date = start_date ,
        tags = tags
)


#Task 定义
batch_down_load = BashOperator(
        #唯一标识必 必须有的参数
        task_id='batch_down_load',
        #具体的shell执行语句  必须有的参数
        bash_command="sh {{ app_deploy_dir }}/batch/shells/batch-load-ods.sh {{ '{% if dag_run.conf[\'dt\'] is defined %}{{ dag_run.conf[\'dt\'] }}{% endif %}' }}",
        dag=dag
)


# Task 依赖关系
batch_down_load