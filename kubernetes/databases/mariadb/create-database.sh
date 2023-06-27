#!/usr/bin/env bash

set -eo pipefail

namespace=(`kubectl config view | grep namespace`)

export NAMESPACE=${namespace[1]}

sed "s/\$NAMESPACE/$NAMESPACE/" mariadb.yaml| kubectl create -f -

sleep 30

kubectl create -f ./patients-db.yaml 
kubectl create -f ./laboratory-db.yaml 
kubectl create -f ./infirmary-db.yaml

echo "All databases created!"
