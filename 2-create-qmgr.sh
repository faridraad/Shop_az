#!/bin/bash

QM="simatest"
ADMINACCIP="192.168.1.34"
APPACCIP="192.168.1.34"
ADMINLSNRPORT="1414"
APPLSNRPORT="1415"
DLQHPATH=$(pwd)
KEYPASS="Misc@102030"
ADMINKEYPASS="misc@102030"
MQUSERMPASS="misc@102030"

echo "Script starts."

useradd -s /sbin/nologin -m -d /home/mquser mquser

echo -e "$MQUSERMPASS\n$MQUSERMPASS" | passwd mquser

echo "user mquser is added."

sudo -u mqm crtmqm -q -d MY.DEFAULT.XMIT.QUEUE -ll $QM

echo "Queue Manager $QM is created."

sudo -u mqm strmqm $QM

sudo -u mqm echo "
INPUTQ('DLQ') INPUTQM('$QM') +
WAIT(YES) RETRYINT(20)
REASON(MQRC_Q_FULL) ACTION(RETRY) +
RETRY(5)
REASON(MQRC_PUT_INHIBITED) ACTION(RETRY) RETRY(5)
REASON(*) ACTION(FWD) FWDQ('SYSTEM.DEAD.LETTER.QUEUE') FWDQM('$QM') HEADER(YES)" >$QM'qrule'.rul

chmod +rwx $QM'qrule'.rul	

echo "File $QM""qrule.rul is creatde."
	
sudo -u mqm echo "#!/usr/bin/env bash
runmqdlq DLQ $QM < $DLQHPATH/$QM""qrule.rul" >$QM'dlqhandler'.sh

chmod +rwx $QM'dlqhandler'.sh

echo "File $QM""dlqhandler.sh is creatde."

sudo -u mqm echo "
ALTER QMGR +
   CCSID(1208) +
   DEFXMITQ('MY.DEFAULT.XMIT.QUEUE') +   
   CERTVPOL(ANY) +
   CERTLABL('ibmwebspheremq$QM') +
   DEADQ('DLQ') +
   CHLAUTH(ENABLED) +
   SSLKEYR('/var/mqm/qmgrs/$QM/ssl/key$QM') +
   FORCE 
   
DEFINE CHANNEL('ADMIN.SVR.CONN') +
   CHLTYPE(SVRCONN) +
   CERTLABL('ibmwebspheremq$QM') +
   DISCINT(0) +
   MCAUSER('mqm') +
   MONCHL(QMGR) +
   SSLCAUTH(REQUIRED) +
   SSLCIPH('TLS_RSA_WITH_AES_128_CBC_SHA256') +
   SSLPEER('CN=admin$QM, O=ISC, C=IR') +
   TRPTYPE(TCP) +
   REPLACE
   
DEFINE CHANNEL('APP.SVR.CONN') +
   CHLTYPE(SVRCONN) +
   CERTLABL('ibmwebspheremq$QM') +
   DESCR('Server-connection') +
   DISCINT(0) +
   MCAUSER('mquser') +
   MONCHL(QMGR) +
   SSLCAUTH(REQUIRED) +
   SSLCIPH('TLS_RSA_WITH_AES_128_CBC_SHA256') +
   SSLPEER('O=ISC,C=IR') +
   TRPTYPE(TCP) +
   REPLACE 
   
ALTER CHANNEL('SYSTEM.AUTO.RECEIVER') CHLTYPE(RCVR) MCAUSER('mquser')
ALTER CHANNEL('SYSTEM.AUTO.SVRCONN') CHLTYPE(SVRCONN) MCAUSER('mquser')
ALTER CHANNEL('SYSTEM.DEF.AMQP') CHLTYPE(AMQP) MCAUSER('mquser')
ALTER CHANNEL('SYSTEM.DEF.CLUSRCVR') CHLTYPE(CLUSRCVR) MCAUSER('mquser')
ALTER CHANNEL('SYSTEM.DEF.CLUSSDR') CHLTYPE(CLUSSDR) MCAUSER('mquser')
ALTER CHANNEL('SYSTEM.DEF.RECEIVER') CHLTYPE(RCVR) MCAUSER('mquser')
ALTER CHANNEL('SYSTEM.DEF.REQUESTER') CHLTYPE(RQSTR) MCAUSER('mquser')
ALTER CHANNEL('SYSTEM.DEF.SENDER') CHLTYPE(SDR) MCAUSER('mquser')
ALTER CHANNEL('SYSTEM.DEF.SERVER') CHLTYPE(SVR) MCAUSER('mquser')
ALTER CHANNEL('SYSTEM.DEF.SVRCONN') CHLTYPE(SVRCONN) MCAUSER('mquser')

DEFINE QLOCAL('DLQ') +
   GET(ENABLED) +
   PUT(ENABLED) +
   ACCTQ(QMGR) +
   DEFPSIST(YES) +
   MAXDEPTH(5000) +
   MAXMSGL(4194304) +
   SCOPE(QMGR) +
   SHARE +
   DEFPRESP(ASYNC) +
   PROPCTL(V6COMPAT) +
   USAGE(NORMAL) +
   REPLACE

DEFINE SERVICE('DLQH') +
   CONTROL(QMGR) +
   SERVTYPE(SERVER) +
   STARTCMD('$DLQHPATH/$QM""dlqhandler.sh') +
   STARTARG(' ') +
   STOPCMD('+MQ_INSTALL_PATH+/bin/amqsstop') +
   STOPARG('-m +QMNAME+ -p +MQ_SERVER_PID+') +
   STDOUT('+MQ_DATA_PATH+/qmgrs/$QM/errors/dlqh.out') +
   STDERR('+MQ_DATA_PATH+/qmgrs/$QM/errors/dlqh.err') +
   DESCR('dead letter queue handler as server service') +
   REPLACE

SET AUTHREC +
   PROFILE('DLQH') +
   GROUP('mqm') +
   OBJTYPE(SERVICE) +
   AUTHADD(CHG,DLT,DSP,CTRL)
   
SET AUTHREC +
   PROFILE('DLQ') +
   GROUP('mqm') +
   OBJTYPE(QUEUE) +
   AUTHADD(BROWSE,CHG,CLR,DLT,DSP,GET,INQ,PUT,PASSALL,PASSID,SET,SETALL,SETID)
   
SET AUTHREC +
   PROFILE('DLQ') +
   GROUP('mquser') +
   OBJTYPE(QUEUE) +
   AUTHADD(BROWSE,GET,INQ,PUT,SET,SETALL,SETID)


SET AUTHREC +
   PROFILE('SYSTEM.MQEXPLORER.REPLY.MODEL') +
   GROUP('mquser') +
   OBJTYPE(QUEUE) +
   AUTHADD(BROWSE,CHG,CLR,DLT,DSP,GET,INQ,PUT,PASSALL,PASSID,SET,SETALL,SETID)
   
SET AUTHREC +
   PROFILE('self') +
   GROUP('mquser') +
   OBJTYPE(QMGR) +
   AUTHADD(CONNECT,INQ,SETALL)   
   
SET AUTHREC +
   PROFILE('@class') +
   GROUP('mquser') +
   OBJTYPE(QUEUE) +
   AUTHADD(NONE)   
   
SET AUTHREC +
   PROFILE('DLQ') +
   GROUP('mquser') +
   OBJTYPE(QUEUE) +
   AUTHADD(BROWSE,GET,INQ,PUT,SET,SETALL,SETID)   
   
SET AUTHREC +
   PROFILE('@class') +
   GROUP('mquser') +
   OBJTYPE(QMGR) +
   AUTHADD(NONE)   

SET AUTHREC +
   PROFILE('@class') +
   GROUP('mquser') +
   OBJTYPE(CHANNEL) +
   AUTHADD(NONE)   

SET AUTHREC +
   PROFILE('@class') +
   GROUP('mquser') +
   OBJTYPE(LISTENER) +
   AUTHADD(NONE)   

   
DEFINE LISTENER('ADMIN.SVR.LSNR') TRPTYPE(TCP) CONTROL(QMGR) +
   PORT($ADMINLSNRPORT) +
   REPLACE
   
DEFINE LISTENER('APP.SVR.LSNR') TRPTYPE(TCP) CONTROL(QMGR) +
   PORT($APPLSNRPORT) +
   REPLACE   
   
SET CHLAUTH('ADMIN.SVR.CONN') +
   TYPE(SSLPEERMAP) +
   SSLPEER('CN=admin$QM,O=ISC,C=IR') +
   ADDRESS('$ADMINACCIP') +
   MCAUSER('mqm') +
   USERSRC(MAP) +
   ACTION(REPLACE) 
   
   
SET CHLAUTH('APP.SVR.CONN') +
   TYPE(SSLPEERMAP) +
   SSLPEER('CN=$QM,O=ISC,C=IR') +
   ADDRESS('$APPACCIP') +
   MCAUSER('mquser') +
   USERSRC(MAP) +
   ACTION(REPLACE)
   
SET CHLAUTH('APP.SVR.CONN') +
   TYPE(ADDRESSMAP) +
   ADDRESS('$APPACCIP') +
   MCAUSER('mquser') +
   USERSRC(MAP) +
   ACTION(REPLACE)   
   
SET CHLAUTH('SYSTEM.*') +
   TYPE(ADDRESSMAP) +
   ADDRESS('*') +
   USERSRC(NOACCESS) +
   WARN(NO) +
   ACTION(REPLACE)
SET CHLAUTH('*') +
   TYPE(ADDRESSMAP) +
   ADDRESS('*') +
   USERSRC(NOACCESS) +
   WARN(NO) +
   ACTION(REPLACE)

   
SET AUTHREC +
   PROFILE('ADMIN.SVR.CONN') +
   GROUP('mqm') +
   OBJTYPE(CHANNEL) +
   AUTHADD(CHG,DLT,DSP,CTRL,CTRLX)   
   
SET AUTHREC +
   PROFILE('ADMIN.SVR.CONN') +
   GROUP('mquser') +
   OBJTYPE(CHANNEL) +
   AUTHADD(NONE)
   
SET AUTHREC +
   PROFILE('ADMIN.SVR.LSNR') +
   GROUP('mqm') +
   OBJTYPE(LISTENER) +
   AUTHADD(CHG,DLT,DSP,CTRL)   

SET AUTHREC +
   PROFILE('APP.SVR.CONN') +
   GROUP('mqm') +
   OBJTYPE(CHANNEL) +
   AUTHADD(CHG,DLT,DSP,CTRL,CTRLX)
   
SET AUTHREC +
   PROFILE('APP.SVR.CONN') +
   GROUP('mquser') +
   OBJTYPE(CHANNEL) +
   AUTHADD(DSP,CTRL,CTRLX)   
   " >$QM.in   

echo "MQ scripts are written in $QM.in"

sudo -u mqm runmqsc $QM <$QM.in >$QM.out

echo "MQ script results are written in $QM.out"


sudo -u mqm endmqm $QM

echo "Wait for keystore creation..."

sudo -u mqm runmqckm -keydb -create -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -type cms -pw $KEYPASS -stash
sudo -u mqm runmqckm -cert -create  -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -pw $KEYPASS -label ibmwebspheremq$QM -dn "CN=$QM,O=ISC,C=IR" -expire 730 -size 4096
sudo -u mqm runmqckm -cert -extract -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -pw $KEYPASS -label ibmwebspheremq$QM -target "/var/mqm/qmgrs/$QM/ssl/cert$QM.arm"

# sudo -u mqm runmqckm -cert -export -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -pw $KEYPASS -label ibmwebspheremq$QM -type jks -target "/var/mqm/qmgrs/$QM/ssl/key$QM.jks"

sudo -u mqm runmqckm -keydb -create -db "/var/mqm/qmgrs/$QM/ssl/keyadmin$QM.kdb" -type cms -pw $ADMINKEYPASS -stash
sudo -u mqm runmqckm -cert -create  -db "/var/mqm/qmgrs/$QM/ssl/keyadmin$QM.kdb" -pw $ADMINKEYPASS -label ibmwebspheremqadmin$QM -dn "CN=admin$QM,O=ISC,C=IR" -expire 730 -size 4096
sudo -u mqm runmqckm -cert -extract -db "/var/mqm/qmgrs/$QM/ssl/keyadmin$QM.kdb" -pw $ADMINKEYPASS -label ibmwebspheremqadmin$QM -target "/var/mqm/qmgrs/$QM/ssl/certadmin$QM.arm"

sudo -u mqm runmqckm -cert -add -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -pw $KEYPASS -label ibmwebspheremqadmin$QM -file "/var/mqm/qmgrs/$QM/ssl/certadmin$QM.arm"
sudo -u mqm runmqckm -cert -add -db "/var/mqm/qmgrs/$QM/ssl/keyadmin$QM.kdb" -pw $ADMINKEYPASS -label ibmwebspheremq$QM -file "/var/mqm/qmgrs/$QM/ssl/cert$QM.arm"

sudo -u mqm runmqckm -cert -export  -db "/var/mqm/qmgrs/$QM/ssl/key$QM.kdb" -pw $KEYPASS -label ibmwebspheremq$QM -type cms -target "/var/mqm/qmgrs/$QM/ssl/key$QM.jks" -target_type jks -target_pw $KEYPASS
sudo -u mqm runmqckm -cert -export  -db "/var/mqm/qmgrs/$QM/ssl/keyadmin$QM.kdb" -pw $ADMINKEYPASS -label ibmwebspheremqadmin$QM -type cms -target "/var/mqm/qmgrs/$QM/ssl/keyadmin$QM.jks" -target_type jks -target_pw $ADMINKEYPASS

echo "key creation finished."

sudo -u mqm strmqm $QM

echo "Script ends."
#cat $QM.out