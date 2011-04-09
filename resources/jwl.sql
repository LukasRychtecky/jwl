CREATE DATABASE  IF NOT EXISTS `wiki` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `wiki`;
-- MySQL dump 10.13  Distrib 5.1.40, for Win32 (ia32)
--
-- Host: localhost    Database: wiki
-- ------------------------------------------------------
-- Server version	5.1.50-community

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
INSERT INTO `rating` VALUES (11,'john',3,'2011-03-28 20:24:39');
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=1247 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `key_word`
--

LOCK TABLES `key_word` WRITE;
/*!40000 ALTER TABLE `key_word` DISABLE KEYS */;
INSERT INTO `key_word` VALUES (1207,'java','2011-03-18 23:34:31',0.0525417700409889,11),(1208,'persistence','2011-03-18 23:34:31',0.040416743606329,11),(1209,'ejb','2011-03-18 23:34:31',0.0323333963751793,11),(1210,'platform','2011-03-18 23:34:31',0.0242500472813845,11),(1211,'enterprise','2011-03-18 23:34:31',0.0242500472813845,11),(1212,'api','2011-03-18 23:34:31',0.0202083718031645,11),(1213,'ee','2011-03-18 23:34:31',0.0161666981875896,11),(1214,'technology','2011-03-18 23:34:31',0.0161666981875896,11),(1215,'entity','2011-03-18 23:34:31',0.0161666981875896,11),(1216,'javabeans','2011-03-18 23:34:31',0.0121250236406922,11),(1217,'facelets','2011-03-18 23:34:32',0.03465735912323,12),(1218,'view','2011-03-18 23:34:32',0.03465735912323,12),(1219,'javaserver','2011-03-18 23:34:32',0.017328679561615,12),(1220,'ui','2011-03-18 23:34:32',0.017328679561615,12),(1221,'web','2011-03-18 23:34:32',0.017328679561615,12),(1222,'framework','2011-03-18 23:34:32',0.017328679561615,12),(1223,'more','2011-03-18 23:34:32',0.017328679561615,12),(1224,'component','2011-03-18 23:34:32',0.017328679561615,12),(1225,'called','2011-03-18 23:34:32',0.017328679561615,12),(1226,'jsf','2011-03-18 23:34:32',0.017328679561615,12),(1227,'city','2011-03-18 23:34:34',0.072615422308445,13),(1228,'county','2011-03-18 23:34:34',0.0264056082814932,13),(1229,'ireland','2011-03-18 23:34:34',0.0198042057454586,13),(1230,'largest','2011-03-18 23:34:34',0.0198042057454586,13),(1231,'lee','2011-03-18 23:34:34',0.0198042057454586,13),(1232,'centre','2011-03-18 23:34:34',0.0198042057454586,13),(1233,'cork','2011-03-18 23:34:34',0.0198042057454586,13),(1234,'while','2011-03-18 23:34:34',0.0198042057454586,13),(1235,'channels','2011-03-18 23:34:34',0.0132028041407466,13),(1236,'irish','2011-03-18 23:34:34',0.0132028041407466,13),(1237,'holiday','2011-03-18 23:34:35',0.044243436306715,14),(1238,'choice','2011-03-18 23:34:35',0.0294956229627132,14),(1239,'coast','2011-03-18 23:34:35',0.0294956229627132,14),(1240,'make','2011-03-18 23:34:35',0.0221217181533575,14),(1241,'newquay','2011-03-18 23:34:35',0.0221217181533575,14),(1242,'newquay’s','2011-03-18 23:34:35',0.0147478114813566,14),(1243,'we’re','2011-03-18 23:34:35',0.0147478114813566,14),(1244,'beaches','2011-03-18 23:34:35',0.0147478114813566,14),(1245,'open','2011-03-18 23:34:35',0.0147478114813566,14),(1246,'destination”','2011-03-18 23:34:35',0.0147478114813566,14);
/*!40000 ALTER TABLE `key_word` ENABLE KEYS */;
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
/*!40000 ALTER TABLE `article_exclude_role` ENABLE KEYS */;
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
INSERT INTO `permission` VALUES (1,'Article','edit'),(2,'Article','view'),(3,'Article','rename'),(4,'Article','restore'),(5,'Article','delete'),(6,'Article','excludeRole'),(7,'Article','lock'),(8,'Attachment','view'),(9,'Attachment','add'),(10,'Attachment','delete'),(11,'Article','rate'),(12,'Knowledge','administer'),(13,'Forum','view'),(14,'Forum','close'),(15,'Forum','createTopic'),(16,'Forum','addPost'),(17,'Forum','deleteTopic'),(18,'Forum','deletePost');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topic` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `closed` tinyint(1) NOT NULL,
  `article_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_topic_article1` (`article_id`),
  CONSTRAINT `fk_topic_article1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (4,'rubbish',1,11),(5,'bollocks',0,11),(16,'my topic',0,11);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
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
INSERT INTO `role_has_permission` VALUES (1,1),(1,2),(2,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(2,8),(1,9),(1,10),(1,11),(2,11),(1,12),(1,13),(2,13),(1,14),(1,15),(1,16),(1,17),(1,18);
/*!40000 ALTER TABLE `role_has_permission` ENABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
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
  `livability` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (11,'The major theme of version 5 of the Java Platform, Enterprise Edition (Java EE, formerly referred to as J2EE) is ease of development. Changes throughout the platform make the development of enterprise Java technology applications much easier, with far less coding. Significantly, these simplifications have not changed the platform\'s power: The Java EE 5 platform maintains all the functional richness of the previous version, J2EE 1.4.\r\n\r\nEnterprise developers should notice dramatic simplification in Enterprise JavaBeans (EJB) technology. Previous articles, such as Introduction to the Java EE 5 Platform and Ease of Development in Enterprise JavaBeans Technology have described the simplifications made in EJB 3.0 technology, an integral part of the Java EE 5 platform.\r\n\r\nA major enhancement in EJB technology is the addition of the new Java Persistence API, which simplifies the entity persistence model and adds capabilities that were not in EJB 2.1 technology. The Java Persistence API deals with the way relational data is mapped to Java objects (\"persistent entities\"), the way that these objects are stored in a relational database so that they can be accessed at a later time, and the continued existence of an entity\'s state even after the application that uses it ends. In addition to simplifying the entity persistence model, the Java Persistence API standardizes object-relational mapping.\r\n\r\nIn short, EJB 3.0 is much easier to learn and use than was EJB 2.1, the technology\'s previous version, and should result in faster development of applications. With the inclusion of the Java Persistence API, EJB 3.0 technology also offers developers an entity programming model that is both easier to use and yet richer.\r\n\r\nThe Java Persistence API draws on ideas from leading persistence frameworks and APIs such as Hibernate, Oracle TopLink, and Java Data Objects (JDO), and well as on the earlier EJB container-managed persistence. The Expert Group for the Enterprise JavaBeans 3.0 Specification (JSR 220) has representation from experts in all of these areas as well as from other individuals of note in the persistence community. \r\n\r\n\r\nI love hibernate','JPA','2011-04-04 15:06:45',0,'john','2011-04-04 15:06:45',1,'',206),(12,'JavaServer Faces (JSF) is a Java-based Web application framework intended to simplify development integration of web-based user interfaces.\r\n\r\nJSF is a request-driven MVC web framework based on component driven UI design model, using XML files called view templates or Facelets views. Requests are processed by the FacesServlet, which loads the appropriate view template, builds a component tree, processes events, and renders the response (typically HTML) to the client. The state of UI components (and some other objects) is saved at the end of each request (called stateSaving (note: transient true)), and restored upon next creation of that view. Several types of state-saving are available, including Client-side and Server-side state saving. Out of the box, JSF 1.x uses JavaServer Pages (JSP) for its display technology, but can also accommodate other technologies (such as XUL and Facelets). JSF 2 uses Facelets by default for this purpose. Facelets is a more efficient, simple, and yet more powerful view description language (VDL).\r\n\r\nI love JSF','JSF','2011-04-02 21:05:21',0,'john','2011-04-02 21:05:21',1,'',20),(13,'Cork (Irish: Corcaigh, pronounced [?k?ork???], from corcach, meaning \"swamp\") is the second largest city in the Republic of Ireland and the island of Ireland\'s third most populous city. It is the principal city and administrative centre of County Cork[2] and the largest city in the province of Munster. Cork has a population of 119,418, while the addition of the suburban areas contained in the county brings the total to 190,384.[3] Metropolitan Cork has a population of approximately 274,000, while the Greater Cork area is about 380,000.[4]\r\n\r\nCounty Cork has earned the nickname of \"the Rebel County\", while Corkonians often refer to the city as the \"real capital of Ireland\", and themselves as the \"Rebels\".\r\n\r\nThe city is built on the River Lee which divides into two channels at the western end of the city. The city centre is located on the island created by the channels. At the eastern end of the city centre they converge; and the Lee flows around Lough Mahon to Cork Harbour, one of the world\'s largest natural harbours.[5][6] The city is a major Irish seaport; there are quays and docks along the banks of the Lee on the city\'s east side.','cork','2011-03-26 16:56:41',0,'john','2011-03-26 16:56:41',0,'',44),(14,'Newquay on Cornwall’s Atlantic Coast -voted “one of the nations favourite seaside towns” in the prestigious Which Holiday Survey and “Best Family Holiday Destination” by readers of COAST magazine, Newquay’s laid back charm is here for everyone to enjoy. We hope this website will encourage you to make Newquay your holiday choice. We’re open all year, accessible by rail, road or air and offer a choice of quality assured accommodation which is second to none alongside classic events, exhilarating activities and some of Cornwalls best beaches. Make it Newquay - make it soon! ','Newquay','2011-03-18 15:29:00',0,'john','2011-03-18 15:29:00',0,'',30);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `author` varchar(100) NOT NULL,
  `created` datetime NOT NULL,
  `text` text,
  `topic_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_post_topic1` (`topic_id`),
  CONSTRAINT `fk_post_topic1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'john','2011-03-23 16:18:26',NULL,4),(2,'john','2011-03-23 21:31:24',NULL,5),(13,'john','2011-03-31 12:46:59','This is a content of this topic',16),(17,'john','2011-04-03 20:04:18','Bigger title\r\n==================',16);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
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
/*!40000 ALTER TABLE `article_has_tag` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES (1,'JavaServer Faces (JSF) is a Java-based Web application framework intended to simplify development integration of web-based user interfaces.\r\n\r\nJSF is a request-driven MVC web framework based on component driven UI design model, using XML files called view templates or Facelets views. Requests are processed by the FacesServlet, which loads the appropriate view template, builds a component tree, processes events, and renders the response (typically HTML) to the client. The state of UI components (and some other objects) is saved at the end of each request (called stateSaving (note: transient true)), and restored upon next creation of that view. Several types of state-saving are available, including Client-side and Server-side state saving. Out of the box, JSF 1.x uses JavaServer Pages (JSP) for its display technology, but can also accommodate other technologies (such as XUL and Facelets). JSF 2 uses Facelets by default for this purpose. Facelets is a more efficient, simple, and yet more powerful view description language (VDL).','JSF','john','2011-03-26 12:07:53','',12),(2,'The major theme of version 5 of the Java Platform, Enterprise Edition (Java EE, formerly referred to as J2EE) is ease of development. Changes throughout the platform make the development of enterprise Java technology applications much easier, with far less coding. Significantly, these simplifications have not changed the platform\'s power: The Java EE 5 platform maintains all the functional richness of the previous version, J2EE 1.4.\r\n\r\nEnterprise developers should notice dramatic simplification in Enterprise JavaBeans (EJB) technology. Previous articles, such as Introduction to the Java EE 5 Platform and Ease of Development in Enterprise JavaBeans Technology have described the simplifications made in EJB 3.0 technology, an integral part of the Java EE 5 platform.\r\n\r\nA major enhancement in EJB technology is the addition of the new Java Persistence API, which simplifies the entity persistence model and adds capabilities that were not in EJB 2.1 technology. The Java Persistence API deals with the way relational data is mapped to Java objects (\"persistent entities\"), the way that these objects are stored in a relational database so that they can be accessed at a later time, and the continued existence of an entity\'s state even after the application that uses it ends. In addition to simplifying the entity persistence model, the Java Persistence API standardizes object-relational mapping.\r\n\r\nIn short, EJB 3.0 is much easier to learn and use than was EJB 2.1, the technology\'s previous version, and should result in faster development of applications. With the inclusion of the Java Persistence API, EJB 3.0 technology also offers developers an entity programming model that is both easier to use and yet richer.\r\n\r\nThe Java Persistence API draws on ideas from leading persistence frameworks and APIs such as Hibernate, Oracle TopLink, and Java Data Objects (JDO), and well as on the earlier EJB container-managed persistence. The Expert Group for the Enterprise JavaBeans 3.0 Specification (JSR 220) has representation from experts in all of these areas as well as from other individuals of note in the persistence community. ','JPA','john','2011-03-29 17:40:59','',11);
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-04-04 15:10:04
