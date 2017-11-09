FROM gradle:jdk8-alpine

USER root

RUN mkdir -p /app

ADD . /app

WORKDIR /app

RUN ./gradlew clean fatJar

CMD java -jar build/libs/coderdojo-app-backend-all-1.0-SNAPSHOT.jar

