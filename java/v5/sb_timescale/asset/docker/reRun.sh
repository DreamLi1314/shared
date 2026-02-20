#!/bin/bash

source /etc/profile
docker-compose down

docker-compose pull

docker-compose --compatibility up -d
