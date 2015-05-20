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
-- Table structure for table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Surname` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `ID_number` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Photo_path` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'img/imagepackage/various/no_user.png',
  `Discount` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=112 ;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`id`, `Name`, `Surname`, `Email`, `Password`, `ID_number`, `Photo_path`, `Discount`) VALUES
(1, 'Superuser', 'hbvalencia', 'info@healthyburgervalencia.com', 'superuser', '00000o', 'img/imagepackage/various/no_user.png', 0),
(2, 'default', 'user', 'defaultuser@healthybuerguervalencia.com', 'default', '00000a', 'img/imagepackage/various/no_user.png', 0),
(101, 'Jose', 'Ruiz Ruano', 'jose@todosa.es', 'jose', '24897563l', 'img/imagepackage/various/user01.png', 0),
(102, 'Luis ', 'Benitez Gomez', 'lubego@enfrico.com', 'luis', '6984789t', 'img/imagepackage/various/no_user.png', 0),
(103, 'Ramon', 'Lopez Escribá', 'ramon@loes.es', 'ramon', '78599963p', 'img/imagepackage/various/no_user.png', 0),
(104, 'Salva', 'Garcia Garcia', 'email@corre.com', 'salva', '63588894o', 'img/imagepackage/various/user02.png', 0),
(105, 'Diego', 'Lucano Perta', 'lucano@eresta.es', 'diego', '74899632j', 'img/imagepackage/various/user03.png', 0),
(106, 'Javier', 'Diez Sanchez', 'jadi@tribun.com', 'javi', '56222248h', 'img/imagepackage/various/no_user.png', 0),
(107, 'Lucho', 'Ruano Hernandez', 'RuanoH@bribado.com', 'lucho', '236559871q', 'img/imagepackage/various/no_user.png', 0),
(108, 'Francisco Javier', 'Lerca Del pino', 'FraJa@claro.es', 'fran', '18633357d', 'img/imagepackage/various/no_user.png', 0),
(109, 'Lucas', 'Garcia Temple', 'lugar@besves.com', 'lucas', '47488562h', 'img/imagepackage/various/user04.png', 0),
(110, 'Ernesto', 'Brega Lopez', 'Ernesto@tritopa.com', 'ernesto', '86355570c', 'img/imagepackage/various/no_user.png', 0),
(111, 'Javier', 'Maroto', 'jama@to.es', 'javi', '12312412r', 'img/imagepackage/various/no_user.png', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
