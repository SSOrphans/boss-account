from amazoncorretto:11.0.11
copy ./boss-account-service/target/boss-account-service-0.0.1-SNAPSHOT.jar /app/
entrypoint java -jar app/boss-account-service-0.0.1-SNAPSHOT.jar
