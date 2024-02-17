FROM --platform=$BUILDPLATFORM  eclipse-temurin:17
LABEL author=apc
ENV DATABASE_URL jdbc:mysql://db-bank:3306/db-bank
ENV DATABASE_USERNAME root
ENV DATABASE_PASSWORD root
ENV AXON_SERVER http://localhost:8024
COPY target/electronic-bank-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]