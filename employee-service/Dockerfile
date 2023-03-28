FROM openjdk:17
EXPOSE 8002
ADD target/employee-service.jar employee-service.jar
ENTRYPOINT ["java","-jar","/employee-service.jar"]

#docker build -t employee-service:3.0.4 . (обязательно напиши название образа и тега вместо просто docker build .)
#docker run -p 8002:8002 employee-service:3.0.4 .
#docker ps