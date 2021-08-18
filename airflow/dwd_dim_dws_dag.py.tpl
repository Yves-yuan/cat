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
dag_id = 'dwd_dim_dws_dag'
tags = ['dwd_dim_dws_dag']
dag = DAG(
    dag_id=dag_id,
    schedule_interval=schedule_interval,
    default_args=default_args,
    start_date=start_date,
    tags=tags
)

# Task 定义
user = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='user',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/user.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dim_party_user_all = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dim_party_user_all',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dim_party_user_all.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dim_party_user = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dim_party_user',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dim_party_user.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dim_campaign_info = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dim_campaign_info',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dim_campaign_info.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dim_agreement_coupon_info = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dim_agreement_coupon_info',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dim_agreement_coupon_info.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dwd_agreement_user_sign = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dwd_agreement_user_sign',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dwd_agreement_user_sign.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dim_party_shop = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dim_party_shop',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dim_party_shop.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dim_party_number_kid = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dim_party_number_kid',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dim_party_number_kid.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dim_item_sku = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dim_item_sku',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dim_item_sku.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)



dwd_sales_order_detail_flow = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dwd_sales_order_detail_flow',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dwd_sales_order_detail_flow.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dwd_sales_order_return_detail = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dwd_sales_order_return_detail',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dwd_sales_order_return_detail.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dwd_sales_order_main_flow = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dwd_sales_order_main_flow',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dwd_sales_order_main_flow.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dwd_sales_order_return_main = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dwd_sales_order_return_main',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dwd_sales_order_return_main.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dws_channel_sales_item = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dws_channel_sales_item',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dws_channel_sales_item.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dws_channel_sales_main = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dws_channel_sales_main',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dws_channel_sales_main.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

event = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='event',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/event.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dwd_agreement_coupon_ver = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dwd_agreement_coupon_ver',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dwd_agreement_coupon_ver.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dwd_agreement_point_order = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dwd_agreement_point_order',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dwd_agreement_point_order.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

dwd_campaign_part_in = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='dwd_campaign_part_in',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/sql/dwd_campaign_part_in.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)
batch_ch_2_hive = BashOperator(
    # 唯一标识必 必须有的参数
    task_id='batch_ch_2_hive',
    # 具体的shell执行语句  必须有的参数
    bash_command='sh {{ app_deploy_dir }}/batch/shells/batch-ch-2-hive.sh {{ '{% if dag_run.conf["dt"] is defined %}{{ dag_run.conf["dt"] }}{% endif %}' }}',
    dag=dag
)

# pre check
pre_check = PythonOperator(
    task_id='pre_check',
    python_callable=python_operator_default_check_do,
    provide_context=True,
    op_kwargs={"pre_dag_id": "batch_down_load", "check_time_type": "dailyOnDaily", "check_execution_date": "None"}
)

start = BashOperator(
    task_id='start',
    bash_command='echo start',
    dag=dag
)

end = BashOperator(
    task_id='end',
    bash_command='echo end',
    dag=dag
)
pre_check >> start
start >> dim_agreement_coupon_info
start >> dim_campaign_info
start >> dim_party_number_kid
start >> dim_party_shop
start >> dwd_agreement_user_sign
start >> dwd_sales_order_return_detail
start >> dwd_sales_order_return_main
start >> dim_item_sku
start >> dwd_sales_order_detail_flow
start >> dwd_agreement_coupon_ver
start >> dwd_agreement_point_order
start >> dwd_campaign_part_in
start >> dwd_sales_order_main_flow
start >> dim_party_user
dim_party_user >> dim_party_user_all
dim_party_user_all >> user
dws_channel_sales_main >> dim_party_user_all
dwd_sales_order_main_flow >> dws_channel_sales_main
dwd_sales_order_detail_flow >> dws_channel_sales_item
dim_item_sku >> dws_channel_sales_item
dws_channel_sales_main >> event
dws_channel_sales_item >> event
dwd_agreement_coupon_ver >> event
dwd_agreement_point_order >> event
dwd_campaign_part_in >> event
user >> end
event >> end
dws_channel_sales_main >> end
dws_channel_sales_item >> end
dim_agreement_coupon_info >> end
dim_campaign_info >> end
dim_party_number_kid >> end
dim_party_shop >> end
dwd_agreement_user_sign >> end
dwd_sales_order_return_detail >> end
dwd_sales_order_return_main >> end
end >> batch_ch_2_hive