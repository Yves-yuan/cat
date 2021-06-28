source /etc/profile
dt=$1
basedir=`dirname $0`
cd $basedir
libs=`echo $basedir/lib/* |tr ' ' ','`
echo $libs
spark-submit --class validate.Completeness \
    --master yarn \
    --deploy-mode client \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --num-executors 4 \
    --jars $libs \
/root/yuanyifei/spark-egg-roll-0.5.jar --dt $dt --savepath '/user/yuanyifei/validate' \
ods_ba_shop_base_df \
ods_mc_cart_di \
ods_mma_marketing_account_di \
ods_mma_marketing_account_extend_di \
ods_mma_marketing_account_off_df \
ods_mma_marketing_award_df \
ods_mma_marketing_condition_gift_df \
ods_mma_marketing_coupon_code_di \
ods_mma_marketing_coupon_prepare_send_di \
ods_mma_marketing_crowd_batch_log_di \
ods_mma_marketing_group_setting_df \
ods_mma_marketing_into_shop_gift_df \
ods_mma_marketing_issue_package_detail_df \
ods_mma_marketing_issue_package_df \
ods_mma_marketing_new_gift_acquire_record_di \
ods_mma_marketing_promotion_df \
ods_mma_marketing_promotion_extend_df \
ods_mma_marketing_subscribe_record_df \
ods_mma_marketing_wx_coupon_consume_df \
ods_mma_marketing_wx_coupon_send_di \
ods_mma_marketing_wx_coupon_stock_df \
ods_mme_coupon_df \
ods_mme_coupon_number_df \
ods_mme_coupon_number_member_df \
ods_mme_member_di \
ods_mme_member_growth_value_di \
ods_mme_member_task_df \
ods_mo_order_detail_di \
ods_mo_order_detail_discount_di \
ods_mo_order_detail_extend_di \
ods_mo_order_extend_di \
ods_mo_order_gift_df \
ods_mo_order_group_list_di \
ods_mo_order_group_member_di \
ods_mo_order_info_di \
ods_mo_order_promotion_di \
ods_mo_order_status_di \
ods_mp_wi_category_df \
ods_mp_wi_product_df \
ods_mp_wi_product_extension_df \
ods_mp_wi_product_order_limit_di \
ods_mp_wi_sku_df \
ods_mpo_member_points_di \
ods_mr_crm_cusaddress_di \
ods_mr_crm_customer_di \
ods_mr_crm_customer_login_log_di \
ods_mr_crm_customer_logout_record_df \
ods_mr_crm_customer_new_into_ref_di \
ods_mr_crm_customer_tag_ref_di \
ods_mr_crm_distributor_df \
ods_mr_crm_extension_di \
ods_mr_crm_promotion_channel_df \
ods_mr_crm_tag_attr_df \
ods_mr_crm_tag_df \
ods_ms_shop_df \
ods_ms_shop_pop_up_ads_df \
ods_ps_product_sku_attr_df \
ods_ps_product_sku_df \
ods_ps_product_sku_sale_df \
ods_ps_product_spu_category_df \
ods_ps_product_spu_df \
ods_wechat_mall_event_di 
