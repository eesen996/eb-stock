-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: ebstock
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `prod_id` int(11) NOT NULL AUTO_INCREMENT,
  `prod_name` varchar(512) DEFAULT NULL,
  `prod_no` varchar(128) DEFAULT NULL,
  `prod_type_id` int(11) DEFAULT NULL,
  `prod_state` int(11) DEFAULT NULL,
  PRIMARY KEY (`prod_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (2,'方向盘','334',41,0),(3,'趣味游戏玩具','5454',30,0),(4,'儿童玩具','5454',30,0),(5,'香水','7676',39,0),(6,'asdas','',30,0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_parameter`
--

DROP TABLE IF EXISTS `product_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_parameter` (
  `prod_param_id` int(11) NOT NULL AUTO_INCREMENT,
  `prod_param_name` varchar(512) DEFAULT NULL,
  `prod_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`prod_param_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_parameter`
--

LOCK TABLES `product_parameter` WRITE;
/*!40000 ALTER TABLE `product_parameter` DISABLE KEYS */;
INSERT INTO `product_parameter` VALUES (2,'黄色',1),(5,'uuuuu',4),(6,'uuuu',4),(7,'werwe',5),(8,'aa',2),(9,'ad',3),(10,'3444',5),(11,'asdasd',6);
/*!40000 ALTER TABLE `product_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_receive_stock`
--

DROP TABLE IF EXISTS `product_receive_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_receive_stock` (
  `prod_rece_stock_id` int(11) NOT NULL AUTO_INCREMENT,
  `prod_id` int(11) DEFAULT NULL,
  `prod_param_id` int(11) DEFAULT NULL,
  `stock_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`prod_rece_stock_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_receive_stock`
--

LOCK TABLES `product_receive_stock` WRITE;
/*!40000 ALTER TABLE `product_receive_stock` DISABLE KEYS */;
INSERT INTO `product_receive_stock` VALUES (9,2,8,37),(10,3,9,4),(11,4,5,28),(12,4,6,5),(13,5,7,26),(14,5,10,33);
/*!40000 ALTER TABLE `product_receive_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_receive_trade`
--

DROP TABLE IF EXISTS `product_receive_trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_receive_trade` (
  `prod_rece_trade_id` int(11) NOT NULL AUTO_INCREMENT,
  `trade_date` datetime DEFAULT NULL,
  `prod_id` int(11) DEFAULT NULL,
  `prod_param_id` int(11) DEFAULT NULL,
  `begin_num` int(11) DEFAULT NULL,
  `trade_add` int(11) DEFAULT NULL,
  `trade_del` int(11) DEFAULT NULL,
  `end_num` int(11) DEFAULT NULL,
  `oper_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`prod_rece_trade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_receive_trade`
--

LOCK TABLES `product_receive_trade` WRITE;
/*!40000 ALTER TABLE `product_receive_trade` DISABLE KEYS */;
INSERT INTO `product_receive_trade` VALUES (21,'2019-01-28 10:09:05',3,9,0,4,NULL,4,5),(22,'2019-01-28 10:09:24',4,5,0,18,NULL,18,5),(23,'2019-01-29 10:09:41',4,5,18,10,NULL,28,5),(24,'2019-01-29 10:09:54',4,6,0,5,NULL,5,5),(25,'2019-01-29 10:12:00',5,10,0,33,NULL,33,5),(26,'2019-01-30 10:13:17',5,7,56,NULL,NULL,36,5),(27,'2019-01-30 10:20:18',5,7,36,NULL,10,26,5),(28,'2019-01-30 10:39:42',2,8,10,NULL,-6,16,5),(29,'2019-01-30 10:40:56',2,8,16,NULL,6,10,5),(30,'2019-01-31 10:41:08',2,8,10,NULL,6,4,5),(31,'2019-01-31 10:46:28',2,8,4,NULL,-6,10,5),(32,'2019-01-31 10:50:48',2,8,10,NULL,-6,16,5),(33,'2019-01-31 10:58:08',2,8,16,NULL,6,10,5),(34,'2019-02-01 10:58:18',2,8,10,NULL,6,4,5),(37,'2019-02-01 11:04:10',2,8,4,333,NULL,337,5),(39,'2019-02-01 11:08:28',2,8,337,NULL,300,37,5);
/*!40000 ALTER TABLE `product_receive_trade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_stock`
--

DROP TABLE IF EXISTS `product_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_stock` (
  `prod_stock_id` int(11) NOT NULL AUTO_INCREMENT,
  `prod_id` int(11) DEFAULT NULL,
  `prod_param_id` int(11) DEFAULT NULL,
  `stock_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`prod_stock_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_stock`
--

LOCK TABLES `product_stock` WRITE;
/*!40000 ALTER TABLE `product_stock` DISABLE KEYS */;
INSERT INTO `product_stock` VALUES (8,5,7,30),(9,2,8,10);
/*!40000 ALTER TABLE `product_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_trade`
--

DROP TABLE IF EXISTS `product_trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_trade` (
  `prod_trade_id` int(11) NOT NULL AUTO_INCREMENT,
  `trade_date` datetime DEFAULT NULL,
  `prod_id` int(11) DEFAULT NULL,
  `prod_param_id` int(11) DEFAULT NULL,
  `begin_num` int(11) DEFAULT NULL,
  `trade_add` int(11) DEFAULT NULL,
  `trade_del` int(11) DEFAULT NULL,
  `end_num` int(11) DEFAULT NULL,
  `oper_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`prod_trade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_trade`
--

LOCK TABLES `product_trade` WRITE;
/*!40000 ALTER TABLE `product_trade` DISABLE KEYS */;
INSERT INTO `product_trade` VALUES (2,'2019-01-31 10:13:17',5,7,0,20,NULL,20,5),(3,'2019-01-31 10:20:18',5,7,20,10,NULL,30,5),(4,'2019-01-31 10:39:42',2,8,0,-6,NULL,-6,5),(5,'2019-01-31 10:40:56',2,8,-6,6,NULL,0,5),(6,'2019-01-31 10:41:08',2,8,0,6,NULL,6,5),(7,'2019-01-31 10:46:28',2,8,6,-6,NULL,0,5),(8,'2019-01-31 10:50:48',2,8,0,-6,NULL,-6,5),(9,'2019-01-31 10:58:08',2,8,-6,6,NULL,0,5),(10,'2019-01-31 10:58:18',2,8,0,6,NULL,6,5),(14,'2019-01-31 16:16:53',2,8,6,10,NULL,16,5),(15,'2019-01-31 16:20:24',2,8,16,NULL,6,10,5);
/*!40000 ALTER TABLE `product_trade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_type`
--

DROP TABLE IF EXISTS `product_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_type` (
  `prod_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `prod_type_name` varchar(64) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`prod_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_type`
--

LOCK TABLES `product_type` WRITE;
/*!40000 ALTER TABLE `product_type` DISABLE KEYS */;
INSERT INTO `product_type` VALUES (29,'玩具',0),(30,'儿童玩具',29),(39,'汽车用品',0),(41,'方向盘',39);
/*!40000 ALTER TABLE `product_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(32) DEFAULT NULL,
  `login_pass` varchar(32) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (3,'aaa','aaa','aaaaaa');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-11 14:59:32
