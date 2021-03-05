-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 05, 2021 at 10:41 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `carservice_7swi1`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `id` int(11) NOT NULL,
  `id_state` int(11) NOT NULL,
  `city` varchar(30) COLLATE utf8_czech_ci NOT NULL,
  `street` varchar(20) COLLATE utf8_czech_ci NOT NULL,
  `street_number` varchar(15) COLLATE utf8_czech_ci NOT NULL,
  `post_code` varchar(15) COLLATE utf8_czech_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`id`, `id_state`, `city`, `street`, `street_number`, `post_code`) VALUES
(1, 1, 'Ostrava', 'Modrá', '154', '74128');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `id_address` int(11) NOT NULL,
  `firstName` varchar(15) COLLATE utf8_czech_ci NOT NULL,
  `surname` varchar(15) COLLATE utf8_czech_ci NOT NULL,
  `telephoneNumber` varchar(15) COLLATE utf8_czech_ci NOT NULL,
  `email` varchar(40) COLLATE utf8_czech_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `id_address`, `firstName`, `surname`, `telephoneNumber`, `email`) VALUES
(1, 1, 'Petr', 'Červený', '741589647', 'petr@sez.cz'),
(2, 1, 'Tomáš', 'Velký', '5525', 'fgfg@sez.cz'),
(3, 1, 'Pepa', 'Zelený', '5525', 'fgfg@sez.cz'),
(4, 1, 'Pepa', 'Zelený', '5525', 'fgfg@sez.cz'),
(5, 1, 'Pepa', 'Zelený', '5525', 'fgfg@sez.cz'),
(6, 1, 'Pepa', 'Zelený', '5525', 'fgfg@sez.cz'),
(7, 1, 'Tomáš', 'Velký', '879254859', 'sfsfsdf'),
(19, 1, 'test', 'test', '779456123', 'test');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `id_customer` int(11) NOT NULL,
  `id_time` int(11) NOT NULL,
  `registration_plate` varchar(8) COLLATE utf8_czech_ci NOT NULL,
  `type_of_car` varchar(30) COLLATE utf8_czech_ci NOT NULL,
  `date_of_fulfillment` date NOT NULL,
  `year_of_production` year(4) NOT NULL,
  `car_service` tinyint(1) NOT NULL DEFAULT 0,
  `tire_service` tinyint(1) NOT NULL DEFAULT 0,
  `other_service` tinyint(1) NOT NULL DEFAULT 0,
  `note` text COLLATE utf8_czech_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `id_customer`, `id_time`, `registration_plate`, `type_of_car`, `date_of_fulfillment`, `year_of_production`, `car_service`, `tire_service`, `other_service`, `note`) VALUES
(10, 1, 2, '98526', 'dfsf', '2021-03-23', 1998, 1, 0, 0, 'Test');

-- --------------------------------------------------------

--
-- Table structure for table `registration_times`
--

CREATE TABLE `registration_times` (
  `id` int(11) NOT NULL,
  `time_of_registration` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_czech_ci;

--
-- Dumping data for table `registration_times`
--

INSERT INTO `registration_times` (`id`, `time_of_registration`) VALUES
(2, '08:00:00'),
(3, '09:00:00'),
(4, '10:00:00'),
(5, '11:00:00'),
(6, '12:00:00'),
(7, '13:00:00'),
(8, '14:00:00'),
(9, '15:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `states`
--

CREATE TABLE `states` (
  `id` int(11) NOT NULL,
  `state_short` varchar(3) COLLATE utf8mb4_czech_ci NOT NULL,
  `state_full` varchar(50) COLLATE utf8mb4_czech_ci NOT NULL,
  `telephone_code` varchar(5) COLLATE utf8mb4_czech_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_czech_ci;

--
-- Dumping data for table `states`
--

INSERT INTO `states` (`id`, `state_short`, `state_full`, `telephone_code`) VALUES
(1, 'CZ', 'Czech Republic', '+420'),
(2, 'SK', 'Slovak Republic', '+421');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `id_state` (`id_state`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `customer_address_id_fk` (`id_address`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `orders_customer_id_fk` (`id_customer`),
  ADD KEY `id_time` (`id_time`);

--
-- Indexes for table `registration_times`
--
ALTER TABLE `registration_times`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `states`
--
ALTER TABLE `states`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `address`
--
ALTER TABLE `address`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `registration_times`
--
ALTER TABLE `registration_times`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `states`
--
ALTER TABLE `states`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `address_ibfk_1` FOREIGN KEY (`id_state`) REFERENCES `states` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_address_id_fk` FOREIGN KEY (`id_address`) REFERENCES `address` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_customer_id_fk` FOREIGN KEY (`id_customer`) REFERENCES `customer` (`id`),
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`id_time`) REFERENCES `registration_times` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
