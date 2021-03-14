-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: bibliotek
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `anställda`
--

DROP TABLE IF EXISTS `anställda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anställda` (
  `id` int NOT NULL AUTO_INCREMENT,
  `namn` varchar(255) DEFAULT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `månadslön` int DEFAULT NULL,
  `semesterdagar_kvar` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `teleindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anställda`
--

LOCK TABLES `anställda` WRITE;
/*!40000 ALTER TABLE `anställda` DISABLE KEYS */;
INSERT INTO `anställda` VALUES (1,'Stefan Grankvist','Sandvägen 33',32000,10),(2,'Olle Karlsson','Blåbärsvägen 22',30000,15),(3,'Stina Svensson','Nya gammelvägen 55',35000,20);
/*!40000 ALTER TABLE `anställda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `böcker`
--

DROP TABLE IF EXISTS `böcker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `böcker` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titel` varchar(255) DEFAULT NULL,
  `antal_sidor` int DEFAULT NULL,
  `klassifikation` int DEFAULT NULL,
  `författare` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `klassindex` (`klassifikation`),
  KEY `författarindex` (`författare`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `böcker`
--

LOCK TABLES `böcker` WRITE;
/*!40000 ALTER TABLE `böcker` DISABLE KEYS */;
INSERT INTO `böcker` VALUES (1,'Den e esamma katten',123,3,1),(2,'ABC',300,3,1),(3,'CBA',320,2,2),(4,'CBAAAA',3880,1,3);
/*!40000 ALTER TABLE `böcker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `författare`
--

DROP TABLE IF EXISTS `författare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `författare` (
  `id` int NOT NULL,
  `namn` varchar(255) NOT NULL,
  PRIMARY KEY (`id`,`namn`),
  CONSTRAINT `författare_ibfk_1` FOREIGN KEY (`id`) REFERENCES `böcker` (`författare`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `författare`
--

LOCK TABLES `författare` WRITE;
/*!40000 ALTER TABLE `författare` DISABLE KEYS */;
INSERT INTO `författare` VALUES (1,'Rudolf Ruskprick'),(2,'Kenny Surströmming'),(3,'Orvar Satorsson');
/*!40000 ALTER TABLE `författare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `klassifikationer`
--

DROP TABLE IF EXISTS `klassifikationer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `klassifikationer` (
  `id` int NOT NULL,
  `klassifikation` varchar(255) NOT NULL,
  PRIMARY KEY (`id`,`klassifikation`),
  CONSTRAINT `klassifikationer_ibfk_1` FOREIGN KEY (`id`) REFERENCES `böcker` (`klassifikation`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `klassifikationer`
--

LOCK TABLES `klassifikationer` WRITE;
/*!40000 ALTER TABLE `klassifikationer` DISABLE KEYS */;
INSERT INTO `klassifikationer` VALUES (1,'uHce'),(2,'Hcf'),(3,'Hce');
/*!40000 ALTER TABLE `klassifikationer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lagerplatser`
--

DROP TABLE IF EXISTS `lagerplatser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lagerplatser` (
  `id` int NOT NULL,
  `lagerplats` varchar(255) NOT NULL,
  PRIMARY KEY (`id`,`lagerplats`),
  CONSTRAINT `lagerplatser_ibfk_1` FOREIGN KEY (`id`) REFERENCES `tidsskrifter` (`lagerplats`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lagerplatser`
--

LOCK TABLES `lagerplatser` WRITE;
/*!40000 ALTER TABLE `lagerplatser` DISABLE KEYS */;
INSERT INTO `lagerplatser` VALUES (1,'HYLLA A'),(2,'HYLLA B'),(3,'HYLLA C');
/*!40000 ALTER TABLE `lagerplatser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `telefonnummer`
--

DROP TABLE IF EXISTS `telefonnummer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `telefonnummer` (
  `anställnings_id` int DEFAULT NULL,
  `telefonnummer` int NOT NULL,
  PRIMARY KEY (`telefonnummer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefonnummer`
--

LOCK TABLES `telefonnummer` WRITE;
/*!40000 ALTER TABLE `telefonnummer` DISABLE KEYS */;
INSERT INTO `telefonnummer` VALUES (1,123123123),(1,123245545),(3,888234528),(3,888888888),(3,889999545),(2,999999545);
/*!40000 ALTER TABLE `telefonnummer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tidsskrifter`
--

DROP TABLE IF EXISTS `tidsskrifter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tidsskrifter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titel` int DEFAULT NULL,
  `utgivningsdatum` varchar(255) DEFAULT NULL,
  `lagerplats` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `titelindex` (`titel`),
  KEY `lagerindex` (`lagerplats`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tidsskrifter`
--

LOCK TABLES `tidsskrifter` WRITE;
/*!40000 ALTER TABLE `tidsskrifter` DISABLE KEYS */;
INSERT INTO `tidsskrifter` VALUES (1,1,'2021-03-05',1),(2,2,'2021-02-05',2),(3,3,'2021-02-09',3),(4,1,'2020-09-10',3);
/*!40000 ALTER TABLE `tidsskrifter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `titlar`
--

DROP TABLE IF EXISTS `titlar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `titlar` (
  `id` int NOT NULL,
  `titelts` varchar(255) NOT NULL,
  PRIMARY KEY (`id`,`titelts`),
  CONSTRAINT `titlar_ibfk_1` FOREIGN KEY (`id`) REFERENCES `tidsskrifter` (`titel`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `titlar`
--

LOCK TABLES `titlar` WRITE;
/*!40000 ALTER TABLE `titlar` DISABLE KEYS */;
INSERT INTO `titlar` VALUES (1,'Aftonbladet'),(2,'Dagens Nyheter'),(3,'Expressen');
/*!40000 ALTER TABLE `titlar` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-14 22:22:53
