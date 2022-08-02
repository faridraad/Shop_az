#!/bin/bash

QM="bkmtsimatest"
RMQMADD="192.168.1.34"
RMQMPORT="2023"
BNKCODE="BKMT"
BNKNAME="bkmt"
BNKLSNRPORT="4033"
APPLSNRPORT="1416"
KEYPASS="Misc@102030"
RMQM="simatest"
CURRENT_PATH=$(pwd)


echo "Script starts."

sudo -u mqm crtmqm -q -d MY.DEFAULT.XMIT.QUEUE -ll $QM

echo "Queue Manager $QM is created."

sudo -u mqm strmqm $QM

sudo -u mqm echo "
ALTER QMGR +
   CCSID(1208) +
   DEFXMITQ('MY.DEFAULT.XMIT.QUEUE') +
   CERTLABL('ibmwebspheremq""$QM') +
   SSLKEYR('/var/mqm/qmgrs/$QM/ssl/key""$QM') +
   FORCE 

DEFINE CHANNEL('$BNKCODE.SVR.CONN') +
   CHLTYPE(SVRCONN) +
   CERTLABL('ibmwebspheremq$QM') +
   DESCR('Server-connection') +
   DISCINT(0) +
   SSLCIPH('TLS_RSA_WITH_AES_128_CBC_SHA256') +
   TRPTYPE(TCP) +
   REPLACE    
      
DEFINE LISTENER('$BNKCODE.SVR.LSNR') TRPTYPE(TCP) CONTROL(QMGR) +
   PORT($APPLSNRPORT) +
   REPLACE 
   
DEFINE QREMOTE('$BNKCODE.REQ') +
   DEFPSIST(YES) +
   DEFPRESP(ASYNC) +
   RQMNAME('$RMQM') +
   RNAME('$BNKCODE.REQ') +
   XMITQ('$BNKCODE.TRANS') +
   REPLACE
   
DEFINE QLOCAL('$BNKCODE.RES') +
   MSGDLVSQ (FIFO) +
   DEFPSIST(YES) +
   USAGE(NORMAL) +
   MAXDEPTH(999999999) +
   MAXMSGL(104857600) +
   DEFPRESP(ASYNC) +
   PROPCTL(V6COMPAT) +
   REPLACE  

DEFINE QLOCAL('$BNKCODE.TRANS') +
   DEFPSIST(YES) +
   USAGE(XMITQ) +
   MAXDEPTH(9999999) +
   DEFPRESP(ASYNC) +
   PROPCTL(V6COMPAT) + 
   REPLACE

   
   
DEFINE CHANNEL('$BNKCODE.TO.SIMA') +
   CHLTYPE(SDR) +
   CERTLABL('ibmwebspheremq$QM') +
   COMPHDR(SYSTEM) +
   COMPMSG(NONE) +
   CONNAME('$RMQMADD($RMQMPORT)') +
   DISCINT(0) +
   SSLCIPH('TLS_RSA_WITH_AES_128_CBC_SHA256') +
   USEDLQ(YES) +
   XMITQ('$BNKCODE.TRANS') +
   PROPCTL(NONE) +
   REPLACE 
   
   
DEFINE CHANNEL('SIMA.TO.$BNKCODE') +
   CHLTYPE(RCVR) +
   CERTLABL('ibmwebspheremq$QM') +
   COMPHDR(NONE) +
   COMPMSG(NONE) +
   MAXMSGL(4194304) +
   SSLCAUTH(REQUIRED) +
   SSLCIPH('TLS_RSA_WITH_AES_128_CBC_SHA256') +
   USEDLQ(YES) +
   REPLACE
 
  
   
DEFINE LISTENER('$BNKCODE.LSNR') TRPTYPE(TCP) CONTROL(QMGR) +
   PORT($BNKLSNRPORT) +
   REPLACE     
   
REFRESH SECURITY  
  
" >$QM.in   

echo "MQ script is written in $QM.in"

sudo -u mqm runmqsc $QM <$QM.in > $QM.out

echo "MQ script results is written in $QM.out"


sudo -u mqm endmqm $QM

echo "Wait for keystore creation..."

sudo -u mqm runmqckm -keydb -create -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -type cms -pw $KEYPASS -stash
sudo -u mqm runmqckm -cert -create  -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -pw $KEYPASS -label ibmwebspheremq$QM -dn "CN=$QM,O=ISC,C=IR" -expire 730 -size 4096
sudo -u mqm runmqckm -cert -extract -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -pw $KEYPASS -label ibmwebspheremq$QM -target "/var/mqm/qmgrs/$QM/ssl/cert$QM.arm"

echo "Key for $QM is created."


#to copy server certificate to current path
FILE=$CURRENT_PATH/cert$RMQM.arm
if test -f "$FILE"; then
    echo "$FILE exist"	
	echo "Wait for adding certificate..."
	sudo -u mqm cp cert$RMQM.arm /var/mqm/qmgrs/$QM/ssl/
	sudo -u mqm runmqckm -cert -add -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -pw $KEYPASS -label ibmwebspheremq$RMQM -file "/var/mqm/qmgrs/$QM/ssl/cert$RMQM.arm"
	echo "$RMQM's certificate is added."
else
	echo -e "\e[1;31mFile cert$RMQM.arm should be exist in current path!\e[0m"
	echo -e "\e[1;31mBefore running this script receive the file from $RMQM administrator and run script 6-sign-server-certificate-on-client.sh!\e[0m"	
	echo -e "\e[1;33mExpected path : /var/mqm/qmgrs/$RMQM/ssl\e[0m"	
fi


sudo -u mqm strmqm $QM

echo "Script ends."
#cat $QM.out