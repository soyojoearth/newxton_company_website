FROM openjdk:8-jre

WORKDIR /

COPY ./target/companywebsite-release.jar /run.jar

EXPOSE 8080

CMD ["java", "-jar", "-Xms500m","-Xmx1500m", "/run.jar"]

