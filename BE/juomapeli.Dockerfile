FROM openjdk:17

EXPOSE 8080

WORKDIR /usr/src/app

COPY . .

RUN ./mvnw package

CMD ["java", "-jar", "./target/JuomaPeli-0.0.1-SNAPSHOT.jar"]