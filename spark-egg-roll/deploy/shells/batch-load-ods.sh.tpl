#! /bin/bash
basedir=`dirname $0`
cd $basedir
files=( \
"d_dl_calendar" \
"d_dl_city_tier" \
"d_dl_crm_admin_event" \
"d_dl_crm_admin_goods" \
"d_dl_crm_admin_goods_sku" \
"d_dl_crm_benefit_action" \
"d_dl_crm_benefit_condition_benefit_reward_rela" \
"d_dl_crm_benefit_reward_condition" \
"d_dl_crm_benefit_rule" \
"d_dl_crm_birthday_history" \
"d_dl_crm_campaign_benefit_rule_rela" \
"d_dl_crm_campaign_channel_org" \
"d_dl_crm_campaign_channel_store" \
"d_dl_crm_campaign_coupon_benefit_rela" \
"d_dl_crm_campaign_event_rela" \
"d_dl_crm_campaign_limit" \
"d_dl_crm_campaign_member_rela" \
"d_dl_crm_campaign_point_benefit_rela" \
"d_dl_crm_campaign_product" \
"d_dl_crm_campaign_property" \
"d_dl_crm_campaign_seriescode" \
"d_dl_crm_channel_store" \
"d_dl_crm_coupon_definition" \
"d_dl_crm_coupon_target_campaign_rela" \
"d_dl_crm_goods" \
"d_dl_crm_goods_sku" \
"d_dl_crm_involved_org" \
"d_dl_crm_member_join_channel" \
"d_dl_crm_member_memo" \
"d_dl_crm_member_opt_org" \
"d_dl_crm_member_org_rela" \
"d_dl_crm_member_phone" \
"d_dl_crm_member_residence" \
"d_dl_crm_point_benefit_rule" \
"d_dl_crm_point_condition" \
"d_dl_crm_point_condition_config" \
"d_dl_crm_point_condition_config_comparator" \
"d_dl_crm_point_definition" \
"d_dl_crm_point_variable" \
"d_dl_crm_region" \
"d_dl_crm_taocan" \
"d_dl_crm_taocan_sku" \
"d_dl_crm_thirdparty_app" \
"d_dl_jd_member" \
"d_dl_phy_store" \
"d_dl_product_info" \
"d_dl_tm_member_his" \
"d_hyt_tm_member" \
"d_lgc_phy_store" \
"f_crm_benefit_event" \
"f_crm_benefit_point" \
"f_crm_benefit_rule_benefit" \
"f_crm_benefit_rule_benefit_detail" \
"f_crm_campaign_detail" \
"f_crm_campaign_history" \
"f_crm_coupon_benefit" \
"f_crm_coupon_benefit_detail" \
"f_crm_coupon_benefit_status" \
"f_crm_coupon_benefit_summary_benefitid" \
"f_crm_dashboard_campaign" \
"f_crm_dashboard_campaign_reservation" \
"f_crm_dashboard_campaign_summary" \
"f_crm_dashboard_channel" \
"f_crm_dashboard_point" \
"f_crm_hyt_tm_member_his" \
"f_crm_kid_detail" \
"f_crm_member_blacklist" \
"f_crm_member_detail" \
"f_crm_member_staff" \
"f_crm_member_status" \
"f_crm_point_benefit" \
"f_crm_point_benefit_detail" \
"f_crm_point_benefit_summary" \
"f_crm_point_benefit_summary_benefitid" \
"f_crm_point_sum_condition" \
"f_crm_point_sum_rules_date" \
"f_crm_point_sum_rules_year" \
"f_crm_privacy_term_detail" \
"f_crm_thirdparty_bind_detail" \
"f_crm_thirdparty_bind_log" \
"f_hyt_tm_member" \
"f_hyt_tm_member_his" \
"f_jd_pop_consignee" \
"f_jd_pop_order" \
"f_jd_pop_order_dtl" \
"f_oms_order" \
"f_oms_order_dtl" \
"f_oms_return_order" \
"f_oms_return_order_dtl" \
"f_phy_store_member" \
"f_phy_store_member_point" \
"f_phy_store_order" \
"f_phy_store_order_dtl" \
"f_phy_store_order_payment_dtl" \
"f_phy_store_return_order" \
"f_phy_store_return_order_dtl" \
"f_phy_store_traffic" \
"f_wc_mini_member_info" \
)

tables=( \
"ods.ods_d_dl_calendar" \
"ods.ods_d_dl_city_tier" \
"ods.ods_admin_event" \
"ods.ods_admin_goods" \
"ods.ods_admin_goods_sku" \
"ods.ods_benefit_action" \
"ods.ods_benefit_condition_benefit_reward_rela" \
"ods.ods_benefit_reward_condition" \
"ods.ods_benefit_rule" \
"ods.ods_birthday_history" \
"ods.ods_campaign_benefit_rule_rela" \
"ods.ods_campaign_channel_org" \
"ods.ods_campaign_channel_store" \
"ods.ods_campaign_coupon_benefit_rela" \
"ods.ods_campaign_event_rela" \
"ods.ods_campaign_limit" \
"ods.ods_campaign_member_rela" \
"ods.ods_campaign_point_benefit_rela" \
"ods.ods_campaign_product" \
"ods.ods_campaign_property" \
"ods.ods_campaign_seriescode" \
"ods.ods_channel_store" \
"ods.ods_coupon_definition" \
"ods.ods_coupon_target_campaign_rela" \
"ods.ods_goods" \
"ods.ods_goods_sku" \
"ods.ods_involved_org" \
"ods.ods_member_join_channel" \
"ods.ods_member_memo" \
"ods.ods_member_opt_org" \
"ods.ods_member_org_rela" \
"ods.ods_member_phone" \
"ods.ods_member_residence" \
"ods.ods_point_benefit_rule" \
"ods.ods_point_condition" \
"ods.ods_point_condition_config" \
"ods.ods_point_condition_config_comparator" \
"ods.ods_point_definition" \
"ods.ods_point_variable" \
"ods.ods_region" \
"ods.ods_taocan" \
"ods.ods_taocan_sku" \
"ods.ods_thirdparty_app" \
"ods.ods_d_dl_jd_member" \
"ods.ods_d_dl_phy_store" \
"ods.ods_d_dl_product_info" \
"ods.ods_d_dl_tm_member_his" \
"ods.ods_d_hyt_tm_member" \
"ods.ods_d_lgc_phy_store" \
"ods.ods_benefit_event" \
"ods.ods_benefit_point" \
"ods.ods_benefit_rule_benefit" \
"ods.ods_benefit_rule_benefit_detail" \
"ods.ods_campaign_detail" \
"ods.ods_campaign_history" \
"ods.ods_coupon_benefit" \
"ods.ods_coupon_benefit_detail" \
"ods.ods_coupon_benefit_status" \
"ods.ods_coupon_benefit_summary_benefitid" \
"ods.ods_dashboard_campaign" \
"ods.ods_dashboard_campaign_reservation" \
"ods.ods_dashboard_campaign_summary" \
"ods.ods_dashboard_channel" \
"ods.ods_dashboard_point" \
"ods.ods_f_crm_hyt_tm_member_his" \
"ods.ods_kid_detail" \
"ods.ods_member_blacklist" \
"ods.ods_member_detail" \
"ods.ods_member_staff" \
"ods.ods_member_status" \
"ods.ods_point_benefit" \
"ods.ods_point_benefit_detail" \
"ods.ods_point_benefit_summary" \
"ods.ods_point_benefit_summary_benefitid" \
"ods.ods_point_sum_condition" \
"ods.ods_point_sum_rules_date" \
"ods.ods_point_sum_rules_year" \
"ods.ods_privacy_term_detail" \
"ods.ods_thirdparty_bind_detail" \
"ods.ods_thirdparty_bind_log" \
"ods.ods_f_hyt_tm_member" \
"ods.ods_f_hyt_tm_member_his" \
"ods.ods_f_jd_pop_consignee" \
"ods.ods_f_jd_pop_order" \
"ods.ods_f_jd_pop_order_dtl" \
"ods.ods_f_oms_order" \
"ods.ods_f_oms_order_dtl" \
"ods.ods_f_oms_return_order" \
"ods.ods_f_oms_return_order_dtl" \
"ods.ods_f_phy_store_member" \
"ods.ods_f_phy_store_member_point" \
"ods.ods_f_phy_store_order" \
"ods.ods_f_phy_store_order_dtl" \
"ods.ods_f_phy_store_order_payment_dtl" \
"ods.ods_f_phy_store_return_order" \
"ods.ods_f_phy_store_return_order_dtl" \
"ods.ods_f_phy_store_traffic" \
"ods.ods_f_wc_mini_member_info" \
)
{{ 'len=${#files[@]}' }}
dt=$1
if [ ! $dt ];then
dt=`date +%Y%m%d -d"-16hours"`
fi
echo dt $dt
dt_7before=`date +%Y%m%d -d"$dt-7days"`
start_ts=$(date +%s)
cur_ts=$(date +%s)
diff=$(((cur_ts - start_ts)/60))
expire_minute=30
echo "down file fail list" >  $basedir/缺失文件列表_$dt.txt
while ( [ $diff -le $expire_minute ] )
do
    # shellcheck disable=SC2068
    exit_code=0
    for ((i=0;i<$len;i++))
    do
      file=${files[i]}
      table=${tables[i]}
      echo running $file
      sh $basedir/down-file.sh $file $dt
      result=$?
      if [ $result -ne 0 ];then
        echo "down load file $file/$dt fail at `date`" >> $basedir/缺失文件列表_$dt.txt
        exit_code=$result
        continue
      fi
    done
    # break for test env
    break
    if [ $exit_code -eq 0 ];then
        break
    fi
    cur_ts=$(date +%s)
    diff=$(((cur_ts - start_ts)/60))
    echo $diff minutes passed
done
#if [ $exit_code -ne 0 ];then
#    echo "Down load file fail after $expire_minute minutes"
#    exit $exit_code
#fi
echo args = $args

# Drop partitions of all ods tables and prepare args
args=""
for ((i=0;i<$len;i++))
do
  file=${files[i]}
  table=${tables[i]}
  args=" $args --runner_config $basedir/../config/parquet_2_ch.json \
 --path /data/redshift-data/$file/$dt/*.parquet \
 --name $table \
 --dt $dt ----"
  # 清理当前分区避免数据重复
  sh $basedir/ch-drop-partition.sh $table $dt
  # 清理7天前分区
  sh $basedir/ch-drop-partition.sh $table $dt_7before
done

libs=`echo $basedir/../dependency/* |tr ' ' ','`
/data/apps/lib/spark/bin/spark-submit --class bin.EtlRunnerGo \
    --master yarn \
    --deploy-mode client \
    --driver-memory 4g \
    --executor-memory 4g \
    --executor-cores 1 \
    --name load_ch_from_parquet \
    --num-executors 5 \
    --jars $libs \
    --conf spark.sql.shuffle.partitions=20 \
    --conf spark.default.parallelism=20 \
 $basedir/../bin/spark-egg-roll-0.5.jar $args
result=$?
if [ $result -ne 0 ];then
echo load file meet fail error
exit_code=$result
fi

# metadata validate
for ((i=0;i<$len;i++))
do
  file=${files[i]}
  table=${tables[i]}
  echo running $file
  table_count=`echo "select count(*) from $table" | curl 'http://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}' --data-binary @-`
  tmp=`echo $table_count|sed 's/[0-9]//g'`
  [ -n "${tmp}" ] && continue
  echo count in table=$table_count
  file_count=`/data/apps/lib/hadoop/bin/hadoop fs -cat /data/redshift-data/$file/$dt/*_manifest | grep -E ".*record_count\": ([0-9]*)$" | sed -r "s/.*record_count\": ([0-9]*)$/\1/"`
  tmp=`echo $file_count|sed 's/[0-9]//g'`
  [ -n "${tmp}" ] && continue
  echo count in file=$file_count
  if [ $table_count -eq $file_count ];then
    flag='ok'
  else
    flag='bad'
  fi
  echo "insert into meta.table_size_validate values ('file_$file',$file_count,'table_$table',$table_count,'$flag','$dt')" \
        | curl 'http://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}/' --data-binary @-
        echo "optimize table meta.table_size_validate" \
        | curl 'http://{{ resources.cdp_clickhouse.default.http.addr.split(',')[0] }}/' --data-binary @-

done

# 调试过程不开启这个
# exit $exit_code
exit 0