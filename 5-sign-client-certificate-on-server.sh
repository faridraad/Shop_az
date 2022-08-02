#!/bin/bash


QM="simatest"
BNKNAME="bkmt"
KEYPASS="Misc@102030"
RMQM=$BNKNAME"simatest"
CURRENT_PATH=$(pwd)

FILE=$CURRENT_PATH/cert$RMQM.arm
if test -f "$FILE"; then
    echo "$FILE exist"
else
	echo -e "\e[1;31mFile cert$RMQM.arm should be exist in current path!\e[0m"
	echo -e "\e[1;31mBefore running this script receive the file from $RMQM administrator!\e[0m"	
	echo -e "\e[1;33mExpected path : /var/mqm/qmgrs/$RMQM/ssl\e[0m"	
	exit
fi

echo "Script starts."

echo "Wait for adding $BNKNAME certificate..."

sudo -u mqm cp cert$RMQM.arm /var/mqm/qmgrs/$QM/ssl/

sudo -u mqm runmqckm -cert -add -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -pw $KEYPASS -label ibmwebspheremq$RMQM -file "/var/mqm/qmgrs/$QM/ssl/cert"$RMQM".arm"

echo "$BNKNAME certificate is added."

echo "Script ends."
