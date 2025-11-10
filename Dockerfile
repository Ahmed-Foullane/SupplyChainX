FROM openjdk:17-jdk-slim
EXPOSE 7070

ADD target/SupplyChainX.war /app/SupplyChainX.war

WORKDIR /app

ENTRYPOINT ["java", "-jar", "SupplyChainX.war"]