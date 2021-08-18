#! /bin/bash
basedir=`dirname $0`
cd $basedir
file=$1
dt=$2
if [ ! $file ];then
echo please input file
exit 1
fi
if [ ! $dt ];then
dt=`date +%Y%m%d -d"-16hours"`
fi
echo dt=$dt
dt_7before=`date +%Y%m%d -d"$dt-7days"`
# dt from airflow is utc time,so i add 8 to dt.

echo 开始导入数据
/usr/local/bin/aws s3 cp  --profile  lego s3://{{ resources.cdp_clickhouse.default.http.addr.split('-')[0] }}-cdm-data-mart/${file}/$dt /data/redshift-data/${file}/$dt --recursive >/dev/null 2>&1
manifest_file=`find /data/redshift-data/$file/$dt -name "*manifest"`
if [ ! $manifest_file ];then
  echo manifest not found $file file is not got completely
  exit 1
fi
echo $manifest_file file exists.

#echo 校验md5
#sh $basedir/check-file-md5.sh $file $dt
#md5_res=$?
#if [ $md5_res -ne 0 ];then
#echo 校验md5失败
#exit $md5_res
#fi
#echo 校验md5成功
whoami
/data/apps/lib/hadoop/bin/hadoop fs -mkdir -p /data/redshift-data/${file}/$dt/
/data/apps/lib/hadoop/bin/hadoop fs -put -f  /data/redshift-data/${file}/$dt/* /data/redshift-data/${file}/$dt/

# 清理7天前数据
rm -rf /data/redshift-data/${file}/$dt_7before
/data/apps/lib/hadoop/bin/hadoop fs -rmr /data/redshift-data/${file}/$dt_7before