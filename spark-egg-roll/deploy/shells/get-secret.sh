#! /bin/bash
passwd=`/usr/local/bin/aws secretsmanager get-secret-value --secret-id gio_lego_secret`
echo $passwd | jq '.SecretString' >> secret-tests
sed -i 's/\"{/{/g' secret-tests
sed -i 's/}\"/}/g' secret-tests
sed -i 's/\\//g' secret-tests
passR=`cat secret-tests | jq '.ch_password'`
echo $passR >> secret-pd
sed -i 's/\"//g' secret-pd
pd=`cat secret-pd`
echo $pd
rm -rf secret-pd
rm -rf secret-tests
