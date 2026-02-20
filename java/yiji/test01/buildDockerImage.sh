#!/usr/bin/env sh

echo chmod mvnw
chmod 777 ./mvnw

echo compile and package first
./mvnw clean package -Dmaven.test.skip=true

docker build -t registry.aws.mlogcn.com/mlog/test01 .

echo "push docker image"

docker login registry.aws.mlogcn.com -u admin -p 'mN!8LCB3&YHcM20Z'
docker push registry.aws.mlogcn.com/mlog/test01
docker logout
