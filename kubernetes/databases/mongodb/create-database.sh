#!/usr/bin/env bash

set -eo pipefail

namespace=(`kubectl config view | grep namespace`)
database_name=covid
username=username

/usr/local/bin/create-mongodb-cluster.sh ${namespace[1]} $database_name $username ${MONGODB_PASSWORD}
