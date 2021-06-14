from amazoncorretto:11.0.11
copy ./boss-account/target/boss-account-0.0.1-SNAPSHOT.jar /app/
entrypoint java -jar app/boss-account-0.0.1-SNAPSHOT.jar
