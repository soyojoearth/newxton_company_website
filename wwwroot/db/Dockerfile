FROM mysql:8

WORKDIR /

COPY ./database.sql /docker-entrypoint-initdb.d/database.sql

CMD ["mysqld", "--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci"]

