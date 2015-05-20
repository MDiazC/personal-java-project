-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost:80
-- Generation Time: Mar 22, 2015 at 06:01 PM
-- Server version: 5.5.41
-- PHP Version: 5.3.10-1ubuntu3.16

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `Java`
--

-- --------------------------------------------------------

--
-- Table structure for table `Category`
--

CREATE TABLE IF NOT EXISTS `Category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Photo_path` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'img/imagepackage/various/no_photo_small.png',
  `Disabled` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=106 ;

--
-- Dumping data for table `Category`
--

INSERT INTO `Category` (`id`, `Name`, `Photo_path`, `Disabled`) VALUES
(100, 'Café', 'img/imagepackage/coffe/generic_cafe.png', 0),
(101, 'Postres', 'img/imagepackage/dessert/generic-desserts.png', 0),
(102, 'Bebidas', 'img/imagepackage/drink/generic-drink.png', 0),
(103, 'Hamburguesa', 'img/imagepackage/hamburger/generic-hamburguer.png', 0),
(104, 'Ensalada', 'img/imagepackage/salad/generic-salad.png', 0),
(105, 'Acompañamientos', 'img/imagepackage/side/generic-side.png', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
