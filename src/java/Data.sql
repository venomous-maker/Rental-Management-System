/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  venom
 * Created: Mar 17, 2024
 */

-- phpMyAdmin SQL Dump
-- version 5.2.1deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 17, 2024 at 04:19 PM
-- Server version: 10.11.6-MariaDB-2
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `RentalProject`
--

-- --------------------------------------------------------

--
-- Table structure for table `Agent`
--

CREATE TABLE `Agent` (
  `Agent_id` int(11) NOT NULL,
  `Agent_name` varchar(50) DEFAULT NULL,
  `Agent_phone` varchar(12) DEFAULT NULL,
  `Agent_mobile` varchar(12) DEFAULT NULL,
  `Agent_Email` varchar(50) DEFAULT NULL,
  `Agent_discription` varchar(500) DEFAULT NULL,
  `Agent_address` varchar(500) DEFAULT NULL,
  `Agent_pic` varchar(30) DEFAULT NULL,
  `Agent_states` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Bill`
--

CREATE TABLE `Bill` (
  `Bill_id` varchar(255) NOT NULL,
  `Month_start_date` date DEFAULT NULL,
  `Month_end_date` date DEFAULT NULL,
  `Bill_Sub_Date` date DEFAULT NULL,
  `House_id` int(11) DEFAULT NULL,
  `Bill_Amount` float DEFAULT NULL,
  `states` varchar(255) DEFAULT NULL,
  `Tenants_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `House`
--

CREATE TABLE `House` (
  `House_id` int(11) NOT NULL,
  `House_name` varchar(100) DEFAULT NULL,
  `Rent_Type` varchar(50) DEFAULT NULL,
  `House_pries` int(11) DEFAULT NULL,
  `House_bed` varchar(20) DEFAULT NULL,
  `House_bath` varchar(20) DEFAULT NULL,
  `House_garage` varchar(20) DEFAULT NULL,
  `House_district` varchar(50) DEFAULT NULL,
  `House_discription` varchar(1000) DEFAULT NULL,
  `House_addr` varchar(550) DEFAULT NULL,
  `House_Amenities` varchar(300) DEFAULT NULL,
  `House_type` varchar(255) DEFAULT NULL,
  `Date` varchar(255) DEFAULT NULL,
  `HouseOwner_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `HouseOwner`
--

CREATE TABLE `HouseOwner` (
  `Owner_id` int(11) NOT NULL,
  `Owner_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `HousePhotos`
--

CREATE TABLE `HousePhotos` (
  `HousePhotos_id` int(11) NOT NULL,
  `HousePhotos_name` varchar(30) DEFAULT NULL,
  `House_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Payment`
--

CREATE TABLE `Payment` (
  `receipt_Id` varchar(255) NOT NULL,
  `House_ID` int(11) DEFAULT NULL,
  `Payment_Type` varchar(255) DEFAULT NULL,
  `Amount` float DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `User_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Tenants`
--

CREATE TABLE `Tenants` (
  `Tenants_id` int(11) NOT NULL,
  `Tenants_Name` varchar(30) DEFAULT NULL,
  `User_id` int(11) DEFAULT NULL,
  `Tenants_states` varchar(30) DEFAULT NULL,
  `Tenants_BOD` date DEFAULT NULL,
  `Tenants_reason` varchar(30) DEFAULT NULL,
  `Tenants_type` varchar(30) DEFAULT NULL,
  `Tenancy_Month` int(11) DEFAULT NULL,
  `Tenants_gender` varchar(7) DEFAULT NULL,
  `Tenant_Start_date` date DEFAULT NULL,
  `Tenant_End_date` date DEFAULT NULL,
  `Check_By_Admin` tinyint(1) DEFAULT NULL,
  `House_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `User_id` int(11) NOT NULL,
  `User_name` varchar(100) DEFAULT NULL,
  `User_email` varchar(150) DEFAULT NULL,
  `User_phone` varchar(12) DEFAULT NULL,
  `User_mobile` varchar(12) DEFAULT NULL,
  `User_password` varchar(20) DEFAULT NULL,
  `User_district` varchar(40) DEFAULT NULL,
  `User_tal` varchar(40) DEFAULT NULL,
  `User_addr` varchar(500) DEFAULT NULL,
  `User_pic` varchar(50) DEFAULT NULL,
  `User_type` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`User_id`, `User_name`, `User_email`, `User_phone`, `User_mobile`, `User_password`, `User_district`, `User_tal`, `User_addr`, `User_pic`, `User_type`) VALUES
(1, 'Morgan Okumu', 'morganokumu8@gmail.com', '1234567891', '1234567891', 'MTIzNDU=', 'Kisii', 'Tal', 'can we take it home', 'user-1742823140.jpg', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Agent`
--
ALTER TABLE `Agent`
  ADD PRIMARY KEY (`Agent_id`);

--
-- Indexes for table `Bill`
--
ALTER TABLE `Bill`
  ADD PRIMARY KEY (`Bill_id`),
  ADD KEY `House_id` (`House_id`),
  ADD KEY `Tenants_id` (`Tenants_id`);

--
-- Indexes for table `House`
--
ALTER TABLE `House`
  ADD PRIMARY KEY (`House_id`),
  ADD KEY `HouseOwner_id` (`HouseOwner_id`);

--
-- Indexes for table `HouseOwner`
--
ALTER TABLE `HouseOwner`
  ADD PRIMARY KEY (`Owner_id`);

--
-- Indexes for table `HousePhotos`
--
ALTER TABLE `HousePhotos`
  ADD PRIMARY KEY (`HousePhotos_id`),
  ADD KEY `House_id` (`House_id`);

--
-- Indexes for table `Payment`
--
ALTER TABLE `Payment`
  ADD PRIMARY KEY (`receipt_Id`),
  ADD KEY `House_ID` (`House_ID`),
  ADD KEY `User_id` (`User_id`);

--
-- Indexes for table `Tenants`
--
ALTER TABLE `Tenants`
  ADD PRIMARY KEY (`Tenants_id`),
  ADD KEY `House_id` (`House_id`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`User_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Agent`
--
ALTER TABLE `Agent`
  MODIFY `Agent_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `House`
--
ALTER TABLE `House`
  MODIFY `House_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `HouseOwner`
--
ALTER TABLE `HouseOwner`
  MODIFY `Owner_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `HousePhotos`
--
ALTER TABLE `HousePhotos`
  MODIFY `HousePhotos_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Tenants`
--
ALTER TABLE `Tenants`
  MODIFY `Tenants_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
  MODIFY `User_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Bill`
--
ALTER TABLE `Bill`
  ADD CONSTRAINT `Bill_ibfk_1` FOREIGN KEY (`House_id`) REFERENCES `House` (`House_id`),
  ADD CONSTRAINT `Bill_ibfk_2` FOREIGN KEY (`Tenants_id`) REFERENCES `Tenants` (`Tenants_id`);

--
-- Constraints for table `House`
--
ALTER TABLE `House`
  ADD CONSTRAINT `House_ibfk_1` FOREIGN KEY (`HouseOwner_id`) REFERENCES `HouseOwner` (`Owner_id`);

--
-- Constraints for table `HousePhotos`
--
ALTER TABLE `HousePhotos`
  ADD CONSTRAINT `HousePhotos_ibfk_1` FOREIGN KEY (`House_id`) REFERENCES `House` (`House_id`);

--
-- Constraints for table `Payment`
--
ALTER TABLE `Payment`
  ADD CONSTRAINT `Payment_ibfk_1` FOREIGN KEY (`House_ID`) REFERENCES `House` (`House_id`),
  ADD CONSTRAINT `Payment_ibfk_2` FOREIGN KEY (`User_id`) REFERENCES `User` (`User_id`);

--
-- Constraints for table `Tenants`
--
ALTER TABLE `Tenants`
  ADD CONSTRAINT `Tenants_ibfk_1` FOREIGN KEY (`House_id`) REFERENCES `House` (`House_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
