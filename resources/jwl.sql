-- MySQL dump 10.13  Distrib 5.1.49, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: wiki
-- ------------------------------------------------------
-- Server version	5.1.49-1ubuntu8.1-log

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `text` mediumtext,
  `title` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `locked` tinyint(1) NOT NULL DEFAULT '0',
  `editor` varchar(100) NOT NULL,
  `modified` datetime NOT NULL,
  `editCount` int(11) NOT NULL DEFAULT '0',
  `changeNote` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'helloasd\r\nsdf\r\nsf\r\ns\r\nf','What could wiki markup language do','2010-11-27 22:47:03',0,'john','2010-11-27 22:47:03',2,''),(2,'hellofs\r\nfs\r\nfs\r\nfvxv\r\nxvxv\r\nxvvxvxvvxvxvxvvvx\r\ns\r\nfs\r\nfs','Malcolm Sheperd Knowles','2010-11-27 22:45:50',0,'john','2010-11-27 22:45:50',3,''),(3,'hello','Maltská konvence','2010-11-22 11:34:16',1,'john','2010-11-22 11:34:16',2,''),(5,'kjkikdsfdsds\r\nds\r\nd\r\nsd\r\nsd\r\ns\r\nds\r\nd','hjuhjhj','2010-11-27 14:17:38',0,'john','2010-11-27 14:17:38',1,''),(6,'','ahoj','2010-11-30 21:25:29',0,'john','2010-11-30 21:25:29',0,''),(8,'','plplplpl','2010-12-01 00:26:23',0,'john','2010-12-01 00:26:23',0,''),(9,'aaaaa','aaaaaaaaaa','2010-12-01 00:35:07',0,'john','2010-12-01 00:35:07',0,'cretaaa'),(11,'','eeeee','2010-12-01 02:28:51',0,'john','2010-12-01 02:28:51',0,''),(12,'ffwfewfwefewfew','aa112','2010-12-01 02:29:25',0,'john','2010-12-01 02:29:25',0,''),(17,'','qwer','2010-12-01 14:34:19',0,'john','2010-12-01 14:34:19',0,''),(18,'','qwerpppppp','2010-12-01 14:38:53',0,'john','2010-12-01 14:38:53',0,''),(19,'ewewe','ewewe','2010-12-01 14:39:07',0,'john','2010-12-01 14:39:07',0,''),(20,'wwww','wwwbbbbc','2010-12-01 14:39:53',0,'john','2010-12-01 14:39:53',0,''),(24,'','sdfgh','2010-12-01 15:47:36',0,'john','2010-12-01 15:47:36',0,''),(25,'','wsxplm','2010-12-01 15:48:18',0,'john','2010-12-01 15:48:18',0,''),(27,'#### badpis\r\n\r\nsdsdsdsdsdsdsd\r\n\r\ndwdwdwd\r\n\r\n\r\ndwdwd\\ddwdwdw\r\n\r\n\r\n\r\nwdwdwdwdsdsdsds\r\n\r\n\r\nddsdsd\r\n\r\n\r\nwddwdwd\r\n\r\n\r\nd\r\n\r\ndw\r\n\r\ndw\r\n\r\n\r\nwdwd\r\n\r\n\r\nwddwdwdw\r\n\r\n\r\nnmnmn\r\n\r\n\r\nddsds\r\n\r\nwd\r\n\r\nwd\r\n\r\nwdwdw','098775t','2011-02-15 19:06:30',0,'john','2011-02-15 19:06:30',1,''),(28,'nadpis 1\r\n========\r\n\r\nnadpis2\r\n-------\r\n\r\nd\r\nd\r\ned\r\ne\r\ndeeed\r\ns\r\nd\r\nsd\r\ns#### NAPDPIS4\r\n\r\n##### NAPDPIS 5\r\n\r\n###### napdpis6','123456jhgfds','2010-12-05 14:29:15',0,'john','2010-12-05 14:29:15',1,'nadpis 2'),(33,'dqdwe','popopopop','2010-12-05 22:16:03',0,'john','2010-12-05 22:16:03',0,''),(34,'ujhbad','rtyuio56789','2010-12-05 22:27:48',0,'john','2010-12-05 22:27:48',0,''),(35,'fewfe','poiuyt','2010-12-05 22:29:10',0,'john','2010-12-05 22:29:10',0,''),(36,'fdfwefe','qwertyuiofghjk','2010-12-05 22:31:29',0,'john','2010-12-05 22:31:29',0,''),(37,'fwfe3w','frifrbfiurwefbgirgb438g3','2010-12-05 22:32:24',0,'john','2010-12-05 22:32:24',0,''),(38,'vergf43rgf3w','r32479rgf8ig4f4h94gfb394gfh39','2010-12-05 22:33:12',0,'john','2010-12-05 22:33:12',0,''),(39,'re','a','2010-12-05 23:06:35',0,'john','2010-12-05 23:06:35',0,''),(44,'fgwgfrwe','popopopopopopodfdfef','2010-12-05 23:24:02',0,'john','2010-12-05 23:24:02',0,''),(45,'wefwefewfewfwefwfew','945934jfdbcvd3939','2010-12-05 23:26:56',0,'john','2010-12-05 23:26:56',0,''),(46,'asedrt','qwertyu','2010-12-06 19:10:43',0,'john','2010-12-06 19:10:43',0,''),(48,'asedrt','qwertyudwsds','2010-12-06 19:11:52',0,'john','2010-12-06 19:11:52',0,''),(49,'asedrt','qwertyud44wsds','2010-12-06 19:12:16',0,'john','2010-12-06 19:12:16',0,''),(50,'','12121dddfgnmjj','2011-03-12 00:01:53',0,'john','2011-03-12 00:01:53',1,''),(52,'','12121dddfgnmjjhbg','2010-12-06 19:30:35',0,'john','2010-12-06 19:30:35',0,''),(53,'','12121ddzapmjjhbg','2010-12-06 19:31:31',0,'john','2010-12-06 19:31:31',0,''),(54,'','13333333dzapmjjhbg','2010-12-06 19:32:52',0,'john','2010-12-06 19:32:52',0,''),(55,'','099oopiukj','2010-12-06 19:37:49',0,'john','2010-12-06 19:37:49',0,''),(56,'fwfwe','ytrhnnnnnn;i;','2010-12-06 19:54:50',0,'john','2010-12-06 19:54:50',0,''),(57,'fwfwe','ytrhnn5nn;i;','2010-12-06 19:55:35',0,'john','2010-12-06 19:55:35',0,''),(58,'fwfwe','ytrhnn50nn;i;','2010-12-06 19:57:06',0,'john','2010-12-06 19:57:06',0,''),(59,'fwfwe','ytrhnn50n','2010-12-06 20:04:08',0,'john','2010-12-06 20:04:08',0,''),(61,'fwfwe','ytrwwchnn50n','2010-12-06 20:07:04',0,'john','2010-12-06 20:07:04',0,''),(63,'fwfwe','ytrwwchn343443n50n','2010-12-06 20:09:09',0,'john','2010-12-06 20:09:09',0,''),(64,'fwfwe','ytrwwch0990987','2010-12-06 20:11:28',0,'john','2010-12-06 20:11:28',0,''),(65,'fwfwe','ytrwwch09934342ff0987','2010-12-06 20:12:50',0,'john','2010-12-06 20:12:50',0,''),(66,'fwfwe','ytrwwch0993dwdwd4342ff0987','2010-12-06 20:13:28',0,'john','2010-12-06 20:13:28',0,''),(67,'fwfwe','ytrwwch0966','2010-12-06 20:15:35',0,'john','2010-12-06 20:15:35',0,''),(68,'','eqwe24234tvg46g4f6','2010-12-06 20:22:35',0,'john','2010-12-06 20:22:35',0,''),(69,'','00','2010-12-06 20:31:18',0,'john','2010-12-06 20:31:18',0,''),(73,'','ewewed','2010-12-06 20:40:13',0,'john','2010-12-06 20:40:13',0,''),(75,'','ewewed36','2010-12-06 20:41:33',0,'john','2010-12-06 20:41:33',0,''),(76,'','ew1ewed36','2010-12-06 20:42:05',0,'john','2010-12-06 20:42:05',0,''),(77,'','ew1ewvcxsded36','2010-12-06 20:42:32',0,'john','2010-12-06 20:42:32',0,''),(78,'','ew1ewvcx4536','2010-12-06 20:44:56',0,'john','2010-12-06 20:44:56',0,''),(80,'','ew1ewlkiu4536','2010-12-06 20:50:30',0,'john','2010-12-06 20:50:30',0,''),(83,'','123456789oitwewyrgfd','2010-12-06 21:36:28',0,'john','2010-12-06 21:36:28',0,''),(86,'ewrwerewr','rewqrewr','2010-12-06 21:56:37',0,'john','2010-12-06 21:56:37',0,''),(87,'ewrwerewr','rewqrewewr','2010-12-06 22:05:07',0,'john','2010-12-06 22:05:07',0,''),(89,'ewrwerewr','rewqreewewwewr','2010-12-06 22:05:55',0,'john','2010-12-06 22:05:55',0,''),(90,'ewrwerewr','rewqreew33ewwewr','2010-12-06 22:06:24',0,'john','2010-12-06 22:06:24',0,''),(91,'ewrwerewr','rewqreew33ewewewe324524tvg6t3t35ewwewr','2010-12-06 22:06:57',0,'john','2010-12-06 22:06:57',0,''),(92,'ewrwerewr','rewqreew33egbhjyjykjjjgwewewe324524tvg6t3t35ewwewr','2010-12-06 22:07:55',0,'john','2010-12-06 22:07:55',0,''),(93,'','1212nh,j','2010-12-06 22:08:29',0,'john','2010-12-06 22:08:29',0,''),(94,'','1212nh,jeeewe','2010-12-06 22:09:09',0,'john','2010-12-06 22:09:09',0,''),(95,'','ewe1212nh,jeeewe','2010-12-06 22:09:24',0,'john','2010-12-06 22:09:24',0,''),(96,'','876hh','2010-12-06 22:10:06',0,'john','2010-12-06 22:10:06',0,''),(97,'45b66b4564','46b6','2010-12-06 22:10:26',0,'john','2010-12-06 22:10:26',0,''),(98,'45b66b4564','46b69899878','2010-12-06 22:11:31',0,'john','2010-12-06 22:11:31',0,''),(99,'','3456ujgfdsfgh','2010-12-06 22:12:03',0,'john','2010-12-06 22:12:03',0,''),(100,'dsafddasfdsafdsaf','4545435bhbhmd','2010-12-06 22:12:29',0,'john','2010-12-06 22:12:29',0,''),(101,'','vy5w4v5yg5v4geyvhvh654y54eg','2010-12-06 22:49:08',0,'john','2010-12-06 22:49:08',0,''),(102,'dwfqwecdxqw','eqrrrttfy65rve','2010-12-06 23:02:18',0,'john','2010-12-06 23:02:18',0,''),(103,'dwfqwecdxqw','eqrrrttfy65rveewekpmoi','2010-12-06 23:03:42',0,'john','2010-12-06 23:03:42',0,''),(104,'dwfqwecdxqw','eqrrrttfywekpmoi','2010-12-06 23:05:25',0,'john','2010-12-06 23:05:25',0,''),(106,'dwfqwecdxqwewe','eqrrrttfywekvpmoiew4','2010-12-06 23:07:51',0,'john','2010-12-06 23:07:51',0,''),(107,'dwfqwecdxqwewe','eqr120mq','2010-12-06 23:08:21',0,'john','2010-12-06 23:08:21',0,''),(108,'dwfqwecdxqwewe','eqr120mqpl','2010-12-06 23:11:42',0,'john','2010-12-06 23:11:42',0,''),(109,'dwfqwecdxqwewe','eqr121a0mqpl','2010-12-06 23:12:40',0,'john','2010-12-06 23:12:40',0,''),(110,'dwfqwecdxqwewe','eqr121a0vvmkmqpl','2010-12-06 23:18:01',0,'john','2010-12-06 23:18:01',0,''),(111,'dwfqwecdxqwewe','eqr121a0vvmewkmqpl','2010-12-06 23:18:51',0,'john','2010-12-06 23:18:51',0,''),(112,'dwfqwecdxqwewe','eqr1122121wkmqpl','2010-12-06 23:27:27',0,'john','2010-12-06 23:27:27',0,''),(113,'prvni\r\n\r\ndruhy\r\n\r\ntreti','001','2011-02-28 17:41:02',0,'john','2011-02-28 17:41:02',1,'3'),(114,'fedwed\r\nfewfewfew\r\n------------------\r\n\r\nblblblblblblblbl','0.0','2011-02-28 17:41:21',0,'john','2011-02-28 17:41:21',1,'blblbllb'),(115,'','54678','2010-12-08 21:17:12',0,'john','2010-12-08 21:17:12',0,'i97op;oj'),(116,'wertyu','qwerty','2011-03-06 22:20:19',0,'john','2011-03-06 22:20:19',0,'');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_exclude_role`
--

DROP TABLE IF EXISTS `article_exclude_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article_exclude_role` (
  `article_id` int(10) unsigned NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`article_id`,`role_id`),
  KEY `fk_article_has_role_article1` (`article_id`),
  KEY `fk_article_exclude_role_role1` (`role_id`),
  CONSTRAINT `fk_article_exclude_role_role1` FOREIGN KEY (`role_id`) REFERENCES `security_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_article_has_role_article1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_exclude_role`
--

LOCK TABLES `article_exclude_role` WRITE;
/*!40000 ALTER TABLE `article_exclude_role` DISABLE KEYS */;
INSERT INTO `article_exclude_role` VALUES (114,2);
/*!40000 ALTER TABLE `article_exclude_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_has_attachment`
--

DROP TABLE IF EXISTS `article_has_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article_has_attachment` (
  `article_id` int(10) unsigned NOT NULL,
  `attachment_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`article_id`,`attachment_id`),
  KEY `fk_article_has_attachment_article1` (`article_id`),
  KEY `fk_article_has_attachment_attachment1` (`attachment_id`),
  CONSTRAINT `fk_article_has_attachment_article1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_article_has_attachment_attachment1` FOREIGN KEY (`attachment_id`) REFERENCES `attachment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_has_attachment`
--

LOCK TABLES `article_has_attachment` WRITE;
/*!40000 ALTER TABLE `article_has_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_has_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_has_tag`
--

DROP TABLE IF EXISTS `article_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article_has_tag` (
  `tag_id` int(10) unsigned NOT NULL,
  `article_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`tag_id`,`article_id`),
  KEY `fk_tag_has_article_tag` (`tag_id`),
  KEY `fk_tag_has_article_article` (`article_id`),
  CONSTRAINT `fk_tag_has_article_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tag_has_article_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_has_tag`
--

LOCK TABLES `article_has_tag` WRITE;
/*!40000 ALTER TABLE `article_has_tag` DISABLE KEYS */;
INSERT INTO `article_has_tag` VALUES (65,69),(65,114),(66,114),(67,69),(67,114),(68,114),(69,69),(69,114),(70,69),(71,69),(72,115),(73,115),(74,27),(75,27),(76,50),(77,50),(78,50);
/*!40000 ALTER TABLE `article_has_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `originalFileName` varchar(255) NOT NULL,
  `uniqueFileName` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` mediumtext,
  `title` varchar(255) NOT NULL,
  `editor` varchar(100) NOT NULL,
  `modified` datetime NOT NULL,
  `changeNote` varchar(255) DEFAULT NULL,
  `article_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`,`article_id`),
  KEY `fk_history_article1` (`article_id`),
  CONSTRAINT `fk_history_article1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES (11,'hello','Maltská konvence','Lukáš Rychtecký','2010-10-10 10:12:00','acticle created',3),(12,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(13,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(14,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(15,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(16,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(17,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(18,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(20,'kjkik','hjuhjhj','john','2010-11-27 14:14:57','',5),(21,'kjkikdsfdsds\r\nds\r\nd\r\nsd\r\nsd\r\ns\r\nds\r\nd','hjuhjhj','john','2010-11-27 14:17:38','',5),(22,'hello','Malcolm Sheperd Knowles','Petr Janouch','2010-10-10 10:12:00','acticle created',2),(23,'hellofs\r\nfs\r\nfs\r\nf\r\nsf\r\nsf\r\nsf\r\ns\r\nfs\r\nfs','Malcolm Sheperd Knowles','john','2010-11-27 22:45:34','',2),(24,'hellofs\r\nfs\r\nfs\r\nf\r\nsf\r\nsf\r\nsf\r\ns\r\nfs\r\nfs','Malcolm Sheperd Knowles','john','2010-11-27 22:45:34','',2),(25,'hellofs\r\nfs\r\nfs\r\nfvxv\r\nxvxv\r\nxvvxvxvvxvxvxvvvx\r\ns\r\nfs\r\nfs','Malcolm Sheperd Knowles','john','2010-11-27 22:45:50','',2),(26,'hello','What could wiki markup language do','Petr Dytrych','2010-10-10 10:12:00','changed text',1),(27,'helloasd\r\nsdf\r\nsf\r\ns\r\nf','What could wiki markup language do','john','2010-11-27 22:47:03','',1),(28,'helloasd\r\nsdf\r\nsf\r\ns\r\nf','What could wiki markup language do','john','2010-11-27 22:47:03','',1),(30,'','098775t','john','2010-12-01 15:53:53','',27),(31,'sdsdsdsdsdsdsd','098775t','john','2010-12-01 16:14:04','',27),(32,'sdsdsdsdsdsdsd','098775t','john','2010-12-01 16:14:04','',27),(33,'sdsdsdsdsdsdsd','098775t','john','2010-12-01 16:14:04','',27),(56,'nadpis 1\r\n========\r\n\r\nd\r\nd\r\ned\r\ne\r\ndeeed\r\ns\r\nd\r\nsd\r\ns#### NAPDPIS4\r\n\r\n##### NAPDPIS 5\r\n\r\n###### napdpis6','123456jhgfds','john','2010-12-05 14:28:30','nadpis 1',28),(57,'nadpis 1\r\n========\r\n\r\nnadpis2\r\n-------\r\n\r\nd\r\nd\r\ned\r\ne\r\ndeeed\r\ns\r\nd\r\nsd\r\ns#### NAPDPIS4\r\n\r\n##### NAPDPIS 5\r\n\r\n###### napdpis6','123456jhgfds','john','2010-12-05 14:28:43','nadpis 2',28),(58,'sdsdsdsdsdsdsd\r\n\r\n\r\n\r\nnmnmn','098775t','john','2010-12-07 13:25:52','',27),(59,'prvni\r\n\r\ndruhy','001','john','2010-12-07 13:28:28','2',113),(60,'prvni\r\n\r\ndruhy\r\n\r\ntreti','001','john','2010-12-07 13:28:36','3',113),(71,'sdsdsdsdsdsdsd\r\n\r\n\r\ndwdwd\\d\r\nwdwdwdw\r\nddsdsd\r\nwd\r\nwd\r\nnmnmn','098775t','john','2010-12-07 14:51:31','',27),(75,'sdsdsdsdsdsdsd\r\n\r\ndwdwdwd\r\ndwdwd\\ddwdwdw\r\nwdwdwdwdsdsdsds\r\nddsdsd\r\nwd\r\nwddwdwdw\r\nnmnmn','098775t','john','2010-12-07 14:54:24','',27),(78,'sdsdsdsdsdsdsd\r\n\r\ndwdwdwd\r\ndwdwd\\ddwdwdw\r\nwdwdwdwdsdsdsds\r\nddsdsd\r\nwddwdwd\r\nd\r\nwdwd\r\n\r\n\r\nwddwdwdw\r\nnmnmn\r\nddsds\r\nwd\r\nwd\r\n\r\nwdwdw','098775t','john','2010-12-07 14:56:38','',27),(79,'sdsdsdsdsdsdsd\r\n\r\ndwdwdwd\r\n\r\n\r\ndwdwd\\ddwdwdw\r\n\r\n\r\n\r\nwdwdwdwdsdsdsds\r\n\r\n\r\nddsdsd\r\n\r\n\r\nwddwdwd\r\n\r\n\r\nd\r\n\r\ndw\r\n\r\ndw\r\n\r\n\r\nwdwd\r\n\r\n\r\nwddwdwdw\r\n\r\n\r\nnmnmn\r\n\r\n\r\nddsds\r\n\r\nwd\r\n\r\nwd\r\n\r\nwdwdw','098775t','john','2010-12-07 14:57:37','',27),(83,'#### badpis\r\n\r\nsdsdsdsdsdsdsd\r\n\r\ndwdwdwd\r\n\r\n\r\ndwdwd\\ddwdwdw\r\n\r\n\r\n\r\nwdwdwdwdsdsdsds\r\n\r\n\r\nddsdsd\r\n\r\n\r\nwddwdwd\r\n\r\n\r\nd\r\n\r\ndw\r\n\r\ndw\r\n\r\n\r\nwdwd\r\n\r\n\r\nwddwdwdw\r\n\r\n\r\nnmnmn\r\n\r\n\r\nddsds\r\n\r\nwd\r\n\r\nwd\r\n\r\nwdwdw','098775t','john','2011-02-15 19:06:29','',27),(84,'fedwed\r\nfewfewfew\r\n------------------','0.0','john','2011-02-20 19:10:31','',114),(85,'fedwed\r\nfewfewfew\r\n------------------\r\n\r\nblblblblblblblbl','0.0','john','2011-02-28 17:41:21','blblbllb',114),(86,'','12121dddfgnmjj','john','2011-03-12 00:01:27','',50);
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `key_word`
--

DROP TABLE IF EXISTS `key_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `key_word` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `word` varchar(45) NOT NULL,
  `created` datetime NOT NULL,
  `weight` double NOT NULL,
  `article_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_key_word_article1` (`article_id`),
  CONSTRAINT `fk_key_word_article1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=640 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `key_word`
--

LOCK TABLES `key_word` WRITE;
/*!40000 ALTER TABLE `key_word` DISABLE KEYS */;
INSERT INTO `key_word` VALUES (608,'ejb','2011-03-11 21:15:37',0.0453856587409973,4),(609,'persistence','2011-03-11 21:15:37',0.036523699760437,4),(610,'platform','2011-03-11 21:15:37',0.034039244055748,4),(611,'enterprise','2011-03-11 21:15:37',0.034039244055748,4),(612,'java','2011-03-11 21:15:37',0.0321133323013783,4),(613,'faces','2011-03-11 21:15:38',0.103505857288837,5),(614,'javaserver','2011-03-11 21:15:38',0.103505857288837,5),(615,'tools','2011-03-11 21:15:38',0.0621035136282444,5),(616,'vendors','2011-03-11 21:15:38',0.0414023399353027,5),(617,'technology','2011-03-11 21:15:38',0.0399817936122417,5),(618,'transaction','2011-03-11 21:15:39',0.149685397744179,6),(619,'manager','2011-03-11 21:15:39',0.0598741583526134,6),(620,'system','2011-03-11 21:15:39',0.0598741583526134,6),(621,'see','2011-03-11 21:15:39',0.0598741583526134,6),(622,'java','2011-03-11 21:15:39',0.0391060560941696,6),(623,'she','2011-03-11 21:15:39',0.0763102024793625,7),(624,'hogwarts','2011-03-11 21:15:39',0.0572326518595219,7),(625,'highly','2011-03-11 21:15:39',0.0572326518595219,7),(626,'as','2011-03-11 21:15:39',0.0415342077612877,7),(627,'madam','2011-03-11 21:15:39',0.0381551012396813,7),(628,'holiday','2011-03-11 21:15:40',0.0621035136282444,8),(629,'choice','2011-03-11 21:15:40',0.0414023399353027,8),(630,'coast','2011-03-11 21:15:40',0.0414023399353027,8),(631,'newquay','2011-03-11 21:15:40',0.0399817936122417,8),(632,'make','2011-03-11 21:15:40',0.027041420340538,8),(633,'gervais','2011-03-11 21:15:41',0.0496828109025955,9),(634,'he','2011-03-11 21:15:41',0.0414023399353027,9),(635,'awards','2011-03-11 21:15:41',0.0414023399353027,9),(636,'series','2011-03-11 21:15:41',0.0248414054512978,9),(637,'stand-up','2011-03-11 21:15:41',0.0248414054512978,9);
/*!40000 ALTER TABLE `key_word` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL,
  `context` varchar(45) NOT NULL,
  `method` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'Article','edit'),(2,'Article','view'),(3,'Article','rename'),(4,'Article','restore'),(5,'Article','delete'),(6,'Article','excludeRole'),(7,'Article','lock'),(8,'Attachment','view'),(9,'Attachment','add'),(10,'Attachment','delete');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating` (
  `article_id` int(10) unsigned NOT NULL,
  `author` varchar(100) NOT NULL,
  `rating` float NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`article_id`,`author`),
  KEY `fk_Rating_article1` (`article_id`),
  CONSTRAINT `fk_Rating_article1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (4,'Petr',10,'2011-03-09 00:03:39'),(5,'Petr',3,'2011-03-09 15:19:06');
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_has_permission`
--

DROP TABLE IF EXISTS `role_has_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_has_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `fk_role_has_permission_permission1` (`permission_id`),
  CONSTRAINT `fk_role_has_permission_permission1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_has_permission_role1` FOREIGN KEY (`role_id`) REFERENCES `security_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_has_permission`
--

LOCK TABLES `role_has_permission` WRITE;
/*!40000 ALTER TABLE `role_has_permission` DISABLE KEYS */;
INSERT INTO `role_has_permission` VALUES (1,1),(1,2),(2,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(2,8),(1,9),(1,10);
/*!40000 ALTER TABLE `role_has_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `security_role`
--

DROP TABLE IF EXISTS `security_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `security_role` (
  `id` int(11) NOT NULL,
  `code` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_role`
--

LOCK TABLES `security_role` WRITE;
/*!40000 ALTER TABLE `security_role` DISABLE KEYS */;
INSERT INTO `security_role` VALUES (1,'administrator'),(2,'visitor');
/*!40000 ALTER TABLE `security_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (69,'a'),(78,'aa'),(67,'b'),(68,'c'),(65,'d'),(76,'dd'),(66,'e'),(74,'elvis'),(77,'ff'),(75,'nononon'),(71,'r'),(70,'t'),(73,'ytuij'),(72,'yutfyiguhij');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-03-13 14:33:51
