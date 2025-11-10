FROM openjdk:17-jdk-slim

LABEL maintainer="foullane"
EXPOSE 7070

WORKDIR /app
COPY target/SupplyChainX.war /app/SupplyChainX.war

ENTRYPOINT ["java", "-jar", "SupplyChainX.war"]
