创建镜像

1、执行以下命令创建Docker数据库镜像

docker build --tag nxtmysql:0.1.0 .

2、执行以下命令启动数据库
docker-compose -f docker-compose-nxtmysql.yml up

3、默认数据库用户名密码在docker-compose-nxtmysql.yml文件中配置


======================


强制清空所有docker 容器

docker rm -f $(docker ps -aq)

强制清空所有docker 镜像

docker rmi -f $(docker images -q)



