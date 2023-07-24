#!/usr/bin/env bash


usage=" 
\033[1mUsage:\033[0m
\t$(basename $0) [OPTION]  
\033[1mOptions:\033[0m  
\t-ce, --create        create database 
\t-ct, --connect       connect to database 
\t-de, --delete        delete database
"

if [[ $1 == "-ce"  ||  $1 == "--create" ]];then
	set -eo pipefail
	
	namespace=(`kubectl config view | grep namespace`)
	sed "s/\$NAMESPACE/${namespace[1]}/" mariadb.yaml | kubectl create -f -
	sleep 30
	kubectl create -f ./patients-db.yaml 
	kubectl create -f ./laboratory-db.yaml 
	kubectl create -f ./infirmary-db.yaml

	echo -e "\nAll databases created!"
elif [[ $1 == "-ct" ||  $1 == "--connect" ]];then
	kubectl run mariadb --image mariadb:10.11.3 --command -- sleep 3600 
	sleep 3
	kubectl exec -it mariadb -- bash -c 'mysql -h mariadb-bolnica-1 -u root -p'${MARIADB_PASSWORD} 
	kubectl delete pod mariadb
elif [[ $1 == "-de"  ||  $1 == "--delete" ]];then
	kubectl delete database patients
	kubectl delete database laboratory
	kubectl delete database infirmary

	kubectl delete mariadb mariadb-bolnica-1 

	kubectl delete secret mariadb-bolnica-1-creds

	kubectl delete pvc storage-mariadb-bolnica-1-0
	kubectl delete pvc storage-mariadb-bolnica-1-1
	kubectl delete pvc storage-mariadb-bolnica-1-2

	echo -e "\nAll databases deleted!"
elif [[ $1 == "-h" ||  $1 == "--help" ]];then
	echo -e "$usage"
else
	echo -e "No argument was passed! \nTry $(basename $0) --help\n"
fi

