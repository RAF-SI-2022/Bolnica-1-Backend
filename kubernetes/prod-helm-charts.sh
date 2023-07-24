#!/usr/bin/env bash

if [[ $1 == "-i" ]];then
	if [[ $2 == "-b" ]];then
		helm install bolnica-1-backend b-1-backend-chart --set namespace=bolnica-1-prod --set image.tag=prod --set domain=bolnica-1.k8s.elab.rs -n bolnica-1-prod
	elif [[ $2 == "-f" ]];then
		helm install bolnica-1-frontend b-1-frontend-chart --set namespace=bolnica-1-prod --set domain=bolnica-1.k8s.elab.rs --set image.tag=prod -n bolnica-1-prod
	else
		helm install bolnica-1-frontend b-1-frontend-chart --set namespace=bolnica-1-prod --set domain=bolnica-1.k8s.elab.rs --set image.tag=prod -n bolnica-1-prod
		helm install bolnica-1-backend b-1-backend-chart --set namespace=bolnica-1-prod --set image.tag=prod --set domain=bolnica-1.k8s.elab.rs -n bolnica-1-prod
	fi
	echo "Production helm charts installed!"
elif [[ $1 == "-u" ]];then
	if [[ $2 == "-b" ]];then
		helm uninstall bolnica-1-backend
	elif [[ $2 == "-f" ]];then
		helm uninstall bolnica-1-frontend
	else
		helm uninstall bolnica-1-backend
		helm uninstall bolnica-1-frontend
	fi
	echo "Production helm charts uninstalled!"
elif [[ $1 == "-h" ]];then
	echo -e "\033[1mUsage:\033[0m\n\t./prod-helm-charts.sh [OPTION]\n\033[1mOptions:\033[0m\n\t-i    install helm charts\n\t-u    uninstall helm charts"
else
	echo "No argument was passed!"
fi
