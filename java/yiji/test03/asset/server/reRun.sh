#!/bin/bash

source /etc/profile
docker-compose down

docker rmi 192.168.100.62:8080/lightning/auth-server:1.0-SNAPSHOT
docker rmi 192.168.100.62:8080/lightning/thunderstorm-api:1.0-SNAPSHOT

docker-compose --compatibility up -d
