FROM openjdk:17-jdk
LABEL authors="acamus"
COPY store/target/store-0.1.8-SNAPSHOT.jar store_api.jar
ENTRYPOINT ["java", "-jar", "store_api.jar"]