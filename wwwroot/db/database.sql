-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Host: rm-j6c3bm95y4bmeg4x0mo.mysql.rds.aliyuncs.com    Database: nxtframework
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


--
-- Table structure for table `nxt_acl_action`
--

DROP TABLE IF EXISTS `nxt_acl_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_acl_action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action_class` varchar(128) DEFAULT NULL,
  `action_name` varchar(255) DEFAULT NULL,
  `action_remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `action_name_UNIQUE` (`action_name`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_action`
--

LOCK TABLES `nxt_acl_action` WRITE;
/*!40000 ALTER TABLE `nxt_acl_action` DISABLE KEYS */;
INSERT INTO `nxt_acl_action` VALUES (1,'NxtApiAdminBannerListController',NULL,NULL),(2,'NxtApiAdminBannerSaveController',NULL,NULL),(3,'NxtApiAdminBlockUserController',NULL,NULL),(4,'NxtApiAdminCreateUserController',NULL,NULL),(5,'NxtApiAdminFilemanageCreateControler',NULL,NULL),(6,'NxtApiAdminGuestmessageDeleteController',NULL,NULL),(7,'NxtApiAdminGuestmessageDetailController',NULL,NULL),(8,'NxtApiAdminGuestmessageListController',NULL,NULL),(9,'NxtApiAdminNewsCategoryCreateController',NULL,NULL),(10,'NxtApiAdminNewsCategoryDeleteController',NULL,NULL),(11,'NxtApiAdminNewsCategoryListController',NULL,NULL),(12,'NxtApiAdminNewsCategoryUpdateController',NULL,NULL),(13,'NxtApiAdminNewsCreateController',NULL,NULL),(14,'NxtApiAdminNewsDeleteController',NULL,NULL),(15,'NxtApiAdminNewsDetailController',NULL,NULL),(16,'NxtApiAdminNewsListController',NULL,NULL),(17,'NxtApiAdminNewsOrderSwapController',NULL,NULL),(18,'NxtApiAdminNewsRecommendController',NULL,NULL),(19,'NxtApiAdminNewsUpdateController',NULL,NULL),(20,'NxtApiAdminProductCategoryCreateController',NULL,NULL),(21,'NxtApiAdminProductCategoryDeleteController',NULL,NULL),(22,'NxtApiAdminProductCategoryListController',NULL,NULL),(23,'NxtApiAdminProductCategoryUpdateController',NULL,NULL),(24,'NxtApiAdminProductCreateController',NULL,NULL),(25,'NxtApiAdminProductDeleteController',NULL,NULL),(26,'NxtApiAdminProductDetailController',NULL,NULL),(27,'NxtApiAdminProductListController',NULL,NULL),(28,'NxtApiAdminProductOrderSwapController',NULL,NULL),(29,'NxtApiAdminProductPictureListController',NULL,NULL),(30,'NxtApiAdminProductRecommendController',NULL,NULL),(31,'NxtApiAdminProductUpdateController',NULL,NULL),(32,'NxtApiAdminResetPwdController',NULL,NULL),(33,'NxtApiAdminResetUserPwdController',NULL,NULL),(34,'NxtApiAdminResetUserTypeController',NULL,NULL),(35,'NxtApiAdminSettingListController',NULL,NULL),(36,'NxtApiAdminSettingSaveController',NULL,NULL),(37,'NxtApiAdminUploadfileCategoryCreateController',NULL,NULL),(38,'NxtApiAdminUploadfileCategoryDeleteController',NULL,NULL),(39,'NxtApiAdminUploadfileCategoryListController',NULL,NULL),(40,'NxtApiAdminUploadfileCategoryUpdateController',NULL,NULL),(41,'NxtApiAdminUploadPublicPicController',NULL,NULL),(42,'NxtApiAdminUserListController',NULL,NULL),(43,'NxtApiAdminWebContentDetailController',NULL,NULL),(44,'NxtApiAdminWebContentListController',NULL,NULL),(45,'NxtApiAdminWebContentUpdateController',NULL,NULL),(46,'NxtApiAdminAclRoleListController',NULL,NULL),(47,'NxtApiAdminResetUserRoleController',NULL,NULL);
/*!40000 ALTER TABLE `nxt_acl_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_acl_group`
--

DROP TABLE IF EXISTS `nxt_acl_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_acl_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(128) DEFAULT NULL,
  `group_remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_name_UNIQUE` (`group_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_group`
--

LOCK TABLES `nxt_acl_group` WRITE;
/*!40000 ALTER TABLE `nxt_acl_group` DISABLE KEYS */;
INSERT INTO `nxt_acl_group` VALUES (1,'系统设置',NULL),(2,'用户管理',NULL),(3,'资讯管理',NULL),(4,'产品管理',NULL),(5,'留言管理',NULL),(6,'广告管理',NULL),(7,'页面管理',NULL),(8,'只读查看',NULL),(9,'个人管理',NULL),(10,'文件管理',NULL);
/*!40000 ALTER TABLE `nxt_acl_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_acl_group_action`
--

DROP TABLE IF EXISTS `nxt_acl_group_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_acl_group_action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) DEFAULT NULL,
  `action_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_action` (`group_id`,`action_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_group_action`
--

LOCK TABLES `nxt_acl_group_action` WRITE;
/*!40000 ALTER TABLE `nxt_acl_group_action` DISABLE KEYS */;
INSERT INTO `nxt_acl_group_action` VALUES (1,1,35),(2,1,36),(3,2,3),(8,2,4),(4,2,33),(5,2,34),(6,2,42),(64,2,46),(65,2,47),(11,3,9),(12,3,10),(13,3,11),(14,3,12),(15,3,13),(16,3,14),(17,3,15),(18,3,16),(19,3,17),(20,3,18),(21,3,19),(10,3,41),(22,4,20),(23,4,21),(24,4,22),(25,4,23),(26,4,24),(27,4,25),(28,4,26),(29,4,27),(30,4,28),(31,4,29),(32,4,30),(33,4,31),(34,4,41),(35,5,6),(36,5,7),(37,5,8),(38,6,1),(39,6,2),(40,6,41),(41,7,41),(42,7,43),(43,7,44),(44,7,45),(45,8,1),(55,8,7),(46,8,8),(47,8,11),(56,8,15),(48,8,16),(49,8,22),(57,8,26),(50,8,27),(51,8,29),(52,8,35),(53,8,42),(58,8,43),(54,8,44),(7,9,32),(59,10,37),(60,10,38),(61,10,39),(62,10,40),(63,10,41);
/*!40000 ALTER TABLE `nxt_acl_group_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_acl_role`
--

DROP TABLE IF EXISTS `nxt_acl_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_acl_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(128) DEFAULT NULL,
  `role_remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name_UNIQUE` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_role`
--

LOCK TABLES `nxt_acl_role` WRITE;
/*!40000 ALTER TABLE `nxt_acl_role` DISABLE KEYS */;
INSERT INTO `nxt_acl_role` VALUES (1,'超级管理员',NULL),(2,'编辑',NULL),(3,'客人','只给看看，不能动');
/*!40000 ALTER TABLE `nxt_acl_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_acl_role_group`
--

DROP TABLE IF EXISTS `nxt_acl_role_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_acl_role_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_group` (`role_id`,`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_role_group`
--

LOCK TABLES `nxt_acl_role_group` WRITE;
/*!40000 ALTER TABLE `nxt_acl_role_group` DISABLE KEYS */;
INSERT INTO `nxt_acl_role_group` VALUES (2,1,1),(3,1,2),(4,1,3),(5,1,4),(6,1,5),(7,1,6),(8,1,7),(14,1,9),(15,1,10),(9,2,3),(10,2,4),(11,2,5),(12,2,6),(13,2,7),(16,2,9),(17,2,10),(1,3,8);
/*!40000 ALTER TABLE `nxt_acl_role_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_acl_user_action`
--

DROP TABLE IF EXISTS `nxt_acl_user_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_acl_user_action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `action_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_action` (`user_id`,`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_user_action`
--

LOCK TABLES `nxt_acl_user_action` WRITE;
/*!40000 ALTER TABLE `nxt_acl_user_action` DISABLE KEYS */;
/*!40000 ALTER TABLE `nxt_acl_user_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_acl_user_role`
--

DROP TABLE IF EXISTS `nxt_acl_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_acl_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_role` (`role_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_user_role`
--

LOCK TABLES `nxt_acl_user_role` WRITE;
/*!40000 ALTER TABLE `nxt_acl_user_role` DISABLE KEYS */;
INSERT INTO `nxt_acl_user_role` VALUES (1,3,1),(14,6,1),(16,7,1),(15,8,3),(17,19,3);
/*!40000 ALTER TABLE `nxt_acl_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_banner`
--

DROP TABLE IF EXISTS `nxt_banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `location_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `uploadfile_id` bigint(20) DEFAULT NULL,
  `click_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_location_name` (`location_name`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_banner`
--

LOCK TABLES `nxt_banner` WRITE;
/*!40000 ALTER TABLE `nxt_banner` DISABLE KEYS */;
INSERT INTO `nxt_banner` VALUES (118,'首页',367,''),(119,'案例页',362,''),(120,'项目页',365,'');
/*!40000 ALTER TABLE `nxt_banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_content`
--

DROP TABLE IF EXISTS `nxt_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) DEFAULT NULL,
  `content_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `dateline_update` bigint(20) DEFAULT NULL,
  `dateline_create` bigint(20) DEFAULT NULL,
  `is_recommend` int(11) DEFAULT NULL,
  `sort_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_content`
--

LOCK TABLES `nxt_content` WRITE;
/*!40000 ALTER TABLE `nxt_content` DISABLE KEYS */;
INSERT INTO `nxt_content` VALUES (24,2,'SpaceX载人龙飞船成功返回地球 完成终极测试再创历史','<p>5624352111115233<img class=\"wscnph\" src=\"http://qdyrmq5wy.bkt.clouddn.com/2020-08-04-1759334020.jpg\" />3333</p>',1596532686625,1596418999575,0,33),(31,5,'啊啊啊','<p>sdddd3333344443335<img class=\"wscnph\" src=\"http://qdyrmq5wy.bkt.clouddn.com/2020-08-04-1567359448.jpg\" />55555544441111145545555</p>',1596540174161,1596523072465,1,27);
/*!40000 ALTER TABLE `nxt_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_cronjob`
--

DROP TABLE IF EXISTS `nxt_cronjob`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_cronjob` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(45) DEFAULT NULL,
  `job_key` varchar(255) DEFAULT NULL,
  `job_status` int(11) DEFAULT NULL,
  `job_status_description` varchar(255) DEFAULT NULL,
  `job_status_dateline` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `job_key_UNIQUE` (`job_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_cronjob`
--

LOCK TABLES `nxt_cronjob` WRITE;
/*!40000 ALTER TABLE `nxt_cronjob` DISABLE KEYS */;
INSERT INTO `nxt_cronjob` VALUES (1,'将本地图片移动到七牛云','moveLocalImageToQiniu',0,'任务结束',1603081709346),(2,'将七牛云图片移动到本地','moveQiniuImageToLocal',0,'任务结束',1603081665815);
/*!40000 ALTER TABLE `nxt_cronjob` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_guestmessage`
--

DROP TABLE IF EXISTS `nxt_guestmessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_guestmessage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `guest_company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `guest_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `guest_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `guest_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `message_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `message_dateline` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_guestmessage`
--

LOCK TABLES `nxt_guestmessage` WRITE;
/*!40000 ALTER TABLE `nxt_guestmessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `nxt_guestmessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_news_category`
--

DROP TABLE IF EXISTS `nxt_news_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_news_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category_pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_news_category`
--

LOCK TABLES `nxt_news_category` WRITE;
/*!40000 ALTER TABLE `nxt_news_category` DISABLE KEYS */;
INSERT INTO `nxt_news_category` VALUES (1,'新闻AAA',0),(2,'十三水1111',8),(5,'国内新闻',1),(6,'领导动态1222',5),(7,'政府通知222',5),(8,'法律',0);
/*!40000 ALTER TABLE `nxt_news_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_product`
--

DROP TABLE IF EXISTS `nxt_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) DEFAULT NULL,
  `product_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `product_subtitle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `price_negotiation` int(11) DEFAULT NULL,
  `price_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `product_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `dateline_create` bigint(20) DEFAULT NULL,
  `dateline_updated` bigint(20) DEFAULT NULL,
  `is_recommend` int(11) DEFAULT NULL,
  `sort_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_product`
--

LOCK TABLES `nxt_product` WRITE;
/*!40000 ALTER TABLE `nxt_product` DISABLE KEYS */;
INSERT INTO `nxt_product` VALUES (64,1,'11','3333',2222,0,'2222','11',1598412642463,1598412642463,0,64),(65,1,'bbb','undefi',123,1,'undefi','<p>b<img class=\"wscnph\" src=\"http://newxton-image-domain/2020-08-28-1321007314.jpg\" />bb</p>',1598614752437,1598614752437,1,65);
/*!40000 ALTER TABLE `nxt_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_product_category`
--

DROP TABLE IF EXISTS `nxt_product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_product_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category_pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_product_category`
--

LOCK TABLES `nxt_product_category` WRITE;
/*!40000 ALTER TABLE `nxt_product_category` DISABLE KEYS */;
INSERT INTO `nxt_product_category` VALUES (1,'类别A',0),(2,'类别B',0),(4,'类别2',1),(5,'类别1122',4),(6,'类别22',2),(7,'类别甲',2);
/*!40000 ALTER TABLE `nxt_product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_product_picture`
--

DROP TABLE IF EXISTS `nxt_product_picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_product_picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `uploadfile_id` bigint(20) DEFAULT NULL,
  `sort_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_product_picture`
--

LOCK TABLES `nxt_product_picture` WRITE;
/*!40000 ALTER TABLE `nxt_product_picture` DISABLE KEYS */;
INSERT INTO `nxt_product_picture` VALUES (1,5,20,1),(2,5,23,2),(3,9,20,3),(4,10,20,4),(5,11,20,5),(14,13,23,14),(15,13,20,15),(16,41,222,16),(17,41,223,17),(20,40,222,20),(21,40,223,21),(22,46,234,22),(23,39,238,23),(24,42,239,24),(25,49,241,25),(26,49,240,26),(39,53,36,39),(40,53,37,40),(41,53,38,41),(47,50,45,47),(48,50,46,48),(75,51,281,75),(76,51,283,76),(85,57,286,85),(86,57,287,86),(87,57,288,87),(88,56,289,88),(89,56,290,89),(92,58,294,92),(93,58,295,93),(96,55,302,96),(97,55,303,97),(104,66,302,104),(105,66,303,105),(122,79,309,122),(129,65,302,129),(130,65,303,130);
/*!40000 ALTER TABLE `nxt_product_picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_product_sku`
--

DROP TABLE IF EXISTS `nxt_product_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_product_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_product_sku`
--

LOCK TABLES `nxt_product_sku` WRITE;
/*!40000 ALTER TABLE `nxt_product_sku` DISABLE KEYS */;
INSERT INTO `nxt_product_sku` VALUES (9,60,'颜色a'),(10,60,'尺码a'),(17,61,'颜色c'),(18,61,'尺码ca'),(19,63,'颜色c'),(20,63,'尺码ca'),(33,64,'颜色1'),(34,64,'尺码ca'),(43,75,'颜色'),(44,75,'尺码'),(45,75,'属性名称'),(46,76,'颜色'),(47,77,'颜色'),(48,77,'尺码'),(49,77,'属性名称'),(50,78,'颜色'),(51,78,'尺码'),(52,78,'属性名称'),(53,66,'颜色'),(54,66,'尺码'),(55,66,'属性名称'),(83,79,'颜色'),(84,79,'尺码'),(85,79,'属性名称'),(89,80,'颜色'),(90,80,'尺码'),(91,80,'属性名称'),(92,84,'123'),(93,85,'123'),(94,86,'111'),(95,87,'123'),(96,88,'123'),(97,88,'456'),(107,89,'阿发啊'),(108,89,'xx'),(109,90,'搜索'),(110,90,'11'),(111,65,'颜色'),(112,65,'尺码'),(113,65,'属性名称');
/*!40000 ALTER TABLE `nxt_product_sku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_product_sku_value`
--

DROP TABLE IF EXISTS `nxt_product_sku_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_product_sku_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sku_id` bigint(20) DEFAULT NULL,
  `sku_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_sku_id` (`sku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_product_sku_value`
--

LOCK TABLES `nxt_product_sku_value` WRITE;
/*!40000 ALTER TABLE `nxt_product_sku_value` DISABLE KEYS */;
INSERT INTO `nxt_product_sku_value` VALUES (25,9,'红2'),(26,9,'绿2'),(27,9,'蓝2'),(28,10,'大1'),(29,10,'很大1'),(30,10,'非常大1'),(43,15,'红2'),(44,15,'绿2'),(45,15,'蓝2'),(46,16,'大1'),(47,16,'很大1'),(48,16,'非常大1'),(49,17,'红2'),(50,17,'绿2'),(51,17,'蓝2'),(52,18,'大1'),(53,18,'很大1'),(54,18,'非常大1'),(55,19,'红2'),(56,19,'绿2'),(57,19,'蓝2'),(58,20,'大1'),(59,20,'很大1'),(60,20,'非常大1'),(97,33,'红1'),(98,33,'绿2'),(99,33,'蓝2'),(100,34,'大1'),(101,34,'很大1'),(102,34,'非常大1'),(127,50,'1'),(128,51,'2'),(129,52,'3'),(130,53,'1'),(131,54,'1'),(132,55,'3'),(188,83,'白'),(189,83,'黑'),(190,84,'L'),(191,84,'XL'),(192,85,'是否'),(193,85,'搜索'),(199,89,'ss'),(200,89,'bb'),(201,90,'a'),(202,90,'b'),(203,91,'嗷嗷'),(204,92,'1111\"'),(205,92,'11112'),(206,93,'111'),(207,93,'111222'),(208,94,'123'),(209,94,'123123'),(210,95,'111'),(211,95,'111222'),(212,96,'111'),(213,97,'222'),(214,97,'22211'),(237,107,'s'),(238,108,'b'),(239,108,'c'),(240,109,'ss'),(241,109,'11'),(242,109,'22'),(243,110,'aaf'),(244,110,'ddd'),(245,111,'红'),(246,111,'绿'),(247,112,'大'),(248,112,'很大'),(249,112,'非常大'),(250,113,'1111'),(251,113,'123');
/*!40000 ALTER TABLE `nxt_product_sku_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_setting`
--

DROP TABLE IF EXISTS `nxt_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `setting_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `setting_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `setting_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `display_type` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dateline_updated` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `setting_key_UNIQUE` (`setting_key`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_setting`
--

LOCK TABLES `nxt_setting` WRITE;
/*!40000 ALTER TABLE `nxt_setting` DISABLE KEYS */;
INSERT INTO `nxt_setting` VALUES (1,'site_name','NxtFramework','网站名','input',1603364518524),(2,'stat_code','   ','统计代码','textarea',1603364527378),(3,'beian','粤ICP备20040927号-2','备案','input',1603364518523),(4,'domain','www.nxtframework.com','域名','input',1603364518528),(5,'admin_name','申生','管理员','input',1603364518527);
/*!40000 ALTER TABLE `nxt_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_uploadfile`
--

DROP TABLE IF EXISTS `nxt_uploadfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_uploadfile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file_location` int(11) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `file_ext` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `filename_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `filename_saved` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `filepath` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `urlpath` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `filesize` bigint(20) DEFAULT NULL,
  `dateline_update` bigint(20) DEFAULT NULL,
  `netdisk_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `netdisk_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=368 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_uploadfile`
--

LOCK TABLES `nxt_uploadfile` WRITE;
/*!40000 ALTER TABLE `nxt_uploadfile` DISABLE KEYS */;
INSERT INTO `nxt_uploadfile` VALUES (362,1,0,'jpg','2020-08-04-1265800877.jpg','2020-10-19-1256105701.jpg','/2020-10-19-1256105701.jpg','/2020-10-19-1256105701.jpg',28126,1603081702310,NULL,NULL),(363,1,0,'jpg','2020-08-04-1991738016.jpg','2020-10-19-1170015765.jpg','/2020-10-19-1170015765.jpg','/2020-10-19-1170015765.jpg',138615,1603081702781,NULL,NULL),(364,1,0,'jpg','2020-08-04-1265800877.jpg','2020-10-19-1849171536.jpg','/2020-10-19-1849171536.jpg','/2020-10-19-1849171536.jpg',28126,1603081703148,NULL,NULL),(365,1,0,'jpg','2020-08-04-1991738016.jpg','2020-10-19-1186307773.jpg','/2020-10-19-1186307773.jpg','/2020-10-19-1186307773.jpg',138615,1603081703544,NULL,NULL),(366,1,0,'jpg','40744d34-06aa-4322-8dca-002cb34428cd.jpg','2020-10-19-1656437741.jpg','/2020-10-19-1656437741.jpg','/2020-10-19-1656437741.jpg',1688323,1603081704096,NULL,NULL),(367,3,0,'jpg','2020-08-04-1265800877.jpg','1551551928.jpg','/public_pic/2020/10/20/1551551928.jpg','/public_pic/2020/10/20/1551551928.jpg',28126,1603182916567,NULL,NULL);
/*!40000 ALTER TABLE `nxt_uploadfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_uploadfile_category`
--

DROP TABLE IF EXISTS `nxt_uploadfile_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_uploadfile_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category_pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_uploadfile_category`
--

LOCK TABLES `nxt_uploadfile_category` WRITE;
/*!40000 ALTER TABLE `nxt_uploadfile_category` DISABLE KEYS */;
INSERT INTO `nxt_uploadfile_category` VALUES (1,'新闻资讯',0),(2,'科技B',1),(3,'政策解读',0),(4,'税收A',1),(5,'新材料',2),(6,'芯片',2),(7,'sssss',6),(8,'',0);
/*!40000 ALTER TABLE `nxt_uploadfile_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_user`
--

DROP TABLE IF EXISTS `nxt_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_user`
--

LOCK TABLES `nxt_user` WRITE;
/*!40000 ALTER TABLE `nxt_user` DISABLE KEYS */;
INSERT INTO `nxt_user` VALUES (3,'admin','f0866b655980a3f4aabf8133432a03bc','D7d5uKC&qRRei*nsc&2oA()$FN@Yj89Z','b83c35af2c686458a93c37c8c25972be',0),(6,'orange','432419d4ceea0fabb5f92b6e6747f8c0','4yOHXhkka6Lk6dELkd@DX$I6)Jt*4i4k','a4e15b09ad0e2fffccae371733d6b57a',0),(7,'apple','ecd8a47d682c5777eedfc5a47d35a499','bury!3qr9IYsuZ$7$VrZuePC623yiN(f','3b94989bae9b37a40272764462161a11',-1),(8,'apple3','745a44dc02f5b43a309438cb8869e0cb','hd!u3V4JCBkU115qGlYmES*j$1R!sRaw','05bd02fa431124882fddadf79927726e',0),(9,'test','03b9b1c79fceeaf80e12344c5ed2705c','b2paXO%mkF!By&XKF)MUhvBhOLBMVQLs',NULL,0),(10,'test11','a4207d3f2d9f77275b5df0ac82957abe','oF8j@aPcfVsJZEPTsJXXR*nNA7Cma&sQ',NULL,0),(11,'333','0dfdb43cd4e5badd25040b6a538d3830','%V@GwM@s6^bE)M98HrP0h#Sl9nEtq4!b',NULL,0),(12,'3445','47671640864c51bcdb5767fc7f734209','6R9Kq&hdOpZvwi@Dq@N&7MSRAZaejQvn',NULL,0),(13,'1111','d2315bd9edcbb93f5713c4b2774e0a98','er3tnwZZI0c8xy&O14n9I%ynnhWL&o9c',NULL,0),(14,'222','1d82a795a3000f22b3fae911b3afaf54','0)wc(44EBU&BkQ)x&BsTgd0@eLPh61Xs',NULL,0),(15,'11111','7d4084087c49da8e2150f4ea5e1c8c70','GfTgsWnnWAK0E1cOqxioG4!8@au^oM75','0de8dd3f1295fdc39427d9788e57b6e3',0),(16,'abc888','4b5487c1868182b42fc2d71fa9bb6369','Lpq0I&%d1*Vig%V3LNEx771#oOE1z1P%','8f0e5f6e7dac59d11e34c43924dc6b43',-1),(17,'main','a36b36b56dee556e59f97b9bb375039f','!ow4F*M9HI7rite57re6^POsYar!QmwU','4a61fe44f0ecaab9e87ca536b90831a2',0),(18,'abc123','10ff109ed38f7209f6624607f20106a5','D6&#dg0NxApoY9YveLaVNQaHwkvMJz!Y',NULL,0),(19,'abcabc123','12a44e0bb4ba695f7ae71af36a5a5c7f','u%Gv0WbkNQu*NI3lrSZ*HvZe4tR&y^)A',NULL,0);
/*!40000 ALTER TABLE `nxt_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nxt_web_page`
--

DROP TABLE IF EXISTS `nxt_web_page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxt_web_page` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `web_key` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `web_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `seo_keyword` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `dateline_update` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_web_key` (`web_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_web_page`
--

LOCK TABLES `nxt_web_page` WRITE;
/*!40000 ALTER TABLE `nxt_web_page` DISABLE KEYS */;
INSERT INTO `nxt_web_page` VALUES (1,NULL,'联系我们111','联系我们x222','<p>。。是否反反复复<img class=\"wscnph\" src=\"http://qdyrmq5wy.bkt.clouddn.com/2020-08-04-1414689064.jpg\" />ssssb<img class=\"wscnph\" src=\"http://qdyrmq5wy.bkt.clouddn.com/2020-08-04-1501262746.jpg\" />b<img class=\"wscnph\" src=\"http://qdyrmq5wy.bkt.clouddn.com/2020-08-04-1408617700.jpg\" />b</p>',NULL,1596546321979),(2,NULL,'关于我们','关于我们xxx','<p>。。adsf</p>',NULL,1596513033323),(3,NULL,'加入我们','加入我们xxx','。。',NULL,0),(4,NULL,'sss','aaaa','<p>asdf<img class=\"wscnph\" src=\"http://qdyrmq5wy.bkt.clouddn.com/2020-08-04-1265800877.jpg\" />bb</p>',NULL,1596546491113);
/*!40000 ALTER TABLE `nxt_web_page` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-22 19:39:01
