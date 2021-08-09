# Docker image for pmrs-xa
# VERSION 0.0.1
# Author: Jack Li
# base image using java8
FROM java:8
# author
MAINTAINER mlogcn <lishuai@mlogcn.com>

# temp folder. link locale:/var/lib/docker to container:/tmp
VOLUME /tmp

# add jar to container and renaming to app.jar
ARG JAR_FILE
ADD ./target/test01-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS="\
   -Xms4g \
   -Xmx4g \
   "

# run command
ENTRYPOINT java ${JAVA_OPTS} \
   -Djava.security.egd=file:/dev/./urandom \
   -jar /app.jar --spring.profiles.active=prod
