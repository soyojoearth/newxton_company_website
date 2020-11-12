FROM mysql:8

WORKDIR /

COPY ./nxtframework.sql /docker-entrypoint-initdb.d/nxtframework.sql

CMD ["mysqld", "--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci"]

