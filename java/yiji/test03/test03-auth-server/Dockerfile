# Docker image for pmrs-xa
# VERSION 0.0.1
# Author: Jack Li
# base image using java8
#FROM openjdk:8-jre-alpine
FROM java:8

LABEL maintainer="mlogcn lishuai@mlogcn.com"
LABEL author="Jack Li"

# temp folder. link locale:/var/lib/docker to container:/tmp
#VOLUME /tmp

# add jar to container and renaming to app.jar
ARG JAR_FILE
ADD ./target/${JAR_FILE} app.jar

ENV JAVA_OPTS="\
   -Xms4g \
   -Xmx4g \
   "

ENV ACTIVE_PROFILE prod

ENV PG_ADDR ""
ENV PG_DB ""
ENV PG_USER ""
ENV PG_PWD ""
ENV REDIS_HOST ""
ENV REDIS_PORT ""
ENV REDIS_PWD ""
ENV MINIO_ENDPOINT ""
ENV MINIO_USER ""
ENV MINIO_PWD ""
ENV FEISHU_TOKEN ""

# run command
ENTRYPOINT java ${JAVA_OPTS} \
   -Djava.security.egd=file:/dev/./urandom \
   -jar /app.jar --spring.profiles.active=${ACTIVE_PROFILE}
