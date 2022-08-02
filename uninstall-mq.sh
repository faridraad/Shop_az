#!/bin/bash

echo -e "\e[1;31mALL MQ packages will be uninstalled and all data will be removed. Are you sure to continue?\e[0m y/n"
read -t 10 YN || exit

if [[ $YN == "y" || $YN == "Y" || $YN == "" ]]; then

	echo "MQ is uninstalling . . ."

	rpm -ev $(rpm -qa MQSeries* | grep "9\.1\.0\-0")    #uninstall all mq packages

	echo "MQ packages are uninstalled."

	userdel --remove mqm

	echo "User mqm is removed."

	rm -rf /var/mqm

	echo "MQ data is removed."
	
	rm -rf MQServer

	echo "MQ uninstallation finished."
	
else 
	exit
fi


  