-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.18-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping data for table car-catalog.brands: ~7 rows (approximately)
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` (`brand_id`, `brand_name`) VALUES
	(1, 'AUDI'),
	(2, 'BMW'),
	(7, 'FERRARI'),
	(6, 'HONDA'),
	(4, 'MERCEDES-BENZ'),
	(5, 'PEUGEOT'),
	(3, 'VW');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;

-- Dumping data for table car-catalog.cars: ~5 rows (approximately)
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` (`car_id`, `vin_number`, `price`, `reg_date`, `remarks`, `models_model_id`, `fuel_types_fuel_type_id`, `transmissions_transmission_id`, `users_user_id`) VALUES
	(1, '12345678998745632', 25000, '2023-08-15', 'note1', 1, 1, 1, 1),
	(2, '95184795126357321', 20005, '2023-08-14', 'note2', 2, 2, 1, 1),
	(3, '98653212457896532', 18000, '2023-08-01', 'note3', 3, 3, 3, 1),
	(4, '33111111111111226', 25000, '2023-08-14', 'note4', 1, 2, 1, 1),
	(7, '66333333331111145', 22223, '2023-08-07', 'note7', 2, 3, 3, 1);
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;

-- Dumping data for table car-catalog.fuel_types: ~4 rows (approximately)
/*!40000 ALTER TABLE `fuel_types` DISABLE KEYS */;
INSERT INTO `fuel_types` (`fuel_type_id`, `fuel_name`) VALUES
	(2, 'DIESEL'),
	(4, 'ELECTRIC'),
	(3, 'GAS'),
	(1, 'GASOLINE');
/*!40000 ALTER TABLE `fuel_types` ENABLE KEYS */;

-- Dumping data for table car-catalog.models: ~11 rows (approximately)
/*!40000 ALTER TABLE `models` DISABLE KEYS */;
INSERT INTO `models` (`model_id`, `model_name`, `brands_brand_id`) VALUES
	(1, 'Q7', 1),
	(2, 'X4', 2),
	(3, 'Q5', 3),
	(6, 'Civic', 6),
	(7, '307', 5),
	(8, '407', 5),
	(9, 'M1', 2),
	(10, 'X5', 2),
	(11, 'G CLASS', 4),
	(12, '458 ITALIA', 7),
	(13, 'A6', 1);
/*!40000 ALTER TABLE `models` ENABLE KEYS */;

-- Dumping data for table car-catalog.transmissions: ~2 rows (approximately)
/*!40000 ALTER TABLE `transmissions` DISABLE KEYS */;
INSERT INTO `transmissions` (`transmission_id`, `transmission_name`) VALUES
	(1, 'AUTOMATIC'),
	(3, 'HYBRID'),
	(2, 'MANUAL');
/*!40000 ALTER TABLE `transmissions` ENABLE KEYS */;

-- Dumping data for table car-catalog.users: ~1 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `username`, `password`, `first_name`, `last_name`) VALUES
	(1, 'pesho99', 'pass', 'Petar', 'Petrov');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
