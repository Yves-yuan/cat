dt=$1
if [ ! $dt ];then
dt="20210627"
fi
basepath=`dirname $0`
cd $basepath
arr=(`echo rules/wanmei/*`)


for i in "${!arr[@]}";   
do   
	rule_path=$basepath/${arr[$i]}
	echo 开始运行 $rule_path dt: $dt
	sh rule_validate.sh "$rule_path" $dt
done
