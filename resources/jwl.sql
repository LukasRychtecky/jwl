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

DROP database `wiki`;
CREATE database `wiki` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `wiki`;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'hello','What could wiki markup language do','2010-10-08 10:12:00',0,'Petr Dytrych','2010-10-10 10:12:00',1,'changed text'),(2,'hello','Malcolm Sheperd Knowles','2010-10-10 10:12:00',0,'Petr Janouch','2010-10-10 10:12:00',1,'acticle created'),(3,'hello','Maltská konvence','2010-11-22 11:34:16',1,'john','2010-11-22 11:34:16',2,''),(4,'helloooo\r\n\r\n\r\nmmm\r\n\r\nm**ffdf**fsf\r\nf\r\ns\r\nff','Data v Harrym Potterovi','2010-11-22 10:43:30',1,'john','2010-11-22 10:43:30',2,'');
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
  KEY `fk_article_has_role_role1` (`role_id`),
  CONSTRAINT `fk_article_has_role_article1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_article_has_role_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_exclude_role`
--

LOCK TABLES `article_exclude_role` WRITE;
/*!40000 ALTER TABLE `article_exclude_role` DISABLE KEYS */;
INSERT INTO `article_exclude_role` VALUES (1,2),(1,3);
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
INSERT INTO `article_has_tag` VALUES (5,2),(6,1),(7,1),(7,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES (1,'hello','Data v Harrym Potterovi','Jiří Ostatnický','2010-10-10 10:12:00','acticle created',4),(2,'hello\r\nf\r\nf\r\n\r\nf\r\ndf\r\nfdfddf','Data v Harrym Potterovi','john','2010-11-22 10:36:40','',4),(3,'hello\r\nf\r\nf\r\n\r\nf\r\ndf\r\nfdfddf','Data v Harrym Potterovi','john','2010-11-22 10:36:40','',4),(4,'hello\r\nf\r\nf\r\n\r\nf\r\ndf\r\nfdfddf','Data v Harrym Potterovi','john','2010-11-22 10:36:40','',4),(5,'helloooo\r\n\r\n\r\nmmm\r\n\r\nm**ffdf**','Data v Harrym Potterovi','john','2010-11-22 10:43:20','',4),(6,'helloooo\r\n\r\n\r\nmmm\r\n\r\nm**ffdf**','Data v Harrym Potterovi','john','2010-11-22 10:43:20','',4),(7,'helloooo\r\n\r\n\r\nmmm\r\n\r\nm**ffdf**fsf\r\nf\r\ns\r\nff','Data v Harrym Potterovi','john','2010-11-22 10:43:30','',4),(8,'helloooo\r\n\r\n\r\nmmm\r\n\r\nm**ffdf**fsf\r\nf\r\ns\r\nff','Data v Harrym Potterovi','john','2010-11-22 10:43:30','',4),(9,'helloooo\r\n\r\n\r\nmmm\r\n\r\nm**ffdf**fsf\r\nf\r\ns\r\nff','Data v Harrym Potterovi','john','2010-11-22 10:43:30','',4),(11,'hello','Maltská konvence','Lukáš Rychtecký','2010-10-10 10:12:00','acticle created',3),(12,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(13,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(14,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(15,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(16,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(17,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(18,'hello','Maltská konvence','john','2010-11-22 11:34:16','',3),(19,'helloooo\r\n\r\n\r\nmmm\r\n\r\nm**ffdf**fsf\r\nf\r\ns\r\nff','Data v Harrym Potterovi','john','2010-11-22 10:43:30','',4);
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(25) NOT NULL,
  `articleView` tinyint(1) NOT NULL,
  `articleEdit` tinyint(1) NOT NULL,
  `articleRename` tinyint(1) NOT NULL,
  `articleRestore` tinyint(1) NOT NULL,
  `articleLock` tinyint(1) NOT NULL,
  `articleDelete` tinyint(1) NOT NULL,
  `articleExcludeRole` tinyint(1) NOT NULL,
  `attachmentView` tinyint(1) NOT NULL,
  `attachmentAdd` tinyint(1) NOT NULL,
  `attachmentDelete` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'administrator',1,1,1,1,1,1,1,1,1,1),(2,'editor',1,1,1,1,0,0,0,1,1,1),(3,'visitor',1,0,0,0,0,0,0,1,0,0);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'Potter'),(2,'úmluva'),(3,'archeologie'),(4,'kniha'),(5,'andragogika'),(6,'markup'),(7,'markdown');
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

-- Dump completed on 2010-11-22 12:15:11
