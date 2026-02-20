#!/bin/bash

source /etc/profile
docker-compose  down

# docker rmi javafamily/xxl-job-admin-mysql:2.3.2-SNAPSHOT
docker rmi 192.168.100.62:8080/lightning/xxl-executor:1.0-SNAPSHOT
docker rmi 192.168.100.62:8080/lightning/companyapi-xxl-executor:1.0-SNAPSHOT

docker-compose --compatibility up -d
