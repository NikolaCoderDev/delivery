FROM openjdk:17
EXPOSE 8888
ADD target/config-service.jar config-service.jar
ENTRYPOINT ["java","-jar","/config-service.jar"]

#docker build -t config-service:3.0.4 .
#docker run -p 8888:8888 config-service:3.0.4
#docker ps