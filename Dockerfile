FROM openjdk:8-jre

WORKDIR /

COPY ./target/supercompanyapi-release.jar /run.jar

EXPOSE 8080

HEALTHCHECK --interval=1m --timeout=10s --start-period=10s --retries=3 CMD curl -f http://127.0.0.1:8080/api/hello || exit 1

CMD ["java", "-jar", "-Xms500m","-Xmx1500m", "/run.jar"]

