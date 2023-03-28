FROM openjdk:17
EXPOSE 8001
ADD target/gateway-service.jar gateway-service.jar
ENTRYPOINT ["java","-jar","/gateway-service.jar"]

#docker build -t gateway-service:3.0.4 .

#docker run -p 8001:8001 gateway-service:3.0.4