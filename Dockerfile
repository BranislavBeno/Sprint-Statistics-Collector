FROM alpine:latest

# Install jdk
RUN apk add --no-cache openjdk11

COPY ./build/libs/SprintStatsCollector.jar /app/
COPY ./conf/application.properties /app/conf/

WORKDIR /app

ENV APP_USER user
ENV APP_PASSWD passwd

CMD /usr/bin/java -jar SprintStatsCollector.jar -u $APP_USER -p $APP_PASSWD -d
