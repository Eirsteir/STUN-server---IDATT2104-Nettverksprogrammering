FROM adoptopenjdk/openjdk11:alpine-jre


VOLUME /tmp
ADD target/*.jar app.jar

EXPOSE 3478
EXPOSE 3479
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
