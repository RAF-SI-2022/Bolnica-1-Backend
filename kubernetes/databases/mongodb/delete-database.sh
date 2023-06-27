#!/usr/bin/env bash

helm uninstall covid

kubectl delete pvc datadir-covid-mongodb-sharded-configsvr-0 
kubectl delete pvc datadir-covid-mongodb-sharded-shard0-data-0
kubectl delete pvc datadir-covid-mongodb-sharded-shard1-data-0

echo "Mongo database deleted!"
