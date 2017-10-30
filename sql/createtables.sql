-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.7.18-log - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para desarrollo
CREATE DATABASE IF NOT EXISTS `desarrollo` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `desarrollo`;

-- Volcando estructura para tabla desarrollo.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla desarrollo.hibernate_sequence: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES
	(2),
	(2);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- Volcando estructura para tabla desarrollo.person
CREATE TABLE IF NOT EXISTS `person` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla desarrollo.person: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` (`id`, `email`, `firstName`, `lastName`, `password`, `version`) VALUES
	(1, 'admin@admin.com', 'admin', 'admin', 'admin', 0);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;

-- Volcando estructura para tabla desarrollo.todo
CREATE TABLE IF NOT EXISTS `todo` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `done` bit(1) DEFAULT NULL,
  `dueDate` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `OWNER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdaq2oet5ji4p6qbvqmclcilh8` (`OWNER_ID`),
  CONSTRAINT `FKdaq2oet5ji4p6qbvqmclcilh8` FOREIGN KEY (`OWNER_ID`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla desarrollo.todo: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `todo` DISABLE KEYS */;
/*!40000 ALTER TABLE `todo` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
