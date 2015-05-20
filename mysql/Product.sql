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
-- Table structure for table `Product`
--

CREATE TABLE IF NOT EXISTS `Product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Category_id` int(11) NOT NULL,
  `Name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Photo_path` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'img/imagepackage/various/no_photo_small.png',
  `Description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Price` float NOT NULL,
  `Disabled` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=137 ;

--
-- Dumping data for table `Product`
--

INSERT INTO `Product` (`id`, `Category_id`, `Name`, `Photo_path`, `Description`, `Price`, `Disabled`) VALUES
(100, 100, 'Capuccino', 'img/imagepackage/coffe/capuccino.png', 'Café Capuccino', 1.1, 0),
(101, 100, 'Café con helado', 'img/imagepackage/coffe/cafe-con-helado.png', 'Nuestro clasico café arabigo con una bola de helado, deja que los sabores se fundan', 1.15, 0),
(102, 100, 'Espresso', 'img/imagepackage/coffe/espresso.png', 'Café Espresso, para la gente que le gusta el sabor a café', 1.2, 0),
(103, 100, 'Espresso con leche', 'img/imagepackage/coffe/espresso-con-leche.png', 'Nuestro conocido café espresso pero con un poco de leche, un "machiatto" como dicen en Italia', 1.1, 0),
(104, 101, 'Cono de nata', 'img/imagepackage/dessert/cono-nata.png', 'Un cono de galleta con una bola de helado de nata', 2.2, 0),
(105, 101, 'Helado de nata', 'img/imagepackage/dessert/helado-nata.png', 'El tradicional helado de nata con sirope de chocolate, caramelo o fresa', 2.5, 0),
(106, 101, 'Manzana', 'img/imagepackage/dessert/manzana.png', 'Una manzana pelada y cortada lista para comer, todo sano !', 2, 0),
(107, 101, 'Pera', 'img/imagepackage/dessert/pera.png', 'Una pera pelada y cortada lista para comer, todo sano !', 2, 0),
(108, 101, 'Zanahoria', 'img/imagepackage/dessert/patatas-zanahoria.png', 'Una zanahoria cortada en tiras, como las patatas fritas pero 100% naturales!', 1.8, 0),
(109, 101, 'Tarta Oreo', 'img/imagepackage/dessert/tarta-oreo.png', 'Para los más golosos una tarta hecha de galleta Oreo', 3.1, 0),
(110, 102, 'Agua', 'img/imagepackage/drink/agua.png', 'Botella de agua natural, fria o del tiempo', 1, 0),
(111, 102, 'Cerveza', 'img/imagepackage/drink/cerveza.png', 'Un buen vaso de cerveza helada', 2, 0),
(112, 102, 'Cerveza sin alcohol', 'img/imagepackage/drink/cerveza-sin.png', 'Para los que les gusta la cerveza pero no pueden beber alcohol', 2, 0),
(113, 102, 'Cococola', 'img/imagepackage/drink/cocacola.png', 'Para los amantes de la cocacola', 2.5, 0),
(114, 102, 'Cococola light', 'img/imagepackage/drink/cocacola-light.png', 'Para los amantes de la cocacola que se preocupan por su linea', 2.5, 0),
(115, 102, 'Cococola zero', 'img/imagepackage/drink/cocacola-zero.png', 'Para los amantes de la cocacola ahora sin azucar', 2.5, 0),
(116, 102, 'Fanta de limón', 'img/imagepackage/drink/fanta-limon.png', 'La clasica fanta de con sabor a limón', 2.7, 0),
(117, 102, 'Fanta de naranja', 'img/imagepackage/drink/fanta-naranja.png', 'La clasica fanta de con sabor a naranja', 2.7, 0),
(118, 102, 'Té helado lipton', 'img/imagepackage/drink/lipton-ice.png', 'Un refrescante té helado', 3, 0),
(119, 103, 'Hamburguesa con pollo', 'img/imagepackage/hamburger/hamburgesa-pollo.png', 'Nuestra tradicional hamburguesa con carne de pollo, lechuga y mahonesa', 5, 0),
(120, 103, 'Hamburguesa con pollo bbq', 'img/imagepackage/hamburger/hamburgesa-pollo-bbq.png', 'Nuestra tradicional hamburguesa con carne de pollo, lechuga, mahonesa y nuestra secreta salsa barbacoa', 5.5, 0),
(121, 103, 'Hamburguesa', 'img/imagepackage/hamburger/hamburguesa.png', 'La clásica hamburguesa de carne de ternera', 4, 0),
(122, 103, 'Hamburguesa con queso', 'img/imagepackage/hamburger/hamburguesa-con-queso.png', 'La clásica hamburguesa de carne de ternera con queso cheddar', 4.5, 0),
(123, 103, 'Hamburguesa de pescado', 'img/imagepackage/hamburger/hamburguesa-pescado.png', 'Para los que no les gusta la carne, esta hamburguesa está hecha con pescado', 5, 0),
(124, 103, 'Hamburguesa xxl', 'img/imagepackage/hamburger/hamburguesa-xxl.png', 'Para los amantes de las hamburguesas esta es nuestra obra maestra, con doble de ternera, lechuga, pepino, tomate... de todo!', 6, 0),
(125, 103, 'Roll de pollo', 'img/imagepackage/hamburger/rollo-de-pollo.png', 'Como la hamburgesa de pollo pero enrrollada', 5.5, 0),
(126, 104, 'Ensalada americana', 'img/imagepackage/salad/ensalada-americana.png', 'Ensalada con pollo empanado, tomate, zanahoria y lechuga', 5.5, 0),
(127, 104, 'Ensalada básica', 'img/imagepackage/salad/ensalada-basica.png', 'Ensalada con tomates cherry, maiz, aceitunas, lechuga y zanahoria', 5, 0),
(128, 104, 'Ensalada con pollo', 'img/imagepackage/salad/ensalada-con-pollo.png', 'Ensalada tiras de pollo frito, tomate, queso parmesano, mezcla de lechugas y canónigos', 5.7, 0),
(129, 104, 'Ensalada local', 'img/imagepackage/salad/ensalada-local.png', 'Ensalada con productos de la tierra, lechuga, tomate, aceitunas, maiz... todo de aqui al ladito!', 5.3, 0),
(130, 104, 'Ensalada mediterranea', 'img/imagepackage/salad/ensalada-mediterranea.png', 'Ensalada con aceitunas, huevo cocido, atun, lechuga, zanahoria y tomate', 5.8, 0),
(131, 105, 'Alitas de pollo', 'img/imagepackage/side/allitas-pollo.png', 'Tiernas alas de pollo fritas, ricas!', 3.6, 0),
(132, 105, 'Alitas de pollo bbq', 'img/imagepackage/side/allitas-pollo.png', 'Tiernas alas de pollo fritas con nuestra salasa barbacoa secreta', 4, 0),
(133, 105, 'Patatas fritas', 'img/imagepackage/side/patatas-fritas.png', 'Nuestras clasicas patatas fritas', 3.5, 0),
(134, 105, 'Patatas fritas barbacoa', 'img/imagepackage/side/patatas-fritas.png', 'Nuestras clasicas patatas fritas con salsa barbacoa de la casa', 3.7, 0),
(135, 105, 'Pollo frito', 'img/imagepackage/side/pollo-frito.png', 'Tiernos trozos de pollo frito', 3.3, 0),
(136, 105, 'Pollo frito bbq', 'img/imagepackage/side/pollo-frito.png', 'Tiernos trozos de pollo frito con salsa barbacoa', 3.7, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
