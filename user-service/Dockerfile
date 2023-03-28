FROM openjdk:17
EXPOSE 8004
ADD target/user-service.jar user-service.jar
ENTRYPOINT ["java","-jar","/user-service.jar"]

#docker build -t user-service:3.0.4 . (обязательно напиши название образа и тега вместо просто docker build .)
#docker run -p 8003:8003 user-service:3.0.4 .
#docker ps