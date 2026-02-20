#!/bin/bash

source /etc/profile

# nohup java -jar sb-rocketmq-main-api-1.0.0-SNAPSHOT.jar --spring.profiles.active=prod --app.tme.basePath=https://test.y.qq.com --mysql.addr=125.76.228.196:13306 --mysql.db=db_test --mysql.username=root --mysql.pwd=geoVis0605! --rocketmq.addr=125.76.228.196:9876 --redis.host=125.76.228.196 --redis.port=16379 --redis.pwd=tme123 &
nohup java -jar sb-rocketmq-main-api-1.0.0-SNAPSHOT.jar --spring.profiles.active=prod > dev-nohup &

tailf dev-nohup