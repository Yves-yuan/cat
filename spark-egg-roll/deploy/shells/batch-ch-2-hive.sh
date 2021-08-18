#! /bin/bash
dt=$1
if [ ! $dt ];then
dt=`date +%Y%m%d -d"-16hours"`
fi


basedir=`dirname $0`
cd $basedir


tables=( \
"dwd.dwd_sales_order_main_flow" \
"dwd.dwd_sales_order_detail_flow" \
"dwd.dwd_sales_order_return_main" \
"dwd.dwd_sales_order_return_detail" \
"dim.dim_agreement_coupon_info" \
"dwd.dwd_agreement_coupon_ver" \
"dim.dim_party_shop" \
"dim.dim_item_sku" \
"dim.dim_party_number_kid" \
"dim.dim_public_date_calendar" \
"dim.dim_public_location_area" \
"dim.dim_party_user_all" \
)
echo $tables
for element in ${tables[@]}
#也可以写成for element in ${array[*]}
do
echo run $element ......................................
sh $basedir/submit-ch-2-hive.sh $element $dt
echo run $element done .................................
done
