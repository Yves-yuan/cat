#! /bin/bash
basedir=`dirname $0`
cd $basedir

files=( \
"d_dl_city_tier" \
"d_dl_jd_member" \
"d_dl_jd_shopper" \
"d_dl_phy_store" \
"d_dl_product_info" \
"d_lgc_phy_store" \
"f_cs_consumer_info" \
"f_jd_b2b_order" \
"f_jd_b2b_order_dtl" \
"f_jd_pop_consignee" \
"f_jd_pop_order" \
"f_jd_pop_order_dtl" \
"f_oms_expense_statement" \
"f_oms_income_expense" \
"f_oms_income_statement" \
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
"admin_event" \
"admin_goods" \
"admin_goods_sku" \
"benefit_action" \
"benefit_condition_benefit_reward_rela" \
"benefit_event" \
"benefit_point" \
"benefit_reward_condition" \
"benefit_rule" \
"benefit_rule_benefit" \
"benefit_rule_benefit_detail" \
"birthday_history" \
"campaign_benefit_rule_rela" \
"campaign_detail" \
"campaign_event_rela" \
"campaign_history" \
"campaign_limit" \
"campaign_point_benefit_rela" \
"campaign_product" \
"campaign_property" \
"campaign_seriescode" \
"channel_store" \
"coupon_benefit" \
"coupon_benefit_detail" \
"coupon_benefit_status" \
"coupon_benefit_summary_benefitid" \
"coupon_definition" \
"coupon_target_campaign_rela" \
"goods" \
"goods_sku" \
"kid_detail" \
"member_detail" \
"member_join_channel" \
"member_memo" \
"member_opt_org" \
"member_org_rela" \
"member_phone" \
"member_residence" \
"point_benefit" \
"point_benefit_detail" \
"point_benefit_rule" \
"point_benefit_summary" \
"point_benefit_summary_benefitid" \
"point_condition" \
"point_condition_config" \
"point_condition_config_comparator" \
"point_sum_condition" \
"point_sum_rules_date" \
"point_sum_rules_year" \
"point_variable" \
"region" \
"taocan" \
"taocan_sku" \
"thirdparty_app" \
"thirdparty_bind_detail" \
"thirdparty_bind_log" \
"dashboard_campaign" \
"dashboard_campaign_reservation" \
"dashboard_campaign_summary" \
"dashboard_channel" \
"dashboard_point" \
"campaign_channel_org" \
"campaign_channel_store" \
"campaign_coupon_benefit_rela" \
"involved_org" \
"member_status" \
"member_staff" \
"member_blacklist" \
"privacy_term_detail" \
"point_definition" \
"campaign_member_rela" \
)

tables=( \
"ods.ods_d_dl_city_tier" \
"ods.ods_d_dl_jd_member" \
"ods.ods_d_dl_jd_shopper" \
"ods.ods_d_dl_phy_store" \
"ods.ods_d_dl_product_info" \
"ods.ods_d_lgc_phy_store" \
"ods.ods_f_cs_consumer_info" \
"ods.ods_f_jd_b2b_order" \
"ods.ods_f_jd_b2b_order_dtl" \
"ods.ods_f_jd_pop_consignee" \
"ods.ods_f_jd_pop_order" \
"ods.ods_f_jd_pop_order_dtl" \
"ods.ods_f_oms_expense_statement" \
"ods.ods_f_oms_income_expense" \
"ods.ods_f_oms_income_statement" \
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
"ods.ods_admin_event" \
"ods.ods_admin_goods" \
"ods.ods_admin_goods_sku" \
"ods.ods_benefit_action" \
"ods.ods_benefit_condition_benefit_reward_rela" \
"ods.ods_benefit_event" \
"ods.ods_benefit_point" \
"ods.ods_benefit_reward_condition" \
"ods.ods_benefit_rule" \
"ods.ods_benefit_rule_benefit" \
"ods.ods_benefit_rule_benefit_detail" \
"ods.ods_birthday_history" \
"ods.ods_campaign_benefit_rule_rela" \
"ods.ods_campaign_detail" \
"ods.ods_campaign_event_rela" \
"ods.ods_campaign_history" \
"ods.ods_campaign_limit" \
"ods.ods_campaign_point_benefit_rela" \
"ods.ods_campaign_product" \
"ods.ods_campaign_property" \
"ods.ods_campaign_seriescode" \
"ods.ods_channel_store" \
"ods.ods_coupon_benefit" \
"ods.ods_coupon_benefit_detail" \
"ods.ods_coupon_benefit_status" \
"ods.ods_coupon_benefit_summary_benefitid" \
"ods.ods_coupon_definition" \
"ods.ods_coupon_target_campaign_rela" \
"ods.ods_goods" \
"ods.ods_goods_sku" \
"ods.ods_kid_detail" \
"ods.ods_member_detail" \
"ods.ods_member_join_channel" \
"ods.ods_member_memo" \
"ods.ods_member_opt_org" \
"ods.ods_member_org_rela" \
"ods.ods_member_phone" \
"ods.ods_member_residence" \
"ods.ods_point_benefit" \
"ods.ods_point_benefit_detail" \
"ods.ods_point_benefit_rule" \
"ods.ods_point_benefit_summary" \
"ods.ods_point_benefit_summary_benefitid" \
"ods.ods_point_condition" \
"ods.ods_point_condition_config" \
"ods.ods_point_condition_config_comparator" \
"ods.ods_point_sum_condition" \
"ods.ods_point_sum_rules_date" \
"ods.ods_point_sum_rules_year" \
"ods.ods_point_variable" \
"ods.ods_region" \
"ods.ods_taocan" \
"ods.ods_taocan_sku" \
"ods.ods_thirdparty_app" \
"ods.ods_thirdparty_bind_detail" \
"ods.ods_thirdparty_bind_log" \
"ods.ods_dashboard_campaign" \
"ods.ods_dashboard_campaign_reservation" \
"ods.ods_dashboard_campaign_summary" \
"ods.ods_dashboard_channel" \
"ods.ods_dashboard_point" \
"ods.ods_campaign_channel_org" \
"ods.ods_campaign_channel_store" \
"ods.ods_campaign_coupon_benefit_rela" \
"ods.ods_involved_org" \
"ods.ods_member_status" \
"ods.ods_member_staff" \
"ods.ods_member_blacklist" \
"ods.ods_privacy_term_detail" \
"ods.ods_point_definition" \
"ods.ods_campaign_member_rela" \
)
mkdir ../dags
{{ 'len=${#files[@]}' }}
minute=30
hour=17
for ((i=0;i<$len;i++))
do
 file=${files[i]}
 table=${tables[i]}
 minute=$(( minute + 2 ))
 minute=$(( minute % 60 ))
 if [ $minute -eq 0 ];then
 hour=$(( hour + 1 ))
 fi
 echo $table dags
 sed "s/\${table}/$table/g"  ./down_load_temp \
 | sed "s/\${file}/$file/g" | sed "s/\${minute}/$minute/g" | sed "s/\${hour}/$hour/g" > ${basedir}/../dags/down_load_${file}.py
done