#!/usr/bin/env sh

# build docker image

echo chmod mvnw
chmod 777 ./mvnw

echo compile and package first
./mvnw clean verify -Dmaven.test.skip=true -s /opt/apache-maven-3.5.4/conf/settings.xml

#mvn clean verify -Dmaven.test.skip=true

#echo build app docker image
#
#./mvnw dockerfile:build
#./mvnw dockerfile:push
