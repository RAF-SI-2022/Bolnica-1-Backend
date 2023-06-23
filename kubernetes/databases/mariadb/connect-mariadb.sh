#!/usr/bin/env bash

kubectl run mariadb --image mariadb:10.11.3 --command -- sleep 3600 

sleep 3

kubectl exec -it mariadb -- bash -c 'mysql -h mariadb-bolnica-1 -u root -p'${MARIADB_PASSWORD} 

kubectl delete pod mariadb
