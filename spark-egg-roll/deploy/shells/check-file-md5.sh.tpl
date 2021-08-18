#! /bin/bash
basedir=`dirname $0`
table=$1
dt=$2
file=`find /data/redshift-data/$table/$dt/ -name "*.parquet"`
file_name=`find /data/redshift-data/$table/$dt/ -name "*.parquet" | sed -r "s/.*$dt\/(.*parquet)/\1/"`
if [ ! -f "$file" ]; then
echo $file  file not exists
exit 1
fi
md5s=`python3 $basedir/checkMD5.py "$file" 33554432`
s3_md5s=`/usr/local/bin/aws s3api --profile lego   head-object --bucket {{ resources.cdp_clickhouse.default.http.addr.split('-')[0] }}-cdm-data-mart --key ${table}/$dt/$file_name`
to_pare=`echo $s3_md5s | jq '.ETag'`
echo $to_pare >> to_pare.txt
sed -i 's/\\//g' to_pare.txt
result=`cat to_pare.txt`
echo 's3上的md5为'   $result
echo '本地文件的md5为'   \"$md5s\"
echo '开始进入判断'
rm -f to_pare.txt
{{ 'if [ ${result} = \\"${md5s}\\" ];then' }}
echo ok
exit 0
else echo bad
exit 1
fi