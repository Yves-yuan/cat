#!/bin/bash
# -*- coding: utf-8 -*-
source /etc/profile
basepath=`dirname $0`
from=$1
to=$2
dt=$3
cd $basepath
sql1="alter table $to drop partition \'$dt\'"
sql2="insert into $to select '*' from $from where dt=\'$dt\'"
ssh root@10.6.8.39 << EOF
echo $sql1
echo $sql1 | clickhouse client -u root --password 7rgbbg93lt67s3sn
echo $sql2
echo $sql2 | clickhouse client -u root --password 7rgbbg93lt67s3sn
exit
EOF
