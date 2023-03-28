FROM openjdk:17
EXPOSE 8000
ADD target/discovery-service.jar discovery-service.jar
ENTRYPOINT ["java","-jar","/discovery-service.jar"]

#docker build -t discovery-service:3.0.4 .
#docker run -p 8000:8000 discovery-service:3.0.4
#docker ps