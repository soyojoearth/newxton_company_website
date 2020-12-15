---
typora-copy-images-to: ./gitbook_resource
---

这是一个开源免费的**电商独立站**系统，基于Java Springboot，Api文档齐全，让小公司和个人在建立电商品牌时，大大降低技术成本。

支持PC、H5、小程序、Android、iOS。



#### **基于本项目做二开，在成本、效率、规范性上可超越”世界上最好的语言“。**



#### 本项目很快开发完成，感谢关注，敬请期待：

1、ACL权限控制模块（完成）；

2、CMS模块（完成）；

3、产品（商品）管理（完成）；

4、订单模块（完成）；

5、分销模块（完成）；

6、短信、邮件、支付接口集成与测试（--进行中--）；



#### 二次开发：

1、Vue-Element-Admin后台源码：https://github.com/soyojoearth/nxtframework_admin

2、Api接口文档：请clone项目后打开 document/nxtframework_api.html

3、数据库结构模型：请clone项目后打开document/nxtframework_db_model.mwb（使用Mysql Workbench 8.0打开）



#### Demo：

1、地址：https://demo.nxtframework.com/admin 

2、用户名：guest 密码：fxxkyou1314 （访客权限，只能看，不能改）




#### 主要涉及到的技术：

1、MySQL 8.0

2、SpringBoot 2

3、Open Jdk 8

4、Docker



#### **特性：**

1、图片可以保存在服务器本地，也可以选择保存在七牛云，并且中途可以一键搬运到本地或搬运到七牛云；

2、上传保存在本地的图片也支持自动生成缩略图（仿七牛云）；

3、可docker-compose一键启动，已配置好nginx文件，支持ssl，只要修改绑定域名和证书文件即可；

4、具备Docker HEALTHCHECK 、Docker autoheal，自动检测健康状态，发现故障自动重启；



#### 后台预览图：

![image-20201215140649883](gitbook_resource/image-20201215140649883.png)



#### 技术架构图：

![image-20201124140216583](gitbook_resource/image-20201124140216583.png)





#### 单机部署方式：

**1、clone项目，用docker创建数据库镜像：**

```
git clone https://github.com/soyojoearth/nxtframework.git
cd nxtframework/wwwroot/db/
#创建、启动docker数据库（仅供测试）
docker build --tag nxtmysql:0.1.0 .
#回到项目目录
cd ../../
```

**2、到项目根目录，执行：**


```
#打包
mvn package -Dmaven.test.skip=true
#创建镜像
docker build --tag nxtframework:0.1.0 .
#启动本地测试
cd wwwroot
docker-compose -f docker-compose-quickstart-with-db.yml up
```

**3、然后浏览器打开：`http://127.0.0.1:8686/admin`  用户名：`admin` 密码：`nxtframework.com`**

**4、如何启用80端口、部署SSL：**

添加以下hosts

`127.0.0.1  test.nxtframework.com`

打开浏览器访问：http://test.nxtframework.com/admin 和 https://test.nxtframework.com/admin

**5、如何更换域名、证书：**

修改`wwwroot/http/nginx.conf`文件，将域名修改成自己的域名；

更换`wwwroot/http/ssl*` 两个证书文件；

**6、其它**

一般不建议用docker部署mysql，正式商用时，还请使用独立的mysql数据库，使用 `docker-compose-quickstart.yml` 启动应用



#### 集群部署方式：

1、使用k8s部署；

2、部署完成^_^。

本项目已经充分考虑过集群多实例部署的情况，已针对性优化过。





#### 开发团队成员：

Leader & PM：soyojo

UI设计：Rong Kai

架构：soyojo

后端开发：soyojo、hexiao、yunian

前端开发：soyojo、ieyieyss、阿 Fa、hexiao、Remondcl、yunian

