source /etc/profile
dt=$1
basepath=`dirname $0`
cd $basepath
sh $basepath/ch2ch.sh ods.ods_mme_coupon_df dwd.dwd_mme_coupon_df $dt
sh $basepath/ch2ch.sh ods.ods_mme_member_di dwd.dwd_mme_member_di $dt
sh $basepath/ch2ch.sh ods.ods_mme_member_growth_value_di dwd.dwd_mme_member_growth_value_di $dt
sh $basepath/ch2ch.sh ods.ods_mo_order_detail_di dwd.dwd_mo_order_detail_di $dt
sh $basepath/ch2ch.sh ods.ods_mo_order_extend_di dwd.dwd_mo_order_extend_di $dt
sh $basepath/ch2ch.sh ods.ods_mo_order_info_di dwd.dwd_mo_order_info_di $dt
sh $basepath/ch2ch.sh ods.ods_mp_wi_product_order_limit_di dwd.dwd_mp_wi_product_order_limit_di $dt
sh $basepath/ch2ch.sh ods.ods_mpo_member_points_di dwd.dwd_mpo_member_points_di $dt
sh $basepath/ch2ch.sh ods.ods_mr_crm_customer_di dwd.dwd_mr_crm_customer_di $dt
sh $basepath/ch2ch.sh ods.ods_mr_crm_extension_di dwd.dwd_mr_crm_extension_di $dt
sh $basepath/ch2ch.sh ods.ods_ps_product_sku_attr_df dwd.dwd_ps_product_sku_attr_df $dt
sh $basepath/ch2ch.sh ods.ods_ps_product_sku_df dwd.dwd_ps_product_sku_df $dt
sh $basepath/ch2ch.sh ods.ods_ps_product_sku_sale_df dwd.dwd_ps_product_sku_sale_df $dt
sh $basepath/ch2ch.sh ods.ods_ps_product_spu_category_df dwd.dwd_ps_product_spu_category_df $dt
sh $basepath/ch2ch.sh ods.ods_ps_product_spu_df dwd.dwd_ps_product_spu_df $dt
