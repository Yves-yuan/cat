#! /bin/bash
BASE_DIR=$(
  cd {{ app_deploy_dir }}
  pwd
)
su {{ deploy_user }} -s /bin/bash <<EOF
mkdir -p $BASE_DIR/airflow/dags/
/bin/cp -rf $BASE_DIR/airflow/shells/*.py $BASE_DIR/airflow/dags/
/bin/cp -rf $BASE_DIR/airflow/dags/*.py /data/apps/lib/airflow/dags/
EOF
