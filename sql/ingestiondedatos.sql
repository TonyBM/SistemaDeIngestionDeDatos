-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-01-2021 a las 01:50:19
-- Versión del servidor: 10.4.16-MariaDB
-- Versión de PHP: 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ingestiondedatos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `direcciones`
--

CREATE TABLE `direcciones` (
  `id_direccion` int(11) NOT NULL,
  `calle` varchar(50) NOT NULL,
  `numero` varchar(20) NOT NULL,
  `cruzamientos` varchar(50) NOT NULL,
  `colonia` varchar(50) NOT NULL,
  `codigo_postal` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `direcciones`
--

INSERT INTO `direcciones` (`id_direccion`, `calle`, `numero`, `cruzamientos`, `colonia`, `codigo_postal`) VALUES
(2, '50', '284', '45 y 43', 'Amapola 43', '97000'),
(4, '50', '284', '45 y 43', 'Amapola 43', '97000'),
(5, '50', '284', '45 y 43', 'Amapola 43', '97000'),
(6, '50', '284', '45 y 43', 'Amapola 43', '97000'),
(7, '14', '12', '5 y 5a', 'tepito', '97130'),
(8, '14', '12', '5 y 5a', 'tepito', '97130'),
(9, '14', '12', '5 y 5a', 'tepito', '97130'),
(10, '14', '12', '5 y 5a', 'tepito', '97130');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `propiedades`
--

CREATE TABLE `propiedades` (
  `id_propiedad` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `precio` double NOT NULL,
  `banos` int(11) NOT NULL,
  `ubicacion` varchar(10) NOT NULL,
  `id_direccion` int(11) NOT NULL,
  `fecha_publicacion` datetime NOT NULL,
  `num_habitaciones` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `metros_cuadrados` float NOT NULL,
  `fecha_creacion` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `propiedades`
--

INSERT INTO `propiedades` (`id_propiedad`, `nombre`, `precio`, `banos`, `ubicacion`, `id_direccion`, `fecha_publicacion`, `num_habitaciones`, `id_usuario`, `metros_cuadrados`, `fecha_creacion`) VALUES
(4, 'casa amarilla', 4806090, 2, 'sur', 4, '2021-01-11 18:06:48', 2, 1, 60, '2021-01-11 18:06:48'),
(5, 'casa amarilla 2', 4806090, 2, 'sur', 6, '2021-01-11 19:13:31', 2, 1, 60, '2021-01-11 19:13:31'),
(8, 'casa amarilla 3', 1000000, 1, 'norte', 7, '2021-01-23 18:48:32', 2, 1, 300, '2021-01-23 18:48:32'),
(9, 'casa amarilla 4', 1000000, 1, 'sur', 7, '2021-01-23 18:48:32', 2, 1, 350, '2021-01-23 18:48:32');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `regresiones_lineales`
--

CREATE TABLE `regresiones_lineales` (
  `id_regresion` int(11) NOT NULL,
  `q1` varchar(20) NOT NULL,
  `q2` varchar(20) NOT NULL,
  `q3` varchar(20) NOT NULL,
  `intercepto` varchar(20) NOT NULL,
  `fecha_regresion` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `usuario` varchar(30) NOT NULL,
  `password` varchar(16) NOT NULL,
  `secret` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `usuario`, `password`, `secret`) VALUES
(1, 'MarceloTorres', 'extreme89', 'b5217cab-e7b3-4ee3-98b3-9221d3e15dae');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `direcciones`
--
ALTER TABLE `direcciones`
  ADD PRIMARY KEY (`id_direccion`),
  ADD KEY `colonia` (`colonia`);

--
-- Indices de la tabla `propiedades`
--
ALTER TABLE `propiedades`
  ADD PRIMARY KEY (`id_propiedad`),
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD KEY `propiedad-usuario` (`id_usuario`),
  ADD KEY `propiedad-direccion` (`id_direccion`),
  ADD KEY `precio` (`precio`),
  ADD KEY `banos` (`banos`),
  ADD KEY `ubicacion` (`ubicacion`),
  ADD KEY `num_habitaciones` (`num_habitaciones`),
  ADD KEY `metros_cuadrados` (`metros_cuadrados`);

--
-- Indices de la tabla `regresiones_lineales`
--
ALTER TABLE `regresiones_lineales`
  ADD PRIMARY KEY (`id_regresion`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `direcciones`
--
ALTER TABLE `direcciones`
  MODIFY `id_direccion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `propiedades`
--
ALTER TABLE `propiedades`
  MODIFY `id_propiedad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `regresiones_lineales`
--
ALTER TABLE `regresiones_lineales`
  MODIFY `id_regresion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `propiedades`
--
ALTER TABLE `propiedades`
  ADD CONSTRAINT `propiedad-direccion` FOREIGN KEY (`id_direccion`) REFERENCES `direcciones` (`id_direccion`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `propiedad-usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
