-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.29-log - MySQL Community Server (GPL)
-- Server OS:                    Linux
-- HeidiSQL Version:             11.0.0.5930
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table sprints.sprint
CREATE TABLE IF NOT EXISTS sprints.sprint (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `refined_SP` json DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.sprint: ~21 rows (approximately)
/*!40000 ALTER TABLE sprints.sprint DISABLE KEYS */;
INSERT INTO sprints.sprint (`id`, `sprint`, `refined_SP`, `updated`) VALUES
	(1, 'Sprint 39', '{"BASIC": 247, "FUTURE": 0, "ADVANCED": 8, "COMMERCIAL": 0}', '2020-04-30 13:11:24'),
	(2, 'Sprint 40', '{"BASIC": 217, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-04-30 14:00:03'),
	(3, 'Sprint 41', '{"BASIC": 281, "FUTURE": 0, "ADVANCED": 3, "COMMERCIAL": 0}', '2020-04-30 14:19:58'),
	(4, 'Sprint 42', '{"BASIC": 286, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-04-30 14:37:38'),
	(5, 'Sprint 43', '{"BASIC": 297, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-04-30 14:54:42'),
	(6, 'Sprint 44', '{"BASIC": 211, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-04-30 15:07:05'),
	(7, 'Sprint 45', '{"BASIC": 221, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '2020-04-30 15:19:51'),
	(8, 'Sprint 46', '{"BASIC": 200, "FUTURE": 0, "ADVANCED": 3, "COMMERCIAL": 0}', '2020-04-30 15:40:00'),
	(9, 'Sprint 47', '{"BASIC": 233, "FUTURE": 0, "ADVANCED": 6, "COMMERCIAL": 1}', '2020-04-30 15:52:15'),
	(10, 'Sprint 48', '{"BASIC": 254, "FUTURE": 0, "ADVANCED": 6, "COMMERCIAL": 0}', '2020-04-30 16:00:01'),
	(11, 'Sprint 49', '{"BASIC": 213, "FUTURE": 0, "ADVANCED": 17, "COMMERCIAL": 0}', '2020-04-30 16:06:57'),
	(12, 'Sprint 50', '{"BASIC": 275, "FUTURE": 0, "ADVANCED": 16, "COMMERCIAL": 0}', '2020-04-30 16:17:05'),
	(13, 'Sprint 51', '{"BASIC": 158, "FUTURE": 0, "ADVANCED": 9, "COMMERCIAL": 0}', '2020-04-30 16:31:56'),
	(14, 'Sprint 52', '{"BASIC": 258, "FUTURE": 0, "ADVANCED": 14, "COMMERCIAL": 0}', '2020-04-30 16:38:51'),
	(15, 'Sprint 53', '{"BASIC": 218, "FUTURE": 0, "ADVANCED": 27, "COMMERCIAL": 0}', '2020-04-30 16:46:36'),
	(16, 'Sprint 54', '{"BASIC": 246, "FUTURE": 0, "ADVANCED": 44, "COMMERCIAL": 3}', '2020-04-30 16:53:36'),
	(17, 'Sprint 55', '{"BASIC": 254, "FUTURE": 0, "ADVANCED": 41, "COMMERCIAL": 0}', '2020-04-30 17:01:35'),
	(18, 'Sprint 56', '{"BASIC": 262, "FUTURE": 0, "ADVANCED": 34, "COMMERCIAL": 0}', '2020-04-30 17:10:19'),
	(19, 'Sprint 57', '{"BASIC": 269, "FUTURE": 0, "ADVANCED": 21, "COMMERCIAL": 0}', '2020-04-30 17:17:49'),
	(20, 'Sprint 58', '{"BASIC": 171, "FUTURE": 0, "ADVANCED": 24, "COMMERCIAL": 0}', '2020-05-04 15:45:34'),
	(21, 'Sprint 59', '{"BASIC": 65, "FUTURE": 0, "ADVANCED": 39, "COMMERCIAL": 0}', '2020-05-04 15:45:34'),
	(22, 'Sprint 60', '{"BASIC": 43, "FUTURE": 0, "ADVANCED": 23, "COMMERCIAL": 0}', '2020-05-04 15:45:34'),
	(23, 'Sprint 61', '{"BASIC": 2, "FUTURE": 0, "ADVANCED": 24, "COMMERCIAL": 0}', '2020-05-04 15:45:34');
/*!40000 ALTER TABLE sprints.sprint ENABLE KEYS */;

-- Dumping structure for table sprints.team_black
CREATE TABLE IF NOT EXISTS sprints.team_black (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `team_name` varchar(64) DEFAULT NULL,
  `team_member_count` decimal(2,0) DEFAULT '0',
  `on_begin_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `on_end_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_sp_sum` decimal(3,0) DEFAULT '0',
  `not_finished_sp_sum` decimal(3,0) DEFAULT '0',
  `to_do_sp_sum` decimal(3,0) DEFAULT '0',
  `in_progress_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_stories_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_bugs_sp_sum` decimal(3,0) DEFAULT '0',
  `time_estimation` decimal(4,0) DEFAULT '0',
  `time_planned` decimal(4,0) DEFAULT '0',
  `time_spent` decimal(4,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_black: ~17 rows (approximately)
/*!40000 ALTER TABLE sprints.team_black DISABLE KEYS */;
INSERT INTO sprints.team_black (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 38', 'Black', 9, 84, 87, 76, 11, 11, 0, 74, 2, 568, 583, 599, 1, 0.0357, 0.9048, '2019-03-14 00:00:00', '2019-04-03 00:00:00', '2020-04-30 13:11:24', '{"BASIC": 76, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Initial version of Decision tree available", "First implementation of steering interface", "Inintial version of NIBP simulator with basic functionality", "Inintial version of NIBP widget with basic functionality", "Test specification for NIBP for Basic version"]'),
	(2, 'Sprint 39', 'Black', 10, 80, 83, 83, 0, 0, 0, 77, 6, 609, 616, 488, 0, 0.0375, 1.0375, '2019-04-04 00:00:00', '2019-04-24 00:00:00', '2020-04-30 14:00:03', '{"BASIC": 83, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Present new engine for all curves", "Update of existing Oximetry test cases (Squish)", "Execution of the first test cases for NIBP (basic functionality)", "Global setting will be applied to NIBP widget", "Analysis of the monitoring CPU usage and simulating CPU load in test bench", "NIBP widget functionality with measurement interval, cuff type and start-stop button", "See IBP curve in monitoring line on MON"]'),
	(3, 'Sprint 40', 'Black', 7, 63, 63, 58, 5, 5, 0, 58, 0, 491, 491, 561, 1, 0.0000, 0.9206, '2019-04-25 00:00:00', '2019-05-15 00:00:00', '2020-04-30 14:19:57', '{"BASIC": 58, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Working IBP contextual menu within Basic functionality with the new design", "Update HMI widget identification to use Squish more effectively", "Possibility to trigger NIBP commands from HMI to NIBP simulator via Steering", "MFBH is available for NIBP", "Test specification for IBP - basic functionality", "Having new \\"Placement\\" dialog for IBP sensor available", "Increase HMI code coverage in NIBP and IBP"]'),
	(4, 'Sprint 41', 'Black', 8, 84, 85, 59, 26, 26, 0, 56, 3, 578, 581, 722, 3, 0.0119, 0.7024, '2019-05-16 00:00:00', '2019-06-05 00:00:00', '2020-04-30 14:37:38', '{"BASIC": 59, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Have Temperature data from simulator showing  on Target HW", "Finalize NIBP feature in Basic scope", "Fix ECG filters", "Working IBP calibration", "IBP service will be running as new process"]'),
	(5, 'Sprint 42', 'Black', 8, 95, 95, 66, 29, 29, 0, 63, 3, 625, 625, 749, 1, 0.0000, 0.6947, '2019-06-06 00:00:00', '2019-06-26 00:00:00', '2020-04-30 14:54:42', '{"BASIC": 66, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Verify that the QT curve engine problem persists on native Linux", "Finish basic scope for IBP", "EA documentation for temperature", "Have a way how to measure GUI HMI responsiveness", "Respiration simulator will be available", "Respiration process inside of steering will be available"]'),
	(6, 'Sprint 43', 'Black', 10, 88, 89, 66, 23, 23, 0, 61, 5, 647, 648, 761, 3, 0.0114, 0.7500, '2019-06-27 00:00:00', '2019-07-15 00:00:00', '2020-04-30 15:07:05', '{"BASIC": 66, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Respiration working", "Finaly finalize NIBP!", "QRS/PT settings manipulated from HMI", "QRS/PT service connected from HMI", "Clickable quick setting menu (QRS/PT volume inside working)", "Oximetry should be  aligned with newest sensor profile (released)", "Contextual menu for oximetry aligned to last actual available design"]'),
	(7, 'Sprint 44', 'Black', 8, 43, 44, 41, 3, 3, 0, 22, 19, 378, 380, 329, 2, 0.0233, 0.9535, '2019-07-18 00:00:00', '2019-08-05 00:00:00', '2020-04-30 15:19:51', '{"BASIC": 41, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(8, 'Sprint 45', 'Black', 8, 68, 50, 42, 8, 8, 0, 16, 26, 460, 438, 462, 0, 0.2647, 0.6176, '2019-08-08 00:00:00', '2019-09-02 00:00:00', '2020-04-30 15:40:00', '{"BASIC": 42, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Update sensor profile implementation: IBP, respiration, oximetry", "Spillpover from Sprint 44", "Findings from Sprint 44"]'),
	(9, 'Sprint 46', 'Black', 9, 46, 48, 33, 15, 15, 0, 28, 5, 329, 339, 399, 0, 0.0435, 0.7174, '2019-09-05 00:00:00', '2019-09-16 00:00:00', '2020-04-30 15:52:15', '{"BASIC": 33, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Adapt ibp to new profile", "Allign and update nibp & ecg meta data", "Prepare HMI refactoring", "Basic Auto II/DE", "Amplitude markers and scaling"]'),
	(10, 'Sprint 47', 'Black', 8, 59, 63, 54, 9, 9, 0, 52, 2, 423, 451, 449, 0, 0.0678, 0.9153, '2019-09-19 00:00:00', '2019-10-07 00:00:00', '2020-04-30 16:00:01', '{"BASIC": 53, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 1}', '["Improve knowledge :-)", "Integrate first sensors"]'),
	(11, 'Sprint 48', 'Black', 8, 81, 85, 66, 19, 19, 0, 61, 5, 530, 548, 590, 1, 0.0494, 0.8148, '2019-10-10 00:00:00', '2019-10-28 00:00:00', '2020-04-30 16:06:57', '{"BASIC": 66, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(12, 'Sprint 49', 'Black', 8, 75, 77, 71, 6, 6, 0, 40, 31, 523, 535, 522, 0, 0.0267, 0.9467, '2019-10-31 00:00:00', '2019-11-18 00:00:00', '2020-04-30 16:17:05', '{"BASIC": 71, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["NIBP sensor integration", "HMI refactoring meetings", "Oximetry sensor integration"]'),
	(13, 'Sprint 50', 'Black', 8, 85, 85, 61, 24, 24, 0, 58, 3, 566, 566, 589, 2, 0.0000, 0.7176, '2019-11-21 00:00:00', '2019-12-09 00:00:00', '2020-04-30 16:31:55', '{"BASIC": 61, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Concept for profile of global settings", "First step of integration standard views", "Ongoing sensor integration", "HMI refactoring important steps", "Design new contextual menus for nonin and masimo"]'),
	(14, 'Sprint 51', 'Black', 8, 69, 69, 24, 45, 45, 0, 22, 2, 400, 400, 391, 2, 0.0000, 0.3478, '2019-12-12 00:00:00', '2020-01-06 00:00:00', '2020-04-30 16:38:51', '{"BASIC": 24, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Concept for profile of global settings", "First step of integration standard views", "Ongoing sensor integration", "HMI refactoring important steps", "Design new contextual menus for nonin and masimo"]'),
	(15, 'Sprint 52', 'Black', 7, 86, 79, 61, 18, 18, 0, 44, 17, 553, 511, 499, 2, 0.0814, 0.7093, '2020-01-09 00:00:00', '2020-01-27 00:00:00', '2020-04-30 16:46:36', '{"BASIC": 55, "FUTURE": 0, "ADVANCED": 6, "COMMERCIAL": 0}', '["Finalize QML Monitoring layout items", "Monitoring layout configuration - first running version", "Fixing flixbus bugfixes fixed", "Continues with sensor integration", "Device Monitor concept available", "Improve context menu"]'),
	(16, 'Sprint 53', 'Black', 7, 69, 69, 50, 19, 16, 3, 36, 14, 461, 461, 537, 1, 0.0000, 0.7246, '2020-01-30 00:00:00', '2020-02-17 00:00:00', '2020-04-30 16:53:35', '{"BASIC": 40, "FUTURE": 0, "ADVANCED": 10, "COMMERCIAL": 0}', '["Monitoring layouts configurable: layout items and items hierarchy", "Monitoring layout QML & connection to backend", "Respiration command analyze", "NIBP inoperable state analyze", "Finalize device monitor concept", "Solve SIGTERM problem", "QRS PT check with real hardware"]'),
	(17, 'Sprint 54', 'Black', 7, 70, 70, 53, 17, 14, 3, 39, 14, 461, 449, 471, 1, 0.0000, 0.7571, '2020-02-20 00:00:00', '2020-03-09 00:00:00', '2020-04-30 17:01:35', '{"BASIC": 40, "FUTURE": 0, "ADVANCED": 13, "COMMERCIAL": 0}', '["Adapt new D-ECG profile to Host and be ready for EcgDiagnosisWaveform", "Use analysis results from last sprint to bring us way more to the state, that real sensors are working, as expected"]'),
	(18, 'Sprint 55', 'Black', 7, 78, 83, 40, 43, 40, 3, 34, 6, 576, 626, 813, 1, 0.0641, 0.5128, '2020-03-12 00:00:00', '2020-03-30 00:00:00', '2020-04-30 17:10:19', '{"BASIC": 31, "FUTURE": 0, "ADVANCED": 9, "COMMERCIAL": 0}', '["Work with new design and adapt current functionality to it"]'),
	(19, 'Sprint 56', 'Black', 4, 71, 79, 79, 0, 0, 0, 68, 11, 490, 545, 559, 0, 0.1127, 1.1127, '2020-04-02 00:00:00', '2020-04-20 00:00:00', '2020-04-30 17:17:49', '{"BASIC": 71, "FUTURE": 0, "ADVANCED": 8, "COMMERCIAL": 0}', '["No spillovers"]'),
	(20, 'Sprint 57', 'Black', 9, 73, 78, 13, 65, 25, 40, 8, 5, 443, 475, 172, 0, 0.0685, 0.1781, '2020-04-23 00:00:00', '2020-05-11 00:00:00', '2020-05-04 15:45:34', '{"BASIC": 8, "FUTURE": 0, "ADVANCED": 5, "COMMERCIAL": 0}', '["Finalize the new design and switch off the old design"]');
/*!40000 ALTER TABLE sprints.team_black ENABLE KEYS */;

-- Dumping structure for table sprints.team_blue
CREATE TABLE IF NOT EXISTS sprints.team_blue (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `team_name` varchar(64) DEFAULT NULL,
  `team_member_count` decimal(2,0) DEFAULT '0',
  `on_begin_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `on_end_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_sp_sum` decimal(3,0) DEFAULT '0',
  `not_finished_sp_sum` decimal(3,0) DEFAULT '0',
  `to_do_sp_sum` decimal(3,0) DEFAULT '0',
  `in_progress_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_stories_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_bugs_sp_sum` decimal(3,0) DEFAULT '0',
  `time_estimation` decimal(4,0) DEFAULT '0',
  `time_planned` decimal(4,0) DEFAULT '0',
  `time_spent` decimal(4,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_blue: ~17 rows (approximately)
/*!40000 ALTER TABLE sprints.team_blue DISABLE KEYS */;
INSERT INTO sprints.team_blue (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 40', 'Blue', 6, 32, 32, 24, 8, 8, 0, 24, 0, 240, 240, 226, 0, 0.0000, 0.7500, '2019-04-25 00:00:00', '2019-05-15 00:00:00', '2020-04-30 14:19:57', '{"BASIC": 24, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Part of screen image comparison", "HW buttons evaluation - Jogdial", "CPU load measurement", "Design of test cases for IBP", "Design of test cases for Pacer", "Design of test cases for Alarm"]'),
	(2, 'Sprint 41', 'Blue', 5, 47, 47, 47, 0, 0, 0, 47, 0, 294, 294, 270, 0, 0.0000, 1.0000, '2019-05-16 00:00:00', '2019-06-05 00:00:00', '2020-04-30 14:37:38', '{"BASIC": 47, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Evaluate automation possibilities to check presence of curve + basic attributes check (like sweep speed)", "Evaluate squish to check data in notification line/alarm notification line", "Design smoke tests for AED, manual defib., pacer, NIBP, IBP, Alarms", "Implement smoke test for status line", "Implement smoke test for oximetry", "Implement smoke test for pairing", "Implement boot up time bench mark (from power on till all service running)"]'),
	(3, 'Sprint 42', 'Blue', 4, 37, 37, 37, 0, 0, 0, 37, 0, 262, 266, 221, 0, 0.0000, 1.0000, '2019-06-06 00:00:00', '2019-06-26 00:00:00', '2020-04-30 14:54:42', '{"BASIC": 37, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Implementation of stability tests for optical connection & disconnection", "Implementation of stability tests for modules and services restart", "Implementation of bench marking test for BT/Opt HW throughput", "Implementation of bench marking test for image size"]'),
	(4, 'Sprint 43', 'Blue', 5, 55, 57, 49, 8, 8, 0, 47, 2, 330, 335, 347, 0, 0.0364, 0.8909, '2019-06-27 00:00:00', '2019-07-15 00:00:00', '2020-04-30 15:07:05', '{"BASIC": 49, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Implementation of benchmark test for link creation throughput", "Implementation of benchmark test for measuring CPU load and memory", "Implementation of smoke test for alarms", "Implementation of smoke test for manual defibrillation"]'),
	(5, 'Sprint 44', 'Blue', 4, 29, 32, 24, 8, 8, 0, 22, 2, 214, 230, 210, 0, 0.1034, 0.8276, '2019-07-18 00:00:00', '2019-08-05 00:00:00', '2020-04-30 15:19:51', '{"BASIC": 24, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Implement performance tests cases for intercom", "Finish the data system throughput benchmark implementation"]'),
	(6, 'Sprint 45', 'Blue', 4, 45, 47, 47, 0, 0, 0, 45, 2, 326, 331, 300, 0, 0.0444, 1.0444, '2019-08-08 00:00:00', '2019-09-02 00:00:00', '2020-04-30 15:40:00', '{"BASIC": 47, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Implement smoke test for NIBP", "Implement integration tests for status system - throughput", "Implement integration tests for alarm system - alarm propagation delay", "Investigate failures of existing smoke tests"]'),
	(7, 'Sprint 46', 'Blue', 5, 43, 44, 44, 0, 0, 0, 44, 0, 314, 317, 239, 0, 0.0233, 1.0233, '2019-09-05 00:00:00', '2019-09-16 00:00:00', '2020-04-30 15:52:15', '{"BASIC": 44, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(8, 'Sprint 47', 'Blue', 4, 40, 41, 38, 3, 3, 0, 37, 1, 253, 256, 265, 0, 0.0250, 0.9500, '2019-09-19 00:00:00', '2019-10-07 00:00:00', '2020-04-30 16:00:01', '{"BASIC": 38, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(9, 'Sprint 48', 'Blue', 4, 38, 38, 38, 0, 0, 0, 37, 1, 271, 271, 243, 0, 0.0000, 1.0000, '2019-10-10 00:00:00', '2019-10-28 00:00:00', '2020-04-30 16:06:57', '{"BASIC": 38, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Benchmark test for Wifi and BT in parallel", "Stability test for data priorisation", "Finish failing tests analysis"]'),
	(10, 'Sprint 49', 'Blue', 5, 38, 38, 38, 0, 0, 0, 35, 3, 302, 302, 200, 0, 0.0000, 1.0000, '2019-10-31 00:00:00', '2019-11-18 00:00:00', '2020-04-30 16:17:05', '{"BASIC": 38, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Status system stability test for fast status generation for short time period", "Data monitoring tool for real sensors reporting number of received data per sec", "Analyse CPU consumption for data throughput measurement"]'),
	(11, 'Sprint 50', 'Blue', 5, 42, 44, 44, 0, 0, 0, 34, 10, 330, 337, 253, 0, 0.0476, 1.0476, '2019-11-21 00:00:00', '2019-12-09 00:00:00', '2020-04-30 16:31:55', '{"BASIC": 44, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Stability test case for link creation pairing mechanism (in loop)", "Fix data throughput test case", "Stability test checking that consumer is notified about disconnection when foundation process crashes", "Evaluate the possibility to measure data overhead at different levels of architecture"]'),
	(12, 'Sprint 51', 'Blue', 7, 18, 19, 16, 3, 3, 0, 2, 14, 173, 177, 148, 0, 0.0556, 0.8889, '2019-12-12 00:00:00', '2020-01-06 00:00:00', '2020-04-30 16:38:51', '{"BASIC": 16, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Fix planned bugs in TB"]'),
	(13, 'Sprint 52', 'Blue', 7, 47, 46, 41, 5, 5, 0, 37, 4, 439, 433, 322, 1, 0.0213, 0.8723, '2020-01-09 00:00:00', '2020-01-27 00:00:00', '2020-04-30 16:46:36', '{"BASIC": 41, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Fix planned bugs in TB"]'),
	(14, 'Sprint 53', 'Blue', 7, 33, 33, 33, 0, 0, 0, 33, 0, 316, 316, 267, 0, 0.0000, 1.0000, '2020-01-30 00:00:00', '2020-02-17 00:00:00', '2020-04-30 16:53:35', '{"BASIC": 33, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Fix planned bugs in TB"]'),
	(15, 'Sprint 54', 'Blue', 8, 25, 25, 25, 0, 0, 0, 25, 0, 184, 184, 127, 0, 0.0000, 1.0000, '2020-02-20 00:00:00', '2020-03-09 00:00:00', '2020-04-30 17:01:35', '{"BASIC": 25, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(16, 'Sprint 55', 'Blue', 7, 42, 43, 43, 0, 0, 0, 41, 2, 298, 298, 291, 0, 0.0238, 1.0238, '2020-03-12 00:00:00', '2020-03-30 00:00:00', '2020-04-30 17:10:19', '{"BASIC": 43, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Talking the motto from the workshop \\"Stabilization is valued higher than features\\" we would like to have the test bench far enough so we can start performance testing and documentation of the test bench itself."]'),
	(17, 'Sprint 56', 'Blue', 2, 43, 44, 44, 0, 0, 0, 43, 1, 383, 385, 295, 0, 0.0233, 1.0233, '2020-04-02 00:00:00', '2020-04-20 00:00:00', '2020-04-30 17:17:49', '{"BASIC": 44, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(18, 'Sprint 57', 'Blue', 7, 38, 38, 3, 35, 9, 26, 3, 0, 329, 329, 139, 0, 0.0000, 0.0789, '2020-04-23 00:00:00', '2020-05-11 00:00:00', '2020-05-04 15:45:34', '{"BASIC": 3, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Have first performance test and extend options to used test data"]');
/*!40000 ALTER TABLE sprints.team_blue ENABLE KEYS */;

-- Dumping structure for table sprints.team_green
CREATE TABLE IF NOT EXISTS sprints.team_green (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `team_name` varchar(64) DEFAULT NULL,
  `team_member_count` decimal(2,0) DEFAULT '0',
  `on_begin_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `on_end_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_sp_sum` decimal(3,0) DEFAULT '0',
  `not_finished_sp_sum` decimal(3,0) DEFAULT '0',
  `to_do_sp_sum` decimal(3,0) DEFAULT '0',
  `in_progress_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_stories_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_bugs_sp_sum` decimal(3,0) DEFAULT '0',
  `time_estimation` decimal(4,0) DEFAULT '0',
  `time_planned` decimal(4,0) DEFAULT '0',
  `time_spent` decimal(4,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_green: ~17 rows (approximately)
/*!40000 ALTER TABLE sprints.team_green DISABLE KEYS */;
INSERT INTO sprints.team_green (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 38', 'Green', 11, 91, 108, 54, 54, 51, 3, 53, 1, 559, 644, 936, 0, 0.1868, 0.5934, '2019-03-14 00:00:00', '2019-04-03 00:00:00', '2020-04-30 13:11:24', '{"BASIC": 54, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Show live picture from camera in HMI widget (nice to have on Target HW)", "Take picture from camera and save it to local storage (nice to have on Target HW)", "Time component should save Time system attributes in the same way as Link-Creation", "Flash light toggle from HMI widget", "Validate the general GS_sensor profile"]'),
	(2, 'Sprint 39', 'Green', 10, 83, 111, 78, 33, 33, 0, 77, 1, 547, 732, 1059, 0, 0.3373, 0.9398, '2019-04-04 00:00:00', '2019-04-24 00:00:00', '2020-04-30 14:00:03', '{"BASIC": 70, "FUTURE": 0, "ADVANCED": 8, "COMMERCIAL": 0}', '["Running monitoring live data pane in HMI for Camera widget", "Show, take and store live picture from the camera", "Having Alarm sounds based on the Alarm priority", "Time system should use only DFS for saving time system attributes", "Blinking Alarm LEDs based on the Alarm priority"]'),
	(3, 'Sprint 40', 'Green', 8, 80, 79, 53, 26, 26, 0, 51, 2, 593, 585, 801, 0, 0.0125, 0.6625, '2019-04-25 00:00:00', '2019-05-15 00:00:00', '2020-04-30 14:19:58', '{"BASIC": 53, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(4, 'Sprint 41', 'Green', 8, 61, 61, 51, 10, 10, 0, 43, 8, 483, 483, 684, 0, 0.0000, 0.8361, '2019-05-16 00:00:00', '2019-06-05 00:00:00', '2020-04-30 14:37:38', '{"BASIC": 51, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["HMI: extend settings file with alarm limits", "HMI: update alarm list widget", "Mute alarms", "Design test cases (SRS Verification)", "Extend global settings file with limits", "Adjustments to alarm list", "Bugfixes concerning flash light", "Bugfix concerning switching front/back camera"]'),
	(5, 'Sprint 42', 'Green', 9, 66, 68, 47, 21, 21, 0, 43, 4, 529, 534, 688, 0, 0.0303, 0.7121, '2019-06-06 00:00:00', '2019-06-26 00:00:00', '2020-04-30 14:54:42', '{"BASIC": 47, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Run the intersteering communication for the first time", "Print a testpage on Target HW", "Play sound and blink light for notification", "Verify the basic functionality of alarms on Target HW"]'),
	(6, 'Sprint 43', 'Green', 7, 48, 53, 41, 12, 12, 0, 30, 11, 339, 358, 518, 0, 0.1042, 0.8542, '2019-06-27 00:00:00', '2019-07-15 00:00:00', '2020-04-30 15:07:05', '{"BASIC": 41, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Working Auto Limits from contextual menu", "Fix playing of Alarm sounds", "Alarm notification available in Alarm intelligence System", "Add missing functionality to Setting System"]'),
	(7, 'Sprint 44', 'Green', 8, 61, 67, 33, 34, 34, 0, 22, 11, 536, 544, 777, 0, 0.0984, 0.5410, '2019-07-18 00:00:00', '2019-08-05 00:00:00', '2020-04-30 15:19:51', '{"BASIC": 33, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Deliver Covert mode for HMI part", "First practical use of Time system - display the system time in HMI", "Have ability to swipe the alarm list with animation", "Complete fixes for Global settings and unit tests", "Improve quality of code in Steering"]'),
	(8, 'Sprint 45', 'Green', 8, 43, 49, 42, 7, 7, 0, 38, 4, 359, 407, 614, 0, 0.1395, 0.9767, '2019-08-08 00:00:00', '2019-09-02 00:00:00', '2020-04-30 15:40:00', '{"BASIC": 42, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Spillover from Sprint 44", "Audio Repository is set up"]'),
	(9, 'Sprint 46', 'Green', 8, 47, 48, 41, 7, 7, 0, 35, 6, 284, 285, 382, 0, 0.0213, 0.8723, '2019-09-05 00:00:00', '2019-09-16 00:00:00', '2020-04-30 15:52:15', '{"BASIC": 38, "FUTURE": 0, "ADVANCED": 3, "COMMERCIAL": 0}', '["Audio resource manager - first implementation", "Further improvements in time system", "Test design for printers", "Improve alarm condition handling on HMI", "Resolve multiple bugfixes for setting system"]'),
	(10, 'Sprint 47', 'Green', 9, 57, 63, 50, 13, 13, 0, 29, 21, 501, 556, 532, 1, 0.1053, 0.8772, '2019-09-19 00:00:00', '2019-10-07 00:00:00', '2020-04-30 16:00:01', '{"BASIC": 50, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Finalize first implementation of Audio Resource Manager", "Fix alarm light", "Fix some memory leaks in HMI", "Implement initial version of dynamic setting of thresholds in time system", "Improve inter steering communication for pacer and defibrillator (part 1)", "Fix various issues in alarm system"]'),
	(11, 'Sprint 48', 'Green', 7, 57, 57, 47, 10, 10, 0, 40, 7, 499, 499, 558, 0, 0.0000, 0.8246, '2019-10-10 00:00:00', '2019-10-28 00:00:00', '2020-04-30 16:06:57', '{"BASIC": 47, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Complete refactoring of inter-steering communication", "Connect IBP-daemon to inter-steering communication", "Show connection indicator icon in status line (incl. error indicator)", "Add new unit test cases for HMI", "Finish dynamic threshold calculation in time system", "Implement deferred change of settings in settings system"]'),
	(12, 'Sprint 49', 'Green', 9, 63, 64, 39, 25, 25, 0, 28, 11, 451, 452, 436, 0, 0.0159, 0.6190, '2019-10-31 00:00:00', '2019-11-18 00:00:00', '2020-04-30 16:17:05', '{"BASIC": 34, "FUTURE": 0, "ADVANCED": 5, "COMMERCIAL": 0}', '["Use text-ids instead of pure texts in HMI", "Add NIBP-related functionality to InterSteering", "Introduce new algorithm for offset calculation with dynamic threshold calculation in time system", "Align LinuxSensor with latest profile 1.1", "Use new IDLs for alarm suppression from alarm system in HMI"]'),
	(13, 'Sprint 50', 'Green', 8, 75, 82, 74, 8, 8, 0, 64, 10, 541, 576, 528, 0, 0.0933, 0.9867, '2019-11-21 00:00:00', '2019-12-09 00:00:00', '2020-04-30 16:31:56', '{"BASIC": 66, "FUTURE": 0, "ADVANCED": 8, "COMMERCIAL": 0}', '["Refactor playing audio files - use one common source code (Alarms, Metronome, Therapy)", "Status Line & Quick Menu: show battery capacities", "Create test design for Status Line and Pairing", "Finish offset calculation in time system", "Localization: Finalize text-ids in HMI"]'),
	(14, 'Sprint 51', 'Green', 7, 33, 33, 19, 14, 14, 0, 13, 6, 279, 271, 252, 0, 0.0000, 0.5758, '2019-12-12 00:00:00', '2020-01-06 00:00:00', '2020-04-30 16:38:51', '{"BASIC": 19, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Connect internal cameras to HMI", "Status Line: get patient info from ePCR", "Prepare repository for SW-Update feature", "Time system: implement weighted arithmetic mean in offset calculation", "Status Line: adjust system time", "Prepare Alarm system for yes/no notification"]'),
	(15, 'Sprint 52', 'Green', 8, 79, 78, 73, 5, 5, 0, 69, 4, 617, 585, 487, 1, 0.0127, 0.9241, '2020-01-09 00:00:00', '2020-01-27 00:00:00', '2020-04-30 16:46:36', '{"BASIC": 73, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Connect internal cameras to HMI", "Status Line: get patient info from ePCR", "SW-Update: parse SWPF meta data", "SW-Update: create interface UpdateIntern", "Settings System: implement blob-settings", "SW-Update: scan file system for SWPF", "Quick Menu: display of connection status", "SW-Update: create interface UpdateObserverIntern", "Alarm List: Yes/No-Notification", "Create communication between Intercom and LinkCreation"]'),
	(16, 'Sprint 53', 'Green', 7, 67, 69, 59, 10, 10, 0, 51, 8, 537, 552, 454, 0, 0.0299, 0.8806, '2020-01-30 00:00:00', '2020-02-17 00:00:00', '2020-04-30 16:53:36', '{"BASIC": 59, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Take picture from camera and save it on local storage", "Settings system documentation update (blob settings)", "ARM master volume control", "SW-Update - control updates on micro-Controllers", "Time system documentation udpate", "Aligning ARM to profile spec 1.0", "InterSteering - DECG-related part"]'),
	(17, 'Sprint 54', 'Green', 8, 58, 58, 44, 14, 14, 0, 37, 7, 501, 501, 583, 1, 0.0000, 0.7586, '2020-02-20 00:00:00', '2020-03-09 00:00:00', '2020-04-30 17:01:35', '{"BASIC": 44, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Adjust handling of alarm LEDs", "Synchronization of new setting values when nodes are connected", "SW-Update: Retrieve USB-path of USB devices", "Mapping from alarm conditions to user alarm ids", "SW-Update: Continue meta data handling"]'),
	(18, 'Sprint 55', 'Green', 8, 59, 59, 56, 3, 3, 0, 49, 7, 494, 496, 560, 0, 0.0000, 0.9492, '2020-03-12 00:00:00', '2020-03-30 00:00:00', '2020-04-30 17:10:19', '{"BASIC": 56, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["SW-Update: Be able to update firmware of USB devices (ECG, DE-ECG, NIBP)", "SW-Update: Be able to update firmware of HID controller", "SW-Update: Collect SWPF information", "SW-Update: Scan for SWPF on USB devices", "SW-Update: Implement validity checks for SWPF", "SW-Update: Collect local version information", "Bugfix: NIBP: base timestamp", "SW-Update: Copy SWPF to remote nodes", "Bugfix: camera flash light", "Bugfix: NIBP: intersteering communication"]'),
	(19, 'Sprint 56', 'Green', 1, 60, 61, 48, 13, 13, 0, 32, 16, 496, 501, 473, 1, 0.0167, 0.8000, '2020-04-02 00:00:00', '2020-04-20 00:00:00', '2020-04-30 17:17:49', '{"BASIC": 48, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["SW-Update: Show SW-Update widget", "SW-Update: gs-update: implement status machine", "Bugfix: Make unit tests of init component green again", "Bugfix: Make unit tests of global settings component green again", "SW-Update: Report staged SWPFs", "SW-Update: Implement SWPF pre-distribution checks", "Bugfix: Fix alarm list", "Bugfix: Adjust access to system controller for BSP 0.16.0"]'),
	(20, 'Sprint 57', 'Green', 7, 61, 56, 8, 48, 8, 40, 8, 0, 544, 513, 328, 3, 0.0820, 0.1311, '2020-04-23 00:00:00', '2020-05-11 00:00:00', '2020-05-04 15:45:34', '{"BASIC": 8, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["We want to select one SWPF for installation, distribute it to all nodes and install the single SW-images", "SW-Update: SW-Update obeys therapy mode", "SW-Update: Extract SW-images and install to smart sensors", "SW-Update: Extract SW-images and install to System Controller, Host, Inductive Transformers", "SW-Update: Implement pull-based staging", "SW-Update: Mount USB devices", "Bugfix: Covert Mode works correlty after system start", "Others: Create StorageFilePath Library", "Others: Improvement of time system"]');
/*!40000 ALTER TABLE sprints.team_green ENABLE KEYS */;

-- Dumping structure for table sprints.team_orange
CREATE TABLE IF NOT EXISTS sprints.team_orange (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `team_name` varchar(64) DEFAULT NULL,
  `team_member_count` decimal(2,0) DEFAULT '0',
  `on_begin_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `on_end_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_sp_sum` decimal(3,0) DEFAULT '0',
  `not_finished_sp_sum` decimal(3,0) DEFAULT '0',
  `to_do_sp_sum` decimal(3,0) DEFAULT '0',
  `in_progress_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_stories_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_bugs_sp_sum` decimal(3,0) DEFAULT '0',
  `time_estimation` decimal(4,0) DEFAULT '0',
  `time_planned` decimal(4,0) DEFAULT '0',
  `time_spent` decimal(4,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_orange: ~17 rows (approximately)
/*!40000 ALTER TABLE sprints.team_orange DISABLE KEYS */;
INSERT INTO sprints.team_orange (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 40', 'Orange', 2, 25, 25, 9, 16, 16, 0, 9, 0, 116, 116, 93, 0, 0.0000, 0.3600, '2019-04-25 00:00:00', '2019-05-15 00:00:00', '2020-04-30 14:19:57', '{"BASIC": 9, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(2, 'Sprint 41', 'Orange', 2, 34, 34, 29, 5, 5, 0, 29, 0, 190, 190, 93, 0, 0.0000, 0.8529, '2019-05-16 00:00:00', '2019-06-05 00:00:00', '2020-04-30 14:37:38', '{"BASIC": 29, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(3, 'Sprint 42', 'Orange', 1, 39, 39, 0, 39, 39, 0, 0, 0, 134, 134, 210, 0, 0.0000, 0.0000, '2019-06-06 00:00:00', '2019-06-26 00:00:00', '2020-04-30 14:54:42', '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]'),
	(4, 'Sprint 43', 'Orange', 1, 52, 52, 0, 52, 52, 0, 0, 0, 204, 204, 220, 0, 0.0000, 0.0000, '2019-06-27 00:00:00', '2019-07-15 00:00:00', '2020-04-30 15:07:05', '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Radio connection can be established on beta HW", "Emlix-Toolchain in gitlab-ci", "Decision for a FHIR types code generator framework"]'),
	(5, 'Sprint 44', 'Orange', 1, 31, 31, 0, 31, 31, 0, 0, 0, 303, 303, 367, 0, 0.0000, 0.0000, '2019-07-18 00:00:00', '2019-08-05 00:00:00', '2020-04-30 15:19:51', '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Plan for communicating with the radio modem", "BT classic capabilities have been researched", "Decided what code generation framework to use", "Connection to a wifi network is possible"]'),
	(6, 'Sprint 45', 'Orange', 0, 34, 34, 0, 34, 34, 0, 0, 0, 258, 258, 303, 0, 0.0000, 0.0000, '2019-08-08 00:00:00', '2019-09-02 00:00:00', '2020-04-30 15:40:00', '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Radio Modem Project Setup", "Radio Network Connection Establishement", "Research for Linux integration of radio modems", "BT classic capabilities have been researched"]'),
	(7, 'Sprint 46', 'Orange', 1, 31, 31, 10, 21, 21, 0, 10, 0, 303, 303, 367, 0, 0.0000, 0.3226, '2019-09-05 00:00:00', '2019-09-16 00:00:00', '2020-04-30 15:52:15', '{"BASIC": 10, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Radio Modem Project Setup", "BT classic capabilities have been researched", "Research for Linux integration of radio modems", "Establishing External WiFi connection", "Linkcreation research for factoring out the wifi code to be reused by telemetry"]'),
	(8, 'Sprint 47', 'Orange', 1, 26, 26, 0, 26, 26, 0, 0, 0, 253, 253, 282, 0, 0.0000, 0.0000, '2019-09-19 00:00:00', '2019-10-07 00:00:00', '2020-04-30 16:00:01', '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Complete BT research", "Complete GSM modem library research", "Establish an external WiFi connection"]'),
	(9, 'Sprint 48', 'Orange', 2, 29, 29, 26, 3, 3, 0, 26, 0, 185, 185, 265, 0, 0.0000, 0.8966, '2019-10-10 00:00:00', '2019-10-28 00:00:00', '2020-04-30 16:06:57', '{"BASIC": 26, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Complete BT research", "Expose WiFi status", "Establish an external WiFi connection", "Complete GSM modem library research or email client library research (depends on the availability of the USB GSM board)"]'),
	(10, 'Sprint 49', 'Orange', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.0000, 0.0000, '2019-10-31 00:00:00', '2019-11-18 00:00:00', '2020-04-30 16:17:05', '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Complete BT research"]'),
	(11, 'Sprint 50', 'Orange', 2, 31, 31, 18, 13, 13, 0, 18, 0, 166, 166, 175, 0, 0.0000, 0.5806, '2019-11-21 00:00:00', '2019-12-09 00:00:00', '2020-04-30 16:31:56', '{"BASIC": 18, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Finish network libraries research", "Propagate wifi settings via BT LE", "Create IDL, Profile and implement a virtual sensor (incl. foundation) for WiFi"]'),
	(12, 'Sprint 51', 'Orange', 1, 23, 23, 13, 10, 10, 0, 13, 0, 146, 146, 235, 0, 0.0000, 0.5652, '2019-12-12 00:00:00', '2020-01-06 00:00:00', '2020-04-30 16:38:51', '{"BASIC": 13, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["BT LE advertisment concept", "Implementation of WiFi virtual sensor has begun", "BT QT patch integration for BT interface selection"]'),
	(13, 'Sprint 52', 'Orange', 1, 15, 28, 2, 26, 26, 0, 2, 0, 112, 196, 364, 0, 0.8667, 0.1333, '2020-01-09 00:00:00', '2020-01-27 00:00:00', '2020-04-30 16:46:36', '{"BASIC": 2, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["BT LE advertisment implementation and system service", "BT QT patch integration for BT interface selection"]'),
	(14, 'Sprint 53', 'Orange', 1, 39, 39, 13, 26, 26, 0, 13, 0, 268, 268, 393, 0, 0.0000, 0.3333, '2020-01-30 00:00:00', '2020-02-17 00:00:00', '2020-04-30 16:53:36', '{"BASIC": 13, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["BT LE advertisment implementation and system service", "WiFi Virtual Sensor & Foundation", "E-Mail sending with attachments (hardcoded content)"]'),
	(15, 'Sprint 54', 'Orange', 2, 101, 93, 51, 42, 42, 0, 51, 0, 347, 323, 541, 0, 0.0792, 0.5050, '2020-02-20 00:00:00', '2020-03-09 00:00:00', '2020-04-30 17:01:35', '{"BASIC": 51, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["WiFi foundation, intercom and steering components are implemented, so we can use those for a UI-Button to connect/disconnect from a hardcoded WiFi."]'),
	(16, 'Sprint 55', 'Orange', 2, 59, 59, 28, 31, 20, 11, 28, 0, 352, 352, 703, 0, 0.0000, 0.4746, '2020-03-12 00:00:00', '2020-03-30 00:00:00', '2020-04-30 17:10:19', '{"BASIC": 28, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Team-Rampup (integration of Lviv and GS developers in one team)"]'),
	(17, 'Sprint 56', 'Orange', 0, 37, 65, 44, 21, 8, 13, 37, 7, 158, 244, 497, 0, 0.7568, 1.1892, '2020-04-02 00:00:00', '2020-04-20 00:00:00', '2020-04-30 17:17:49', '{"BASIC": 44, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Stabilize existing components and get an understanding of the APIs of Settings System and Internode Steering Communication"]'),
	(18, 'Sprint 57', 'Orange', 6, 62, 65, 13, 52, 27, 25, 13, 0, 335, 349, 326, 0, 0.0484, 0.2097, '2020-04-23 00:00:00', '2020-05-11 00:00:00', '2020-05-04 15:45:34', '{"BASIC": 13, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Green Builds after the sprint and a broader knowledge about networkmanager & modemmanager"]');
/*!40000 ALTER TABLE sprints.team_orange ENABLE KEYS */;

-- Dumping structure for table sprints.team_red
CREATE TABLE IF NOT EXISTS sprints.team_red (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `team_name` varchar(64) DEFAULT NULL,
  `team_member_count` decimal(2,0) DEFAULT '0',
  `on_begin_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `on_end_planned_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_sp_sum` decimal(3,0) DEFAULT '0',
  `not_finished_sp_sum` decimal(3,0) DEFAULT '0',
  `to_do_sp_sum` decimal(3,0) DEFAULT '0',
  `in_progress_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_stories_sp_sum` decimal(3,0) DEFAULT '0',
  `finished_bugs_sp_sum` decimal(3,0) DEFAULT '0',
  `time_estimation` decimal(4,0) DEFAULT '0',
  `time_planned` decimal(4,0) DEFAULT '0',
  `time_spent` decimal(4,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `delta_sp` double(7,4) DEFAULT '0.0000',
  `planned_sp_closed` double(7,4) DEFAULT '0.0000',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_red: ~17 rows (approximately)
/*!40000 ALTER TABLE sprints.team_red DISABLE KEYS */;
INSERT INTO sprints.team_red (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `updated`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 38', 'Red', 8, 55, 67, 67, 0, 0, 0, 65, 2, 459, 539, 428, 0, 0.2182, 1.2182, '2019-03-14 00:00:00', '2019-04-03 00:00:00', '2020-04-30 13:11:23', '{"BASIC": 67, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Pacer widget according to wireframe v2.3", "Control widget connected to steering to TAP", "Decision tree up2date to reflect EA from 13 Mar 2019", "Test specification created and approved for Pacer functionality from Basic version"]'),
	(2, 'Sprint 39', 'Red', 8, 32, 61, 56, 5, 5, 0, 56, 0, 168, 384, 318, 0, 0.9062, 1.7500, '2019-04-04 00:00:00', '2019-04-24 00:00:00', '2020-04-30 14:00:03', '{"BASIC": 56, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["CPR cycle widget inline with wireframes and ready for communication with Steering", "All therapy control widgets for Manual defibrillation and Pacer will be inline with the wireframes", "First version of HID sensor dispatcher ready for integration"]'),
	(3, 'Sprint 40', 'Red', 6, 36, 43, 43, 0, 0, 0, 41, 2, 224, 276, 228, 0, 0.1944, 1.1944, '2019-04-25 00:00:00', '2019-05-15 00:00:00', '2020-04-30 14:19:57', '{"BASIC": 43, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["First step of the integration - implement prerequisites for ECG sensor", "Finish Basic scope of Manual defibrillation from the Host SW side", "Show progress of CPR cycle started in Steering", "Finish Basic scope of Pacemaker from the Host SW side"]'),
	(4, 'Sprint 41', 'Red', 6, 49, 57, 57, 0, 0, 0, 55, 2, 346, 382, 311, 0, 0.1633, 1.1633, '2019-05-16 00:00:00', '2019-06-05 00:00:00', '2020-04-30 14:37:38', '{"BASIC": 54, "FUTURE": 0, "ADVANCED": 3, "COMMERCIAL": 0}', '["Design test cases fro Manual defibrillation and AED", "Start implementation of corPatch CPR", "Finish basic scope of CPR cycle"]'),
	(5, 'Sprint 42', 'Red', 6, 33, 47, 47, 0, 0, 0, 35, 12, 193, 226, 171, 0, 0.4242, 1.4242, '2019-06-06 00:00:00', '2019-06-26 00:00:00', '2020-04-30 14:54:42', '{"BASIC": 47, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Finish basic scope for Manual defibrillation", "Finish basic scope for AED", "Finish basic scope for Cardioversion", "Finish basic scope for Pacer"]'),
	(6, 'Sprint 43', 'Red', 5, 45, 46, 46, 0, 0, 0, 44, 2, 246, 247, 215, 0, 0.0222, 1.0222, '2019-06-27 00:00:00', '2019-07-15 00:00:00', '2020-04-30 15:07:04', '{"BASIC": 46, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["CPR cycle", "Defibrillator is discharged when press home button - change therapy mode", "Use can see DE lead within the monitoring layout provided by TAP", "The STIM marker is controlled by TAP sensor/simulator - blink with frequency set in Pacer", "PAM working light can be turn on or off by GUI and multi-function button", "Event system is more stable or target hardware"]'),
	(7, 'Sprint 44', 'Red', 5, 33, 37, 35, 2, 2, 0, 17, 18, 205, 233, 210, 0, 0.1212, 1.0606, '2019-07-18 00:00:00', '2019-08-05 00:00:00', '2020-04-30 15:19:51', '{"BASIC": 35, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Improve stability of therapy functionality (resolve several bugs reported by testers)", "Improve CPR cycle feature", "Display ECG curve on Beta3 hardware with use of internal ECG sensor on PUK (support for Black team)"]'),
	(8, 'Sprint 45', 'Red', 5, 29, 41, 41, 0, 0, 0, 30, 11, 157, 195, 174, 0, 0.4138, 1.4138, '2019-08-08 00:00:00', '2019-09-02 00:00:00', '2020-04-30 15:39:59', '{"BASIC": 41, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Spillovers from Sprint 44", "Integration workshop in Kaufering", "Vacation time mostly :-)"]'),
	(9, 'Sprint 46', 'Red', 5, 32, 32, 32, 0, 0, 0, 30, 2, 230, 230, 189, 0, 0.0000, 1.0000, '2019-09-05 00:00:00', '2019-09-16 00:00:00', '2020-04-30 15:52:15', '{"BASIC": 32, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Spillover from Sprint 45", "Continue on follow up tasks from last Integration workshop", "Improvements of the Therapy instruction - notifications", "Minor bug fixes and improvements", "Test case specification for PAM Work Light"]'),
	(10, 'Sprint 47', 'Red', 6, 42, 47, 44, 3, 3, 0, 36, 8, 305, 322, 227, 0, 0.1190, 1.0476, '2019-09-19 00:00:00', '2019-10-07 00:00:00', '2020-04-30 16:00:01', '{"BASIC": 38, "FUTURE": 0, "ADVANCED": 6, "COMMERCIAL": 0}', '["Integration workshop \'Black channel\' 23 Sep 2019 till 27 Sep 2019", "General HMI improvements", "Continue in implementation of General Therapy functionality"]'),
	(11, 'Sprint 48', 'Red', 6, 41, 51, 43, 8, 8, 0, 39, 4, 304, 343, 318, 0, 0.2439, 1.0488, '2019-10-10 00:00:00', '2019-10-28 00:00:00', '2020-04-30 16:06:57', '{"BASIC": 37, "FUTURE": 0, "ADVANCED": 6, "COMMERCIAL": 0}', '["General Therapy - stabilization", "General Therapy - indication of shock readiness", "CPR cycle - enhancement of continues mode"]'),
	(12, 'Sprint 49', 'Red', 6, 49, 51, 46, 5, 5, 0, 38, 8, 334, 339, 343, 0, 0.0408, 0.9388, '2019-10-31 00:00:00', '2019-11-18 00:00:00', '2020-04-30 16:17:05', '{"BASIC": 34, "FUTURE": 0, "ADVANCED": 12, "COMMERCIAL": 0}', '["Solving parts of HMI Refactoring", "AED - Auto start analyse", "Bug fixing", "preShock CPR - prepare feature implementation", "D-ECG - prepare changes of functionality"]'),
	(13, 'Sprint 50', 'Red', 6, 49, 49, 47, 2, 2, 0, 37, 10, 391, 391, 381, 0, 0.0000, 0.9592, '2019-11-21 00:00:00', '2019-12-09 00:00:00', '2020-04-30 16:31:55', '{"BASIC": 41, "FUTURE": 0, "ADVANCED": 6, "COMMERCIAL": 0}', '["Fix CPR cycle", "Start working on Placing therapy electrodes", "Go on with D-ECG changes", "Bug fixing"]'),
	(14, 'Sprint 51', 'Red', 5, 22, 23, 17, 6, 6, 0, 17, 0, 164, 165, 128, 0, 0.0455, 0.7727, '2019-12-12 00:00:00', '2020-01-06 00:00:00', '2020-04-30 16:38:51', '{"BASIC": 14, "FUTURE": 0, "ADVANCED": 3, "COMMERCIAL": 0}', '["Continue in Treatment development", "Start with basic scope of the \\"Quick Menu\\""]'),
	(15, 'Sprint 52', 'Red', 5, 39, 41, 41, 0, 0, 0, 30, 11, 300, 312, 247, 0, 0.0513, 1.0513, '2020-01-09 00:00:00', '2020-01-27 00:00:00', '2020-04-30 16:46:36', '{"BASIC": 33, "FUTURE": 0, "ADVANCED": 8, "COMMERCIAL": 0}', '["Continue in quick setting implementation", "Steering stuff"]'),
	(16, 'Sprint 53', 'Red', 5, 35, 35, 35, 0, 0, 0, 25, 10, 277, 277, 229, 0, 0.0000, 1.0000, '2020-01-30 00:00:00', '2020-02-17 00:00:00', '2020-04-30 16:53:35', '{"BASIC": 18, "FUTURE": 0, "ADVANCED": 17, "COMMERCIAL": 0}', '["Continue in quick setting implementation", "Bug Fixing", "First part of Testload implementation"]'),
	(17, 'Sprint 54', 'Red', 5, 43, 47, 47, 0, 0, 0, 41, 6, 300, 321, 242, 0, 0.0930, 1.0930, '2020-02-20 00:00:00', '2020-03-09 00:00:00', '2020-04-30 17:01:35', '{"BASIC": 16, "FUTURE": 0, "ADVANCED": 28, "COMMERCIAL": 3}', '["To enable the RD1812 to play the sounds in therapy modes, to complete multiple functionalities", "To transform the look of the therapy widgets in a new design, to ease the work of the user with the new clear design", "To start the implementation of the defibrillator test, to enable this security feature", "To finalize the synchronisation of CPR cycle and Metronome, to give the user a reliable guidance in every resuscitation", "To perform regression test of basic therapy functionality on new PAM (DE-ECG firmware v0.6.0) and identify any bugs"]'),
	(18, 'Sprint 55', 'Red', 5, 47, 51, 48, 3, 3, 0, 40, 8, 373, 395, 310, 0, 0.0851, 1.0213, '2020-03-12 00:00:00', '2020-03-30 00:00:00', '2020-04-30 17:10:18', '{"BASIC": 24, "FUTURE": 0, "ADVANCED": 24, "COMMERCIAL": 0}', '["We work together on the optimisation of the system, the defibrillation modes and the quick settings."]'),
	(19, 'Sprint 56', 'Red', 0, 44, 47, 42, 5, 5, 0, 37, 5, 248, 256, 256, 0, 0.0682, 0.9545, '2020-04-02 00:00:00', '2020-04-20 00:00:00', '2020-04-30 17:17:49', '{"BASIC": 21, "FUTURE": 0, "ADVANCED": 21, "COMMERCIAL": 0}', '["We will start the implementation of Trends-Feature. Gaps are filled with stabilisations and bug fixing"]'),
	(20, 'Sprint 57', 'Red', 5, 40, 40, 12, 28, 13, 15, 10, 2, 261, 261, 168, 0, 0.0000, 0.3000, '2020-04-23 00:00:00', '2020-05-11 00:00:00', '2020-05-04 15:45:34', '{"BASIC": 4, "FUTURE": 0, "ADVANCED": 8, "COMMERCIAL": 0}', '["The stabilisation of the system in a \\"green built\\" is our goal"]');
/*!40000 ALTER TABLE sprints.team_red ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
