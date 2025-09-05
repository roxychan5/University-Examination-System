-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 02, 2025 at 03:50 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `unisystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `cgpa`
--

CREATE TABLE `cgpa` (
  `StudentID` varchar(100) NOT NULL,
  `CGPA` double(3,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cgpa`
--

INSERT INTO `cgpa` (`StudentID`, `CGPA`) VALUES
('24WMD03453', 3.67);

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `CourseID` varchar(100) NOT NULL,
  `CourseName` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`CourseID`, `CourseName`) VALUES
('ACC', 'Diploma in Accounting'),
('ARC', 'Diploma in Architecture'),
('CHS', 'Diploma in Chemistry'),
('DFT', 'Diploma in Information Technology'),
('EET', 'Diploma in Electronics Engineering Technology');

-- --------------------------------------------------------

--
-- Table structure for table `exams`
--

CREATE TABLE `exams` (
  `ExamID` varchar(100) NOT NULL,
  `SubjectID` varchar(100) NOT NULL,
  `ExamDate` date NOT NULL,
  `ExamDay` varchar(50) NOT NULL,
  `Time` int(50) NOT NULL,
  `Duration` varchar(50) NOT NULL,
  `Venue` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `exams`
--

INSERT INTO `exams` (`ExamID`, `SubjectID`, `ExamDate`, `ExamDay`, `Time`, `Duration`, `Venue`) VALUES
('EX101', 'ADACC003', '2025-06-03', 'MON', 12, '3', 'KS2A'),
('EX102', 'MAACC002', '2025-06-04', 'WED', 10, '2 hours', 'M1'),
('EX103', 'TXACC001', '2025-06-06', 'FRI', 11, '2 hours', 'Q1'),
('EX201', 'BTARC003', '2025-06-09', 'MON', 9, '2 hours', 'SE1'),
('EX202', 'SDARC001', '2025-06-11', 'WED', 10, '2 hours', 'M2'),
('EX203', 'SSARC002', '2025-05-07', 'FRI', 9, '2', 'KSA'),
('EX301', 'OCCHS001', '2025-06-16', 'MON', 9, '2 hours', 'KS2A'),
('EX302', 'PCCHS002', '2025-06-18', 'WED', 10, '2 hours', 'M3'),
('EX303', 'CTCHS003', '2025-06-20', 'FRI', 11, '2 hours', 'Q3'),
('EX401', 'AACS2204', '2025-06-23', 'MON', 9, '2 hours', 'SE2'),
('EX402', 'AACS2303', '2025-06-25', 'WED', 10, '2 hours', 'M4'),
('EX501', 'AEEET001', '2025-06-30', 'MON', 9, '2 hours', 'M5'),
('EX502', 'EMEET002', '2025-06-03', 'TUE', 10, '2 hours', 'Q5'),
('EX503', 'EMEET003', '2025-06-05', 'THU', 11, '2 hours', 'M6');

-- --------------------------------------------------------

--
-- Table structure for table `facultyinfo`
--

CREATE TABLE `facultyinfo` (
  `FacultyID` varchar(100) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `IC` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `Contact` varchar(100) NOT NULL,
  `CourseID` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `facultyinfo`
--

INSERT INTO `facultyinfo` (`FacultyID`, `Name`, `IC`, `Email`, `Password`, `Contact`, `CourseID`) VALUES
('24WMD02345', 'Niama', '050215142375', 'cnm@gmail.com', 'G@y1234567890', '222222222', 'ARC'),
('24WMD02576', 'kai', '050105156778', 'kai@gmail.com', '456', '122720577', 'CHS'),
('24WMD02678', 'han', '060502306789', 'han@gmail.com', '345', '123456789', 'ACC'),
('24WMD02987', 'hock', '060428901234', 'hock@gmail.com', '567', '0103846198', 'EET'),
('24WMD02988', 'WEi', '060209080078', 'wei@gmail.com', 'G@y12345', '1234567890', 'DFT');

-- --------------------------------------------------------

--
-- Table structure for table `result`
--

CREATE TABLE `result` (
  `ResultID` varchar(100) NOT NULL,
  `StudentID` varchar(100) NOT NULL,
  `SubjectID` varchar(100) NOT NULL,
  `MarksObtained` int(50) NOT NULL,
  `FinalGrade` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `result`
--

INSERT INTO `result` (`ResultID`, `StudentID`, `SubjectID`, `MarksObtained`, `FinalGrade`) VALUES
('R004', '24WMD03453', 'BTARC003', 78, 'A-'),
('R006', '24WMD03453', 'SSARC002', 93, 'A+'),
('R007', '24WMD02988', 'OCCHS001', 64, 'B'),
('R008', '24WMD02988', 'PCCHS002', 91, 'A+'),
('R009', '24WMD02988', 'CTCHS003', 100, 'A+'),
('R010', '24WMD03472', 'AACS2204', 88, 'A'),
('R011', '24WMD03472', 'AACS2303', 67, 'B+'),
('R012', '24WMD03472', 'AMMS2603', 92, 'A+'),
('R013', '24WMD02762', 'AEEET001', 68, 'B+'),
('R014', '24WMD02762', 'EMEET002', 85, 'A'),
('R015', '24WMD02762', 'EMEET003', 57, 'C+'),
('R773', '24WMD03453', 'BTARC003', 67, 'B'),
('R868', '24WMD03472', 'AACS2204', 100, 'A+');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `StaffID` varchar(100) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `IC` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Contact` varchar(100) NOT NULL,
  `SubjectID` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`StaffID`, `Name`, `IC`, `Email`, `Password`, `Contact`, `SubjectID`) VALUES
('24WMD03432', 'Kai', '012345678900', 'kai@gmail.com', '123', '0123456789', 'AACS2204');

-- --------------------------------------------------------

--
-- Table structure for table `studentinfo`
--

CREATE TABLE `studentinfo` (
  `StudentID` varchar(100) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `IC` varchar(100) NOT NULL,
  `Age` int(100) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `Contact` varchar(100) NOT NULL,
  `CourseID` varchar(100) DEFAULT NULL,
  `YearOfStudy` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `studentinfo`
--

INSERT INTO `studentinfo` (`StudentID`, `Name`, `IC`, `Age`, `Gender`, `Email`, `Password`, `Contact`, `CourseID`, `YearOfStudy`) VALUES
('24WMD02762', 'Kaizen', '050530112233', 22, 'Male', 'neziak@gmail.com', 'Gay@12345678', '0122720566', 'EET', 1),
('24WMD02988', 'Wei Shin', '030625098765', 20, 'Female', 'weishin@gmail.com', 'wei298', '0166181521', 'CHS', 3),
('24WMD03453', 'Rou Xuan', '060203145678', 19, 'Female', 'rouxuan@gmail.com', 'rou123', '01112225686', 'ARC', 1),
('24WMD03472', 'Han Yean', '880101234567', 19, 'Male', 'h@gmail.com', 'HanYean.28', '0123456789', 'DFT', 2),
('24WMD23625', 'hiv', '012345678900', 20, 'Male', 'hiv@gmail.com', 'H1v@gmail.com', '0192345678', 'DFT', 1),
('24WMD29117', 'Chan Wing Kit', '050627084321', 19, 'Male', 'wing@gmail.com', 'Wing@123', '127651009', 'CHS', 1),
('24WMD49888', 'Herman Chan', '010203101234', 25, 'Male', 'herman@gmail.com', 'Herman@123', '0111111111', 'CHS', 1),
('24WMD68729', 'han', '012345678900', 20, 'Male', 'h@gmail.com', 'HanYean.28', '0123456789', 'CHS', 1);

-- --------------------------------------------------------

--
-- Table structure for table `students_exams`
--

CREATE TABLE `students_exams` (
  `StudentID` varchar(100) NOT NULL,
  `ExamID` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students_exams`
--

INSERT INTO `students_exams` (`StudentID`, `ExamID`) VALUES
('24WMD03453', 'EX201'),
('24WMD03453', 'EX202'),
('24WMD03453', 'EX203'),
('24WMD02988', 'EX301'),
('24WMD02988', 'EX302'),
('24WMD02988', 'EX303'),
('24WMD03472', 'EX401'),
('24WMD03472', 'EX402'),
('24WMD02762', 'EX501'),
('24WMD02762', 'EX502'),
('24WMD02762', 'EX503'),
('24WMD49888', 'EX303'),
('24WMD49888', 'EX301'),
('24WMD49888', 'EX302'),
('24WMD68729', 'EX303'),
('24WMD68729', 'EX301'),
('24WMD68729', 'EX302');

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE `subject` (
  `SubjectID` varchar(100) NOT NULL,
  `SubjectName` varchar(100) NOT NULL,
  `CourseID` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`SubjectID`, `SubjectName`, `CourseID`) VALUES
('AACS2204', 'OBJECT-ORIENTED PROGRAMMING TECHNIQUES', 'DFT'),
('AACS2303', 'INTRODUCTION TO INTERFACE DESIGN', 'DFT'),
('ADACC003', 'AUDITING', 'ACC'),
('AEEET001', 'ANALOGUE ELECTRONICS', 'EET'),
('AMMS2603', 'DISCRETE MATHEMATICS', 'DFT'),
('BTARC003', 'BUILDING TECHNOLOGY', 'ARC'),
('CTCHS003', 'CATALYSIS', 'CHS'),
('EMEET002', 'ENGINEERING MATHEMATICS', 'EET'),
('EMEET003', 'ELECTROMAGNETISM', 'EET'),
('MAACC002', 'MANAGEMENT ACCOUNTING', 'ACC'),
('OCCHS001', 'ORGANIC CHEMISTRY', 'CHS'),
('PCCHS002', 'POLYMER CHEMISTRY', 'CHS'),
('SDARC001', 'STUDIO DESIGN', 'ARC'),
('SSARC002', 'STRUCTURAL STUDIES', 'ARC'),
('TXACC001', 'TAXATION', 'ACC');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cgpa`
--
ALTER TABLE `cgpa`
  ADD KEY `cgpa_ibfk_1` (`StudentID`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`CourseID`);

--
-- Indexes for table `exams`
--
ALTER TABLE `exams`
  ADD PRIMARY KEY (`ExamID`),
  ADD KEY `SubjectID` (`SubjectID`);

--
-- Indexes for table `facultyinfo`
--
ALTER TABLE `facultyinfo`
  ADD PRIMARY KEY (`FacultyID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Indexes for table `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`ResultID`),
  ADD KEY `StudentID` (`StudentID`),
  ADD KEY `SubjectID` (`SubjectID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`StaffID`),
  ADD KEY `SubjectID` (`SubjectID`);

--
-- Indexes for table `studentinfo`
--
ALTER TABLE `studentinfo`
  ADD PRIMARY KEY (`StudentID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Indexes for table `students_exams`
--
ALTER TABLE `students_exams`
  ADD KEY `ExamID` (`ExamID`),
  ADD KEY `StudentID` (`StudentID`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`SubjectID`),
  ADD KEY `CourseID` (`CourseID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cgpa`
--
ALTER TABLE `cgpa`
  ADD CONSTRAINT `cgpa_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `studentinfo` (`StudentID`) ON DELETE CASCADE;

--
-- Constraints for table `exams`
--
ALTER TABLE `exams`
  ADD CONSTRAINT `exams_ibfk_1` FOREIGN KEY (`SubjectID`) REFERENCES `subject` (`SubjectID`);

--
-- Constraints for table `facultyinfo`
--
ALTER TABLE `facultyinfo`
  ADD CONSTRAINT `facultyinfo_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`);

--
-- Constraints for table `result`
--
ALTER TABLE `result`
  ADD CONSTRAINT `fk_student` FOREIGN KEY (`StudentID`) REFERENCES `studentinfo` (`StudentID`) ON DELETE CASCADE,
  ADD CONSTRAINT `result_ibfk_1` FOREIGN KEY (`StudentID`) REFERENCES `studentinfo` (`StudentID`),
  ADD CONSTRAINT `result_ibfk_2` FOREIGN KEY (`SubjectID`) REFERENCES `subject` (`SubjectID`);

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`SubjectID`) REFERENCES `subject` (`SubjectID`);

--
-- Constraints for table `studentinfo`
--
ALTER TABLE `studentinfo`
  ADD CONSTRAINT `studentinfo_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`);

--
-- Constraints for table `students_exams`
--
ALTER TABLE `students_exams`
  ADD CONSTRAINT `students_exams_ibfk_1` FOREIGN KEY (`ExamID`) REFERENCES `exams` (`ExamID`),
  ADD CONSTRAINT `students_exams_ibfk_2` FOREIGN KEY (`StudentID`) REFERENCES `studentinfo` (`StudentID`) ON DELETE CASCADE;

--
-- Constraints for table `subject`
--
ALTER TABLE `subject`
  ADD CONSTRAINT `subject_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `course` (`CourseID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
