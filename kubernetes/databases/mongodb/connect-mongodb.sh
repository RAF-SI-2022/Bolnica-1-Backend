#!/usr/bin/env bash

kubectl run mongo --image docker.io/bitnami/mongodb-sharded:6.0.6-debian-11-r3 --command -- sleep 3600

sleep 3

kubectl exec -it mongo -- bash -c 'mongosh mongodb://username:'${MONGODB_PASSWORD}'@covid-mongodb-sharded:27017'

kubectl delete pod mongo
