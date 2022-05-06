# Build stage
FROM maven:3.6.0-jdk-11-slim AS build
COPY "src" "app/src"
COPY "pom.xml" "app/"
RUN mvn -f "app/pom.xml" clean package

# Package stage
FROM openjdk:11-jre-slim
COPY --from=build "app/target/bank-products-bootcoin*.jar" "service.jar"
#EXPOSE 8761
ENTRYPOINT ["java","-jar","service.jar"]