-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: onestation
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dev_company`
--

DROP TABLE IF EXISTS `dev_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_company` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司主键ID',
  `com_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公司名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `com_address` varchar(200) DEFAULT NULL COMMENT '公司地址',
  `com_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '公司类型',
  `com_logo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '公司logo',
  `com_picture` varchar(1000) DEFAULT NULL COMMENT '公司轮播图片',
  `industry` int(11) DEFAULT NULL COMMENT '公司所属行业',
  `total_iso` varchar(45) DEFAULT NULL,
  `sign` int(11) DEFAULT '0' COMMENT '标记公司等级 0、普通 1、VIP',
  `file_size` bigint(20) DEFAULT '0' COMMENT '总文件大小',
  `login_number` varchar(125) DEFAULT NULL COMMENT '登录账号',
  `login_password` varchar(50) DEFAULT '123456' COMMENT '登录密码',
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `name_uindex` (`com_name`),
  KEY `type_index` (`com_type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='公司信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_company`
--

LOCK TABLES `dev_company` WRITE;
/*!40000 ALTER TABLE `dev_company` DISABLE KEYS */;
INSERT INTO `dev_company` VALUES (1,'聚特易','2019-04-08 00:00:00','宝安','1','http://172.16.31.184:8080/profile/iso4_88/170b63aa0f84fe3f6a5ed76c245321ee.jpg','[\"http://172.16.31.184:88/profile/sop1/eb48cc82d8561be92ce34398863e7ad1.jpg\",\"http://172.16.31.184:88/profile/sop1/7640c92ce5181a1fba1f7bf4a23dcf96.jpg\"]',NULL,NULL,0,0,'JTY','123456'),(2,'测试技术有限公司','2019-11-05 08:46:47',NULL,'0',NULL,NULL,NULL,NULL,0,0,'CSJSYXGS','123456');
/*!40000 ALTER TABLE `dev_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_data_log`
--

DROP TABLE IF EXISTS `dev_data_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_data_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  `work_id` int(11) DEFAULT NULL,
  `dev_id` int(11) DEFAULT NULL,
  `io_id` int(11) DEFAULT NULL,
  `io_order` int(11) DEFAULT NULL,
  `data_total` int(11) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_data` int(11) DEFAULT '0',
  `sc_type` int(11) DEFAULT NULL COMMENT '车间或者流水线标记(0、流水线，1、车间)',
  PRIMARY KEY (`id`),
  KEY `line_word_dev_io` (`line_id`,`work_id`,`dev_id`,`io_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='数据上报日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_data_log`
--

LOCK TABLES `dev_data_log` WRITE;
/*!40000 ALTER TABLE `dev_data_log` DISABLE KEYS */;
INSERT INTO `dev_data_log` VALUES (1,2,1,NULL,4,NULL,NULL,4,'2019-11-05','2019-11-05 16:56:27',0,1),(2,2,1,1,2,3,NULL,10,'2019-11-05','2019-11-05 16:57:32',10,1),(3,2,1,1,2,3,NULL,20,'2019-11-05','2019-11-05 16:58:13',10,1),(4,2,1,1,2,3,NULL,30,'2019-11-05','2019-11-05 17:03:31',10,1),(5,2,1,1,2,3,NULL,50,'2019-11-05','2019-11-05 17:05:16',20,1),(6,2,1,1,2,3,NULL,60,'2019-11-05','2019-11-05 17:06:34',10,1),(7,2,1,NULL,2,NULL,NULL,70,'2019-11-05','2019-11-05 17:06:39',0,1),(8,2,1,1,2,3,NULL,70,'2019-11-05','2019-11-05 17:06:57',10,1),(9,2,1,1,2,3,NULL,80,'2019-11-05','2019-11-05 17:07:44',10,1),(10,2,1,NULL,2,NULL,NULL,80,'2019-11-05','2019-11-05 17:07:49',0,1),(11,2,1,NULL,2,NULL,NULL,80,'2019-11-05','2019-11-05 17:07:50',0,1),(12,2,1,NULL,2,NULL,NULL,80,'2019-11-05','2019-11-05 17:07:51',0,1),(13,2,1,1,2,3,NULL,100,'2019-11-05','2019-11-05 17:08:01',20,1),(14,2,1,NULL,4,NULL,NULL,100,'2019-11-05','2019-11-05 17:11:06',0,1),(15,2,1,1,2,3,NULL,120,'2019-11-05','2019-11-05 17:11:21',20,1),(16,2,1,1,2,3,NULL,130,'2019-11-05','2019-11-05 17:42:25',10,1),(17,2,1,NULL,2,NULL,NULL,130,'2019-11-05','2019-11-05 17:42:30',0,1),(18,2,1,NULL,2,NULL,NULL,130,'2019-11-05','2019-11-05 17:42:31',0,1),(19,2,1,NULL,2,NULL,NULL,130,'2019-11-05','2019-11-05 17:42:31',0,1),(20,2,1,1,2,3,NULL,200,'2019-11-05','2019-11-05 17:42:50',70,1),(21,2,1,1,2,3,NULL,500,'2019-11-05','2019-11-05 18:25:11',300,1),(22,2,1,3,5,9,NULL,10,'2019-11-06','2019-11-06 13:45:17',10,1),(23,2,1,NULL,4,NULL,NULL,10,'2019-11-06','2019-11-06 13:45:29',0,1),(24,2,1,3,2,3,NULL,10,'2019-11-06','2019-11-06 13:45:35',10,1),(25,2,1,3,2,3,NULL,20,'2019-11-06','2019-11-06 13:46:39',10,1),(26,2,1,3,5,9,NULL,30,'2019-11-06','2019-11-06 13:54:26',20,1),(27,2,1,3,5,9,NULL,50,'2019-11-06','2019-11-06 13:54:33',20,1),(28,2,1,3,5,9,NULL,430,'2019-11-06','2019-11-06 13:57:37',380,1),(29,2,1,3,5,9,NULL,480,'2019-11-06','2019-11-06 13:57:48',50,1),(30,2,1,3,5,9,NULL,480,'2019-11-06','2019-11-06 13:58:15',0,1),(31,2,2,2,5,9,NULL,20,'2019-11-06','2019-11-06 13:59:27',20,1),(32,2,2,2,5,9,NULL,30,'2019-11-06','2019-11-06 14:00:51',10,1),(33,2,2,NULL,5,NULL,NULL,30,'2019-11-06','2019-11-06 14:01:00',0,1),(34,2,2,2,5,9,NULL,50,'2019-11-06','2019-11-06 14:01:27',20,1),(35,2,2,2,5,9,NULL,500,'2019-11-06','2019-11-06 14:01:34',450,1),(36,2,2,2,5,9,NULL,500,'2019-11-08','2019-11-08 08:30:36',0,1),(37,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:26:09',0,1),(38,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:26:44',0,1),(39,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:28:31',0,1),(40,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:33:37',0,1),(41,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:34:58',0,1),(42,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:35:32',0,1),(43,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:36:34',0,1),(44,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:37:16',0,1),(45,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:38:18',0,1),(46,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:39:28',0,1),(47,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:40:11',0,1),(48,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:41:25',0,1),(49,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:41:33',0,1),(50,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:43:18',0,1),(51,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:43:23',0,1),(52,2,2,NULL,5,NULL,NULL,500,'2019-11-08','2019-11-08 17:43:38',0,1),(53,2,2,NULL,5,NULL,NULL,400,'2019-11-08','2019-11-08 17:43:45',0,1),(54,2,2,NULL,5,NULL,NULL,100,'2019-11-08','2019-11-08 18:30:48',0,1),(55,2,2,NULL,5,NULL,NULL,100,'2019-11-11','2019-11-11 10:44:29',0,1),(56,2,2,NULL,5,NULL,NULL,100,'2019-11-11','2019-11-11 10:45:27',0,1),(57,2,2,NULL,5,NULL,NULL,100,'2019-11-11','2019-11-11 10:46:34',0,1),(58,2,1,NULL,2,NULL,NULL,1,'2019-11-11','2019-11-11 10:48:32',0,1),(59,2,1,NULL,2,NULL,NULL,2,'2019-11-11','2019-11-11 10:49:59',0,1);
/*!40000 ALTER TABLE `dev_data_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_device`
--

DROP TABLE IF EXISTS `dev_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `iemi` varchar(50) NOT NULL COMMENT 'iemi编码',
  `uploadtime` varchar(11) NOT NULL COMMENT '上报数据间隔时间秒',
  `name` varchar(50) NOT NULL COMMENT '设备名称',
  `note` varchar(255) DEFAULT NULL COMMENT '设备描述',
  `code` varchar(64) NOT NULL COMMENT '唯一编码',
  `first_time` date DEFAULT NULL COMMENT '首次上报',
  `last_time` date DEFAULT NULL COMMENT '最后一次上报',
  `state` int(10) unsigned DEFAULT NULL COMMENT '设备状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_device`
--

LOCK TABLES `dev_device` WRITE;
/*!40000 ALTER TABLE `dev_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_device_counts`
--

DROP TABLE IF EXISTS `dev_device_counts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_device_counts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `iemi` varchar(50) DEFAULT NULL COMMENT '设备ID',
  `ioname` varchar(10) DEFAULT NULL COMMENT 'io口',
  `counts` int(11) DEFAULT NULL COMMENT '当前总数',
  `create_time` datetime DEFAULT NULL COMMENT '上报时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='IO上报数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_device_counts`
--

LOCK TABLES `dev_device_counts` WRITE;
/*!40000 ALTER TABLE `dev_device_counts` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_device_counts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_device_io`
--

DROP TABLE IF EXISTS `dev_device_io`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_device_io` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `dev_id` int(11) DEFAULT NULL COMMENT '设备id',
  `ioname` varchar(16) DEFAULT NULL COMMENT 'IO名称',
  `ioposition` int(11) DEFAULT NULL COMMENT 'IO对应值的位置',
  `note` varchar(255) DEFAULT NULL COMMENT 'IO用途描述',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备IO表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_device_io`
--

LOCK TABLES `dev_device_io` WRITE;
/*!40000 ALTER TABLE `dev_device_io` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_device_io` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_io`
--

DROP TABLE IF EXISTS `dev_io`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_io` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_id` int(11) DEFAULT NULL,
  `io_order` int(11) DEFAULT NULL,
  `io_name` varchar(45) DEFAULT NULL,
  `line_id` int(11) DEFAULT '0',
  `is_sign` int(11) DEFAULT '0',
  `remark` text,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='硬件IO口配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_io`
--

LOCK TABLES `dev_io` WRITE;
/*!40000 ALTER TABLE `dev_io` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_io` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_list`
--

DROP TABLE IF EXISTS `dev_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(12) DEFAULT NULL,
  `device_status` int(11) DEFAULT '1',
  `device_name` varchar(45) DEFAULT NULL,
  `dev_model_id` int(11) DEFAULT NULL,
  `device_upload_time` int(11) DEFAULT '15',
  `company_id` int(11) DEFAULT '0',
  `remark` text,
  `def_id` int(11) DEFAULT '0',
  `config_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `config_status` int(1) unsigned zerofill DEFAULT '0',
  `sign` int(1) DEFAULT '0',
  `dev_type` int(11) DEFAULT NULL COMMENT '硬件配置类型(0、流水线，1、车间)',
  `line_id` int(11) DEFAULT NULL COMMENT '计数器硬件关联的产线id',
  PRIMARY KEY (`id`),
  KEY `device_id` (`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='硬件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_list`
--

LOCK TABLES `dev_list` WRITE;
/*!40000 ALTER TABLE `dev_list` DISABLE KEYS */;
INSERT INTO `dev_list` VALUES (2,'JSE6343142EB',1,'绕线装备车间-绕线A号位',2,15,2,NULL,0,NULL,'2019-11-05 14:25:51',0,1,1,3),(4,'JS6688AF2C15',1,'绕线装备车间-绕线B号位',2,15,2,NULL,0,NULL,'2019-11-05 16:24:58',0,1,1,NULL),(5,'JS2E0E9CECB6',1,'研磨专业车间-绕线C号位',2,15,2,NULL,0,NULL,'2019-11-05 16:25:29',0,1,1,NULL),(6,'JSEA2E05006D',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 10:47:29',0,0,NULL,NULL),(7,'JSFF67A6A495',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 10:52:35',0,0,NULL,NULL),(8,'JSFF06C5651A',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 11:04:28',0,0,NULL,NULL),(9,'JSF55814335A',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 11:04:45',0,0,NULL,NULL),(10,'JS661960A04C',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 11:28:00',0,0,NULL,NULL),(11,'JSBC3FD2C1A7',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 11:33:31',0,0,NULL,NULL),(12,'JS5CD5A99B66',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 11:38:52',0,0,NULL,NULL),(13,'JS19792D86F6',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 11:46:19',0,0,NULL,NULL),(14,'JSFB227C69E6',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 11:48:53',0,0,NULL,NULL),(15,'JSCF6ABCCFAC',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 11:50:52',0,0,NULL,NULL),(16,'JS0FD991C35B',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 11:53:09',0,0,NULL,NULL),(17,'JSF2F0B04E9F',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 11:53:53',0,0,NULL,NULL),(18,'JS509CA8D8C5',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 12:00:06',0,0,NULL,NULL),(19,'JS74F03E1EAD',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 13:28:28',0,0,NULL,NULL),(20,'JS7FAD0EE2A0',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 13:32:39',0,0,NULL,NULL),(21,'JS3645CD62E1',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 13:35:34',0,0,NULL,NULL),(22,'JS07094C808A',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 13:39:02',0,0,NULL,NULL),(23,'JS1151ECB1A4',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 13:46:20',0,0,NULL,NULL),(24,'JS4CB430DD9F',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 13:47:09',0,0,NULL,NULL),(25,'JSD3CA63CFF6',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 13:50:20',0,0,NULL,NULL),(26,'JSF96BE36A51',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 13:51:24',0,0,NULL,NULL),(27,'JSFD03D6F56F',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 13:52:01',0,0,NULL,NULL),(28,'JS3F0B0690E5',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 13:56:56',0,0,NULL,NULL),(29,'JSC66E71E2D0',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 13:57:39',0,0,NULL,NULL),(30,'JSEA44084B3F',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 14:03:57',0,0,NULL,NULL),(31,'JS5B5C5387B1',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 14:20:22',0,0,NULL,NULL),(32,'JS852DB59FC2',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 14:21:14',0,0,NULL,NULL),(33,'JSC4496ACB27',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 14:21:52',0,0,NULL,NULL),(34,'JSD79B66F0AD',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 14:23:03',0,0,NULL,NULL),(35,'JS5C10B92DCD',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 14:24:48',0,0,NULL,NULL),(36,'JS889A07D537',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 15:20:35',0,0,NULL,NULL),(37,'JS2DEFAACE0A',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 16:17:00',0,0,NULL,NULL),(38,'JS5990A5D21C',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 16:21:53',0,0,NULL,NULL),(39,'JS8FC39223E9',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 16:33:47',0,0,NULL,NULL),(40,'JSFAD0E7038B',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 16:36:40',0,0,NULL,NULL),(41,'JSA92C8CD208',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 16:38:46',0,0,NULL,NULL),(42,'JSADA7A49E99',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 16:57:22',0,0,NULL,NULL),(43,'JS4EE5C9369E',1,NULL,2,15,0,NULL,0,NULL,'2019-11-11 17:03:01',0,0,NULL,NULL);
/*!40000 ALTER TABLE `dev_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_notice`
--

DROP TABLE IF EXISTS `dev_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息主键ID',
  `notice_content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '通知内容',
  `create_id` int(11) DEFAULT NULL COMMENT '创建者',
  `receive_by` int(11) DEFAULT NULL COMMENT '消息通知者',
  `notice_status` int(11) DEFAULT '1' COMMENT '消息状态:1,未发布 2,已发布 3,已下线',
  `company_id` int(11) DEFAULT NULL COMMENT '公司主键ID',
  `create_time` datetime DEFAULT NULL COMMENT '消息创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司消息通知';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_notice`
--

LOCK TABLES `dev_notice` WRITE;
/*!40000 ALTER TABLE `dev_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_page_config`
--

DROP TABLE IF EXISTS `dev_page_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_page_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) DEFAULT NULL,
  `dev_id` int(11) DEFAULT NULL,
  `dev_name` varchar(45) DEFAULT NULL,
  `io_id` int(11) DEFAULT NULL,
  `io_name` varchar(45) DEFAULT NULL,
  `dev_io_config` text,
  `create_time` datetime DEFAULT NULL,
  `dev_id1` int(11) DEFAULT NULL,
  `dev_name1` varchar(45) DEFAULT NULL,
  `io_id1` int(11) DEFAULT NULL,
  `io_name1` varchar(45) DEFAULT NULL,
  `line_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_page_config`
--

LOCK TABLES `dev_page_config` WRITE;
/*!40000 ALTER TABLE `dev_page_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_page_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_page_layout`
--

DROP TABLE IF EXISTS `dev_page_layout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_page_layout` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `layout_type` int(11) DEFAULT '1',
  `layout_name` varchar(45) DEFAULT NULL,
  `layout_status` int(11) DEFAULT '1',
  `create_date` datetime DEFAULT NULL,
  `layout_num` int(11) DEFAULT '2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_page_layout`
--

LOCK TABLES `dev_page_layout` WRITE;
/*!40000 ALTER TABLE `dev_page_layout` DISABLE KEYS */;
INSERT INTO `dev_page_layout` VALUES (1,1,'产线汇总(Sum)',1,'2019-06-18 10:27:48',0),(2,2,'产线详情(Detail)',1,'2019-06-18 10:27:48',0);
/*!40000 ALTER TABLE `dev_page_layout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_page_list`
--

DROP TABLE IF EXISTS `dev_page_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_page_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `page_id` varchar(20) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `page_name` varchar(225) DEFAULT NULL,
  `page_url` varchar(225) DEFAULT NULL,
  `page_type` int(11) DEFAULT NULL,
  `page_layout_type` int(11) DEFAULT NULL,
  `page_back_img` varchar(225) DEFAULT NULL,
  `create_by` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `page_pwd` varchar(45) DEFAULT '123456',
  `img_path` varchar(225) DEFAULT NULL COMMENT 'app端预览背景图',
  PRIMARY KEY (`id`),
  UNIQUE KEY `company_page` (`company_id`,`page_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_page_list`
--

LOCK TABLES `dev_page_list` WRITE;
/*!40000 ALTER TABLE `dev_page_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_page_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_product_list`
--

DROP TABLE IF EXISTS `dev_product_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_product_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `product_code` varchar(45) DEFAULT NULL,
  `product_name` varchar(125) DEFAULT NULL,
  `remark` text,
  `create_time` datetime DEFAULT NULL,
  `def_id` int(11) DEFAULT '0',
  `standard_hour_yield` int(11) DEFAULT NULL,
  `create_by` varchar(45) DEFAULT NULL,
  `product_model` varchar(85) DEFAULT 'null' COMMENT '产品型号',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '单价(含税)',
  `product_img` text COMMENT '产品图片(最多五张)',
  `img_size` int(11) DEFAULT '0' COMMENT '图片数量',
  `ecnstatus` int(11) DEFAULT '0' COMMENT 'ECN状态 0、默认为开启 1、已开启',
  `ecn_text` text,
  `sign` int(11) DEFAULT '0' COMMENT '标记 0、产品 1、半成品',
  `unit` varchar(45) DEFAULT NULL COMMENT '单位',
  `rule_id` int(11) DEFAULT '0' COMMENT '规格配置',
  `labour_price` float(7,2) DEFAULT '0.00' COMMENT '产品工价',
  PRIMARY KEY (`id`),
  KEY `compal_id_code` (`company_id`,`product_code`) /*!80000 INVISIBLE */,
  KEY `def_id` (`def_id`,`id`),
  KEY `com_code` (`company_id`,`product_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='产品编码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_product_list`
--

LOCK TABLES `dev_product_list` WRITE;
/*!40000 ALTER TABLE `dev_product_list` DISABLE KEYS */;
INSERT INTO `dev_product_list` VALUES (1,2,'KFH-0432-V33','手机元器件','主要部件','2019-11-05 09:32:53',0,NULL,'王小虎','V33',0.00,NULL,0,0,NULL,0,NULL,0,12.13),(2,2,'UYT-4295-RF54','手机CPU组件','组装件之一','2019-11-05 10:22:59',0,NULL,'王小虎','4295-RF54',0.00,NULL,0,0,NULL,0,NULL,0,18.45),(3,2,'DYETK-2349V_33','显微镜镜头','红色','2019-11-06 14:32:41',0,NULL,'13559095980','V-33-360',0.00,NULL,0,0,NULL,0,'片',0,25.45);
/*!40000 ALTER TABLE `dev_product_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_production_line`
--

DROP TABLE IF EXISTS `dev_production_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_production_line` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `line_name` varchar(125) DEFAULT NULL,
  `device_liable` int(11) DEFAULT NULL,
  `remark` text,
  `def_id` int(11) DEFAULT '0',
  `company_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `device_liable1` int(11) DEFAULT NULL,
  `manual` int(1) DEFAULT '1',
  `param_config` text,
  `sop_id` int(11) DEFAULT '0' COMMENT '作业指导书编号',
  `product_codes` varchar(545) DEFAULT NULL COMMENT '产品编码',
  `ed_user` int(11) DEFAULT NULL COMMENT '工程责任人',
  `ipqc_user` int(11) DEFAULT NULL COMMENT '品质管理员',
  `me_user` int(11) DEFAULT NULL COMMENT '机械工程师',
  `te_user` int(11) DEFAULT NULL COMMENT '测试员',
  PRIMARY KEY (`id`),
  UNIQUE KEY `company_line_name` (`company_id`,`line_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_production_line`
--

LOCK TABLES `dev_production_line` WRITE;
/*!40000 ALTER TABLE `dev_production_line` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_production_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_user`
--

DROP TABLE IF EXISTS `dev_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone_number` varchar(15) NOT NULL COMMENT '手机号码',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `user_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_user`
--

LOCK TABLES `dev_user` WRITE;
/*!40000 ALTER TABLE `dev_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_user_company`
--

DROP TABLE IF EXISTS `dev_user_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_user_company` (
  `company_id` int(11) NOT NULL COMMENT '公司主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`company_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户公司表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_user_company`
--

LOCK TABLES `dev_user_company` WRITE;
/*!40000 ALTER TABLE `dev_user_company` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_user_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_work_data`
--

DROP TABLE IF EXISTS `dev_work_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_work_data` (
  `data_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '工单数据主键ID',
  `company_id` int(11) DEFAULT NULL COMMENT '公司主键ID',
  `line_id` int(11) DEFAULT NULL COMMENT '产线主键ID',
  `work_id` int(11) DEFAULT NULL COMMENT '工单主键ID',
  `dev_id` int(11) DEFAULT NULL COMMENT '硬件主键ID',
  `dev_name` varchar(125) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '硬件名称',
  `io_id` int(11) DEFAULT NULL COMMENT 'IO主键ID',
  `io_name` varchar(125) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'IO名称',
  `io_order` int(11) DEFAULT NULL COMMENT 'IO顺序',
  `data_sign` int(11) DEFAULT '1' COMMENT '标记是否需要记录初始值',
  `initial_data` int(11) DEFAULT '0' COMMENT '初始值',
  `cumulative_num` int(11) DEFAULT '0' COMMENT '累计产量',
  `manual_num` int(11) DEFAULT '0' COMMENT '手动调整产量数据',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `io_sign` int(1) DEFAULT '0',
  `sc_type` int(11) DEFAULT NULL COMMENT '车间或者流水线标记(0、流水线，1、车间)',
  PRIMARY KEY (`data_id`),
  KEY `company_line_dev_io_order` (`company_id`,`line_id`,`work_id`,`dev_id`,`io_id`,`io_order`),
  KEY `single_kb_index` (`work_id`,`io_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='工单数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_work_data`
--

LOCK TABLES `dev_work_data` WRITE;
/*!40000 ALTER TABLE `dev_work_data` DISABLE KEYS */;
INSERT INTO `dev_work_data` VALUES (1,2,1,1,2,'绕线装备车间-绕线A号位',3,NULL,NULL,1,0,500,0,'2019-11-05 16:44:00',0,1),(4,2,1,3,2,'绕线装备车间-绕线A号位',3,NULL,NULL,1,0,20,0,'2019-11-06 13:44:32',0,1),(5,2,1,3,5,'绕线装备车间-JS2E0E9CECB6',9,NULL,NULL,1,0,480,0,'2019-11-06 13:44:32',0,1),(6,2,2,2,5,'研磨专业车间-绕线C号位',9,NULL,NULL,1,0,500,0,'2019-11-06 13:58:58',0,1),(7,2,1,4,2,'绕线装备车间-绕线A号位',3,NULL,NULL,1,0,0,0,'2019-11-11 10:51:42',0,1);
/*!40000 ALTER TABLE `dev_work_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_work_day_hour`
--

DROP TABLE IF EXISTS `dev_work_day_hour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_work_day_hour` (
  `hour_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '工单IO每小时数据主键ID',
  `company_id` int(11) DEFAULT NULL COMMENT '公司主键ID',
  `line_id` int(11) DEFAULT NULL COMMENT '产线主键ID',
  `work_id` int(11) DEFAULT NULL COMMENT '工单主键ID',
  `dev_id` int(11) DEFAULT NULL COMMENT '硬件主键ID',
  `dev_name` varchar(125) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '硬件名称',
  `io_id` int(11) DEFAULT NULL COMMENT 'IO口',
  `io_name` varchar(125) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'IO名称',
  `io_order` int(11) DEFAULT NULL COMMENT 'IO顺序',
  `hour1` int(11) DEFAULT '0' COMMENT '0~1点的数据',
  `hour2` int(11) DEFAULT '0' COMMENT '1~2点的数据',
  `hour3` int(11) DEFAULT '0' COMMENT '2~3点的数据',
  `hour4` int(11) DEFAULT '0' COMMENT '3~4点的数据',
  `hour5` int(11) DEFAULT '0' COMMENT '4~5点的数据',
  `hour6` int(11) DEFAULT '0' COMMENT '5~6点的数据',
  `hour7` int(11) DEFAULT '0' COMMENT '6~7点的数据',
  `hour8` int(11) DEFAULT '0' COMMENT '7~8点的数据',
  `hour9` int(11) DEFAULT '0' COMMENT '8~9点的数据',
  `hour10` int(11) DEFAULT '0' COMMENT '9~10点的数据',
  `hour11` int(11) DEFAULT '0' COMMENT '10~11点的数据',
  `hour12` int(11) DEFAULT '0' COMMENT '11~12点的数据',
  `hour13` int(11) DEFAULT '0' COMMENT '12~13点的数据',
  `hour14` int(11) DEFAULT '0' COMMENT '13~14点的数据',
  `hour15` int(11) DEFAULT '0' COMMENT '14~15点的数据',
  `hour16` int(11) DEFAULT '0' COMMENT '15~16点的数据',
  `hour17` int(11) DEFAULT '0' COMMENT '16~17点的数据',
  `hour18` int(11) DEFAULT '0' COMMENT '17~18点的数据',
  `hour19` int(11) DEFAULT '0' COMMENT '18~19点的数据',
  `hour20` int(11) DEFAULT '0' COMMENT '19~20点的数据',
  `hour21` int(11) DEFAULT '0' COMMENT '20~21点的数据',
  `hour22` int(11) DEFAULT '0' COMMENT '21~22点的数据',
  `hour23` int(11) DEFAULT '0' COMMENT '22~23点的数据',
  `hour24` int(11) DEFAULT '0' COMMENT '23~24点的数据',
  `data_time` date DEFAULT NULL COMMENT '对应数据记录日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`hour_id`),
  KEY `company_id_work_dev_io_hour` (`company_id`,`line_id`,`work_id`,`dev_id`,`data_time`,`io_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单各个IO口每天每小时数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_work_day_hour`
--

LOCK TABLES `dev_work_day_hour` WRITE;
/*!40000 ALTER TABLE `dev_work_day_hour` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_work_day_hour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_work_exception_list`
--

DROP TABLE IF EXISTS `dev_work_exception_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_work_exception_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '工单异常主键ID',
  `company_id` int(11) DEFAULT NULL COMMENT '公司主键ID',
  `work_id` int(11) DEFAULT NULL COMMENT '工单主键ID',
  `exce_type` int(11) DEFAULT NULL COMMENT '异常类型ID',
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注信息',
  `exce_statut` int(11) DEFAULT '0' COMMENT '异常状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `handle_user` varchar(30) DEFAULT NULL COMMENT '处理者',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `handle_content` text,
  PRIMARY KEY (`id`),
  KEY `company_work_id` (`company_id`,`work_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='各个工单异常情况记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_work_exception_list`
--

LOCK TABLES `dev_work_exception_list` WRITE;
/*!40000 ALTER TABLE `dev_work_exception_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_work_exception_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_work_exception_type`
--

DROP TABLE IF EXISTS `dev_work_exception_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_work_exception_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '工单异常类型主键ID',
  `company_id` int(11) DEFAULT NULL COMMENT '公司主键ID',
  `type_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '异常类型名称',
  `def_id` int(11) DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单工单异常类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_work_exception_type`
--

LOCK TABLES `dev_work_exception_type` WRITE;
/*!40000 ALTER TABLE `dev_work_exception_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `dev_work_exception_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dev_work_order`
--

DROP TABLE IF EXISTS `dev_work_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dev_work_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '工单主键ID',
  `workorder_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '工单名称',
  `workorder_number` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '工单号(唯一)',
  `company_id` int(11) DEFAULT NULL COMMENT '公司主键ID',
  `line_id` int(11) DEFAULT NULL COMMENT '产线ID',
  `product_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '产品名称',
  `product_code` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '产品编码',
  `product_number` int(11) DEFAULT NULL COMMENT '生产数量',
  `production_start` datetime DEFAULT NULL COMMENT '计划生产开始时间',
  `production_end` datetime DEFAULT NULL COMMENT '计划生产完成时间',
  `start_time` datetime DEFAULT NULL COMMENT '实际生产开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '实际生产完成时间',
  `product_standard_hour` int(11) DEFAULT '0' COMMENT '标准小时产量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(30) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `device_liable` int(11) DEFAULT NULL COMMENT '责任人',
  `update_by` varchar(30) DEFAULT NULL COMMENT '更新者',
  `people_number` int(11) DEFAULT '1' COMMENT '用工人数',
  `working_hour` float DEFAULT NULL COMMENT '正常工时',
  `overtime_hour` float DEFAULT NULL COMMENT '加班工时',
  `overtime_race` float DEFAULT NULL COMMENT '加班倍率',
  `manual_time` float DEFAULT NULL COMMENT '手动调整工时',
  `workorder_status` int(11) DEFAULT '0' COMMENT '工单生产状态',
  `operation_status` int(11) DEFAULT '0' COMMENT '工单操作状态',
  `init_date` int(11) DEFAULT '1' COMMENT '是否需要初始化数据',
  `work_sign` int(11) DEFAULT '0' COMMENT '标记是否确认数据',
  `scrap_num` int(11) DEFAULT '0' COMMENT '报废品',
  `actual_warehouse_num` int(11) DEFAULT '0' COMMENT '实际入库数量',
  `cumulative_number` int(11) DEFAULT '0',
  `direct_pass_rate` float DEFAULT NULL,
  `paramConfig` text,
  `bad_number` int(11) DEFAULT '0',
  `product_model` varchar(85) DEFAULT NULL COMMENT '产品型号',
  `remark` text,
  `order_code` varchar(45) DEFAULT NULL,
  `ecn_status` int(11) DEFAULT '0',
  `ecn_text` text,
  `sign` int(11) DEFAULT '1',
  `put_into_number` int(11) DEFAULT NULL COMMENT '投入产量',
  `sign_time` datetime DEFAULT NULL COMMENT '标记开始时间',
  `sign_huor` float(10,2) DEFAULT '0.00',
  `abolish` tinyint(1) DEFAULT '0' COMMENT '标记工单是否作废',
  `wl_sign` int(11) DEFAULT '0' COMMENT '工位是在车间还是在单工位生产标记(0、流水线默认值，1、单工位)',
  `make_type` int(11) DEFAULT '0' COMMENT '工单生产类型(0、默认值生产的是成品，1、生产半成品)',
  `work_price` float(10,2) DEFAULT '0.00' COMMENT '工单生产单价',
  `pb_sign` int(11) DEFAULT '0' COMMENT '暂停开始最后一次计数标志(默认值0、需要计数，1、不需要计数)',
  `res_sign` int(11) DEFAULT '-1' COMMENT 'ASOP更新反馈 -1、默认值 0、未更新完 1、全部更新',
  PRIMARY KEY (`id`),
  UNIQUE KEY `work_id_company` (`workorder_number`,`company_id`) /*!80000 INVISIBLE */,
  KEY `company_line_status` (`company_id`,`line_id`,`workorder_status`,`operation_status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='工单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dev_work_order`
--

LOCK TABLES `dev_work_order` WRITE;
/*!40000 ALTER TABLE `dev_work_order` DISABLE KEYS */;
INSERT INTO `dev_work_order` VALUES (1,NULL,'GD20191105095935',2,1,'手机元器件','KFH-0432-V33',500,'2019-11-05 09:59:00',NULL,'2019-11-05 16:44:00','2019-11-05 18:25:19',0,'2019-11-05 09:59:49','王小虎','2019-11-05 18:25:19',3,'赵云龙',1,NULL,NULL,NULL,NULL,2,3,1,1,0,0,500,NULL,NULL,0,'V33',NULL,NULL,0,NULL,1,600,'2019-11-05 17:42:41',1.64,0,1,0,12.13,0,-1),(2,NULL,'GD20191105103642',2,2,'手机CPU组件','UYT-4295-RF54',500,'2019-11-05 10:36:00',NULL,'2019-11-06 13:58:58','2019-11-06 14:02:42',0,'2019-11-05 10:36:50','王小虎','2019-11-08 08:30:35',3,'王小虎',1,NULL,NULL,NULL,NULL,2,3,1,1,0,0,500,NULL,NULL,0,'4295-RF54',NULL,NULL,0,NULL,0,500,'2019-11-06 14:01:14',0.04,0,1,0,18.45,0,-1),(3,NULL,'GD20191106100446',2,1,'手机元器件','KFH-0432-V33',1000,'2019-11-06 10:04:00',NULL,'2019-11-06 13:44:32','2019-11-06 13:57:57',0,'2019-11-06 10:04:59','王小虎','2019-11-06 13:58:15',3,'王小虎',1,NULL,NULL,NULL,NULL,2,3,1,1,0,0,480,NULL,NULL,0,'V33',NULL,NULL,0,NULL,0,NULL,'2019-11-06 13:44:32',0.22,0,1,0,12.13,0,-1),(4,NULL,'GD20191106140307',2,1,'手机CPU组件','UYT-4295-RF54',500,'2019-11-06 14:03:00',NULL,'2019-11-11 10:51:42','2019-11-11 11:55:33',0,'2019-11-06 14:03:24','王小虎','2019-11-11 11:55:33',3,'王小虎',1,NULL,NULL,NULL,NULL,2,3,1,1,0,0,0,NULL,NULL,0,'4295-RF54',NULL,NULL,0,NULL,1,NULL,'2019-11-11 11:55:30',0.07,0,1,0,18.45,0,-1);
/*!40000 ALTER TABLE `dev_work_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `language` (
  `info` text,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='app本地化';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES ('{\"计划排单-0\":\"计划排单\",\"计划排单-1\":\"Planning\",\"产线列表-0\":\"产线列表\",\"产线列表-1\":\"Prod. Line\",\"消息通知-0\":\"消息通知\",\"消息通知-1\":\"Message\",\"生产报表-0\":\"生产报表\",\"生产报表-1\":\"Prod. Reports\",\"硬件配置-0\":\"硬件配置\",\"硬件配置-1\":\"Configuration\",\"文件管理-0\":\"文件管理\",\"文件管理-1\":\"File Mgmt.\",\"硬件状态-0\":\"硬件状态\",\"硬件状态-1\":\"Monitor Status\",\"工单录入-0\":\"工单录入\",\"工单录入-1\":\"New Prod. Order\",\"MES规则-0\":\"MES规则\",\"MES规则-1\":\"MES Rules\",\"MES数据-0\":\"MES数据\",\"MES数据-1\":\"MES Data\",\"售后录入-0\":\"售后录入\",\"售后录入-1\":\"Scan Returns\",\"流水线工单-0\":\"流水线工单\",\"流水线工单-1\":\"Prod. Order\",\"选择产线-0\":\"选择产线\",\"选择产线-1\":\"Select Prod.Line\",\"生产状态-0\":\"生产状态\",\"生产状态-1\":\"Prod. Status\",\"全部-0\":\"全部\",\"全部-1\":\"All\",\"当天-0\":\"当天\",\"当天-1\":\"Today\",\"生产未开始-0\":\"生产未开始\",\"生产未开始-1\":\"Prod. Not Start\",\"生产进行中-0\":\"生产进行中\",\"生产进行中-1\":\"Ongoing\",\"生产已完成-0\":\"生产已完成\",\"生产已完成-1\":\"Finished\",\"操作未开始-0\":\"操作未开始\",\"操作未开始-1\":\"Not Start\",\"待提交工单-0\":\"待提交工单\",\"待提交工单-1\":\"Waiting Submit\",\"已提交工单-0\":\"已提交工单\",\"已提交工单-1\":\"Submitted\",\"开始-0\":\"开始\",\"开始-1\":\"Go\",\"完成-0\":\"完成\",\"完成-1\":\"End\",\"工单详情-0\":\"工单详情\",\"工单详情-1\":\"Prod. Order Details\",\"编辑-0\":\"编辑\",\"编辑-1\":\"Edit\",\"产线\\/车间-0\":\"产线\\/车间\",\"产线\\/车间-1\":\"Prod.Line\\/Workshop\",\"工单号-0\":\"工单号\",\"工单号-1\":\"Prod. Order No.\",\"产品编码-0\":\"产品编码\",\"产品编码-1\":\"Prod. Part Number\",\"名称-0\":\"名称\",\"名称-1\":\"Description\",\"产品型号-0\":\"产品型号\",\"产品型号-1\":\"Model\",\"计划开始\\/完成-0\":\"计划开始\\/完成\",\"计划开始\\/完成-1\":\"Planed Start Finish\",\"实际开始\\/完成-0\":\"实际开始\\/完成\",\"实际开始\\/完成-1\":\"Actual Start Finish\",\"用工人数-0\":\"用工人数\",\"用工人数-1\":\"Operator Qty.\",\"正常\\/加班工时-0\":\"正常\\/加班工时\",\"正常\\/加班工时-1\":\"Woking Hours OT Hours\",\"调整工时-0\":\"调整工时\",\"调整工时-1\":\"Woking Hours Adjustment \",\"加班倍率-0\":\"加班倍率\",\"加班倍率-1\":\"OT Setting\",\"实际产量-0\":\"实际产量\",\"实际产量-1\":\"Actural Output\",\"投入\\/入库数量-0\":\"投入\\/入库数量\",\"投入\\/入库数量-1\":\"In Out Qty.\",\"不良品数量-0\":\"不良品数量\",\"不良品数量-1\":\"Failure Qty.\",\"报废数量-0\":\"报废数量\",\"报废数量-1\":\"Discard Qty.\",\"备注信息-0\":\"备注信息\",\"备注信息-1\":\"Remarks\",\"产线名称-0\":\"产线名称\",\"产线名称-1\":\"Prod.Line Name\",\"责任人一-0\":\"责任人一\",\"责任人一-1\":\"Resp.1\",\"责任人二-0\":\"责任人二\",\"责任人二-1\":\"Resp.2\",\"创建时间-0\":\"创建时间\",\"创建时间-1\":\"Created Date\",\"点击查看-0\":\"点击查看\",\"点击查看-1\":\"Click to View\",\"浏览-0\":\"浏览\",\"浏览-1\":\"Browse\",\"开始时间-0\":\"开始时间\",\"开始时间-1\":\"Start Time\",\"结束时间-0\":\"结束时间\",\"结束时间-1\":\"Finish Time\",\"查询-0\":\"查询\",\"查询-1\":\"Searching\",\"最近-0\":\"最近\",\"最近-1\":\"Recent\",\"工作-0\":\"工作\",\"工作-1\":\"Work\",\"我的-0\":\"我的\",\"我的-1\":\"My\",\"功能切换-0\":\"功能切换\",\"功能切换-1\":\"Select Function\",\"生产看板-0\":\"生产看板\",\"生产看板-1\":\"Prod. KanBan\",\"设置-0\":\"设置\",\"设置-1\":\"Configuration\",\"检查更新-0\":\"检查更新\",\"检查更新-1\":\"Check Update\",\"关于本机-0\":\"关于本机\",\"切换用户-0\":\"切换用户\",\"修改密码-0\":\"修改密码\",\"修改密码-1\":\"Change PassWord\",\"手机号-0\":\"手机号\",\"手机号-1\":\"Phone No.\",\"验证-0\":\"验证\",\"验证-1\":\"Verification\",\"验证码-0\":\"验证码\",\"验证码-1\":\"Verification Code\",\"重置-0\":\"重置\",\"重置-1\":\"Reset\",\"提示-0\":\"提示\",\"提示-1\":\"Tips\",\"确定要退出么?-0\":\"确定要退出么?\",\"确定要退出么?-1\":\"Sure to quit?\",\"确定-0\":\"确定\",\"确定-1\":\"Yes\",\"取消-0\":\"取消\",\"取消-1\":\"No\",\"已是最新版本-0\":\"已是最新版本\",\"已是最新版本-1\":\"Latest Version\",\"仓库发料-0\":\"仓库发料\",\"仓库发料-1\":\"Warehouse \",\"仓库-0\":\"仓库\",\"仓库-1\":\"Warehouse\",\"生产-0\":\"生产\",\"生产-1\":\"Prod.\",\"物料编码-0\":\"物料编码\",\"物料编码-1\":\"Material P\\/N\",\"批次数量-0\":\"批次数量\",\"批次数量-1\":\"Batch Qty.\",\"配料批次-0\":\"配料批次\",\"配料批次-1\":\"Material Batch Code\",\"保存-0\":\"保存\",\"保存-1\":\"Save\",\"规则名称-0\":\"规则名称\",\"规则名称-1\":\"Rule Name\",\"成品编码-0\":\"成品编码\",\"成品编码-1\":\"Finish Goods P\\/N\",\"半成品-0\":\"半成品\",\"半成品-1\":\"Semi Finished\",\"半成品编码-0\":\"半成品编码\",\"半成品编码-1\":\"Semi Finished P\\/N\",\"成品-0\":\"成品\",\"成品-1\":\"Finished Goods\",\"MES规则配置明细-0\":\"MES规则配置明细\",\"MES规则配置明细-1\":\"MES Rules Details\",\"主码信息-0\":\"主码信息\",\"主码信息-1\":\"Finished Batch Information\",\"批次号-0\":\"批次号\",\"批次号-1\":\"Batch No.\",\"快速检索-0\":\"快速检索\",\"快速检索-1\":\"Searching\",\"检索内容-0\":\"检索内容\",\"检索内容-1\":\"Search Contents\",\"是否允许八爪鱼用户端拍摄照片和录制视频-0\":\"是否允许八爪鱼用户端拍摄照片和录制视频\",\"是否允许八爪鱼用户端拍摄照片和录制视频-1\":\"Allowed to use camera?\",\"拒绝-0\":\"拒绝\",\"拒绝-1\":\"No\",\"总是允许-0\":\"总是允许\",\"总是允许-1\":\"Yes\",\"打开闪光灯-0\":\"打开闪光灯\",\"打开闪光灯-1\":\"Flashing Light\",\"相册-0\":\"相册\",\"相册-1\":\"Album\",\"扫一扫-0\":\"扫一扫\",\"扫一扫-1\":\"Scan\",\"条码录入-0\":\"条码录入\",\"条码录入-1\":\"Scan Barcode\",\"流水线配置-0\":\"流水线配置\",\"流水线配置-1\":\"Prod.Line Config.\",\"工位配置-0\":\"工位配置\",\"工位配置-1\":\"Process Config.\",\"SOP配置-0\":\"SOP配置\",\"SOP配置-1\":\"SOP Files Config.\",\"工位名称-0\":\"工位名称\",\"工位名称-1\":\"Process Name\",\"SOP文件-0\":\"SOP文件\",\"SOP文件-1\":\"SOP Files\",\"未选择-0\":\"未选择\",\"未选择-1\":\"Unselected\",\"配置名称-0\":\"配置名称\",\"配置名称-1\":\"Configuration Name\",\"适用编码-0\":\"适用编码\",\"适用编码-1\":\"Apply to P\\/N\",\"SOP名称-0\":\"SOP名称\",\"SOP名称-1\":\"SOP Name\",\"ASOP看板-0\":\"ASOP看板\",\"ASOP看板-1\":\"SOP KanBan\",\"我的收藏-0\":\"我的收藏\",\"我的收藏-1\":\"My Favorites\",\"没有任务足迹-0\":\"没有任务足迹\",\"没有任务足迹-1\":\"No Footprints\",\"最近浏览-0\":\"最近浏览\",\"最近浏览-1\":\"Recently Browse\"}\n',1);
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) NOT NULL,
  `calendar_name` varchar(200) NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `cron_expression` varchar(200) NOT NULL,
  `time_zone_id` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

LOCK TABLES `qrtz_cron_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_cron_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_cron_triggers` VALUES ('RuoyiScheduler','__TASK_CLASS_NAME__2','DEFAULT','0/20 * * * * ?','Asia/Shanghai'),('RuoyiScheduler','__TASK_CLASS_NAME__4','DEFAULT','0 0/1 * * * ?','Asia/Shanghai');
/*!40000 ALTER TABLE `qrtz_cron_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `entry_id` varchar(95) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) NOT NULL,
  `job_name` varchar(200) DEFAULT NULL,
  `job_group` varchar(200) DEFAULT NULL,
  `is_nonconcurrent` varchar(1) DEFAULT NULL,
  `requests_recovery` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `job_class_name` varchar(250) NOT NULL,
  `is_durable` varchar(1) NOT NULL,
  `is_nonconcurrent` varchar(1) NOT NULL,
  `is_update_data` varchar(1) NOT NULL,
  `requests_recovery` varchar(1) NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

LOCK TABLES `qrtz_job_details` WRITE;
/*!40000 ALTER TABLE `qrtz_job_details` DISABLE KEYS */;
INSERT INTO `qrtz_job_details` VALUES ('RuoyiScheduler','__TASK_CLASS_NAME__1','DEFAULT',NULL,'com.ruoyi.project.monitor.job.util.ScheduleJob','0','1','0','0',_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0__TASK_PROPERTIES__sr\0(com.ruoyi.project.monitor.job.domain.Job\0\0\0\0\0\0\0\0L\0cronExpressiont\0Ljava/lang/String;L\0jobGroupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0jobNameq\0~\0	L\0\nmethodNameq\0~\0	L\0methodParamsq\0~\0	L\0\rmisfirePolicyq\0~\0	L\0statusq\0~\0	xr\0)com.ruoyi.framework.web.domain.BaseEntity\0\0\0\0\0\0\0\0L\0createByq\0~\0	L\0\ncreateTimet\0Ljava/util/Date;L\0paramsq\0~\0L\0remarkq\0~\0	L\0searchValueq\0~\0	L\0updateByq\0~\0	L\0\nupdateTimeq\0~\0xpt\0adminsr\0java.util.Datehj�KYt\0\0xpw\0\0b,\�)\�xpt\0\0pppt\00/10 * * * * ?t\0系统默认（无参）sr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0ryTaskt\0\nryNoParamst\0\0t\03t\01x\0'),('RuoyiScheduler','__TASK_CLASS_NAME__2','DEFAULT',NULL,'com.ruoyi.project.monitor.job.util.ScheduleJob','0','1','0','0',_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0__TASK_PROPERTIES__sr\0(com.ruoyi.project.monitor.job.domain.Job\0\0\0\0\0\0\0\0L\0cronExpressiont\0Ljava/lang/String;L\0jobGroupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0jobNameq\0~\0	L\0\nmethodNameq\0~\0	L\0methodParamsq\0~\0	L\0\rmisfirePolicyq\0~\0	L\0statusq\0~\0	xr\0)com.ruoyi.framework.web.domain.BaseEntity\0\0\0\0\0\0\0\0L\0createByq\0~\0	L\0\ncreateTimet\0Ljava/util/Date;L\0paramsq\0~\0L\0remarkq\0~\0	L\0searchValueq\0~\0	L\0updateByq\0~\0	L\0\nupdateTimeq\0~\0xpt\0adminsr\0java.util.Datehj�KYt\0\0xpw\0\0b,\�)\�xpt\0\0pppt\00/20 * * * * ?t\0系统默认（有参）sr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0ryTaskt\0ryParamst\0ryt\03t\01x\0'),('RuoyiScheduler','__TASK_CLASS_NAME__4','DEFAULT',NULL,'com.ruoyi.project.monitor.job.util.ScheduleJob','0','1','0','0',_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0__TASK_PROPERTIES__sr\0(com.ruoyi.project.monitor.job.domain.Job\0\0\0\0\0\0\0\0L\0cronExpressiont\0Ljava/lang/String;L\0jobGroupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0jobNameq\0~\0	L\0\nmethodNameq\0~\0	L\0methodParamsq\0~\0	L\0\rmisfirePolicyq\0~\0	L\0statusq\0~\0	xr\0)com.ruoyi.framework.web.domain.BaseEntity\0\0\0\0\0\0\0\0	L\0createByq\0~\0	L\0\ncreateTimet\0Ljava/util/Date;L\0pageNumt\0Ljava/lang/Integer;L\0pageSizeq\0~\0\rL\0paramsq\0~\0L\0remarkq\0~\0	L\0searchValueq\0~\0	L\0updateByq\0~\0	L\0\nupdateTimeq\0~\0xpt\0adminppppt\0\0pppt\0\r0 0/1 * * * ?t\0系统默认(无参)sr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0ryTaskt\0\nryNoParamst\0\0t\01t\01x\0');
/*!40000 ALTER TABLE `qrtz_job_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) NOT NULL,
  `lock_name` varchar(40) NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
INSERT INTO `qrtz_locks` VALUES ('RuoyiScheduler','STATE_ACCESS'),('RuoyiScheduler','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) NOT NULL,
  `instance_name` varchar(200) NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
INSERT INTO `qrtz_scheduler_state` VALUES ('RuoyiScheduler','DY-2019032812111573455331201',1573468026750,15000);
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `str_prop_1` varchar(512) DEFAULT NULL,
  `str_prop_2` varchar(512) DEFAULT NULL,
  `str_prop_3` varchar(512) DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) DEFAULT NULL,
  `bool_prop_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) NOT NULL,
  `trigger_name` varchar(200) NOT NULL,
  `trigger_group` varchar(200) NOT NULL,
  `job_name` varchar(200) NOT NULL,
  `job_group` varchar(200) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `next_fire_time` bigint(13) DEFAULT NULL,
  `prev_fire_time` bigint(13) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) NOT NULL,
  `trigger_type` varchar(8) NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) DEFAULT NULL,
  `calendar_name` varchar(200) DEFAULT NULL,
  `misfire_instr` smallint(2) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--

LOCK TABLES `qrtz_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_triggers` DISABLE KEYS */;
INSERT INTO `qrtz_triggers` VALUES ('RuoyiScheduler','__TASK_CLASS_NAME__1','DEFAULT','__TASK_CLASS_NAME__1','DEFAULT',NULL,1548855960000,-1,5,'PAUSED','CRON',1548855955000,0,NULL,2,_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0__TASK_PROPERTIES__sr\0(com.ruoyi.project.monitor.job.domain.Job\0\0\0\0\0\0\0\0L\0cronExpressiont\0Ljava/lang/String;L\0jobGroupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0jobNameq\0~\0	L\0\nmethodNameq\0~\0	L\0methodParamsq\0~\0	L\0\rmisfirePolicyq\0~\0	L\0statusq\0~\0	xr\0)com.ruoyi.framework.web.domain.BaseEntity\0\0\0\0\0\0\0\0L\0createByq\0~\0	L\0\ncreateTimet\0Ljava/util/Date;L\0paramsq\0~\0L\0remarkq\0~\0	L\0searchValueq\0~\0	L\0updateByq\0~\0	L\0\nupdateTimeq\0~\0xpt\0adminsr\0java.util.Datehj�KYt\0\0xpw\0\0b,\�)\�xpt\0\0pppt\00/10 * * * * ?t\0系统默认（无参）sr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0ryTaskt\0\nryNoParamst\0\0t\03t\01x\0'),('RuoyiScheduler','__TASK_CLASS_NAME__2','DEFAULT','__TASK_CLASS_NAME__2','DEFAULT',NULL,1548855960000,-1,5,'PAUSED','CRON',1548855955000,0,NULL,2,_binary '�\�\0sr\0org.quartz.JobDataMap���迩�\�\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�\�\��\�](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap\�.�(v\n\�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap\��\�`\�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0__TASK_PROPERTIES__sr\0(com.ruoyi.project.monitor.job.domain.Job\0\0\0\0\0\0\0\0L\0cronExpressiont\0Ljava/lang/String;L\0jobGroupq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0jobNameq\0~\0	L\0\nmethodNameq\0~\0	L\0methodParamsq\0~\0	L\0\rmisfirePolicyq\0~\0	L\0statusq\0~\0	xr\0)com.ruoyi.framework.web.domain.BaseEntity\0\0\0\0\0\0\0\0L\0createByq\0~\0	L\0\ncreateTimet\0Ljava/util/Date;L\0paramsq\0~\0L\0remarkq\0~\0	L\0searchValueq\0~\0	L\0updateByq\0~\0	L\0\nupdateTimeq\0~\0xpt\0adminsr\0java.util.Datehj�KYt\0\0xpw\0\0b,\�)\�xpt\0\0pppt\00/20 * * * * ?t\0系统默认（有参）sr\0java.lang.Long;�\�̏#\�\0J\0valuexr\0java.lang.Number����\��\0\0xp\0\0\0\0\0\0\0t\0ryTaskt\0ryParamst\0ryt\03t\01x\0'),('RuoyiScheduler','__TASK_CLASS_NAME__4','DEFAULT','__TASK_CLASS_NAME__4','DEFAULT',NULL,1571192580000,1571192520000,5,'PAUSED','CRON',1571192446000,0,NULL,-1,'');
/*!40000 ALTER TABLE `qrtz_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(100) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','Y','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','初始化密码 123456');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` int(11) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dict_data` (
  `dict_code` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'男','0','sys_user_sex','','','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态'),(10,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统默认是'),(11,2,'否','N','sys_yes_no','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统默认否'),(12,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','通知'),(13,2,'公告','2','sys_notice_type','','success','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','公告'),(14,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),(15,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','关闭状态'),(16,1,'新增','1','sys_oper_type','','info','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','新增操作'),(17,2,'修改','2','sys_oper_type','','info','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','修改操作'),(18,3,'删除','3','sys_oper_type','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','删除操作'),(19,4,'授权','4','sys_oper_type','','primary','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','授权操作'),(20,5,'导出','5','sys_oper_type','','warning','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','导出操作'),(21,6,'导入','6','sys_oper_type','','warning','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','导入操作'),(22,7,'强退','7','sys_oper_type','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','强退操作'),(23,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','生成操作'),(24,8,'清空数据','9','sys_oper_type','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','清空操作'),(25,1,'成功','0','sys_common_status','','primary','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),(26,2,'失败','1','sys_common_status','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态');
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_dict_type` (
  `dict_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'用户性别','sys_user_sex','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','用户性别列表'),(2,'菜单状态','sys_show_hide','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','菜单状态列表'),(3,'系统开关','sys_normal_disable','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统开关列表'),(4,'任务状态','sys_job_status','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','任务状态列表'),(5,'系统是否','sys_yes_no','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统是否列表'),(6,'通知类型','sys_notice_type','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','通知类型列表'),(7,'通知状态','sys_notice_status','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','通知状态列表'),(8,'操作类型','sys_oper_type','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','操作类型列表'),(9,'系统状态','sys_common_status','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','登录状态列表');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_job` (
  `job_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT '' COMMENT '任务组名',
  `method_name` varchar(500) DEFAULT '' COMMENT '任务方法',
  `method_params` varchar(50) DEFAULT NULL COMMENT '方法参数',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` VALUES (2,'ryTask','系统默认（有参）','ryParams','ry','0/20 * * * * ?','3','1','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),(4,'ryTask','系统默认(无参)','ryNoParams',NULL,'0 0/1 * * * ?','0','1','admin','2019-10-16 10:20:46','admin','2019-10-16 10:22:05','');
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_job_log` (
  `job_log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `method_name` varchar(500) DEFAULT NULL COMMENT '任务方法',
  `method_params` varchar(50) DEFAULT NULL COMMENT '方法参数',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_logininfor` (
  `info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `ipaddr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` int(11) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `url` varchar(200) DEFAULT '#' COMMENT '请求地址',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `app_sign` int(11) DEFAULT '0' COMMENT 'app是否展示标识(0、默认值不展示，1、展示)',
  `menu_tag` int(11) DEFAULT '0' COMMENT '菜单系统权限(0、共有，1、asop专用，2、mes专有，3、看板专有)',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1532 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'后台管理',0,1,'#','M','0','','fa fa-gear','admin','2018-03-16 11:33:00','admin','2019-04-23 14:04:31','系统管理目录',1,0),(2,'系统监控',0,2,'#','M','0','','fa fa-video-camera','admin','2018-03-16 11:33:00','admin','2019-04-28 17:53:33','系统监控目录',0,0),(100,'用户管理',1,1,'/system/user','C','0','system:user:view','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','用户管理菜单',1,0),(101,'角色分配',1,2,'/system/role','C','0','system:role:view','#','admin','2018-03-16 11:33:00','admin','2019-04-23 14:05:34','角色管理菜单',0,0),(102,'菜单管理',1,3,'/system/menu','C','0','system:menu:view','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','菜单管理菜单',0,0),(111,'数据监控',2,3,'/monitor/data','C','0','monitor:data:view','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','数据监控菜单',0,0),(112,'服务监控',2,3,'/monitor/server','C','0','monitor:server:view','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','服务监控菜单',0,0),(1000,'用户查询',100,1,'#','F','0','system:user:list','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1001,'用户新增',100,2,'#','F','0','system:user:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1002,'用户修改',100,3,'#','F','0','system:user:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1003,'用户删除',100,4,'#','F','0','system:user:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1004,'用户导出',100,5,'#','F','0','system:user:export','#','admin','2018-03-16 11:33:00','admin','2019-04-03 08:51:19','',0,0),(1005,'用户导入',100,6,'#','F','0','system:user:import','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1006,'重置密码',100,7,'#','F','0','system:user:resetPwd','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1007,'角色查询',101,1,'#','F','0','system:role:list','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1008,'角色新增',101,2,'#','F','0','system:role:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1009,'角色修改',101,3,'#','F','0','system:role:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1010,'角色删除',101,4,'#','F','0','system:role:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1011,'角色导出',101,5,'#','F','0','system:role:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1012,'菜单查询',102,1,'#','F','0','system:menu:list','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1013,'菜单新增',102,2,'#','F','0','system:menu:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1014,'菜单修改',102,3,'#','F','0','system:menu:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1015,'菜单删除',102,4,'#','F','0','system:menu:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1058,'个人设置',1,10,'/userDetail/userDetail','C','0','','#','admin','2019-03-30 09:47:16','admin','2019-03-30 17:33:00','',0,0),(1059,'首页设置',1,11,'/indexSetting/indexSetting','C','0','','#','admin','2019-03-30 10:09:50','admin','2019-03-30 17:33:06','',0,0),(1060,'编码管理',0,3,'#','M','0','','fa fa-calculator','admin','2019-03-30 10:21:19','admin','2019-09-20 11:43:15','',1,0),(1062,'工单管理',1452,1,'/device/devWorkOrder/lineWorkOrder','C','0','device:devWorkOrder:view','#','admin','2019-03-30 10:22:33','admin','2019-09-20 11:31:43','',1,2),(1071,'硬件管理',1470,5,'/device/devList','C','0','device:devList:view','#','admin','2019-04-10 15:03:18','admin','2019-09-25 18:41:36','',1,1),(1074,'产品编码',1060,1,'/product','C','0','device:devProductList:view','#','admin','2019-04-10 15:50:08','admin','2019-09-20 11:42:34','',1,0),(1083,'修改',1058,1,'#','F','0','','#','admin','2019-04-18 09:00:06','admin','2019-05-20 14:23:49','',0,0),(1084,'密码重置',1058,2,'#','F','0','','#','admin','2019-04-18 09:00:44','admin','2019-05-20 14:23:58','',0,0),(1086,'查询',1062,1,'#','F','0','device:devWorkOrder:list','#','admin','2019-04-18 09:03:35','admin','2019-07-30 09:24:54','',0,2),(1087,'添加',1062,2,'#','F','0','device:devWorkOrder:add','#','admin','2019-04-18 09:04:14','',NULL,'',0,2),(1088,'修改',1062,3,'#','F','0','device:devWorkOrder:edit','#','admin','2019-04-18 09:04:38','',NULL,'',1,2),(1089,'删除',1062,4,'#','F','0','device:devWorkOrder:remove','#','admin','2019-04-18 09:05:15','',NULL,'',1,2),(1090,'导出',1062,5,'#','F','0','device:devWorkOrder:export','#','admin','2019-04-18 09:05:35','',NULL,'',0,2),(1107,'查询',1071,1,'#','F','0','device:devList:list','#','admin','2019-04-18 09:18:09','',NULL,'',0,1),(1108,'添加',1071,2,'#','F','0','device:devList:add','#','admin','2019-04-18 09:18:59','',NULL,'',0,1),(1109,'修改',1071,3,'#','F','0','device:devList:edit','#','admin','2019-04-18 09:20:19','',NULL,'',0,1),(1110,'删除',1071,4,'#','F','0','device:devList:remove','#','admin','2019-04-18 09:20:41','',NULL,'',0,1),(1111,'导出',1071,5,'#','F','0','device:devList:export','#','admin','2019-04-18 09:21:04','',NULL,'',0,1),(1118,'查询',1074,1,'#','F','0','device:devProductList:list','#','admin','2019-04-18 09:24:31','',NULL,'',0,0),(1119,'添加',1074,2,'#','F','0','device:devProductList:add','#','admin','2019-04-18 09:24:59','',NULL,'',0,0),(1120,'修改',1074,3,'#','F','0','device:devProductList:edit','#','admin','2019-04-18 09:25:14','',NULL,'',0,0),(1121,'删除',1074,4,'#','F','0','device:devProductList:remove','#','admin','2019-04-18 09:25:30','',NULL,'',0,0),(1122,'导入',1074,5,'#','F','0','device:devProductList:import','#','admin','2019-04-18 09:25:51','admin','2019-04-18 10:01:00','',0,0),(1123,'导出',1074,6,'#','F','0','device:devProductList:export','#','admin','2019-04-18 09:26:10','',NULL,'',0,0),(1143,'生成硬件编码',1071,6,'#','F','0','device:devList:createCode','#','admin','2019-04-19 09:19:21','admin','2019-09-25 18:39:42','',0,1),(1297,'文件管理',0,4,'#','M','0','','fa fa-folder-open','admin','2019-06-13 10:03:06','admin','2019-09-26 08:32:39','',1,1),(1299,'作业指导书',1297,3,'/iso/iso/0','C','0','iso:iso:view','#','admin','2019-06-13 16:03:27','admin','2019-08-22 14:37:11','',1,1),(1303,'作业指导书产线配置查询',1299,1,'#','F','0','iso:sopLine:list,iso:sopLine:view','#','admin','2018-03-01 00:00:00','admin','2019-06-15 16:58:18','',0,1),(1304,'作业指导书产线配置新增',1299,2,'#','F','0','iso:sopLine:add','#','admin','2018-03-01 00:00:00','admin','2019-06-15 16:58:54','',0,1),(1305,'作业指导书产线配置修改',1299,3,'#','F','0','iso:sopLine:edit','#','admin','2018-03-01 00:00:00','admin','2019-06-15 16:58:40','',0,1),(1306,'作业指导书产线配置删除',1299,4,'#','F','0','iso:sopLine:remove','#','admin','2018-03-01 00:00:00','admin','2019-06-15 16:59:36','',0,1),(1341,'导入配置',1074,12,'#','F','0','device:devProductList:importConfig','#','admin','2019-07-03 09:34:26','',NULL,'',0,0),(1369,'作业指导书配置单工位',1299,5,'#','F','0','production:singleWork:configSop','#','admin','2019-07-19 09:11:52','',NULL,'',0,1),(1416,'添加作业指导指导书',1299,6,'#','F','0','iso:iso:add','#','admin','2019-08-22 14:20:45','',NULL,'',0,1),(1417,'查询作业指导指导书',1299,7,'#','F','0','iso:iso:list','#','admin','2019-08-22 15:03:16','',NULL,'',0,1),(1418,'修改作业指导指导书',1299,8,'#','F','0','iso:iso:edit','#','admin','2019-08-22 15:04:09','',NULL,'',0,1),(1419,'删除作业指导书',1299,9,'#','F','0','iso:iso:remove','#','admin','2019-08-22 15:04:49','',NULL,'',0,1),(1420,'公司管理',1,1,'/device/devCompany','C','0','device:devCompany:view','#','admin','2019-08-24 14:27:15','',NULL,'',0,0),(1421,'查询',1420,1,'#','F','0','device:devCompany:list','#','admin','2019-08-24 14:27:38','admin','2019-08-24 14:31:45','',0,0),(1422,'添加',1420,2,'#','F','0','device:devCompany:add','#','admin','2019-08-24 14:27:59','',NULL,'',0,0),(1423,'修改',1420,3,'#','F','0','device:devCompany:edit','#','admin','2019-08-24 14:28:17','',NULL,'',0,0),(1425,'导出',1420,5,'#','F','0','device:devCompany:export','#','admin','2019-08-24 14:29:19','',NULL,'',0,0),(1431,'激活码管理',0,5,'#','M','0','','fa fa-audio-description','admin','2019-08-26 09:34:06','admin','2019-09-26 08:32:29','',0,1),(1432,'激活码列表',1431,1,'/code/activeCode','C','0','code:activeCode:view','#','admin','2018-03-01 00:00:00','admin','2019-08-26 09:39:04','激活码菜单',0,1),(1433,'激活码查询',1432,1,'#','F','0','code:activeCode:list','#','admin','2018-03-01 00:00:00','ry','2018-03-01 00:00:00','',0,1),(1434,'激活码新增',1432,2,'#','F','0','code:activeCode:add','#','admin','2018-03-01 00:00:00','ry','2018-03-01 00:00:00','',0,1),(1435,'激活码分配',1432,3,'#','F','0','code:activeCode:edit','#','admin','2018-03-01 00:00:00','admin','2019-08-26 09:38:55','',0,1),(1437,'激活码导出',1432,4,'#','F','0','code:activeCode:export','#','admin','2019-08-26 09:40:15','',NULL,'',0,1),(1438,'我的激活码',1431,2,'/code/myActiveCode','C','0','code:myActiveCode:view','#','admin','2019-08-26 13:52:58','admin','2019-08-26 13:57:58','',0,1),(1439,'激活码查询',1438,1,'#','F','0','code:myActiveCode:list','#','admin','2019-08-26 13:53:55','',NULL,'',0,1),(1440,'激活码导出',1438,2,'#','F','0','code:myActiveCode:export','#','admin','2019-08-26 13:54:25','admin','2019-08-26 13:54:44','',0,1),(1442,'半成品编码',1060,2,'/product/part','C','0','device:devPart:view','#','admin','2019-09-20 08:51:38','admin','2019-09-20 11:42:43','',0,4),(1443,'导入配置',1442,1,'#','F','0','device:devPart:importConfig','#','admin','2019-09-20 08:52:46','',NULL,'',0,4),(1444,'新增修改',1442,2,'#','F','0','device:devPart:add','#','admin','2019-09-20 08:53:15','',NULL,'',0,4),(1445,'查询',1442,3,'#','F','0','device:devPart:list','#','admin','2019-09-20 08:53:44','',NULL,'',0,4),(1446,'删除',1442,4,'#','F','0','device:devPart:remove','#','admin','2019-09-20 08:54:09','',NULL,'',0,4),(1447,'物料编码',1060,3,'/erp/materiel','C','0','erp:materiel:view','#','admin','2019-09-20 09:03:11','admin','2019-09-20 11:42:52','',0,2),(1448,'查询',1447,1,'#','F','0','erp:materiel:list','#','admin','2019-09-20 09:03:41','',NULL,'',0,2),(1449,'新增修改',1447,2,'#','F','0','erp:materiel:edit','#','admin','2019-09-20 09:04:09','',NULL,'',0,2),(1450,'删除',1447,3,'#','F','0','erp:materiel:remove','#','admin','2019-09-20 09:04:32','',NULL,'',0,2),(1451,'导入导出',1447,4,'#','F','0','erp:materiel:export','#','admin','2019-09-20 09:04:56','',NULL,'',0,2),(1452,'MES',0,4,'#','M','0','','fa fa-balance-scale','admin','2019-09-20 09:19:52','admin','2019-09-20 11:43:23','',0,2),(1453,'MES规则',1452,2,'/mes/mesBatchRule','C','0','mes:mesBatchRule:view','#','admin','2019-09-20 09:20:57','admin','2019-09-20 11:31:50','',0,2),(1454,'MES数据',1452,3,'/mes/mesBatch','C','0','mes:mesBatch:view','#','admin','2019-09-20 09:21:33','admin','2019-09-20 11:31:57','',0,2),(1455,'售后录入',1452,4,'/quality/afterService','C','0','quality:afterService:view','#','admin','2019-09-20 09:22:00','admin','2019-09-20 11:32:03','',0,2),(1456,'查看',1453,1,'#','F','0','mes:mesBatchRule:list','#','admin','2019-09-20 09:22:53','',NULL,'',0,2),(1457,'新增修改',1453,2,'#','F','0','mes:mesBatchRule:add','#','admin','2019-09-20 09:28:03','',NULL,'',0,2),(1458,'删除',1453,3,'#','F','0','mes:mesBatchRule:remove','#','admin','2019-09-20 09:28:23','',NULL,'',0,2),(1459,'查看',1454,1,'mes:mesBatch:list','F','0','mes:mesBatch:list','#','admin','2019-09-20 09:28:53','',NULL,'',0,2),(1460,'新增修改',1454,2,'#','F','0','mes:mesBatch:add','#','admin','2019-09-20 09:37:43','',NULL,'',0,2),(1461,'查询',1455,1,'#','F','0','quality:afterService:list','#','admin','2019-09-20 09:46:20','',NULL,'',0,2),(1462,'删除',1455,3,'#','F','0','quality:afterService:remove','#','admin','2019-09-20 10:00:35','admin','2019-09-20 10:01:08','',0,2),(1463,'新增录入',1455,2,'#','F','0','quality:afterService:add','#','admin','2019-09-20 10:00:55','admin','2019-09-20 10:01:14','',0,2),(1464,'MES仓库录入',1062,6,'#','F','0','device:devWorkOrder:configMes','#','admin','2019-09-20 10:34:02','',NULL,'',0,2),(1465,'MES生产录入',1062,7,'#','F','0','device:devWorkOrder:produceMes','#','admin','2019-09-20 10:34:34','',NULL,'',0,2),(1466,'导入导出',1442,5,'#','F','0','device:devPart:import','#','admin','2019-09-20 11:08:55','admin','2019-09-20 11:10:16','',0,4),(1467,'查询产线',1062,8,'#','F','0','production:productionLine:list','#','admin','2019-09-20 11:38:53','',NULL,'',0,2),(1468,'新增修改产线',1062,9,'#','F','0','production:productionLine:add','#','admin','2019-09-20 11:39:19','',NULL,'',0,2),(1469,'删除产线',1062,10,'#','F','0','production:productionLine:remove','#','admin','2019-09-20 11:39:38','',NULL,'',0,2),(1470,'ASOP',0,4,'#','M','0','','fa fa-bar-chart','admin','2019-09-25 18:32:59','admin','2019-09-25 18:58:01','',0,1),(1471,'工单管理',1470,1,'/device/devWorkOrder/lineWorkOrder','C','0','device:devWorkOrder:view','#','admin','2019-09-25 18:36:42','admin','2019-09-25 18:37:03','',0,1),(1472,'产线管理',1470,2,'/production/productionLine','C','0','production:productionLine:view','#','admin','2019-09-25 18:53:37','',NULL,'',0,1),(1473,'查询产线',1472,1,'#','F','0','production:productionLine:list','#','admin','2019-09-25 18:54:06','',NULL,'',0,1),(1474,'新增修改产线',1472,2,'#','F','0','production:productionLine:add','#','admin','2019-09-25 18:54:32','',NULL,'',0,1),(1475,'删除产线',1472,3,'#','F','0','production:productionLine:remove','#','admin','2019-09-25 18:54:50','',NULL,'',0,1),(1476,'配置ASOP',1472,4,'#','F','0','iso:sopLine:add','#','admin','2019-09-25 18:55:35','',NULL,'',0,1),(1477,'查看工位列表',1472,5,'#','F','0','production:workstation:list','#','admin','2019-09-25 18:56:01','admin','2019-09-25 18:56:44','',0,1),(1478,'工位新增修改删除',1472,6,'#','F','0','production:workstation:add','#','admin','2019-09-25 18:56:31','',NULL,'',0,1),(1479,'工单列表',1471,1,'#','F','0','device:devWorkOrder:list','#','admin','2019-09-25 19:02:31','',NULL,'',0,1),(1480,'新增修改工单',1471,2,'#','F','0','device:devWorkOrder:add','#','admin','2019-09-25 19:03:17','',NULL,'',0,1),(1481,'删除工单',1471,3,'#','F','0','device:devWorkOrder:remove','#','admin','2019-09-25 19:03:40','',NULL,'',0,1),(1482,'生产看板',0,4,'#','M','0','','fa fa-newspaper-o','admin','2019-09-26 08:35:46','admin','2019-09-26 08:48:03','',0,3),(1483,'工单管理',1482,1,'/device/devWorkOrder/lineWorkOrder','C','0','device:devWorkOrder:view','#','admin','2019-09-26 08:37:20','',NULL,'',0,3),(1484,'产线管理',1482,2,'/production/productionLine','C','0','production:productionLine:view','#','admin','2019-09-26 08:38:36','',NULL,'',0,3),(1485,'查看列表',1483,1,'#','F','0','device:devWorkOrder:list','#','admin','2019-09-26 08:39:06','admin','2019-09-26 08:40:08','',0,3),(1486,'新增工单',1483,2,'#','F','0','device:devWorkOrder:add','#','admin','2019-09-26 08:39:54','',NULL,'',0,3),(1487,'修改工单',1483,3,'#','F','0','device:devWorkOrder:edit','#','admin','2019-09-26 08:40:28','',NULL,'',0,3),(1488,'删除工单',1483,4,'#','F','0','device:devWorkOrder:remove','#','admin','2019-09-26 08:40:44','',NULL,'',0,3),(1489,'查看列表',1484,1,'#','F','0','production:productionLine:list','#','admin','2019-09-26 08:41:32','',NULL,'',0,3),(1490,'新增修改产线',1484,2,'#','F','0','production:productionLine:add','#','admin','2019-09-26 08:41:50','',NULL,'',0,3),(1491,'删除产线',1484,3,'#','F','0','production:productionLine:remove','#','admin','2019-09-26 08:42:18','',NULL,'',0,3),(1492,'看板管理',1482,3,'/page/pageInfo','C','0','page:pageInfo:view','#','admin','2019-09-26 08:44:29','',NULL,'',0,3),(1493,'查看列表',1492,1,'#','F','0','page:pageInfo:list','#','admin','2019-09-26 08:44:52','',NULL,'',0,3),(1494,'新增修改看板',1492,2,'#','F','0','page:pageInfo:add','#','admin','2019-09-26 08:45:06','admin','2019-10-16 08:25:17','',0,3),(1495,'删除看板',1492,3,'#','F','0','page:pageInfo:remove','#','admin','2019-09-26 08:45:45','',NULL,'',0,3),(1496,'数据报表',1482,4,'/production/report','C','0','production:report:view','#','admin','2019-09-26 08:46:24','',NULL,'',0,3),(1497,'导出报表',1496,1,'#','F','0','production:report:pdf','#','admin','2019-09-26 08:46:45','admin','2019-09-27 13:53:21','',0,3),(1498,'工单异常信息查看',1483,5,'#','F','0','production:workExceptionList:list','#','admin','2019-09-26 11:15:51','admin','2019-09-26 11:17:04','',0,3),(1499,'工单异常类型管理',1483,6,'#','F','0','production:workExceptionList:add','#','admin','2019-09-26 11:16:51','',NULL,'',0,3),(1500,'消息通知',1,4,'/device/devNotice','C','0','device:devNotice:view','#','admin','2019-09-27 11:01:27','admin','2019-09-27 11:01:39','',0,3),(1501,'查看消息列表',1500,1,'#','F','0','device:devNotice:list','#','admin','2019-09-27 11:02:01','',NULL,'',0,3),(1502,'新增修改消息',1500,2,'#','F','0','device:devNotice:add','#','admin','2019-09-27 11:02:31','',NULL,'',0,3),(1503,'查看历史报表',1496,2,'#','F','0','erp:fileSourceInfo:list','#','admin','2019-09-27 13:57:48','',NULL,'',0,3),(1504,'删除历史报表',1496,3,'#','F','0','erp:fileSourceInfo:remove','#','admin','2019-09-27 13:58:09','',NULL,'',0,3),(1505,'修改历史报表',1496,4,'#','F','0','erp:fileSourceInfo:add','#','admin','2019-09-27 13:58:33','',NULL,'',0,3),(1506,'看板密码',1492,4,'#','F','0','page:pageInfo:pwd','#','admin','2019-10-16 08:27:19','',NULL,'',0,3),(1507,'任务管理',2,3,'/monitor/job','C','0','monitor:job:view','#','admin','2019-10-16 10:10:11','',NULL,'',0,0),(1508,'查看任务管理',1507,1,'#','F','0','monitor:job:list','#','admin','2019-10-16 10:11:07','',NULL,'',0,0),(1509,'新增修改任务管理',1507,2,'#','F','0','monitor:job:add','#','admin','2019-10-16 10:11:41','',NULL,'',0,0),(1510,'代码生成',2,4,'/tool/gen','C','0','tool:gen:view','#','admin','2019-10-21 09:02:05','',NULL,'',0,0),(1511,'公司数据分析',1496,5,'#','F','0','production:cost:list','#','admin','2019-10-25 10:26:54','',NULL,'',0,3),(1512,'生产管理',0,6,'#','M','0','','fa fa-edit','admin','2019-11-05 08:35:56','admin','2019-11-05 08:44:36','',0,0),(1513,'工单管理',1512,1,'/device/devWorkOrder/singWorkOrder','C','0','device:devWorkOrder:list','#','admin','2019-11-05 08:38:09','admin','2019-11-05 08:38:51','',0,0),(1514,'工单列表',1513,1,'#','F','0','device:devWorkOrder:list','#','admin','2019-11-05 08:38:40','',NULL,'',0,0),(1515,'新增工单',1513,2,'#','F','0','device:devWorkOrder:add','#','admin','2019-11-05 08:39:28','',NULL,'',0,0),(1516,'修改工单',1513,3,'#','F','0','device:devWorkOrder:edit','#','admin','2019-11-05 08:39:45','',NULL,'',0,0),(1517,'删除工单',1513,4,'#','F','0','device:devWorkOrder:remove','#','admin','2019-11-05 08:40:02','',NULL,'',0,0),(1518,'工位管理',1512,2,'/production/singleWork','C','0','production:singleWork:view','#','admin','2019-11-05 08:41:15','',NULL,'',0,0),(1519,'工位列表',1518,1,'#','F','0','production:singleWork:list','#','admin','2019-11-05 08:41:33','',NULL,'',0,0),(1520,'新增工位',1518,2,'#','F','0','production:singleWork:add','#','admin','2019-11-05 08:41:54','',NULL,'',0,0),(1521,'修改工位',1518,3,'#','F','0','production:singleWork:edit','#','admin','2019-11-05 08:42:26','',NULL,'',0,0),(1522,'删除工位',1518,4,'#','F','0','production:singleWork:remove','#','admin','2019-11-05 08:42:40','',NULL,'',0,0),(1523,'计件管理',1512,3,'#','C','0','','#','admin','2019-11-05 11:25:35','admin','2019-11-05 11:28:16','',0,0),(1524,'我的计件',1523,1,'/production/countPiece/myCountPiece','C','0','production:countPiece:list','#','admin','2019-11-05 11:26:08','',NULL,'',0,0),(1525,'全部计件',1523,2,'/production/countPiece','C','0','production:countPiece:view','#','admin','2019-11-05 11:26:30','',NULL,'',0,0),(1526,'计件查询',1525,1,'#','F','0','production:countPiece:list','#','admin','2019-11-05 11:26:50','',NULL,'',0,0),(1527,'计件新增修改',1525,2,'#','F','0','production:countPiece:edit','#','admin','2019-11-05 11:27:14','',NULL,'',0,0),(1528,'计件删除',1525,3,'#','F','0','production:countPiece:remove','#','admin','2019-11-05 11:27:36','',NULL,'',0,0),(1529,'计件导出',1525,4,'#','F','0','production:countPiece:export','#','admin','2019-11-05 11:27:52','',NULL,'',0,0),(1530,'硬件列表',1518,5,'#','F','0','device:devList:list','#','admin','2019-11-05 14:54:59','',NULL,'',0,0),(1531,'删除硬件',1518,6,'#','F','0','device:devList:remove','#','admin','2019-11-05 14:55:36','',NULL,'',0,0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu_en`
--

DROP TABLE IF EXISTS `sys_menu_en`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_menu_en` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` int(11) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `url` varchar(200) DEFAULT '#' COMMENT '请求地址',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `app_sign` int(11) DEFAULT '0' COMMENT 'app是否展示标识(0、默认值不展示，1、展示)',
  `menu_tag` int(11) DEFAULT '0' COMMENT '菜单系统权限(0、共有，1、asop专用，2、mes专有，3、看板专有)',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1532 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu_en`
--

LOCK TABLES `sys_menu_en` WRITE;
/*!40000 ALTER TABLE `sys_menu_en` DISABLE KEYS */;
INSERT INTO `sys_menu_en` VALUES (1,'Admin',0,1,'#','M','0','','fa fa-gear','admin','2018-03-16 11:33:00','admin','2019-04-23 14:04:31','系统管理目录',1,0),(2,'系统监控',0,2,'#','M','0','','fa fa-video-camera','admin','2018-03-16 11:33:00','admin','2019-04-28 17:53:33','系统监控目录',0,0),(100,'User Mgmt.',1,1,'/system/user','C','0','system:user:view','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','用户管理菜单',1,0),(101,'Role Assignment',1,2,'/system/role','C','0','system:role:view','#','admin','2018-03-16 11:33:00','admin','2019-04-23 14:05:34','角色管理菜单',0,0),(102,'菜单管理',1,3,'/system/menu','C','0','system:menu:view','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','菜单管理菜单',0,0),(111,'数据监控',2,3,'/monitor/data','C','0','monitor:data:view','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','数据监控菜单',0,0),(112,'服务监控',2,3,'/monitor/server','C','0','monitor:server:view','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','服务监控菜单',0,0),(1000,'User List',100,1,'#','F','0','system:user:list','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1001,'User Add',100,2,'#','F','0','system:user:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1002,'User Edit',100,3,'#','F','0','system:user:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1003,'User Remove',100,4,'#','F','0','system:user:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1004,'User Export',100,5,'#','F','0','system:user:export','#','admin','2018-03-16 11:33:00','admin','2019-04-03 08:51:19','',0,0),(1005,'User Import',100,6,'#','F','0','system:user:import','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1006,'User ResetPwd',100,7,'#','F','0','system:user:resetPwd','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1007,'Role List',101,1,'#','F','0','system:role:list','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1008,'Role Add',101,2,'#','F','0','system:role:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1009,'Role Edit',101,3,'#','F','0','system:role:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1010,'Role Remove',101,4,'#','F','0','system:role:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1011,'Role Export',101,5,'#','F','0','system:role:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1012,'菜单查询',102,1,'#','F','0','system:menu:list','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1013,'菜单新增',102,2,'#','F','0','system:menu:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1014,'菜单修改',102,3,'#','F','0','system:menu:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1015,'菜单删除',102,4,'#','F','0','system:menu:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','',0,0),(1058,'Personal Setting',1,10,'/userDetail/userDetail','C','0','','#','admin','2019-03-30 09:47:16','admin','2019-03-30 17:33:00','',0,0),(1059,'FistPage Setting',1,11,'/indexSetting/indexSetting','C','0','','#','admin','2019-03-30 10:09:50','admin','2019-03-30 17:33:06','',0,0),(1060,'P.N.Mgmt.',0,3,'#','M','0','','fa fa-calculator','admin','2019-03-30 10:21:19','admin','2019-09-20 11:43:15','',1,0),(1061,'计划排单',1060,7,'#','M','0','','#','admin','2019-03-30 10:22:18','admin','2019-09-20 08:46:27','',1,0),(1062,'Prod.Order',1452,1,'/device/devWorkOrder/lineWorkOrder','C','0','device:devWorkOrder:view','#','admin','2019-03-30 10:22:33','admin','2019-09-20 11:31:43','',1,2),(1071,'Hardware Mgmt.',1470,5,'/device/devList','C','0','device:devList:view','#','admin','2019-04-10 15:03:18','admin','2019-09-25 18:41:36','',1,1),(1074,'Product P.N.',1060,1,'/product','C','0','device:devProductList:view','#','admin','2019-04-10 15:50:08','admin','2019-09-20 11:42:34','',1,0),(1080,'Prod.Mgmt.',1060,3,'/production/productionLine','C','0','production:productionLine:view','#','admin','2019-04-11 11:51:26','admin','2019-09-20 08:46:14','',1,0),(1083,'Edit',1058,1,'#','F','0','','#','admin','2019-04-18 09:00:06','admin','2019-05-20 14:23:49','',0,0),(1084,'Reset Pwd',1058,2,'#','F','0','','#','admin','2019-04-18 09:00:44','admin','2019-05-20 14:23:58','',0,0),(1086,'List',1062,1,'#','F','0','device:devWorkOrder:list','#','admin','2019-04-18 09:03:35','admin','2019-07-30 09:24:54','',0,2),(1087,'Add',1062,2,'#','F','0','device:devWorkOrder:add','#','admin','2019-04-18 09:04:14','',NULL,'',0,2),(1088,'Edit',1062,3,'#','F','0','device:devWorkOrder:edit','#','admin','2019-04-18 09:04:38','',NULL,'',1,2),(1089,'Remove',1062,4,'#','F','0','device:devWorkOrder:remove','#','admin','2019-04-18 09:05:15','',NULL,'',1,2),(1090,'Export',1062,5,'#','F','0','device:devWorkOrder:export','#','admin','2019-04-18 09:05:35','',NULL,'',0,2),(1091,'List',1080,1,'#','F','0','production:productionLine:list','#','admin','2019-04-18 09:09:27','',NULL,'',0,0),(1092,'Add',1080,2,'#','F','0','production:productionLine:add','#','admin','2019-04-18 09:09:52','',NULL,'',0,0),(1093,'Edit',1080,3,'#','F','0','production:productionLine:edit','#','admin','2019-04-18 09:10:17','',NULL,'',0,0),(1094,'Remove',1080,4,'#','F','0','production:productionLine:remove','#','admin','2019-04-18 09:10:42','',NULL,'',0,0),(1095,'Export',1080,5,'#','F','0','production:productionLine:export','#','admin','2019-04-18 09:11:38','',NULL,'',0,0),(1107,'List',1071,1,'#','F','0','device:devList:list','#','admin','2019-04-18 09:18:09','',NULL,'',0,1),(1108,'Add',1071,2,'#','F','0','device:devList:add','#','admin','2019-04-18 09:18:59','',NULL,'',0,1),(1109,'Edit',1071,3,'#','F','0','device:devList:edit','#','admin','2019-04-18 09:20:19','',NULL,'',0,1),(1110,'Remove',1071,4,'#','F','0','device:devList:remove','#','admin','2019-04-18 09:20:41','',NULL,'',0,1),(1111,'Export',1071,5,'#','F','0','device:devList:export','#','admin','2019-04-18 09:21:04','',NULL,'',0,1),(1118,'List',1074,1,'#','F','0','device:devProductList:list','#','admin','2019-04-18 09:24:31','',NULL,'',0,0),(1119,'Add',1074,2,'#','F','0','device:devProductList:add','#','admin','2019-04-18 09:24:59','',NULL,'',0,0),(1120,'Edit',1074,3,'#','F','0','device:devProductList:edit','#','admin','2019-04-18 09:25:14','',NULL,'',0,0),(1121,'Remove',1074,4,'#','F','0','device:devProductList:remove','#','admin','2019-04-18 09:25:30','',NULL,'',0,0),(1122,'Import',1074,5,'#','F','0','device:devProductList:import','#','admin','2019-04-18 09:25:51','admin','2019-04-18 10:01:00','',0,0),(1123,'Export',1074,6,'#','F','0','device:devProductList:export','#','admin','2019-04-18 09:26:10','',NULL,'',0,0),(1143,'Create K.B. Code',1071,6,'#','F','0','device:devList:createCode','#','admin','2019-04-19 09:19:21','admin','2019-09-25 18:39:42','',0,1),(1147,'新增工单',1061,1,'/device/devWorkOrder/add','C','0','device:devWorkOrder:add','#','admin','2019-04-23 14:12:47','admin','2019-04-23 14:13:30','',1,0),(1148,'添加',1147,1,'#','F','0','device:devWorkOrder:add','#','admin','2019-04-23 14:13:50','',NULL,'',0,0),(1297,'File Mgmt.',0,4,'#','M','0','','fa fa-folder-open','admin','2019-06-13 10:03:06','admin','2019-09-26 08:32:39','',1,1),(1299,'SOP Files',1297,3,'/iso/iso/0','C','0','iso:iso:view','#','admin','2019-06-13 16:03:27','admin','2019-08-22 14:37:11','',1,1),(1303,'SOP Line C.F. List',1299,1,'#','F','0','iso:sopLine:list,iso:sopLine:view','#','admin','2018-03-01 00:00:00','admin','2019-06-15 16:58:18','',0,1),(1304,'SOP Line C.F. Add',1299,2,'#','F','0','iso:sopLine:add','#','admin','2018-03-01 00:00:00','admin','2019-06-15 16:58:54','',0,1),(1305,'SOP Line C.F. Edit',1299,3,'#','F','0','iso:sopLine:edit','#','admin','2018-03-01 00:00:00','admin','2019-06-15 16:58:40','',0,1),(1306,'SOP Line C.F. Remove',1299,4,'#','F','0','iso:sopLine:remove','#','admin','2018-03-01 00:00:00','admin','2019-06-15 16:59:36','',0,1),(1307,'Workstation List',1080,10,'#','F','0','production:workstation:list','#','admin','2019-06-15 17:03:15','',NULL,'',1,0),(1308,'Workstation Add',1080,11,'#','F','0','production:workstation:add','#','admin','2019-06-15 17:03:45','',NULL,'',0,0),(1309,'Edit Workstation',1080,12,'#','F','0','production:workstation:edit,production:workstation:remove','#','admin','2019-06-15 17:04:30','',NULL,'',0,0),(1341,'Import Config',1074,12,'#','F','0','device:devProductList:importConfig','#','admin','2019-07-03 09:34:26','',NULL,'',0,0),(1369,'SOP SingW C.F.Add',1299,5,'#','F','0','production:singleWork:configSop','#','admin','2019-07-19 09:11:52','',NULL,'',0,1),(1416,'SOP Add',1299,6,'#','F','0','iso:iso:add','#','admin','2019-08-22 14:20:45','',NULL,'',0,1),(1417,'SOP List',1299,7,'#','F','0','iso:iso:list','#','admin','2019-08-22 15:03:16','',NULL,'',0,1),(1418,'SOP Edit',1299,8,'#','F','0','iso:iso:edit','#','admin','2019-08-22 15:04:09','',NULL,'',0,1),(1419,'SOP Remove',1299,9,'#','F','0','iso:iso:remove','#','admin','2019-08-22 15:04:49','',NULL,'',0,1),(1420,'公司管理',1,1,'/device/devCompany','C','0','device:devCompany:view','#','admin','2019-08-24 14:27:15','',NULL,'',0,0),(1421,'查询',1420,1,'#','F','0','device:devCompany:list','#','admin','2019-08-24 14:27:38','admin','2019-08-24 14:31:45','',0,0),(1422,'添加',1420,2,'#','F','0','device:devCompany:add','#','admin','2019-08-24 14:27:59','',NULL,'',0,0),(1423,'修改',1420,3,'#','F','0','device:devCompany:edit','#','admin','2019-08-24 14:28:17','',NULL,'',0,0),(1425,'导出',1420,5,'#','F','0','device:devCompany:export','#','admin','2019-08-24 14:29:19','',NULL,'',0,0),(1431,'License Key',0,5,'#','M','0','','fa fa-audio-description','admin','2019-08-26 09:34:06','admin','2019-09-26 08:32:29','',0,1),(1432,'激活码列表',1431,1,'/code/activeCode','C','0','code:activeCode:view','#','admin','2018-03-01 00:00:00','admin','2019-08-26 09:39:04','激活码菜单',0,1),(1433,'激活码查询',1432,1,'#','F','0','code:activeCode:list','#','admin','2018-03-01 00:00:00','ry','2018-03-01 00:00:00','',0,1),(1434,'激活码新增',1432,2,'#','F','0','code:activeCode:add','#','admin','2018-03-01 00:00:00','ry','2018-03-01 00:00:00','',0,1),(1435,'激活码分配',1432,3,'#','F','0','code:activeCode:edit','#','admin','2018-03-01 00:00:00','admin','2019-08-26 09:38:55','',0,1),(1437,'激活码导出',1432,4,'#','F','0','code:activeCode:export','#','admin','2019-08-26 09:40:15','',NULL,'',0,1),(1438,'My License Key',1431,2,'/code/myActiveCode','C','0','code:myActiveCode:view','#','admin','2019-08-26 13:52:58','admin','2019-08-26 13:57:58','',0,1),(1439,'License Key List',1438,1,'#','F','0','code:myActiveCode:list','#','admin','2019-08-26 13:53:55','',NULL,'',0,1),(1440,'License Key Export',1438,2,'#','F','0','code:myActiveCode:export','#','admin','2019-08-26 13:54:25','admin','2019-08-26 13:54:44','',0,1),(1441,'Config ASOP',1080,6,'#','F','0','iso:sopLine:add,iso:sopLine:edit','#','admin','2019-09-06 11:27:42','',NULL,'',0,0),(1442,'半成品编码',1060,2,'/product/part','C','0','device:devPart:view','#','admin','2019-09-20 08:51:38','admin','2019-09-20 11:42:43','',0,4),(1443,'导入配置',1442,1,'#','F','0','device:devPart:importConfig','#','admin','2019-09-20 08:52:46','',NULL,'',0,4),(1444,'新增修改',1442,2,'#','F','0','device:devPart:add','#','admin','2019-09-20 08:53:15','',NULL,'',0,4),(1445,'查询',1442,3,'#','F','0','device:devPart:list','#','admin','2019-09-20 08:53:44','',NULL,'',0,4),(1446,'删除',1442,4,'#','F','0','device:devPart:remove','#','admin','2019-09-20 08:54:09','',NULL,'',0,4),(1447,'Material P.N.',1060,3,'/erp/materiel','C','0','erp:materiel:view','#','admin','2019-09-20 09:03:11','admin','2019-09-20 11:42:52','',0,2),(1448,'List',1447,1,'#','F','0','erp:materiel:list','#','admin','2019-09-20 09:03:41','',NULL,'',0,2),(1449,'Add Or Edit',1447,2,'#','F','0','erp:materiel:edit','#','admin','2019-09-20 09:04:09','',NULL,'',0,2),(1450,'Remove',1447,3,'#','F','0','erp:materiel:remove','#','admin','2019-09-20 09:04:32','',NULL,'',0,2),(1451,'Export',1447,4,'#','F','0','erp:materiel:export','#','admin','2019-09-20 09:04:56','',NULL,'',0,2),(1452,'MES',0,4,'#','M','0','','fa fa-balance-scale','admin','2019-09-20 09:19:52','admin','2019-09-20 11:43:23','',0,2),(1453,'MES Rules',1452,2,'/mes/mesBatchRule','C','0','mes:mesBatchRule:view','#','admin','2019-09-20 09:20:57','admin','2019-09-20 11:31:50','',0,2),(1454,'MES Data',1452,3,'/mes/mesBatch','C','0','mes:mesBatch:view','#','admin','2019-09-20 09:21:33','admin','2019-09-20 11:31:57','',0,2),(1455,'After Sale Records',1452,4,'/quality/afterService','C','0','quality:afterService:view','#','admin','2019-09-20 09:22:00','admin','2019-09-20 11:32:03','',0,2),(1456,'List',1453,1,'#','F','0','mes:mesBatchRule:list','#','admin','2019-09-20 09:22:53','',NULL,'',0,2),(1457,'Add',1453,2,'#','F','0','mes:mesBatchRule:add','#','admin','2019-09-20 09:28:03','',NULL,'',0,2),(1458,'Remove',1453,3,'#','F','0','mes:mesBatchRule:remove','#','admin','2019-09-20 09:28:23','',NULL,'',0,2),(1459,'List',1454,1,'mes:mesBatch:list','F','0','mes:mesBatch:list','#','admin','2019-09-20 09:28:53','',NULL,'',0,2),(1460,'Add',1454,2,'#','F','0','mes:mesBatch:add','#','admin','2019-09-20 09:37:43','',NULL,'',0,2),(1461,'List',1455,1,'#','F','0','quality:afterService:list','#','admin','2019-09-20 09:46:20','',NULL,'',0,2),(1462,'Remove',1455,3,'#','F','0','quality:afterService:remove','#','admin','2019-09-20 10:00:35','admin','2019-09-20 10:01:08','',0,2),(1463,'Add',1455,2,'#','F','0','quality:afterService:add','#','admin','2019-09-20 10:00:55','admin','2019-09-20 10:01:14','',0,2),(1464,'MES Storehouse Records',1062,6,'#','F','0','device:devWorkOrder:configMes','#','admin','2019-09-20 10:34:02','',NULL,'',0,2),(1465,'MES Produce Records',1062,7,'#','F','0','device:devWorkOrder:produceMes','#','admin','2019-09-20 10:34:34','',NULL,'',0,2),(1466,'导入导出',1442,5,'#','F','0','device:devPart:import','#','admin','2019-09-20 11:08:55','admin','2019-09-20 11:10:16','',0,4),(1467,'Line List',1062,8,'#','F','0','production:productionLine:list','#','admin','2019-09-20 11:38:53','',NULL,'',0,2),(1468,'Add Line',1062,9,'#','F','0','production:productionLine:add','#','admin','2019-09-20 11:39:19','',NULL,'',0,2),(1469,'Remove Line',1062,10,'#','F','0','production:productionLine:remove','#','admin','2019-09-20 11:39:38','',NULL,'',0,2),(1470,'ASOP',0,4,'#','M','0','','fa fa-bar-chart','admin','2019-09-25 18:32:59','admin','2019-09-25 18:58:01','',0,1),(1471,'Prod.Order',1470,1,'/device/devWorkOrder/lineWorkOrder','C','0','device:devWorkOrder:view','#','admin','2019-09-25 18:36:42','admin','2019-09-25 18:37:03','',0,1),(1472,'Prod.Mgmt.',1470,2,'/production/productionLine','C','0','production:productionLine:view','#','admin','2019-09-25 18:53:37','',NULL,'',0,1),(1473,'Line List',1472,1,'#','F','0','production:productionLine:list','#','admin','2019-09-25 18:54:06','',NULL,'',0,1),(1474,'Line Add Or Edit',1472,2,'#','F','0','production:productionLine:add','#','admin','2019-09-25 18:54:32','',NULL,'',0,1),(1475,'Line Remove',1472,3,'#','F','0','production:productionLine:remove','#','admin','2019-09-25 18:54:50','',NULL,'',0,1),(1476,'Config ASOP',1472,4,'#','F','0','iso:sopLine:add','#','admin','2019-09-25 18:55:35','',NULL,'',0,1),(1477,'Workstation List',1472,5,'#','F','0','production:workstation:list','#','admin','2019-09-25 18:56:01','admin','2019-09-25 18:56:44','',0,1),(1478,'Mgmt. Workstation',1472,6,'#','F','0','production:workstation:add','#','admin','2019-09-25 18:56:31','',NULL,'',0,1),(1479,'List',1471,1,'#','F','0','device:devWorkOrder:list','#','admin','2019-09-25 19:02:31','',NULL,'',0,1),(1480,'Add Or Edit',1471,2,'#','F','0','device:devWorkOrder:add','#','admin','2019-09-25 19:03:17','',NULL,'',0,1),(1481,'Remove',1471,3,'#','F','0','device:devWorkOrder:remove','#','admin','2019-09-25 19:03:40','',NULL,'',0,1),(1482,'Production Kanban',0,4,'#','M','0','','fa fa-newspaper-o','admin','2019-09-26 08:35:46','admin','2019-09-26 08:48:03','',0,3),(1483,'Prod.Order',1482,1,'/device/devWorkOrder/lineWorkOrder','C','0','device:devWorkOrder:view','#','admin','2019-09-26 08:37:20','',NULL,'',0,3),(1484,'Prod.Mgmt.',1482,2,'/production/productionLine','C','0','production:productionLine:view','#','admin','2019-09-26 08:38:36','',NULL,'',0,3),(1485,'Order List',1483,1,'#','F','0','device:devWorkOrder:list','#','admin','2019-09-26 08:39:06','admin','2019-09-26 08:40:08','',0,3),(1486,'Add Order',1483,2,'#','F','0','device:devWorkOrder:add','#','admin','2019-09-26 08:39:54','',NULL,'',0,3),(1487,'Edit Order',1483,3,'#','F','0','device:devWorkOrder:edit','#','admin','2019-09-26 08:40:28','',NULL,'',0,3),(1488,'Remove Order',1483,4,'#','F','0','device:devWorkOrder:remove','#','admin','2019-09-26 08:40:44','',NULL,'',0,3),(1489,'Line List',1484,1,'#','F','0','production:productionLine:list','#','admin','2019-09-26 08:41:32','',NULL,'',0,3),(1490,'Add Or Edit',1484,2,'#','F','0','production:productionLine:add','#','admin','2019-09-26 08:41:50','',NULL,'',0,3),(1491,'Remove',1484,3,'#','F','0','production:productionLine:remove','#','admin','2019-09-26 08:42:18','',NULL,'',0,3),(1492,'KanBan Mgmt.',1482,3,'/page/pageInfo','C','0','page:pageInfo:view','#','admin','2019-09-26 08:44:29','',NULL,'',0,3),(1493,'List',1492,1,'#','F','0','page:pageInfo:list','#','admin','2019-09-26 08:44:52','',NULL,'',0,3),(1494,'Add Or Edit',1492,2,'#','F','0','page:pageInfo:add','#','admin','2019-09-26 08:45:06','',NULL,'',0,3),(1495,'Remove',1492,3,'#','F','0','page:pageInfo:remove','#','admin','2019-09-26 08:45:45','',NULL,'',0,3),(1496,'Reports',1482,4,'/production/report','C','0','production:report:view','#','admin','2019-09-26 08:46:24','',NULL,'',0,3),(1497,'Export',1496,1,'#','F','0','production:report:pdf','#','admin','2019-09-26 08:46:45','admin','2019-09-27 13:53:21','',0,3),(1498,'Order Exc List',1483,5,'#','F','0','production:workExceptionList:list','#','admin','2019-09-26 11:15:51','admin','2019-09-26 11:17:04','',0,3),(1499,'Order Exc.Type Mgmt.',1483,6,'#','F','0','production:workExceptionList:add','#','admin','2019-09-26 11:16:51','',NULL,'',0,3),(1500,'Message Mgmt.',1,4,'/device/devNotice','C','0','device:devNotice:view','#','admin','2019-09-27 11:01:27','admin','2019-09-27 11:01:39','',0,3),(1501,'Msg List',1500,1,'#','F','0','device:devNotice:list','#','admin','2019-09-27 11:02:01','',NULL,'',0,3),(1502,'Msg Add',1500,2,'#','F','0','device:devNotice:add','#','admin','2019-09-27 11:02:31','',NULL,'',0,3),(1503,'Export Old List',1496,2,'#','F','0','erp:fileSourceInfo:list','#','admin','2019-09-27 13:57:48','',NULL,'',0,3),(1504,'Remove Old List',1496,3,'#','F','0','erp:fileSourceInfo:remove','#','admin','2019-09-27 13:58:09','',NULL,'',0,3),(1505,'Edit Old List',1496,4,'#','F','0','erp:fileSourceInfo:add','#','admin','2019-09-27 13:58:33','',NULL,'',0,3),(1506,'Password',1492,4,'#','F','0','page:pageInfo:pwd','#','admin','2019-10-16 08:27:19','',NULL,'',0,3),(1507,'任务管理',2,3,'/monitor/job','C','0','monitor:job:view','#','admin','2019-10-16 10:10:11','',NULL,'',0,0),(1508,'查看任务管理',1507,1,'#','F','0','monitor:job:list','#','admin','2019-10-16 10:11:07','',NULL,'',0,0),(1509,'新增修改任务管理',1507,2,'#','F','0','monitor:job:add','#','admin','2019-10-16 10:11:41','',NULL,'',0,0),(1510,'代码生成',2,4,'/tool/gen','C','0','tool:gen:view','#','admin','2019-10-21 09:02:05','',NULL,'',0,0),(1511,'Company data analysis',1496,5,'#','F','0','production:cost:list','#','admin','2019-10-25 10:26:54','',NULL,'',0,3),(1512,'生产管理',0,6,'#','M','0','','fa fa-edit','admin','2019-11-05 08:35:56','admin','2019-11-05 08:44:36','',0,0),(1513,'工单管理',1512,1,'/device/devWorkOrder/singWorkOrder','C','0','device:devWorkOrder:list','#','admin','2019-11-05 08:38:09','admin','2019-11-05 08:38:51','',0,0),(1514,'工单列表',1513,1,'#','F','0','device:devWorkOrder:list','#','admin','2019-11-05 08:38:40','',NULL,'',0,0),(1515,'新增工单',1513,2,'#','F','0','device:devWorkOrder:add','#','admin','2019-11-05 08:39:28','',NULL,'',0,0),(1516,'修改工单',1513,3,'#','F','0','device:devWorkOrder:edit','#','admin','2019-11-05 08:39:45','',NULL,'',0,0),(1517,'删除工单',1513,4,'#','F','0','device:devWorkOrder:remove','#','admin','2019-11-05 08:40:02','',NULL,'',0,0),(1518,'工位管理',1512,2,'/production/singleWork','C','0','production:singleWork:view','#','admin','2019-11-05 08:41:15','',NULL,'',0,0),(1519,'工位列表',1518,1,'#','F','0','production:singleWork:list','#','admin','2019-11-05 08:41:33','',NULL,'',0,0),(1520,'新增工位',1518,2,'#','F','0','production:singleWork:add','#','admin','2019-11-05 08:41:54','',NULL,'',0,0),(1521,'修改工位',1518,3,'#','F','0','production:singleWork:edit','#','admin','2019-11-05 08:42:26','',NULL,'',0,0),(1522,'删除工位',1518,4,'#','F','0','production:singleWork:remove','#','admin','2019-11-05 08:42:40','',NULL,'',0,0),(1523,'计件管理',1512,3,'#','C','0','','#','admin','2019-11-05 11:25:35','admin','2019-11-05 11:28:16','',0,0),(1524,'我的计件',1523,1,'/production/countPiece/myCountPiece','C','0','production:countPiece:list','#','admin','2019-11-05 11:26:08','',NULL,'',0,0),(1525,'全部计件',1523,2,'/production/countPiece','C','0','production:countPiece:view','#','admin','2019-11-05 11:26:30','',NULL,'',0,0),(1526,'计件查询',1525,1,'#','F','0','production:countPiece:list','#','admin','2019-11-05 11:26:50','',NULL,'',0,0),(1527,'计件新增修改',1525,2,'#','F','0','production:countPiece:edit','#','admin','2019-11-05 11:27:14','',NULL,'',0,0),(1528,'计件删除',1525,3,'#','F','0','production:countPiece:remove','#','admin','2019-11-05 11:27:36','',NULL,'',0,0),(1529,'计件导出',1525,4,'#','F','0','production:countPiece:export','#','admin','2019-11-05 11:27:52','',NULL,'',0,0),(1530,'硬件列表',1518,5,'#','F','0','device:devList:list','#','admin','2019-11-05 14:54:59','',NULL,'',0,0),(1531,'删除硬件',1518,6,'#','F','0','device:devList:remove','#','admin','2019-11-05 14:55:36','',NULL,'',0,0);
/*!40000 ALTER TABLE `sys_menu_en` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` varchar(2000) DEFAULT NULL COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_oper_log` (
  `oper_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(255) DEFAULT '' COMMENT '请求参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_post` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限）',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `company_id` int(11) NOT NULL COMMENT '公司主键ID',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',1,'1','0','0','admin','2018-03-16 11:33:00','admin','2019-08-26 09:09:29','管理员',0),(2,'管理员','common',3,'1','0','0','admin','2018-03-16 11:33:00','admin','2019-11-06 14:16:21','普通角色',1),(3,'系统管理员','jty',2,'1','0','0','admin','2019-08-24 17:15:28','admin','2019-08-26 16:20:17','',1);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role_dept` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (1,1),(1,2),(1,100),(1,101),(1,102),(1,111),(1,112),(1,1000),(1,1001),(1,1002),(1,1003),(1,1004),(1,1005),(1,1006),(1,1007),(1,1008),(1,1009),(1,1010),(1,1011),(1,1012),(1,1013),(1,1014),(1,1015),(1,1058),(1,1059),(1,1060),(1,1062),(1,1071),(1,1074),(1,1086),(1,1087),(1,1088),(1,1089),(1,1090),(1,1107),(1,1108),(1,1109),(1,1110),(1,1111),(1,1118),(1,1119),(1,1120),(1,1121),(1,1122),(1,1123),(1,1143),(1,1297),(1,1299),(1,1303),(1,1304),(1,1305),(1,1306),(1,1341),(1,1369),(1,1416),(1,1417),(1,1418),(1,1419),(2,1),(2,100),(2,101),(2,1000),(2,1001),(2,1002),(2,1003),(2,1004),(2,1005),(2,1006),(2,1007),(2,1008),(2,1009),(2,1010),(2,1011),(2,1058),(2,1060),(2,1074),(2,1083),(2,1084),(2,1118),(2,1119),(2,1120),(2,1121),(2,1122),(2,1123),(2,1341),(2,1512),(2,1513),(2,1514),(2,1515),(2,1516),(2,1517),(2,1518),(2,1519),(2,1520),(2,1521),(2,1522),(2,1523),(2,1524),(2,1525),(2,1526),(2,1527),(2,1528),(2,1529),(2,1530),(2,1531),(3,1),(3,2),(3,100),(3,101),(3,102),(3,111),(3,112),(3,1000),(3,1001),(3,1002),(3,1003),(3,1004),(3,1005),(3,1006),(3,1007),(3,1008),(3,1009),(3,1010),(3,1011),(3,1012),(3,1013),(3,1014),(3,1015),(3,1058),(3,1059),(3,1083),(3,1084),(3,1420),(3,1421),(3,1422),(3,1423),(3,1425),(3,1431),(3,1432),(3,1433),(3,1434),(3,1435),(3,1437);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(30) NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `login_tag` char(1) DEFAULT '1',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) DEFAULT '' COMMENT '密码',
  `salt` varchar(20) DEFAULT '' COMMENT '盐加密',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `company_id` int(11) NOT NULL COMMENT '公司主键ID',
  `tag` char(1) NOT NULL COMMENT '用户标记',
  `user_position` varchar(32) DEFAULT NULL,
  `user_department` varchar(32) DEFAULT NULL,
  `industry` int(11) DEFAULT '0' COMMENT '所属行业',
  `devType` varchar(45) DEFAULT NULL COMMENT '设备类型',
  `devCode` varchar(45) DEFAULT NULL COMMENT '设备号',
  `grade` int(11) DEFAULT '1' COMMENT '用户等级',
  `sign` int(11) DEFAULT '0',
  `lang_version` int(11) DEFAULT '1' COMMENT '选择的语言版本，默认为1中文版，2为英文版',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,103,'admin','聚特易信息技术','00','ry@163.com','15888888888','0','0','http://172.16.30.55:89/profile/mes1/dd51e44e258e6d13f6a5139084be2110.jpg','0036ffb77e9ea3b4632da355e7214b57','c80b8e','0','0','127.0.0.1','2019-06-05 14:49:40','admin','2018-03-16 11:33:00','ry','2019-09-20 08:39:23','管理员',1,'3',NULL,NULL,0,NULL,NULL,1,0,1),(2,103,'jty','jty','00','13052199701@qq.com','18033419683','0','0','','5a932c464349737c1a7d5b19fdcadb58','f84437','0','0','',NULL,'admin','2019-08-26 11:40:48','admin','2019-08-26 16:19:12','',1,'2',NULL,NULL,0,NULL,NULL,1,0,1),(3,NULL,'13559095980','王小虎','00','43827587234@qq.com','','0','0','','2ee8183c04f00a5f6566dc622d88dbf0','2c8c0a','0','0','',NULL,'','2019-11-05 08:46:47','','2019-11-05 08:47:02','',2,'1',NULL,NULL,0,NULL,NULL,1,1,1),(4,NULL,'13559095981','李四','00','88888888@qq.com','13559095981','1','0','','4a6e1c8860af00fb1fe090be6b6acecb','742130','0','0','',NULL,'13559095980','2019-11-05 09:04:31','',NULL,'',2,'0',NULL,NULL,0,NULL,NULL,1,0,1),(5,NULL,'13559095982','周业梅','00','34879587@qq.com','13559095982','1','0','','561a0abd1e70998cb4b8c2e8c6295f6c','88a5c3','0','0','',NULL,'13559095980','2019-11-05 09:05:17','',NULL,'',2,'0',NULL,NULL,0,NULL,NULL,1,0,1),(6,NULL,'13559095983','东于盛','00','888888388@qq.com','13559095983','1','0','','b67472dd15acc9f241a6d373132a3348','d2d353','0','0','',NULL,'13559095980','2019-11-05 09:05:36','',NULL,'',2,'0',NULL,NULL,0,NULL,NULL,1,0,1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_online`
--

DROP TABLE IF EXISTS `sys_user_online`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user_online` (
  `sessionId` varchar(50) NOT NULL DEFAULT '' COMMENT '用户会话id',
  `login_name` varchar(50) DEFAULT '' COMMENT '登录账号',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `ipaddr` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` varchar(10) DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
  `start_timestamp` datetime DEFAULT NULL COMMENT 'session创建时间',
  `last_access_time` datetime DEFAULT NULL COMMENT 'session最后访问时间',
  `expire_time` int(5) DEFAULT '0' COMMENT '超时时间，单位为分钟',
  PRIMARY KEY (`sessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='在线用户记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_online`
--

LOCK TABLES `sys_user_online` WRITE;
/*!40000 ALTER TABLE `sys_user_online` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_online` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user_post` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `post_id` int(11) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
INSERT INTO `sys_user_post` VALUES (1,1);
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,3),(3,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_active_code`
--

DROP TABLE IF EXISTS `tab_active_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_active_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(80) DEFAULT NULL COMMENT '激活码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机IMEI号',
  `act_sign` int(1) DEFAULT '0' COMMENT '是否激活0、否 1、是',
  `company_id` int(11) DEFAULT NULL COMMENT '分配公司',
  `dis_sign` int(11) DEFAULT '0' COMMENT '是否分配',
  `active_ip` varchar(45) DEFAULT NULL COMMENT '激活IP',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `dis_date` datetime DEFAULT NULL COMMENT '分配时间',
  `act_date` datetime DEFAULT NULL COMMENT '激活时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `imei_uindex` (`imei`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='激活码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_active_code`
--

LOCK TABLES `tab_active_code` WRITE;
/*!40000 ALTER TABLE `tab_active_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_active_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_after_service`
--

DROP TABLE IF EXISTS `tab_after_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_after_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '售后服务主键',
  `input_batch_info` varchar(400) DEFAULT NULL COMMENT '录入批次信息',
  `input_time` datetime DEFAULT NULL COMMENT '录入时间',
  `input_user_id` int(11) DEFAULT NULL COMMENT '录入对象id',
  `batch_code` varchar(125) DEFAULT NULL COMMENT '批次号(预留字段)',
  `company_id` int(11) DEFAULT NULL COMMENT '所属公司id',
  PRIMARY KEY (`id`),
  KEY `as_index` (`input_batch_info`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='售后服务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_after_service`
--

LOCK TABLES `tab_after_service` WRITE;
/*!40000 ALTER TABLE `tab_after_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_after_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_bom_change`
--

DROP TABLE IF EXISTS `tab_bom_change`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_bom_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长id',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `p_id` int(11) DEFAULT NULL COMMENT '产品id',
  `old_version` int(11) DEFAULT NULL COMMENT '上一个版本',
  `new_version` int(11) DEFAULT NULL COMMENT '新版本',
  `c_time` datetime DEFAULT NULL COMMENT '创建时间',
  `c_id` int(11) DEFAULT NULL COMMENT '创建者',
  `sh_time` datetime DEFAULT NULL COMMENT '审核时间',
  `sh_id` int(11) DEFAULT NULL COMMENT '审核人',
  `sh_status` int(11) DEFAULT '0' COMMENT '审核状态(0、未审核 1、已审核)',
  `change_text` text COMMENT '修改内容(每行用<br/>隔开)',
  `new_bom_id` int(11) DEFAULT NULL COMMENT 'BOM id',
  `old_bom_id` int(11) DEFAULT NULL,
  `def_id` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `new_bom_id` (`new_bom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BOM更改记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_bom_change`
--

LOCK TABLES `tab_bom_change` WRITE;
/*!40000 ALTER TABLE `tab_bom_change` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_bom_change` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_bom_config`
--

DROP TABLE IF EXISTS `tab_bom_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_bom_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增长id',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `materiel_code` int(11) NOT NULL COMMENT '物料编码下标',
  `materiel_name` int(11) NOT NULL COMMENT '物料名称下标',
  `materiel_model` int(11) NOT NULL COMMENT '物料型号下标',
  `number` int(11) NOT NULL COMMENT '用量下标',
  `unit` int(11) DEFAULT NULL COMMENT '单位下标',
  `place_number` int(11) DEFAULT NULL COMMENT '位号下标',
  `remark_index` int(11) DEFAULT NULL COMMENT '备注下标',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `row_index` int(11) DEFAULT NULL COMMENT '从第几行开始解析',
  `c_type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='导入配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_bom_config`
--

LOCK TABLES `tab_bom_config` WRITE;
/*!40000 ALTER TABLE `tab_bom_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_bom_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_count_piece`
--

DROP TABLE IF EXISTS `tab_count_piece`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_count_piece` (
  `cp_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '计件管理主键id',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `cp_user_id` int(11) DEFAULT NULL COMMENT '计件记录归属人',
  `cp_date` date DEFAULT NULL COMMENT '计件日期',
  `work_id` int(11) DEFAULT NULL COMMENT '工单id',
  `work_number` varchar(45) DEFAULT NULL COMMENT '工单号',
  `work_price` float(10,2) DEFAULT '0.00' COMMENT '工价',
  `cp_number` int(11) DEFAULT '0' COMMENT '计件数量',
  `total_price` float(10,2) DEFAULT '0.00' COMMENT '总价((计件数量-不良品数量)*工价)',
  `cp_bad_number` int(11) DEFAULT '0' COMMENT '不良品',
  `cp_remark` text COMMENT '备注',
  `cp_last_update` datetime DEFAULT NULL COMMENT '最后修改时间',
  `cp_last_id` int(11) DEFAULT NULL COMMENT '最后修改人',
  PRIMARY KEY (`cp_id`),
  UNIQUE KEY `count_unique` (`cp_user_id`,`cp_date`,`work_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='计件管理数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_count_piece`
--

LOCK TABLES `tab_count_piece` WRITE;
/*!40000 ALTER TABLE `tab_count_piece` DISABLE KEYS */;
INSERT INTO `tab_count_piece` VALUES (1,2,5,'2019-11-05',1,'GD20191105095935',12.13,20,230.47,1,'有一个不良品','2019-11-06 13:55:26',3),(2,2,4,'2019-11-05',1,'GD20191105095935',12.13,480,5822.40,0,NULL,NULL,NULL),(3,2,3,'2019-11-06',3,'GD20191106100446',12.13,30,363.90,0,NULL,NULL,NULL),(4,2,5,'2019-11-06',3,'GD20191106100446',12.13,470,5701.10,10,'有10个不良品','2019-11-06 13:55:08',3),(5,2,5,'2019-11-06',2,'GD20191105103642',18.45,500,9225.00,0,NULL,NULL,NULL),(6,2,5,'2019-11-08',2,'GD20191105103642',18.45,0,0.00,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tab_count_piece` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_file_source_info`
--

DROP TABLE IF EXISTS `tab_file_source_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_file_source_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增长',
  `company_id` int(11) DEFAULT NULL COMMENT '所属公司',
  `file_type` int(11) DEFAULT '1' COMMENT '文件类型',
  `save_type` int(11) DEFAULT '1' COMMENT '保存类型',
  `save_id` int(11) DEFAULT NULL COMMENT '保存id',
  `save_path` varchar(245) DEFAULT NULL,
  `file_path` varchar(245) DEFAULT NULL COMMENT '文件路径',
  `file_name` varchar(245) DEFAULT NULL COMMENT '文件名称',
  `create_time` datetime DEFAULT NULL COMMENT '上传时间',
  `f_size` bigint(20) DEFAULT '0' COMMENT '文件大小',
  PRIMARY KEY (`id`),
  KEY `com_type_index` (`company_id`,`save_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件素材管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_file_source_info`
--

LOCK TABLES `tab_file_source_info` WRITE;
/*!40000 ALTER TABLE `tab_file_source_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_file_source_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_import_config`
--

DROP TABLE IF EXISTS `tab_import_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_import_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `con1` int(11) DEFAULT NULL COMMENT '产品编码、半产品编码',
  `con2` int(11) DEFAULT NULL COMMENT '产品名称/半产品名称',
  `con3` int(11) DEFAULT NULL COMMENT '产品型号/半成品信号',
  `price` int(11) DEFAULT NULL COMMENT '单价',
  `con4` int(11) DEFAULT NULL COMMENT '标准工时',
  `con5` int(11) DEFAULT '0' COMMENT '备注信息',
  `c_type` int(11) DEFAULT '0' COMMENT '配置类型 0、产品配置 1、半成品配置',
  `c_time` datetime DEFAULT NULL COMMENT '操作时间',
  `unit` int(11) NOT NULL DEFAULT '0' COMMENT '单位',
  `row_index` int(11) DEFAULT '1',
  `c_sign` int(11) DEFAULT '0' COMMENT '用于标记是否配置图片解析配置',
  `app_id` varchar(345) DEFAULT NULL,
  `api_key` varchar(345) DEFAULT NULL,
  `secret_key` varchar(345) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_uindex` (`c_type`,`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='导入配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_import_config`
--

LOCK TABLES `tab_import_config` WRITE;
/*!40000 ALTER TABLE `tab_import_config` DISABLE KEYS */;
INSERT INTO `tab_import_config` VALUES (1,2,1,2,3,4,NULL,6,0,'2019-11-06 14:29:15',5,2,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tab_import_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_iso`
--

DROP TABLE IF EXISTS `tab_iso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_iso` (
  `iso_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(145) DEFAULT NULL COMMENT '中文名称',
  `e_name` varchar(145) DEFAULT NULL COMMENT '英文名称',
  `disk` varchar(245) DEFAULT NULL COMMENT '文件夹路径',
  `disk_path` varchar(245) DEFAULT NULL COMMENT '访问拼接地址',
  `path` varchar(245) DEFAULT NULL COMMENT '访问路径',
  `i_type` int(11) DEFAULT '0' COMMENT '文件类型，0、文件夹 1、文件',
  `file_size` varchar(45) DEFAULT '--' COMMENT '文件大小',
  `category` int(11) DEFAULT '0' COMMENT '类别 0、普通文件 1、SOP 2、ECN 3、DCN',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `parent_id` int(11) DEFAULT '0' COMMENT '父类别id',
  `grparent_id` int(11) DEFAULT '0' COMMENT '祖父id',
  `c_id` int(11) DEFAULT '0' COMMENT '创建者id',
  `c_time` datetime DEFAULT NULL COMMENT '创建时间',
  `def_id` int(11) DEFAULT '1' COMMENT '是否可以删除 0、不能 1、可以',
  `f_size` bigint(20) DEFAULT '0',
  PRIMARY KEY (`iso_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_iso`
--

LOCK TABLES `tab_iso` WRITE;
/*!40000 ALTER TABLE `tab_iso` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_iso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_jpush_info`
--

DROP TABLE IF EXISTS `tab_jpush_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_jpush_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_number` varchar(45) DEFAULT NULL,
  `first_time` varchar(125) DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `j_unindex` (`company_number`,`first_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='极光推送记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_jpush_info`
--

LOCK TABLES `tab_jpush_info` WRITE;
/*!40000 ALTER TABLE `tab_jpush_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_jpush_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_materiel`
--

DROP TABLE IF EXISTS `tab_materiel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_materiel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '物料主键ID',
  `company_id` int(11) DEFAULT NULL COMMENT '公司主键ID',
  `materiel_code` varchar(45) NOT NULL COMMENT '物料编码',
  `materiel_name` varchar(45) DEFAULT NULL COMMENT '物料名称',
  `materiel_model` varchar(45) NOT NULL COMMENT '物料型号',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '单价(含税)',
  `materiel_img` text COMMENT '物料图片(最多五张)',
  `img_size` int(11) DEFAULT '0' COMMENT '图片张数',
  `remark` text COMMENT '备注信息',
  `create_id` int(11) DEFAULT NULL COMMENT '创建者',
  `create_name` varchar(45) DEFAULT NULL COMMENT '创建者名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `unit` varchar(45) DEFAULT NULL COMMENT '物料添加单位',
  PRIMARY KEY (`id`),
  UNIQUE KEY `m_u_index` (`materiel_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物料';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_materiel`
--

LOCK TABLES `tab_materiel` WRITE;
/*!40000 ALTER TABLE `tab_materiel` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_materiel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_mes_batch`
--

DROP TABLE IF EXISTS `tab_mes_batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_mes_batch` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增长',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `mes_code` varchar(65) DEFAULT NULL COMMENT '主码',
  `work_code` varchar(45) DEFAULT NULL COMMENT '工单号',
  `order_code` varchar(45) DEFAULT NULL COMMENT '订单号',
  `pb_code` varchar(45) DEFAULT NULL COMMENT '产品/半成品编码',
  `rule_id` int(11) DEFAULT NULL COMMENT '规则id',
  `rule_name` varchar(145) DEFAULT NULL COMMENT '规则名称',
  `rule_materiel` varchar(645) DEFAULT NULL COMMENT '规则物料',
  `c_time` datetime DEFAULT NULL COMMENT '创建时间',
  `u_time` datetime DEFAULT NULL,
  `mes_sign` varchar(45) DEFAULT NULL COMMENT 'mes标记扫描字段(1-12优先级别递增)、0代表生产未扫描，1，代表生产已扫描',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_uindex` (`mes_code`,`company_id`),
  KEY `work_index` (`work_code`),
  KEY `order_index` (`order_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MES批准追踪';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_mes_batch`
--

LOCK TABLES `tab_mes_batch` WRITE;
/*!40000 ALTER TABLE `tab_mes_batch` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_mes_batch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_mes_batch_detail`
--

DROP TABLE IF EXISTS `tab_mes_batch_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_mes_batch_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增长',
  `b_id` int(11) DEFAULT NULL COMMENT '追踪id',
  `materiel_code` varchar(45) DEFAULT NULL COMMENT '物料编码',
  `batch_code` varchar(45) DEFAULT NULL COMMENT '批次号',
  `pro_batch_code` varchar(45) DEFAULT NULL COMMENT '生产MES批次信息',
  `c_time` datetime DEFAULT NULL COMMENT '创建时间',
  `d_tag` int(11) DEFAULT '0' COMMENT '关联半成品是否配置(默认值0未配置，1已配置)',
  `d_type` int(11) DEFAULT '2' COMMENT 'MES关联类型(默认值2物料，1半成品)',
  `mes_number` int(11) DEFAULT NULL COMMENT 'mes批次数量',
  `rule_order` int(11) DEFAULT NULL COMMENT 'mes数据规则排序(7代表无先后顺序，1-6优先级别逐渐递减)',
  PRIMARY KEY (`id`),
  KEY `m_d_index` (`b_id`),
  KEY `m_ds_index` (`batch_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MES批准追踪详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_mes_batch_detail`
--

LOCK TABLES `tab_mes_batch_detail` WRITE;
/*!40000 ALTER TABLE `tab_mes_batch_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_mes_batch_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_mes_batch_rule`
--

DROP TABLE IF EXISTS `tab_mes_batch_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_mes_batch_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增长',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `rule_name` varchar(145) DEFAULT NULL COMMENT '规则名称',
  `p_type` int(11) DEFAULT '0' COMMENT '类型区分',
  `materiels` varchar(600) DEFAULT NULL COMMENT '物料编码',
  `c_time` datetime DEFAULT NULL COMMENT '创建时间',
  `u_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) DEFAULT '0' COMMENT '规格排序是否有效(0、默认值无效，1、有效)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_uindex` (`rule_name`,`company_id`),
  KEY `materiels_index` (`materiels`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MES批准追踪规则';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_mes_batch_rule`
--

LOCK TABLES `tab_mes_batch_rule` WRITE;
/*!40000 ALTER TABLE `tab_mes_batch_rule` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_mes_batch_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_mes_batch_rule_detail`
--

DROP TABLE IF EXISTS `tab_mes_batch_rule_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_mes_batch_rule_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'MES规则明细id',
  `rule_id` int(11) DEFAULT NULL COMMENT 'MES规则主表id',
  `d_type` int(11) DEFAULT '2' COMMENT '配置规则明细类型(1、半成品，2、物料默认值)',
  `d_code` varchar(125) DEFAULT NULL COMMENT '半成品/物料的编码',
  `d_tag` int(11) DEFAULT '0' COMMENT '用于判断半成品是否已经配置了MES规则(0、默认值未配置，1、已配置)',
  `rule_order` int(11) DEFAULT NULL COMMENT '规则排序(默认7无优先级别，1-6优先级别递减，1最高)',
  PRIMARY KEY (`id`),
  KEY `m_s_index` (`d_type`,`d_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MES配置规则明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_mes_batch_rule_detail`
--

LOCK TABLES `tab_mes_batch_rule_detail` WRITE;
/*!40000 ALTER TABLE `tab_mes_batch_rule_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_mes_batch_rule_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_mes_part_log`
--

DROP TABLE IF EXISTS `tab_mes_part_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_mes_part_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '半成品mes批次追踪记录主键id',
  `work_code` varchar(45) DEFAULT NULL COMMENT '对应工单编号',
  `part_code` varchar(65) DEFAULT NULL COMMENT '半成品编码',
  `mes_code` varchar(65) DEFAULT NULL COMMENT '对应的主码编号',
  `materiel_code` varchar(65) DEFAULT NULL COMMENT '关联编码',
  `batch_code` varchar(125) DEFAULT NULL COMMENT '批次信息',
  `c_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='半成品mes批次追踪记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_mes_part_log`
--

LOCK TABLES `tab_mes_part_log` WRITE;
/*!40000 ALTER TABLE `tab_mes_part_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_mes_part_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_nect_work_single`
--

DROP TABLE IF EXISTS `tab_nect_work_single`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_nect_work_single` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '工单单工位关联表主键id',
  `work_id` int(11) DEFAULT NULL COMMENT '工单id',
  `sw_id` int(11) DEFAULT NULL COMMENT '单工位主键id',
  `c_time` datetime DEFAULT NULL COMMENT '配置时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单单工位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_nect_work_single`
--

LOCK TABLES `tab_nect_work_single` WRITE;
/*!40000 ALTER TABLE `tab_nect_work_single` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_nect_work_single` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_single_work`
--

DROP TABLE IF EXISTS `tab_single_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_single_work` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '单工位主键id',
  `company_id` int(11) DEFAULT NULL COMMENT '公司主键id',
  `workshop_name` varchar(45) DEFAULT NULL COMMENT '车间名称',
  `im_id` int(11) DEFAULT NULL COMMENT '设备主键id',
  `im_code` varchar(125) DEFAULT NULL COMMENT '设备编号',
  `sign` int(11) DEFAULT '1' COMMENT '标记(0、车间 1、设备)',
  `liable_one` int(11) DEFAULT NULL COMMENT '责任人1(如果是车间则是责任人，是设备则是员工)',
  `liable_two` int(11) DEFAULT NULL,
  `dev_id` int(11) DEFAULT '0' COMMENT '计数器id(默认是0、未配置)',
  `dev_code` varchar(45) DEFAULT NULL COMMENT '计数器硬件编号',
  `watch_id` int(11) DEFAULT '0' COMMENT '看板硬件id',
  `watch_code` varchar(45) DEFAULT NULL COMMENT '看板硬件编码',
  `e_id` int(11) DEFAULT '0' COMMENT '扫码枪硬件id',
  `e_code` varchar(45) DEFAULT NULL COMMENT '扫码枪硬件编码',
  `c_time` datetime DEFAULT NULL,
  `parent_id` int(11) DEFAULT '0' COMMENT '上一级id(父id)',
  `sop_sign` int(11) DEFAULT '0' COMMENT '预留字段',
  PRIMARY KEY (`id`),
  KEY `sw_sop_index` (`parent_id`,`liable_one`,`company_id`) /*!80000 INVISIBLE */,
  KEY `kb_sw_index` (`company_id`,`liable_one`,`sign`,`dev_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='单工位数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_single_work`
--

LOCK TABLES `tab_single_work` WRITE;
/*!40000 ALTER TABLE `tab_single_work` DISABLE KEYS */;
INSERT INTO `tab_single_work` VALUES (1,2,'绕线装备车间',NULL,NULL,0,3,3,0,NULL,0,NULL,0,NULL,'2019-11-05 14:18:02',0,0),(2,2,'研磨专业车间',NULL,NULL,0,3,4,0,NULL,0,NULL,0,NULL,'2019-11-05 14:18:23',0,0),(3,2,'绕线A号位',NULL,NULL,1,3,NULL,2,'JSE6343142EB',0,NULL,0,NULL,'2019-11-05 14:18:40',1,0),(4,2,'绕线B号位',NULL,NULL,1,6,NULL,4,'JS6688AF2C15',0,NULL,0,NULL,'2019-11-05 14:18:53',1,0),(5,2,'研磨AA工位',NULL,NULL,1,5,NULL,0,NULL,0,NULL,0,NULL,'2019-11-05 14:19:15',2,0),(6,2,'研磨BB工位',NULL,NULL,1,6,NULL,0,NULL,0,NULL,0,NULL,'2019-11-05 14:19:24',2,0),(9,2,'绕线C号位',NULL,NULL,1,5,NULL,5,'JS2E0E9CECB6',0,NULL,0,NULL,'2019-11-06 10:49:23',2,0);
/*!40000 ALTER TABLE `tab_single_work` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_single_work_order`
--

DROP TABLE IF EXISTS `tab_single_work_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_single_work_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `work_id` int(11) DEFAULT NULL COMMENT '工单id',
  `work_code` varchar(45) DEFAULT NULL COMMENT '工单编号',
  `single_id` int(11) DEFAULT NULL COMMENT '单工位id',
  `single_p` int(11) DEFAULT NULL COMMENT '父级id',
  `c_time` datetime DEFAULT NULL COMMENT '配置时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `s_w_uindex` (`work_id`,`work_code`,`single_id`,`single_p`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='单工位与工单进行配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_single_work_order`
--

LOCK TABLES `tab_single_work_order` WRITE;
/*!40000 ALTER TABLE `tab_single_work_order` DISABLE KEYS */;
INSERT INTO `tab_single_work_order` VALUES (1,1,'GD20191105095935',3,1,'2019-11-05 16:26:26'),(11,3,'GD20191106100446',9,1,'2019-11-06 10:52:19'),(12,3,'GD20191106100446',3,1,'2019-11-06 13:44:27'),(13,2,'GD20191105103642',9,2,'2019-11-06 13:58:25'),(14,4,'GD20191106140307',3,1,'2019-11-11 10:51:38');
/*!40000 ALTER TABLE `tab_single_work_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_soft_version`
--

DROP TABLE IF EXISTS `tab_soft_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_soft_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL COMMENT '手机端类型',
  `version` varchar(45) DEFAULT NULL COMMENT '版本号',
  `tag` int(11) DEFAULT NULL COMMENT '类型(1、ASOP，2、生产看板，3、用户APP端)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag_unique` (`type`,`tag`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='app软件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_soft_version`
--

LOCK TABLES `tab_soft_version` WRITE;
/*!40000 ALTER TABLE `tab_soft_version` DISABLE KEYS */;
INSERT INTO `tab_soft_version` VALUES (1,'android','2.0',3),(2,'ios','1.0',3),(3,'android','1.0',2),(4,'ios','1.0',2),(5,'android','1.0',1),(6,'ios','1.0',1);
/*!40000 ALTER TABLE `tab_soft_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_sop`
--

DROP TABLE IF EXISTS `tab_sop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_sop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `cnf_name` varchar(245) DEFAULT NULL COMMENT '配置名称',
  `line_id` int(11) DEFAULT NULL COMMENT '产线名称',
  `sop_id` int(11) DEFAULT NULL COMMENT 'sop书id',
  `p_id` varchar(845) DEFAULT NULL COMMENT '产品id ',
  `p_code` text COMMENT '产品编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `company_name_uindex` (`company_id`,`cnf_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SOP配置主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_sop`
--

LOCK TABLES `tab_sop` WRITE;
/*!40000 ALTER TABLE `tab_sop` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_sop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_sop_line`
--

DROP TABLE IF EXISTS `tab_sop_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_sop_line` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `sop_id` int(11) DEFAULT NULL COMMENT '作业指定数id，文件夹',
  `line_id` int(11) DEFAULT NULL COMMENT '产线id/单工位id',
  `pn_id` int(11) DEFAULT NULL COMMENT '产品id',
  `pn_code` varchar(145) DEFAULT NULL COMMENT '产品编码',
  `c_id` int(11) DEFAULT NULL COMMENT '修改者id',
  `c_time` datetime DEFAULT NULL COMMENT '修改时间',
  `sop_tag` int(11) DEFAULT '0' COMMENT 'sop配置标记(默认0、流水线，1、单工位)',
  `w_id` int(11) DEFAULT NULL COMMENT '工位id',
  `page_id` int(11) DEFAULT NULL COMMENT '页id',
  `s_id` int(11) DEFAULT NULL COMMENT '主表id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cnf_uindex` (`company_id`,`line_id`,`sop_id`,`pn_id`,`w_id`,`page_id`,`sop_tag`,`s_id`),
  KEY `q_index` (`company_id`,`line_id`,`pn_code`,`w_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作业指导书  产线 配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_sop_line`
--

LOCK TABLES `tab_sop_line` WRITE;
/*!40000 ALTER TABLE `tab_sop_line` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_sop_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_sop_line_work`
--

DROP TABLE IF EXISTS `tab_sop_line_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_sop_line_work` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `line_id` int(11) DEFAULT NULL COMMENT '产线id/车间id',
  `sop_id` int(11) DEFAULT NULL COMMENT '作业指导书id',
  `w_id` int(11) DEFAULT NULL COMMENT '工位id/单工位id',
  `page_id` int(11) DEFAULT NULL COMMENT '作业指导书页id',
  `c_id` int(11) DEFAULT NULL COMMENT '修改者id',
  `c_time` datetime DEFAULT NULL COMMENT '修改时间',
  `sop_tag` int(11) DEFAULT '0' COMMENT 'sop配置标记(默认0、流水线，1、车间单工位)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sop_sw_index` (`line_id`,`sop_id`,`w_id`,`sop_tag`,`company_id`,`page_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产线工位配置指导书页';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_sop_line_work`
--

LOCK TABLES `tab_sop_line_work` WRITE;
/*!40000 ALTER TABLE `tab_sop_line_work` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_sop_line_work` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_time_record`
--

DROP TABLE IF EXISTS `tab_time_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_time_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司考勤记录主键id',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `line_id` int(11) DEFAULT NULL COMMENT '产线id',
  `normal_hour` float(7,2) DEFAULT NULL COMMENT '正常工时数',
  `normal_number` int(11) DEFAULT NULL COMMENT '正常用工人数',
  `overtime_hour` float(7,2) DEFAULT NULL COMMENT '加班小时数',
  `overtime_number` int(11) DEFAULT NULL COMMENT '加班的人数',
  `overtime_race` float(4,1) DEFAULT '1.5' COMMENT '加班倍率(1.5、2、3)',
  `change_hour` float(7,2) DEFAULT NULL COMMENT '手动调整工时',
  `record_date` date DEFAULT NULL COMMENT '记录日期年月日',
  `input_flag` int(11) DEFAULT '0' COMMENT '考勤确认状态(0、默认值未确认，1、已经确认)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tr_uindex` (`company_id`,`line_id`,`record_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司考勤记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_time_record`
--

LOCK TABLES `tab_time_record` WRITE;
/*!40000 ALTER TABLE `tab_time_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_time_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_work_log`
--

DROP TABLE IF EXISTS `tab_work_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_work_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `work_id` int(11) DEFAULT NULL COMMENT '工单id',
  `work_number` int(11) DEFAULT NULL COMMENT '工单总生产数',
  `work_product` varchar(45) DEFAULT NULL COMMENT '工单生产产品',
  `line_id` int(11) DEFAULT NULL COMMENT '产线id',
  `line_name` varchar(45) DEFAULT NULL COMMENT '产线名称',
  `bz_output` int(11) DEFAULT '0' COMMENT '标准产量',
  `sj_output` int(11) DEFAULT '0' COMMENT '实际产量',
  `input_data` date DEFAULT NULL COMMENT '录入时间年月日',
  `input_time` datetime DEFAULT NULL COMMENT '录入时间时分秒显示',
  `total_output` int(11) DEFAULT '0' COMMENT '总共生产产量',
  `work_code` varchar(45) DEFAULT NULL COMMENT '工单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_work_log`
--

LOCK TABLES `tab_work_log` WRITE;
/*!40000 ALTER TABLE `tab_work_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_work_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tab_workstation`
--

DROP TABLE IF EXISTS `tab_workstation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tab_workstation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `line_id` int(11) DEFAULT NULL COMMENT '产线id',
  `w_name` varchar(145) DEFAULT NULL COMMENT '工位名称',
  `sop_id` int(11) DEFAULT '0' COMMENT '指导书页数id',
  `dev_id` int(11) DEFAULT '0' COMMENT '计数器id',
  `dev_code` varchar(45) DEFAULT NULL COMMENT '计数器编码',
  `c_id` int(11) DEFAULT '0' COMMENT '看板硬件id',
  `c_code` varchar(45) DEFAULT NULL COMMENT '看板硬件编码',
  `e_id` int(11) DEFAULT '0' COMMENT 'ECM 扫描枪硬件id',
  `e_code` varchar(45) DEFAULT NULL COMMENT 'EMC 扫码枪编码',
  `sign` int(11) DEFAULT '0' COMMENT '计数器数据判断依据0、不是 1、是',
  `c_time` datetime DEFAULT NULL COMMENT '创建时间',
  `def_id` int(11) DEFAULT '0' COMMENT '标记是否需要删除',
  `res_sign` varchar(45) DEFAULT '-1' COMMENT '对应工位ASOP 更新标记 -1、默认值 0、未更新 1、更新',
  PRIMARY KEY (`id`),
  KEY `line_company_index` (`company_id`,`line_id`) /*!80000 INVISIBLE */,
  KEY `line_id` (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工位配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tab_workstation`
--

LOCK TABLES `tab_workstation` WRITE;
/*!40000 ALTER TABLE `tab_workstation` DISABLE KEYS */;
/*!40000 ALTER TABLE `tab_workstation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'onestation'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-26  9:17:54
