#!/bin/bash


QM="simatest"
RMQMADD="192.168.1.34"
RMQMPORT="4033"
BNKCODE="BKMT"
BNKNAME="bkmt"
BNKLSNRPORT="2023"
KEYPASS="Misc@102030"
RMQM=$BNKNAME"simatest"
BNKUSERPASS=$BNKNAME"@$KJ98"
CURRENT_PATH=$(pwd)

echo "Script starts."

useradd -s /sbin/nologin -m -d /home/$BNKNAME'user' $BNKNAME'user'

echo -e "$BNKUSERPASS\n$BNKUSERPASS" | passwd $BNKNAME'user'

echo "user $BNKNAME'user' is added."

sudo -u mqm echo "
DEFINE QLOCAL('$BNKCODE.REQ') +
   PUT(ENABLED) +
   GET(ENABLED) +
   DEFPSIST(YES) +
   SCOPE(QMGR) +
   USAGE(NORMAL) +
   MAXDEPTH(5000) +
   MAXMSGL(4194304) +
   DEFPRESP(ASYNC) +
   PROPCTL(V6COMPAT) +
   MSGDLVSQ(FIFO) +
   REPLACE  
   
DEFINE QREMOTE('$BNKCODE.RES') +
   DEFBIND(OPEN) +
   DEFPSIST(YES) +
   DEFPRESP(ASYNC) +
   PUT(ENABLED) +
   RQMNAME('$RMQM') +
   RNAME('$BNKCODE.RES') +
   SCOPE(QMGR) +
   XMITQ('$BNKCODE.TRANS') +
   REPLACE   

DEFINE QLOCAL('$BNKCODE.TRANS') +
   PUT(ENABLED) +
   GET(ENABLED) +
   DEFPSIST(YES) +
   SCOPE(QMGR) +
   USAGE(XMITQ) +
   MAXDEPTH(5000) +
   MAXMSGL(4194304) +
   DEFPRESP(ASYNC) +
   PROPCTL(V6COMPAT) + 
   REPLACE
   
DEFINE CHANNEL('$BNKCODE.TO.SIMA') +
   CHLTYPE(RCVR) +
   CERTLABL('ibmwebspheremq$QM') +
   COMPHDR(SYSTEM) +
   COMPMSG(NONE) +
   MAXMSGL(4194304) +
   MCAUSER('$BNKNAME""user') +
   SSLCAUTH(REQUIRED) +
   SSLCIPH('TLS_RSA_WITH_AES_128_CBC_SHA256') +
   SSLPEER('CN=$RMQM, O=ISC, C=IR') +
   USEDLQ(YES) +
   REPLACE   
   
   
DEFINE CHANNEL('SIMA.TO.$BNKCODE') +
   CHLTYPE(SDR) +
   CERTLABL('ibmwebspheremq$QM') +
   COMPHDR(SYSTEM) +
   COMPMSG(NONE) +
   CONNAME('$RMQMADD($RMQMPORT)') +
   DISCINT(0) +
   MAXMSGL(4194304) +
   MCAUSER('$BNKNAME""user') +
   SSLCIPH('TLS_RSA_WITH_AES_128_CBC_SHA256') +
   SSLPEER('CN=$RMQM, O=ISC, C=IR') +
   USEDLQ(YES) +
   XMITQ('$BNKCODE.TRANS') +
   PROPCTL(NONE) +
   REPLACE   
   

DEFINE LISTENER('$BNKCODE.LSNR') TRPTYPE(TCP) CONTROL(QMGR) +
   PORT($BNKLSNRPORT) +
   REPLACE

SET CHLAUTH('$BNKCODE.TO.SIMA') +
   TYPE(SSLPEERMAP) +
   SSLPEER('CN=$RMQM,O=ISC,C=IR') +
   ADDRESS('$RMQMADD') +
   MCAUSER('$BNKNAME""user') +
   USERSRC(MAP) +
   ACTION(REPLACE)
   
SET CHLAUTH('$BNKCODE.TO.SIMA') +
   TYPE(ADDRESSMAP) +
   ADDRESS('$RMQMADD') +
   MCAUSER('$BNKNAME""user') +
   USERSRC(MAP) +
   ACTION(REPLACE)   
   
   
SET CHLAUTH('SIMA.TO.$BNKCODE') +
   TYPE(SSLPEERMAP) +
   SSLPEER('CN=$RMQM,O=ISC,C=IR') +
   ADDRESS('$RMQMADD') +
   MCAUSER('$BNKNAME""user') +
   USERSRC(MAP) +
   ACTION(REPLACE)
SET CHLAUTH('SIMA.TO.$BNKCODE') +
   TYPE(ADDRESSMAP) +
   ADDRESS('$RMQMADD') +
   MCAUSER('$BNKNAME""user') +
   USERSRC(MAP) +
   ACTION(REPLACE)   
   
SET AUTHREC +
   PROFILE('APP.SVR.CONN') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(CHANNEL) +
   AUTHADD(NONE)   
   
SET AUTHREC +
   PROFILE('$BNKCODE.RES') +
   GROUP('mqm') +
   OBJTYPE(QUEUE) +
   AUTHADD(BROWSE,CHG,CLR,DLT,DSP,GET,INQ,PUT,PASSALL,PASSID,SET,SETALL,SETID)
SET AUTHREC +
   PROFILE('$BNKCODE.RES') +
   GROUP('mquser') +
   OBJTYPE(QUEUE) +
   AUTHADD(PUT,SETALL,SETID)
SET AUTHREC +
   PROFILE('$BNKCODE.RES') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(QUEUE) +
   AUTHADD(PUT,SETALL,SETID)   
   
   
SET AUTHREC +
   PROFILE('SIMA.TO.$BNKCODE') +
   GROUP('mqm') +
   OBJTYPE(CHANNEL) +
   AUTHADD(CHG,DLT,DSP,CTRL,CTRLX)
SET AUTHREC +
   PROFILE('SIMA.TO.$BNKCODE') +
   GROUP('mquser') +
   OBJTYPE(CHANNEL) +
   AUTHADD(DSP,CTRL,CTRLX)
SET AUTHREC +
   PROFILE('SIMA.TO.$BNKCODE') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(CHANNEL) +
   AUTHADD(DSP,CTRL,CTRLX)   
   
SET AUTHREC +
   PROFILE('$BNKCODE.TO.SIMA') +
   GROUP('mquser') +
   OBJTYPE(CHANNEL) +
   AUTHADD(DSP,CTRL,CTRLX)
SET AUTHREC +
   PROFILE('$BNKCODE.TO.SIMA') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(CHANNEL) +
   AUTHADD(DSP,CTRL,CTRLX)   
   
SET AUTHREC +
   PROFILE('$BNKCODE.LSNR') +
   GROUP('mqm') +
   OBJTYPE(LISTENER) +
   AUTHADD(CHG,DLT,DSP,CTRL)
SET AUTHREC +
   PROFILE('$BNKCODE.LSNR') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(LISTENER) +
   AUTHADD(DSP,CTRL)   
   
SET AUTHREC +
   PROFILE('$BNKCODE.REQ') +
   GROUP('mqm') +
   OBJTYPE(QUEUE) +
   AUTHADD(BROWSE,CHG,CLR,DLT,DSP,GET,INQ,PUT,PASSALL,PASSID,SET,SETALL,SETID)
SET AUTHREC +
   PROFILE('$BNKCODE.REQ') +
   GROUP('mquser') +
   OBJTYPE(QUEUE) +
   AUTHADD(BROWSE,GET,INQ,PUT,SET,SETALL,SETID)
SET AUTHREC +
   PROFILE('$BNKCODE.REQ') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(QUEUE) +
   AUTHADD(BROWSE,GET,INQ,PUT,SET,SETALL,SETID)   
   
SET AUTHREC +
   PROFILE('self') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(QMGR) +
   AUTHADD(CONNECT,INQ,SETALL)   
   
SET AUTHREC +
   PROFILE('@class') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(QUEUE) +
   AUTHADD(NONE)   
   
SET AUTHREC +
   PROFILE('DLQ') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(QUEUE) +
   AUTHADD(BROWSE,GET,INQ,PUT,SET,SETALL,SETID)   
   
SET AUTHREC +
   PROFILE('@class') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(QMGR) +
   AUTHADD(NONE)   
   
SET AUTHREC +
   PROFILE('$BNKCODE.TRANS') +
   GROUP('mqm') +
   OBJTYPE(QUEUE) +
   AUTHADD(BROWSE,CHG,CLR,DLT,DSP,GET,INQ,PUT,PASSALL,PASSID,SET,SETALL,SETID)
SET AUTHREC +
   PROFILE('$BNKCODE.TRANS') +
   GROUP('mquser') +
   OBJTYPE(QUEUE) +
   AUTHADD(PUT,PASSALL,PASSID)
SET AUTHREC +
   PROFILE('$BNKCODE.TRANS') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(QUEUE) +
   AUTHADD(PUT,PASSALL)   
   
SET AUTHREC +
   PROFILE('@class') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(CHANNEL) +
   AUTHADD(NONE)   
   
SET AUTHREC +
   PROFILE('ADMIN.SVR.CONN') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(CHANNEL) +
   AUTHADD(NONE)   
   
SET AUTHREC +
   PROFILE('@class') +
   GROUP('$BNKNAME""user') +
   OBJTYPE(LISTENER) +
   AUTHADD(NONE)   
   
REFRESH SECURITY   
   
   " >$BNKNAME.in   
   
echo "MQ script is written in $BNKNAME.in"

sudo -u mqm runmqsc $QM <$BNKNAME.in >$BNKNAME.out

echo "MQ script results is written in $BNKNAME.out"

echo "Bank $BNKNAME is added in $QM queue manager." 

#to copy client certificate to current path
FILE=$CURRENT_PATH/cert$RMQM.arm
if test -f "$FILE"; then
    echo "$FILE exist"
	echo "Wait for adding $BNKNAME certificate..."
	sudo -u mqm cp cert$RMQM.arm /var/mqm/qmgrs/$QM/ssl/
	sudo -u mqm runmqckm -cert -add -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -pw $KEYPASS -label ibmwebspheremq$RMQM -file "/var/mqm/qmgrs/$QM/ssl/cert"$RMQM".arm"
	echo "$BNKNAME's certificate is added."
else
	echo -e "\e[1;31mFile cert$RMQM.arm should be exist in current path!\e[0m"
	echo -e "\e[1;31mBefore running this script receive the file from $RMQM administrator and run script 5-sign-client-certificate-on-server.sh!\e[0m"	
	echo -e "\e[1;33mExpected path : /var/mqm/qmgrs/$RMQM/ssl\e[0m"	
fi

echo "Script ends."


#cat $BNKNAME.out