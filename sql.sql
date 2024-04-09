-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.32-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for java5_asm
DROP DATABASE IF EXISTS `java5_asm`;
CREATE DATABASE IF NOT EXISTS `java5_asm` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `java5_asm`;

-- Dumping structure for table java5_asm.authorities
DROP TABLE IF EXISTS `authorities`;
CREATE TABLE IF NOT EXISTS `authorities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2uf74smucdwf9qal2n67m2343` (`username`,`authority`),
  UNIQUE KEY `UK_baahryprcge2u172egph1qwur` (`username`),
  CONSTRAINT `FKhjuy9y4fd8v5m3klig05ktofg` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table java5_asm.authorities: ~4 rows (approximately)
REPLACE INTO `authorities` (`id`, `authority`, `username`) VALUES
	(2, 'ROLE_USER', 'daoducdung2000@gmail.com'),
	(1, 'ROLE_ADMIN', 'dungddps26188@fpt.edu.vn'),
	(5, 'ROLE_USER', 'hohoho@gmail.com'),
	(4, 'ROLE_USER', 'zekoxpop@gmail.com');

-- Dumping structure for table java5_asm.orders
DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `orders_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `date_add` date DEFAULT NULL,
  `status_pay` bit(1) DEFAULT NULL,
  `status_transport` bit(1) DEFAULT NULL,
  `username` bigint(20) DEFAULT NULL,
  `status_orders` bit(1) DEFAULT NULL,
  PRIMARY KEY (`orders_id`),
  KEY `FKs9ytu43s1gnlpp1cr9n7iaw6h` (`username`),
  CONSTRAINT `FKs9ytu43s1gnlpp1cr9n7iaw6h` FOREIGN KEY (`username`) REFERENCES `user_info` (`user_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table java5_asm.orders: ~4 rows (approximately)
REPLACE INTO `orders` (`orders_id`, `address`, `date_add`, `status_pay`, `status_transport`, `username`, `status_orders`) VALUES
	(1, '14/12 đường gì đó, phường gì đó, thành phố gì đó', '2024-02-11', b'0', b'0', 1, b'1'),
	(2, '14/12 đường gì đó, phường gì đó, thành phố gì đó', '2024-02-12', b'1', b'1', 2, b'1'),
	(3, '14/12 đường gì đó, phường gì đó, thành phố gì đó', '2024-02-12', b'0', b'0', 1, b'0'),
	(7, '', NULL, b'1', b'1', 5, b'1'),
	(9, '', NULL, b'1', b'1', 6, b'1');

-- Dumping structure for table java5_asm.order_details
DROP TABLE IF EXISTS `order_details`;
CREATE TABLE IF NOT EXISTS `order_details` (
  `order_details_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) DEFAULT NULL,
  `orders_id` bigint(20) DEFAULT NULL,
  `products_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`order_details_id`),
  KEY `FKint27bl8qoql1ksaw8ik7cq95` (`orders_id`),
  KEY `FK2h8sqod2uhp3381vnr8f9unyj` (`products_id`),
  CONSTRAINT `FK2h8sqod2uhp3381vnr8f9unyj` FOREIGN KEY (`products_id`) REFERENCES `products` (`products_id`),
  CONSTRAINT `FKint27bl8qoql1ksaw8ik7cq95` FOREIGN KEY (`orders_id`) REFERENCES `orders` (`orders_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table java5_asm.order_details: ~8 rows (approximately)
REPLACE INTO `order_details` (`order_details_id`, `quantity`, `orders_id`, `products_id`) VALUES
	(4, 2, 1, 26),
	(10, 1, 1, 8),
	(17, 1, 9, 3),
	(18, 1, 9, 8),
	(24, 1, 1, 12),
	(27, 1, 2, 6),
	(31, 1, 2, 4),
	(32, 1, 2, 12);

-- Dumping structure for table java5_asm.products
DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `products_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `sale` int(11) DEFAULT NULL,
  `season` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`products_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table java5_asm.products: ~33 rows (approximately)
REPLACE INTO `products` (`products_id`, `category`, `country`, `image`, `name`, `price`, `sale`, `season`, `status`) VALUES
	(1, 'Dessert', 'Asia', NULL, 'Quần', 1, 0, 'Spring', NULL),
	(2, 'Dessert', 'Europe', 'high-angle-delicious-pie-table.jpg', 'Dreamy biscuits', 2, 0, 'Spring', b'1'),
	(3, 'Dessert', 'Asia', 'thai-dessert-called-bualoy-balls-dippers-with-hot-coconut-milk-pandan-leaves-increase-deliciousness.jpg', 'Sweet Snow White', 3, 0, 'Summer', b'1'),
	(4, 'Dessert', 'Europe', 'top-view-little-delicious-cake-with-cream-fruits-inside-white-plate-along-with-fresh-fruits-grey-blue-desk-fruit-cake-biscuit.jpg', 'Strawberry kiss', 4, 0, 'Summer', b'1'),
	(5, 'Dessert', 'Asia', NULL, NULL, 5, 0, 'Autumn', b'1'),
	(6, 'Dessert', 'Europe', 'piece-honey-cake-plate-vase-with-daffodils.jpg', 'Dream Honey Cake', 6, 0, 'Autumn', b'1'),
	(7, 'Dessert', 'Asia', 'cottagecore-food-inspiration-still-life.jpg', 'Dance of strawberries', 7, 0, 'Winter', b'1'),
	(8, 'Dessert', 'Europe', 'side-view-apple-cake-with-ice-cream-decorated-with-fresh-strawberry-sauce-plate-wood.jpg', 'Tropical cream cake', 8, 0, 'Winter', b'1'),
	(10, 'Drinks_and_Beverages', 'Asia', 'tea-set.jpg', 'Apricots tea', 9, 0, 'Spring', b'1'),
	(11, 'Drinks_and_Beverages', 'Europe', 'delicious-gulas-dish-assortment', 'Enchanted Strawberry Elixir', 10, 0, 'Spring', b'1'),
	(12, 'Drinks_and_Beverages', 'Asia', 'delicious-blue-matcha-recipe-still-life.jpg', 'Paradise Elixir', 11, 0, 'Summer', b'1'),
	(13, 'Drinks_and_Beverages', 'Europe', 'ice-bucket-with-cucumber-green.jpg', 'Lemon Chiffon Delight', 12, 0, 'Summer', b'1'),
	(14, 'Drinks_and_Beverages', 'Asia', 'vertical-view-various-biscuits-cup-tea-flowers-chocolate-bars-mixed-color-table.jpg', 'Blissful Botanical Brew', 13, 0, 'Autumn', b'1'),
	(15, 'Drinks_and_Beverages', 'Europe', 'table-arrangement-with-dragon-fruit-snacks-outdoors.jpg', 'Dragon\'s Kiss', 14, 0, 'Autumn', b'1'),
	(16, 'Drinks_and_Beverages', 'Asia', 'sake-japanese-beverage-still-life.jpg', 'Elixir of Life', 15, 0, 'Winter', b'1'),
	(17, 'Drinks_and_Beverages', 'Europe', NULL, NULL, 16, 0, 'Winter', b'1'),
	(18, 'Main_course', 'Asia', 'pagliatelle-with-meat-jte-table.jpg', 'Golden Threads of Beijing', 17, 0, 'Spring', b'1'),
	(19, 'Main_course', 'Europe', 'fresh-salad-table-top-view.jpg', 'Citrusy Grouper Rice', 18, 0, 'Spring', b'1'),
	(20, 'Main_course', 'Asia', 'noodles-with-beef-vegetables-black-table.jpg', 'Celestial Harmony Stir-fry', 19, 0, 'Summer', b'1'),
	(21, 'Main_course', 'Europe', 'baked-salmon-with-vegetables.jpg', 'Pan-Seared Tuna Medallions', 20, 0, 'Summer', b'1'),
	(22, 'Main_course', 'Asia', 'dry-thin-noodles-with-stewed-pork-pork-ball.jpg', 'Noodlicious Beef Ball Jar', 21, 0, 'Autumn', b'1'),
	(23, 'Main_course', 'Europe', 'delicious-food-table.jpg', 'Rice of the Phoenix', 22, 0, 'Autumn', b'1'),
	(24, 'Main_course', 'Asia', 'top-view-female-hands-putting-plate-with-roasted-chicken-with-grilled-tomato-fresh-herbs-sauce-table.jpg', 'Dragon\'s Breath Chicken', 23, 0, 'Winter', b'1'),
	(25, 'Main_course', 'Europe', 'side-view-grilled-beef-medallion-with-sauce-vegetables-white-plate-table.jpg', 'Bistecca alla Fiorentina', 24, 0, 'Winter', b'1'),
	(26, 'Starter', 'Asia', 'traditional-seaweed-vegetable-salad.jpg', 'Oriental Lettuce Stir-fry', 25, 0, 'Spring', b'1'),
	(27, 'Starter', 'Europe', 'pancake-breakfast.jpg', 'Fetaphoria Salad', 26, 0, 'Spring', b'1'),
	(28, 'Starter', 'Asia', 'fresh-shrimp-soaked-fish-sauce-thai-food.jpg', 'Spicy Thai Shrimp', 27, 0, 'Summer', b'1'),
	(29, 'Starter', 'Europe', 'salad-with-walnuts-tomatoes-oranges-shrimps.jpg', 'Pompeii Shrimp', 28, 0, 'Summer', b'1'),
	(30, 'Starter', 'Asia', 'mixed-salad-topped-with-lime-slices.jpg', 'Limelight Salad', 29, 0, 'Autumn', b'1'),
	(31, 'Starter', 'Europe', 'mixed-bell-pepper-salad-side-view.jpg', 'Amuse-bouche', 30, 0, 'Autumn', b'1'),
	(32, 'Starter', 'Asia', 'fresh-shrimp-rolls-with-salad-sauce.jpg', 'Translucent Rice Paper Rolls', 31, 0, 'Winter', b'1'),
	(33, 'Starter', 'Europe', 'high-angle-bowl-with-cream-soup.jpg', 'Liquid Sunshine', 32, 0, 'Winter', b'1');

-- Dumping structure for table java5_asm.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table java5_asm.users: ~5 rows (approximately)
REPLACE INTO `users` (`username`, `enabled`, `password`) VALUES
	('daoducdung2000@gmail.com', b'1', '{noop}113'),
	('dungddps26188@fpt.edu.vn', b'1', '{noop}123'),
	('hohoho@gmail.com', b'1', '{noop}123'),
	('zekoxpop@gmail.com', b'1', '{noop}123');

-- Dumping structure for table java5_asm.user_info
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE IF NOT EXISTS `user_info` (
  `user_info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `birth_day` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_info_id`),
  UNIQUE KEY `UK_f2ksd6h8hsjtd57ipfq9myr64` (`username`),
  CONSTRAINT `FKag3spj31assp7ewttptxceds4` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table java5_asm.user_info: ~5 rows (approximately)
REPLACE INTO `user_info` (`user_info_id`, `username`, `address`, `avatar`, `birth_day`, `name`, `phone`) VALUES
	(1, 'daoducdung2000@gmail.com', '14/12 đường gì đó, phường gì đó, thành phố gì đó không biết nữa', NULL, '1212-12-12', 'DungVipPro', '0906786888'),
	(2, 'dungddps26188@fpt.edu.vn', '14/12 đường gì đó, phường gì đó, thành phố gì đó', NULL, '2001-03-14', 'Admin', '000000000'),
	(5, 'zekoxpop@gmail.com', NULL, NULL, '1111-11-11', 'Đào Đức Dũng', '0901411018'),
	(6, 'hohoho@gmail.com', NULL, NULL, '1111-11-11', 'Quần', '0901411018');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
