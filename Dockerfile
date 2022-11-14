FROM openjdk:18
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw package
EXPOSE 8999
ENTRYPOINT ["java","-jar","target/thatstory-multimedia.jar"] 