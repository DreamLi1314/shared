#!/usr/bin/env sh

node -v
npm -v

#echo "exec npm install"
#npm install -registry=https://registry.npm.taobao.org
#
#echo "delete old dist"
#
#if [ -d "./dist" ]; then
#    rm -rf ./dist
#fi

echo "build web dist"

npm run build:prod

echo "build docker image"

docker build -t 161.189.222.190:8080/mlog/blog-statistic-web .

echo "push docker image"

docker login 161.189.222.190:8080 -u pushuser -p 'yiJi123!@#'
docker push 161.189.222.190:8080/mlog/blog-statistic-web:latest
