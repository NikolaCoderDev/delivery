FROM openjdk:17
EXPOSE 8003
ADD target/order-service.jar order-service.jar
ENTRYPOINT ["java","-jar","/order-service.jar"]

#docker build -t order-service:3.0.4 . (обязательно напиши название образа и тега вместо просто docker build .)
#docker run -p 8003:8003 order-service:3.0.4 .
#docker ps