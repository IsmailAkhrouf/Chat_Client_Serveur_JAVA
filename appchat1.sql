-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Dim 04 Avril 2021 à 15:19
-- Version du serveur :  5.6.15-log
-- Version de PHP :  5.4.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `appchat1`
--

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `ID` varchar(255) NOT NULL,
  `Nom` varchar(255) NOT NULL,
  `Status` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `client`
--

INSERT INTO `client` (`ID`, `Nom`, `Status`) VALUES
('0611223344', 'ISMAIL', 'deconnecte'),
('0610203040', 'SOUFAIN', 'deconnecte'),
('0620406080', 'NOUHAILA', 'deconnecte'),
('0690807060', 'MOHSEN', 'deconnecte'),
('0625354555', 'NAJWA', 'deconnecte'),
('0605101520', 'CHAIMAE', 'deconnecte'),
('0612233445', 'SALIM', 'deconnecte'),
('0656789078', 'FATIMA', 'deconnecte'),
('0697554433', 'AHMED', 'deconnecte'),
('0612456789', 'SALIMA', 'deconnecte'),
('0634455667', 'RACHID', 'deconnecte');

-- --------------------------------------------------------

--
-- Structure de la table `contact`
--

CREATE TABLE IF NOT EXISTS `contact` (
  `IDClient` varchar(255) NOT NULL,
  `IDContact` varchar(255) NOT NULL,
  `NomContact` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `contact`
--

INSERT INTO `contact` (`IDClient`, `IDContact`, `NomContact`) VALUES
('0611223344', '0620406080', 'NOUHAILA'),
('0611223344', '0605101520', 'CHAIMAE'),
('0611223344', '0610203040', 'SOUFAIN'),
('0611223344', '0690807060', 'MOHSEN'),
('0620406080', '0611223344', 'ISMAIL'),
('0620406080', '0610203040', 'SOUFAIN'),
('0610203040', '0620406080', 'NOUHAILA'),
('0610203040', '0611223344', 'ISMAIL'),
('0690807060', '0605101520', 'CHAIMAE'),
('0690807060', '0625354555', 'NAJWA'),
('0690807060', '0610203040', 'SOUFAIN'),
('0690807060', '0611223344', 'ISMAIL'),
('0625354555', '0690807060', 'MOHSEN'),
('0625354555', '0605101520', 'CHAIMAE'),
('0625354555', '0611223344', 'ISMAIL'),
('0605101520', '0611223344', 'ISMAIL'),
('0611223344', '0625354555', 'NAJWA'),
('0612456789', '0611223344', 'ISMAIL'),
('0634455667', '0611223344', 'ISMAIL');

-- --------------------------------------------------------

--
-- Structure de la table `conversation`
--

CREATE TABLE IF NOT EXISTS `conversation` (
  `IDSend` varchar(255) NOT NULL,
  `IDReceve` varchar(255) NOT NULL,
  `Message` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `listeconnecte`
--

CREATE TABLE IF NOT EXISTS `listeconnecte` (
  `IDConnect` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
