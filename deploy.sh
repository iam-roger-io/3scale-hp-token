#!/bin/bash


export HP_TOKEN_HOME=$(pwd)

rm -rfv /tmp/hp-token

mkdir /tmp/hp-token

cp -r ./ /tmp/hp-token

cd /tmp/hp-token

rm -rfv ./target
rm ./src/main/resources/application.properties
rm ./src/main/resources/logback.xml 

mvn clean install
podman build -t quay.apps.mgmt.telcostack.br.telefonica.com/redhat/3scale-hp-token:$1 .
podman push quay.apps.mgmt.telcostack.br.telefonica.com/redhat/3scale-hp-token:$1 --tls-verify=false

#oc project 3scale-custom-policies


cd  $HP_TOKEN_HOME



