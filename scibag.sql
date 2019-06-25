-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 12, 2017 at 08:32 AM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 7.0.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `scibag`
--

-- --------------------------------------------------------

--
-- Table structure for table `Admins`
--

CREATE TABLE `Admins` (
  `adminID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Admins`
--

INSERT INTO `Admins` (`adminID`) VALUES
(1),
(3);

-- --------------------------------------------------------

--
-- Table structure for table `Authors`
--

CREATE TABLE `Authors` (
  `authorID` int(11) NOT NULL,
  `authorName` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Authors`
--

INSERT INTO `Authors` (`authorID`, `authorName`) VALUES
(1, 'mark'),
(2, 'james');

-- --------------------------------------------------------

--
-- Table structure for table `BookAuthors`
--

CREATE TABLE `BookAuthors` (
  `authorID` int(11) NOT NULL,
  `bookID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `BookCourses`
--

CREATE TABLE `BookCourses` (
  `bookID` int(11) NOT NULL,
  `courseID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `BookCourses`
--

INSERT INTO `BookCourses` (`bookID`, `courseID`) VALUES
(1, 4),
(2, 1),
(3, 2),
(4, 1),
(5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `Books`
--

CREATE TABLE `Books` (
  `bookID` int(11) NOT NULL,
  `bookName` varchar(255) NOT NULL,
  `bookCode` varchar(50) NOT NULL,
  `dateAdded` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(200) DEFAULT NULL,
  `level` tinyint(1) NOT NULL DEFAULT '0',
  `price` int(11) NOT NULL DEFAULT '0',
  `bookIcon` varchar(100) NOT NULL DEFAULT 'default.png'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Books`
--

INSERT INTO `Books` (`bookID`, `bookName`, `bookCode`, `dateAdded`, `description`, `level`, `price`, `bookIcon`) VALUES
(1, 'logic simulation', '', '2017-04-16 15:45:11', 'logic simulation', 0, 5, 'default.png'),
(2, 'math book', 'mthbk', '2017-04-16 15:45:41', 'maths book', 0, 0, 'engmath.png'),
(3, 'physics book', 'phybk', '2017-04-16 15:45:41', 'physics book', 0, 0, 'default.png'),
(4, 'statistics book', '', '2017-04-16 15:49:31', 'statistics book', 0, 0, 'default.png'),
(5, 'algebra book', '', '2017-04-16 15:49:31', 'algebra advanced', 0, 0, 'default.png');

-- --------------------------------------------------------

--
-- Table structure for table `Courses`
--

CREATE TABLE `Courses` (
  `courseID` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `shortCode` varchar(10) NOT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Courses`
--

INSERT INTO `Courses` (`courseID`, `name`, `shortCode`, `description`) VALUES
(1, 'mathematics', 'mth', NULL),
(2, 'physics', 'phy', NULL),
(3, 'chemistry', 'chm', NULL),
(4, 'computer science', 'csc', NULL),
(5, 'biology', 'bio', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Developers`
--

CREATE TABLE `Developers` (
  `developerID` int(11) NOT NULL,
  `absoluteID` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `private_auth` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Developers`
--

INSERT INTO `Developers` (`developerID`, `absoluteID`, `url`, `private_auth`) VALUES
(1, 'dddddddddddddd', 'sssssssssssssssss', 'ssssssssssssssssssssssss'),
(3, 'ssssssssssssssssdddddddd', 'dsd', 'ssssssssssssdd'),
(6, NULL, 'www', NULL),
(7, NULL, 'sss', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `PluginBooks`
--

CREATE TABLE `PluginBooks` (
  `pluginID` int(11) NOT NULL,
  `bookID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PluginBooks`
--

INSERT INTO `PluginBooks` (`pluginID`, `bookID`) VALUES
(26, 2),
(26, 3),
(27, 2),
(27, 4),
(27, 5);

-- --------------------------------------------------------

--
-- Table structure for table `PluginCourses`
--

CREATE TABLE `PluginCourses` (
  `pluginID` int(11) NOT NULL,
  `courseID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PluginCourses`
--

INSERT INTO `PluginCourses` (`pluginID`, `courseID`) VALUES
(25, 1),
(26, 1),
(27, 1);

-- --------------------------------------------------------

--
-- Table structure for table `PluginRelationship`
--

CREATE TABLE `PluginRelationship` (
  `pluginID` int(11) NOT NULL,
  `relatedPluginID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `PluginRelationship`
--

INSERT INTO `PluginRelationship` (`pluginID`, `relatedPluginID`) VALUES
(26, 25),
(27, 25);

-- --------------------------------------------------------

--
-- Table structure for table `Plugins`
--

CREATE TABLE `Plugins` (
  `pluginID` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `absoluteID` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `developerID` int(11) NOT NULL,
  `dateAdded` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pluginHash` varchar(200) DEFAULT NULL,
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `level` tinyint(1) DEFAULT NULL,
  `approverID` int(11) DEFAULT NULL,
  `downloadCount` int(11) NOT NULL DEFAULT '0',
  `lastDownloaded` datetime DEFAULT NULL,
  `pluginIcon` varchar(255) NOT NULL DEFAULT 'default.png'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Plugins`
--

INSERT INTO `Plugins` (`pluginID`, `name`, `absoluteID`, `description`, `developerID`, `dateAdded`, `pluginHash`, `type`, `level`, `approverID`, `downloadCount`, `lastDownloaded`, `pluginIcon`) VALUES
(25, 'Matrices Hard', 'com.scibag.app.math.MatriceSecond', 'simple matrices solver', 3, '2017-06-10 23:25:23', '785709ed95d4d49b9c319b2ffa138fabd972b0ee', 1, 2, NULL, 0, NULL, 'com.scibag.app.math.MatriceSecond.png'),
(26, 'Matrices Y', 'com.scibag.app.math.MatricesY', 'simple matrices solver', 3, '2017-06-10 23:26:54', '0cf472acec5cde67dace80a4178364afd4f399d6', 1, 2, NULL, 0, NULL, 'com.scibag.app.math.MatricesY.png'),
(27, 'Matrices', 'com.scibag.app.math.Matricesz', 'simple matrices solver', 3, '2017-06-10 23:27:10', '75b0ba8e883075165bb3b736afee2c056ee27996', 1, 2, NULL, 0, NULL, 'com.scibag.app.math.Matricesz.png');

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `userID` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `regDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `lastSeen` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gender` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`userID`, `username`, `email`, `password`, `regDate`, `lastSeen`, `gender`) VALUES
(1, 'john', 'dddddddddd', 'qqqqqqqqqqqqqqq', '2017-04-17 01:00:08', '2017-04-17 01:00:08', 'M'),
(3, 'mark', 'dddddddddddd', 'qqqqqqqqqqssqqqqq', '2017-04-17 01:00:37', '2017-04-17 01:00:37', 'M'),
(4, 'ddd', 'dddd', 'dddd', '2017-06-09 18:30:50', '2017-06-09 18:30:50', NULL),
(6, 'www', 'www', 'www', '2017-06-09 20:18:03', '2017-06-09 20:18:03', NULL),
(7, 'sss', 'sss', 'sss', '2017-06-09 20:24:32', '2017-06-09 20:24:32', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Admins`
--
ALTER TABLE `Admins`
  ADD PRIMARY KEY (`adminID`),
  ADD UNIQUE KEY `adminID` (`adminID`);

--
-- Indexes for table `Authors`
--
ALTER TABLE `Authors`
  ADD PRIMARY KEY (`authorID`);

--
-- Indexes for table `BookAuthors`
--
ALTER TABLE `BookAuthors`
  ADD PRIMARY KEY (`authorID`,`bookID`),
  ADD KEY `fk_edd` (`bookID`);

--
-- Indexes for table `BookCourses`
--
ALTER TABLE `BookCourses`
  ADD PRIMARY KEY (`bookID`,`courseID`),
  ADD KEY `fk_sw` (`courseID`);

--
-- Indexes for table `Books`
--
ALTER TABLE `Books`
  ADD PRIMARY KEY (`bookID`);

--
-- Indexes for table `Courses`
--
ALTER TABLE `Courses`
  ADD PRIMARY KEY (`courseID`);

--
-- Indexes for table `Developers`
--
ALTER TABLE `Developers`
  ADD PRIMARY KEY (`developerID`),
  ADD UNIQUE KEY `developerID` (`developerID`);

--
-- Indexes for table `PluginBooks`
--
ALTER TABLE `PluginBooks`
  ADD PRIMARY KEY (`pluginID`,`bookID`),
  ADD KEY `fk_wwdwwe` (`bookID`);

--
-- Indexes for table `PluginCourses`
--
ALTER TABLE `PluginCourses`
  ADD PRIMARY KEY (`pluginID`,`courseID`),
  ADD KEY `fk_plzcs` (`courseID`);

--
-- Indexes for table `PluginRelationship`
--
ALTER TABLE `PluginRelationship`
  ADD PRIMARY KEY (`pluginID`,`relatedPluginID`),
  ADD KEY `fk_rpl` (`relatedPluginID`);

--
-- Indexes for table `Plugins`
--
ALTER TABLE `Plugins`
  ADD PRIMARY KEY (`pluginID`),
  ADD UNIQUE KEY `absoluteID` (`absoluteID`),
  ADD KEY `fk_plactvtor` (`approverID`),
  ADD KEY `fk_pl_dev` (`developerID`);

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`userID`),
  ADD UNIQUE KEY `userID` (`userID`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Authors`
--
ALTER TABLE `Authors`
  MODIFY `authorID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `Books`
--
ALTER TABLE `Books`
  MODIFY `bookID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `Courses`
--
ALTER TABLE `Courses`
  MODIFY `courseID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `Plugins`
--
ALTER TABLE `Plugins`
  MODIFY `pluginID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `Admins`
--
ALTER TABLE `Admins`
  ADD CONSTRAINT `fk_adm_usr` FOREIGN KEY (`adminID`) REFERENCES `Users` (`userID`);

--
-- Constraints for table `BookAuthors`
--
ALTER TABLE `BookAuthors`
  ADD CONSTRAINT `fk_edd` FOREIGN KEY (`bookID`) REFERENCES `Books` (`bookID`),
  ADD CONSTRAINT `fk_sss` FOREIGN KEY (`authorID`) REFERENCES `Authors` (`authorID`);

--
-- Constraints for table `BookCourses`
--
ALTER TABLE `BookCourses`
  ADD CONSTRAINT `fk_sw` FOREIGN KEY (`courseID`) REFERENCES `Courses` (`courseID`),
  ADD CONSTRAINT `fk_wwe` FOREIGN KEY (`bookID`) REFERENCES `Books` (`bookID`);

--
-- Constraints for table `Developers`
--
ALTER TABLE `Developers`
  ADD CONSTRAINT `fk_dev_usr` FOREIGN KEY (`developerID`) REFERENCES `Users` (`userID`);

--
-- Constraints for table `PluginBooks`
--
ALTER TABLE `PluginBooks`
  ADD CONSTRAINT `fk_srrww` FOREIGN KEY (`pluginID`) REFERENCES `Plugins` (`pluginID`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_wwdwwe` FOREIGN KEY (`bookID`) REFERENCES `Books` (`bookID`) ON DELETE CASCADE;

--
-- Constraints for table `PluginCourses`
--
ALTER TABLE `PluginCourses`
  ADD CONSTRAINT `fk_plgcs` FOREIGN KEY (`pluginID`) REFERENCES `Plugins` (`pluginID`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_plzcs` FOREIGN KEY (`courseID`) REFERENCES `Courses` (`courseID`) ON DELETE CASCADE;

--
-- Constraints for table `PluginRelationship`
--
ALTER TABLE `PluginRelationship`
  ADD CONSTRAINT `fk_pl` FOREIGN KEY (`pluginID`) REFERENCES `Plugins` (`pluginID`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_rpl` FOREIGN KEY (`relatedPluginID`) REFERENCES `Plugins` (`pluginID`) ON DELETE CASCADE;

--
-- Constraints for table `Plugins`
--
ALTER TABLE `Plugins`
  ADD CONSTRAINT `fk_pl_dev` FOREIGN KEY (`developerID`) REFERENCES `Developers` (`developerID`),
  ADD CONSTRAINT `fk_plactvtor` FOREIGN KEY (`approverID`) REFERENCES `Admins` (`adminID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
