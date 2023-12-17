FROM openjdk:17-jdk

WORKDIR /app

COPY src /app/src

COPY target/opituvalnik-0.0.1-SNAPSHOT.jar /app/surveyBot.jar

EXPOSE 8080

CMD ["java", "-jar", "surveyBot.jar"]