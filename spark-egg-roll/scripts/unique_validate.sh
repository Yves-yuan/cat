source /etc/profile
dt=$1
basedir=`dirname $0`
cd $basedir
libs=`echo $basedir/lib/* |tr ' ' ','`
echo $libs
spark-submit --class validate.Uniqueness \
    --master yarn \
    --deploy-mode client \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --num-executors 4 \
    --jars $libs \
/root/yuanyifei/spark-egg-roll-0.5.jar --dt $dt \
ods_mo_order_info_di,id \
ods_mo_order_info_di,customer_id \
ods_mo_order_info_di,uid \
ods_mo_order_info_di,pay_id \
ods_mo_order_info_di,down_pay_id \
ods_mo_order_extend_di,id \
ods_mo_order_extend_di,customer_id \
ods_mo_order_extend_di,order_id \
ods_mo_order_extend_di,coupon_account_id \
ods_mo_order_extend_di,tenant_id \
ods_mo_order_extend_di,creator_id \
ods_mo_order_detail_di,id \
ods_mo_order_detail_di,order_id \
ods_mo_order_detail_di,product_id \
ods_mo_order_detail_di,sku_id \
ods_mo_order_detail_di,brand_id \
ods_mo_order_detail_di,tenant_id \
ods_mp_wi_product_df,id \
ods_mp_wi_product_df,brand_id \
ods_mp_wi_product_df,category_id \
ods_mp_wi_product_df,presale_subscribe_event_id \
ods_mp_wi_product_df,tenant_id \
ods_mp_wi_sku_df,id \
ods_mp_wi_sku_df,product_id \
ods_mp_wi_sku_df,tenant_id \
ods_mp_wi_sku_df,pms_sku_id \
ods_mp_wi_category_df,id \
ods_mp_wi_category_df,category_parent_id \
ods_mp_wi_product_extension_df,id \
ods_mp_wi_product_extension_df,product_id \
ods_mp_wi_product_extension_df,template_id \
ods_mp_wi_product_extension_df,tenant_id \
ods_mp_wi_product_order_limit_di,id \
ods_mp_wi_product_order_limit_di,customer_id \
ods_mp_wi_product_order_limit_di,product_id \
ods_mp_wi_product_order_limit_di,tenant_id \
ods_ps_product_sku_df,id \
ods_ps_product_sku_df,spu_id \
ods_ps_product_sku_df,flow_id \
ods_ps_product_sku_attr_df,id \
ods_ps_product_sku_attr_df,sku_id \
ods_ps_product_sku_attr_df,attr_id \
ods_ps_product_sku_attr_df,attr_value_id \
ods_ps_product_sku_sale_df,id \
ods_ps_product_sku_sale_df,sku_id \
ods_ps_product_sku_sale_df,price_group_id \
ods_ps_product_spu_df,id \
ods_ps_product_spu_df,brand_id \
ods_ps_product_spu_df,company_id \
ods_ps_product_spu_category_df,id \
ods_ps_product_spu_category_df,spu_id \
ods_ps_product_spu_category_df,category_id \
ods_mr_crm_customer_di,id \
ods_mr_crm_customer_di,open_id \
ods_mr_crm_customer_di,union_id \
ods_mr_crm_customer_di,uid \
ods_mr_crm_extension_di,id \
ods_mr_crm_extension_di,customer_id \
ods_mr_crm_extension_di,union_id \
ods_mme_member_di,id \
ods_mme_member_di,user_id \
ods_mme_member_di,app_id \
ods_mme_member_di,open_id \
ods_mme_member_di,union_id \
ods_mme_member_di,member_type_id \
ods_mme_member_di,tenant_id \
ods_mme_member_growth_value_di,id \
ods_mpo_member_points_di,id \
ods_mme_coupon_df,id \
ods_mme_coupon_df,tenant_id 
