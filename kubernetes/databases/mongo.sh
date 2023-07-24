#!/usr/bin/env bash

usage=" 
\033[1mUsage:\033[0m
\t$(basename $0) [OPTION]  
\033[1mOptions:\033[0m  
\t-ce, --create        create database 
\t-ct, --connect       connect to database 
\t-de, --delete        delete database
"

database_name=covid
username=username

if [[ $1 == "-ce" || $1 == "--create" ]];then
	set -eo pipefail
	namespace=(`kubectl config view | grep namespace`)

	/usr/local/bin/create-mongodb-cluster.sh ${namespace[1]} $database_name $username ${MONGODB_PASSWORD}
elif [[ $1 == "-ct" || $1 == "--connect" ]];then
	kubectl run mongo --image docker.io/bitnami/mongodb-sharded:6.0.6-debian-11-r3 --command -- sleep 3600
	sleep 10
	kubectl exec -it mongo -- bash -c 'mongosh mongodb://'$username':'${MONGODB_PASSWORD}'@'$database_name'-mongodb-sharded:27017'
	kubectl delete pod mongo
elif [[ $1 == "-de" || $1 == "--delete" ]];then
	helm uninstall $database_name

	kubectl delete pvc datadir-$database_name-mongodb-sharded-configsvr-0 
	kubectl delete pvc datadir-$database_name-mongodb-sharded-shard0-data-0
	kubectl delete pvc datadir-$database_name-mongodb-sharded-shard1-data-0

	echo -e "\nMongo database deleted!"
elif [[ $1 == "-h" || $1 == "--help" ]];then
	echo -e "$usage"
else
	echo -e "No argument was passed! \nTry $(basename $0) --help\n"
fi
