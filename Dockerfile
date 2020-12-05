FROM openjdk:8-jre

WORKDIR /

COPY ./target/nxtframework-release.jar /run.jar

ENV MALLOC_ARENA_MAX=4

EXPOSE 8080

HEALTHCHECK --interval=1m --timeout=10s --start-period=10s --retries=3 CMD curl -f http://127.0.0.1:8080/api/hello || exit 1

CMD ["java", "-Xms500m","-Xmx1500m", "-jar", "/run.jar"]

