-- MySQL dump 10.13  Distrib 8.0.19, for macos10.15 (x86_64)
--
-- Database: nxtframework_demo
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
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_action`
--

LOCK TABLES `nxt_acl_action` WRITE;
/*!40000 ALTER TABLE `nxt_acl_action` DISABLE KEYS */;
INSERT INTO `nxt_acl_action` VALUES (1,'NxtApiAdminBannerListController','广告横幅列表',NULL),(2,'NxtApiAdminBannerSaveController','广告横幅保存',NULL),(3,'NxtApiAdminBlockUserController','拉黑用户',NULL),(4,'NxtApiAdminCreateUserController','增加用户',NULL),(5,'NxtApiAdminFilemanageCreateControler','文件添加',NULL),(6,'NxtApiAdminGuestmessageDeleteController','留言删除',NULL),(7,'NxtApiAdminGuestmessageDetailController','留言详情查看',NULL),(8,'NxtApiAdminGuestmessageListController','留言列表',NULL),(9,'NxtApiAdminNewsCategoryCreateController','文章类别添加',NULL),(10,'NxtApiAdminNewsCategoryDeleteController','文章类别删除',NULL),(11,'NxtApiAdminNewsCategoryListController','文章类别列表',NULL),(12,'NxtApiAdminNewsCategoryUpdateController','文章类别修改',NULL),(13,'NxtApiAdminNewsCreateController','文章添加',NULL),(14,'NxtApiAdminNewsDeleteController','文章删除',NULL),(15,'NxtApiAdminNewsDetailController','文章详情查看',NULL),(16,'NxtApiAdminNewsListController','文章列表查看',NULL),(17,'NxtApiAdminNewsOrderSwapController','文章顺序修改',NULL),(18,'NxtApiAdminNewsRecommendController','文章推荐操作',NULL),(19,'NxtApiAdminNewsUpdateController','文章修改',NULL),(20,'NxtApiAdminProductCategoryCreateController','产品类别添加',NULL),(21,'NxtApiAdminProductCategoryDeleteController','产品类别删除',NULL),(22,'NxtApiAdminProductCategoryListController','产品类别列表',NULL),(23,'NxtApiAdminProductCategoryUpdateController','产品类别修改',NULL),(24,'NxtApiAdminProductCreateController','产品添加',NULL),(25,'NxtApiAdminProductDeleteController','产品删除',NULL),(26,'NxtApiAdminProductDetailController','产品详情查看',NULL),(27,'NxtApiAdminProductListController','产品列表',NULL),(28,'NxtApiAdminProductOrderSwapController','产品顺序修改',NULL),(29,'NxtApiAdminProductPictureListController','产品图片列表',NULL),(30,'NxtApiAdminProductRecommendController','产品推荐操作',NULL),(31,'NxtApiAdminProductUpdateController','产品修改',NULL),(32,'NxtApiAdminResetPwdController','个人密码修改',NULL),(33,'NxtApiAdminResetUserPwdController','修改他人密码',NULL),(34,'NxtApiAdminResetUserTypeController','修改他人类型',NULL),(35,'NxtApiAdminSettingListController','系统设置列表',NULL),(36,'NxtApiAdminSettingSaveController','系统设置修改',NULL),(37,'NxtApiAdminUploadfileCategoryCreateController','文件类别添加',NULL),(38,'NxtApiAdminUploadfileCategoryDeleteController','文件类别删除',NULL),(39,'NxtApiAdminUploadfileCategoryListController','文件类别列表',NULL),(40,'NxtApiAdminUploadfileCategoryUpdateController','文件类别修改',NULL),(41,'NxtApiAdminUploadPublicPicController','上传图片',NULL),(42,'NxtApiAdminUserListController','用户列表',NULL),(43,'NxtApiAdminWebContentDetailController','页面详情',NULL),(44,'NxtApiAdminWebContentListController','页面列表',NULL),(45,'NxtApiAdminWebContentUpdateController','页面修改',NULL),(47,'NxtApiAdminResetUserRoleController','用户角色修改',NULL),(48,'NxtApiAdminOSSInfoController','查看OSS信息',NULL),(49,'NxtApiAdminOSSAddJobTransferController','OSS图片搬运任务',NULL),(50,'NxtApiAdminAclRoleListController','ACL角色列表',NULL),(51,'NxtApiAdminAclRoleDeleteController','ACL角色删除',NULL),(52,'NxtApiAdminAclGroupListController','ACL权限组列表',NULL),(53,'NxtApiAdminAclGroupDeleteController','ACL权限组删除',NULL),(54,'NxtApiAdminAclActionListController','ACL权限资源列表',NULL),(55,'NxtApiAdminAclRoleDetailController','ACL角色详情',NULL),(56,'NxtApiAdminAclGroupDetailController','ACL权限组详情',NULL),(57,'NxtApiAdminAclRoleDetailUpdateController','ACL角色详情更新',NULL),(58,'NxtApiAdminAclGroupDetailUpdateController','ACL权限组详情更新',NULL),(59,'NxtApiAdminAclRoleAddController','ACL角色添加',NULL),(60,'NxtApiAdminAclGroupAddController','ACL权限组添加',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_group`
--

LOCK TABLES `nxt_acl_group` WRITE;
/*!40000 ALTER TABLE `nxt_acl_group` DISABLE KEYS */;
INSERT INTO `nxt_acl_group` VALUES (1,'系统设置',NULL),(2,'用户管理',NULL),(3,'资讯管理',NULL),(4,'产品管理',NULL),(5,'留言管理',NULL),(6,'广告管理',NULL),(7,'页面管理',NULL),(8,'只读查看',NULL),(9,'个人管理',NULL),(10,'文件管理',NULL),(16,'ACL管理','权限分配');
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
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_group_action`
--

LOCK TABLES `nxt_acl_group_action` WRITE;
/*!40000 ALTER TABLE `nxt_acl_group_action` DISABLE KEYS */;
INSERT INTO `nxt_acl_group_action` VALUES (1,1,35),(2,1,36),(66,1,48),(67,1,49),(3,2,3),(8,2,4),(4,2,33),(5,2,34),(6,2,42),(65,2,47),(68,2,50),(69,2,51),(70,2,52),(71,2,53),(72,2,54),(74,2,55),(75,2,56),(76,2,57),(77,2,58),(80,2,59),(81,2,60),(11,3,9),(12,3,10),(13,3,11),(14,3,12),(15,3,13),(16,3,14),(17,3,15),(18,3,16),(19,3,17),(20,3,18),(21,3,19),(10,3,41),(22,4,20),(23,4,21),(24,4,22),(25,4,23),(26,4,24),(27,4,25),(28,4,26),(29,4,27),(30,4,28),(31,4,29),(32,4,30),(33,4,31),(34,4,41),(35,5,6),(36,5,7),(37,5,8),(38,6,1),(39,6,2),(40,6,41),(41,7,41),(42,7,43),(43,7,44),(44,7,45),(45,8,1),(55,8,7),(46,8,8),(47,8,11),(56,8,15),(48,8,16),(49,8,22),(57,8,26),(50,8,27),(51,8,29),(52,8,35),(53,8,42),(58,8,43),(54,8,44),(97,8,48),(98,8,50),(99,8,52),(100,8,54),(101,8,55),(102,8,56),(78,9,32),(59,10,37),(60,10,38),(61,10,39),(62,10,40),(63,10,41),(82,14,1),(83,14,2),(84,15,1),(85,15,2),(86,16,50),(87,16,51),(88,16,52),(89,16,53),(90,16,54),(91,16,55),(92,16,56),(93,16,57),(94,16,58),(95,16,59),(96,16,60);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_role`
--

LOCK TABLES `nxt_acl_role` WRITE;
/*!40000 ALTER TABLE `nxt_acl_role` DISABLE KEYS */;
INSERT INTO `nxt_acl_role` VALUES (1,'超级管理员',NULL),(2,'内容主管',NULL),(3,'访客','只给看看，不许改'),(8,'产品编辑','仅管理产品'),(9,'资讯编辑','可管理资讯、留言板');
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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_role_group`
--

LOCK TABLES `nxt_acl_role_group` WRITE;
/*!40000 ALTER TABLE `nxt_acl_role_group` DISABLE KEYS */;
INSERT INTO `nxt_acl_role_group` VALUES (2,1,1),(3,1,2),(4,1,3),(5,1,4),(6,1,5),(7,1,6),(14,1,9),(15,1,10),(28,1,16),(9,2,3),(10,2,4),(11,2,5),(12,2,6),(13,2,7),(16,2,9),(17,2,10),(34,3,8),(29,8,4),(30,8,9),(31,9,3),(32,9,5),(33,9,9);
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_acl_user_role`
--

LOCK TABLES `nxt_acl_user_role` WRITE;
/*!40000 ALTER TABLE `nxt_acl_user_role` DISABLE KEYS */;
INSERT INTO `nxt_acl_user_role` VALUES (1,3,1),(14,6,2),(15,8,3),(17,19,3),(19,20,3),(16,7,8);
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
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_banner`
--

LOCK TABLES `nxt_banner` WRITE;
/*!40000 ALTER TABLE `nxt_banner` DISABLE KEYS */;
INSERT INTO `nxt_banner` VALUES (122,'案例页',369,''),(123,'项目页',370,''),(126,'首页',374,'');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_cronjob`
--

LOCK TABLES `nxt_cronjob` WRITE;
/*!40000 ALTER TABLE `nxt_cronjob` DISABLE KEYS */;
INSERT INTO `nxt_cronjob` VALUES (1,'将本地图片移动到七牛云','moveLocalImageToQiniu',0,'任务结束',1603796797219),(2,'将七牛云图片移动到本地','moveQiniuImageToLocal',0,'任务结束',1603796805167),(3,'清理Acl缓存','cleanAclCache',1,NULL,1603961847430),(4,'清理SettingValue缓存','cleanSettingValueCache',1,NULL,1603797844053);
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
  `placeholder` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `setting_key_UNIQUE` (`setting_key`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_setting`
--

LOCK TABLES `nxt_setting` WRITE;
/*!40000 ALTER TABLE `nxt_setting` DISABLE KEYS */;
INSERT INTO `nxt_setting` VALUES (1,'stat_code','','统计代码','textarea',1603364518524,NULL),(2,'contact_code','   ','客服代码','textarea',1603678545799,NULL),(3,'contact_link','','客服链接','input',1603364518523,'用户点击网页上的一个按钮，然后会打开一个客服窗口，这里填写该按钮的链接'),(6,'oss_location','local','图片存储位置','input',1603797843948,'存储在本机，填写：local   存储在七牛云，填写：qiniu （不填或填错，默认local）'),(7,'oss_qiniuAccessKey','---','七牛云AccessKey','input',1603797843947,NULL),(8,'oss_qiniuSecretKey','---','七牛云SecretKey','input',1603797843948,NULL),(9,'oss_qiniuBucket','---','七牛云bucket','input',1603797843948,NULL),(10,'oss_qiniuDomain','---','七牛云OSS域名','input',1603797843947,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=375 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_uploadfile`
--

LOCK TABLES `nxt_uploadfile` WRITE;
/*!40000 ALTER TABLE `nxt_uploadfile` DISABLE KEYS */;
INSERT INTO `nxt_uploadfile` VALUES (374,3,0,'jpg','2020-08-04-1265800877.jpg','1494511210.jpg','/public_pic/2020/10/27/1494511210.jpg','/public_pic/2020/10/27/1494511210.jpg',28126,1603796799943,NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nxt_user`
--

LOCK TABLES `nxt_user` WRITE;
/*!40000 ALTER TABLE `nxt_user` DISABLE KEYS */;
INSERT INTO `nxt_user` VALUES (3,'admin','31d20de972bf6c0a162be9d08c2c1d11','k81lni(w5QeyI8R(7PWrn5SHPeV$)#%^','f96bec8b84fe98aeb206bf3342eb5632',0),(6,'orange','9fb44f81950b2007f2d4e9cb4e9ab0a7','Yg3mXXe0gorH9qxSCGa1sh5S^VSRypQt','',0),(7,'apple','ecd8a47d682c5777eedfc5a47d35a499','bury!3qr9IYsuZ$7$VrZuePC623yiN(f','f2b7a3f5dfa8ba23f4c1bab11a57e66e',-1),(20,'guest','d3cb90cc581e3c3d1c00b0d48a26cef4','GxlUk&d!u16qB4O$lszjYJ4k@NhnO5&J','04cde8bea463094dd30738a2736891bb',0);
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

-- Dump completed on 2020-10-29 17:00:38
