FROM alpine:latest

RUN apk --update add openjdk11

COPY ./build/libs/SprintStats-R1.0.1-all.jar /var/www/java/

COPY ./*.json /var/www/java/

#COPY . /var/www/java

WORKDIR /var/www/java

RUN pwd

RUN ls -la

ENTRYPOINT ["java", "-jar", "SprintStats-R1.0.1-all.jar", "-d"]
