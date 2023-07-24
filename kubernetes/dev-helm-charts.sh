#!/usr/bin/env bash

if [[ $1 == "-i" ]];then 
	if [[ $2 == "-b" ]];then
		helm install bolnica-1-backend b-1-backend-chart
	elif [[ $2 == "-f" ]];then
		helm install bolnica-1-frontend b-1-frontend-chart
	else
		helm install bolnica-1-backend b-1-backend-chart
		helm install bolnica-1-frontend b-1-frontend-chart
	fi
	echo "Development helm charts installed!"
elif [[ $1 == "-u" ]];then
	if [[ $2 == "-b" ]];then
		helm uninstall bolnica-1-backend
	elif [[ $2 == "-f" ]];then
		helm uninstall bolnica-1-frontend
	else
		helm uninstall bolnica-1-backend
		helm uninstall bolnica-1-frontend
	fi
	echo "Development helm charts uninstalled!"
elif [[ $1 == "-h" ]];then
	echo -e "\033[1mUsage:\033[0m\n\t./dev-helm-charts.sh [OPTION]\n\033[1mOptions:\033[0m\n\t-i    install helm charts\n\t-u    uninstall helm charts"
else
	echo "No argument was passed!"
fi

