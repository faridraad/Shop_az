#!/bin/bash

#colors----> 36:Cyan|35:Magenta|34:Blue|33:Yellow|32:Green|31:Red 
CURRENT_PATH=$(pwd)
CURRENTVER=$(sed 's/.\{1\}$//' <<< $(/lib64/libc.so.6  | head -1 | awk '/version/ {print $7}'))
REQUIREDVER="2.14"
LD_LIB_PATH="/lib64"
MQMPASS="Misc@102030"

if [ "$(printf '%s\n' "$REQUIREDVER" "$CURRENTVER" | sort -V | head -n1)" = "$REQUIREDVER" ]; then 
	echo -e "\e[1;32mGLIBC VERSION IS GREATER THAN OR EQUAL TO $REQUIREDVER\e[0m"

else

	echo -e "\e[1;31mGLIBC VERSION IS LESS THAN $REQUIREDVER!\e[0m"
	
	echo -e "\e[1;31mYOU HAVE TO INSTALL glibc-$REQUIREDVER OR GREATER VERSION THAN THAT!\e[0m"
	
	echo -e "\e[1;36mglibc-$REQUIREDVER IS INSTALLING . . . \e[0m"
	
	tar xvfz glibc-2.14.tar.gz

	chown -R root:root glibc-2.14

	cd ./glibc-2.14

	mkdir build

	cd ./build

	../configure --prefix=/opt/glibc-2.14

	make -j4

	sudo make install

	grep -q LD_LIBRARY_PATH /etc/environment || echo 'LD_LIBRARY_PATH="/opt/glibc-2.14/lib"' >> /etc/environment
	
	source /etc/environment

	LD_LIB_PATH="/opt/glibc-2.14/lib"

	sudo ln -sf /opt/glibc-2.14/lib/libc-2.14.so /lib64/libc.so.6

	cd $CURRENT_PATH
	
fi

groupadd mqm

grep mqm /etc/group

useradd -g mqm -s /bin/bash -d /home/mqm -m mqm

echo -e "$MQMPASS\n$MQMPASS" | passwd mqm

id mqm

rm -rf MQServer

tar -zxvf mqadv_dev910_linux_x86-64.tar.gz

chown -R root:root MQServer

cd ./MQServer/

bash ./mqlicense.sh -accept

LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesRuntime-9.1.0-0.x86_64.rpm
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesJRE-9.1.0-0.x86_64.rpm
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesJava-9.1.0-0.x86_64.rpm
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesServer-9.1.0-0.x86_64.rpm
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesFTBase-9.1.0-0.x86_64.rpm     
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesFTAgent-9.1.0-0.x86_64.rpm    
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesFTService-9.1.0-0.x86_64.rpm  
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesFTLogger-9.1.0-0.x86_64.rpm   
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesFTTools-9.1.0-0.x86_64.rpm    
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesAMQP-9.1.0-0.x86_64.rpm       
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesAMS-9.1.0-0.x86_64.rpm  
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesWeb-9.1.0-0.x86_64.rpm
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesXRService-9.1.0-0.x86_64.rpm
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesBCBridge-9.1.0-0.x86_64.rpm   
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesSFBridge-9.1.0-0.x86_64.rpm
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesExplorer-9.1.0-0.x86_64.rpm   
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesClient-9.1.0-0.x86_64.rpm     
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesGSKit-9.1.0-0.x86_64.rpm      
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMan-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_cs-9.1.0-0.x86_64.rpm 
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_de-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_es-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_fr-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_hu-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_it-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_ja-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_ko-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_pl-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_pt-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_ru-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_Zh_CN-9.1.0-0.x86_64.rpm
# LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesMsg_Zh_TW-9.1.0-0.x86_64.rpm
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesSamples-9.1.0-0.x86_64.rpm
LD_LIBRARY_PATH=$LD_LIB_PATH rpm -ivh MQSeriesSDK-9.1.0-0.x86_64.rpm

rpm -qa | grep -i mq

ls -dl /etc/opt/mqm

ls -dl /etc/opt/mqm/*

cat /etc/opt/mqm/mqinst.ini

ls -dl /opt/mqm

ls -dl /opt/mqm/*

ls -dl /var/mqm

ls -dl /var/mqm/*

ls -l /usr/bin/*mq*

dspmqver

. /opt/mqm/bin/setmqenv -n Installation1

/opt/mqm/bin/setmqinst -i -p /opt/mqm

cat /etc/opt/mqm/mqinst.ini

ls -l /usr/bin/*mq*

dspmqver

export MQ_INSTALLATION_PATH=/opt/mqm

export PATH=$PATH:$MQ_INSTALLATION_PATH/java/bin:$MQ_INSTALLATION_PATH/samp/bin:$MQ_INSTALLATION_PATH/samp/jms/samples:

export CLASSPATH=$CLASSPATH:

dspmqver -f 2

sudo -u mqm -i set | grep -i mq

sudo -u mqm -i . /opt/mqm/bin/setmqenv -n Installation1

sudo -u mqm -i export PATH=$PATH:$MQ_INSTALLATION_PATH/java/bin:$MQ_INSTALLATION_PATH/samp/bin:$MQ_INSTALLATION_PATH/samp/jms/samples:

sudo -u mqm -i export CLASSPATH=$CLASSPATH:

sudo -u mqm -i set | grep -i mq

cd /var/mqm

cat mqs.ini

cd /var/mqm/qmgrs

ls -al
cd /var/mqm/log
ls -al

cd $CURRENT_PATH

echo "MQ installation finished."

  