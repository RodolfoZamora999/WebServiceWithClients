FROM openjdk:17

WORKDIR /usr/src/myapp

EXPOSE 8443

COPY target/web-service.jar .

CMD ["java", "-jar", "web-service.jar"]