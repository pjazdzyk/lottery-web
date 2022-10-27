FROM openjdk:17-jdk-slim-buster
EXPOSE 8000
COPY /target/lottery-web-*.jar /lotto-web.jar
ENTRYPOINT ["java","-jar","/lotto-web.jar"]