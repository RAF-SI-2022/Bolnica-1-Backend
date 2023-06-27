#!/usr/bin/env bash

kubectl delete database patients
kubectl delete database laboratory
kubectl delete database infirmary

kubectl delete mariadb mariadb-bolnica-1 

kubectl delete secret mariadb-bolnica-1-creds

kubectl delete pvc storage-mariadb-bolnica-1-0
kubectl delete pvc storage-mariadb-bolnica-1-1
kubectl delete pvc storage-mariadb-bolnica-1-2

echo "All databases deleted!"
