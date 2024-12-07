FROM openjdk:17
EXPOSE 9003
ADD "./target/bpicustomer-0.0.1-SNAPSHOT.jar" "customer.jar"
ENTRYPOINT ["java", "-jar", "customer.jar"]