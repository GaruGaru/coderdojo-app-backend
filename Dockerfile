FROM gradle:jdk8-alpine

USER root

RUN mkdir -p /app

ADD . /app

WORKDIR /app

RUN ./gradlew clean fatJar

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost/probe || exit 1

CMD java -jar build/libs/coderdojo-app-backend-all-1.0-SNAPSHOT.jar

