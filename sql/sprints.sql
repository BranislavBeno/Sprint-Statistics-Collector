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

-- Dumping structure for table sprints.engineer
CREATE TABLE IF NOT EXISTS sprints.engineer (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `engineer_name` varchar(64) DEFAULT NULL,
  `sprint` varchar(64) DEFAULT NULL,
  `finished_sp` decimal(3,0) DEFAULT '0',
  `not_finished_sp` decimal(3,0) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.engineer: ~7 rows (approximately)
/*!40000 ALTER TABLE `engineer` DISABLE KEYS */;
INSERT INTO sprints.engineer (`id`, `engineer_name`, `sprint`, `finished_sp`, `not_finished_sp`) VALUES
	(1, 'Adamik Silvester', 'Sprint 55', 3, 0),
	(2, 'Bukovy Lubomir', 'Sprint 55', 8, 0),
	(3, 'Caban Michal', 'Sprint 55', 3, 0),
	(4, 'Dunda Michal', 'Sprint 55', 3, 0),
	(5, 'Ehrlich Andrei', 'Sprint 55', 0, 5),
	(6, 'Fura Martin', 'Sprint 55', 3, 0),
	(7, 'Fuzik Radoslav', 'Sprint 55', 8, 0),
	(8, 'Hrdy Radovan', 'Sprint 55', 7, 0),
	(9, 'Janeckova Miriam', 'Sprint 55', 0, 8),
	(10, 'Janis Andrej ', 'Sprint 55', 4, 0),
	(11, 'Janssen Siegfried', 'Sprint 55', 1, 0),
	(12, 'Kobolka Vladimir', 'Sprint 55', 0, 5),
	(13, 'Kocvara Lubos', 'Sprint 55', 9, 0),
	(14, 'Kupriianov Aleksandr', 'Sprint 55', 10, 0),
	(15, 'Lashini Milad', 'Sprint 55', 5, 0),
	(16, 'Luliak Milan', 'Sprint 55', 5, 0),
	(17, 'Lupberger Matthias', 'Sprint 55', 0, 3),
	(18, 'Mlich Julius', 'Sprint 55', 9, 0),
	(19, 'Moravcik Michal', 'Sprint 55', 7, 0),
	(20, 'Obona Jozef', 'Sprint 55', 13, 0),
	(21, 'Paulen Juraj', 'Sprint 55', 0, 5),
	(22, 'Pavlik Zdenko', 'Sprint 55', 6, 0),
	(23, 'Pekar Jakub', 'Sprint 55', 6, 0),
	(24, 'Schmid Hubert', 'Sprint 55', 0, 3),
	(25, 'Tyczynski Lukasz', 'Sprint 55', 5, 0),
	(26, 'Veselovsky Ivan', 'Sprint 55', 1, 0),
	(27, 'Zucha Vladimir', 'Sprint 55', 5, 0),
	(28, 'Ehrlich Andrei', 'Sprint 56', 0, 5),
	(29, 'Janeckova Miriam', 'Sprint 56', 0, 8),
	(30, 'Kobolka Vladimir', 'Sprint 56', 5, 0),
	(31, 'Kocvara Lubos', 'Sprint 56', 1, 0),
	(32, 'Paulen Juraj', 'Sprint 56', 0, 5),
	(33, 'Schmid Hubert', 'Sprint 56', 0, 3),
	(34, 'Veselovsky Ivan', 'Sprint 56', 0, 1);
/*!40000 ALTER TABLE `engineer` ENABLE KEYS */;

-- Dumping structure for table sprints.sprint
CREATE TABLE IF NOT EXISTS sprints.sprint (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `refined_SP` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.sprint: ~4 rows (approximately)
/*!40000 ALTER TABLE `sprint` DISABLE KEYS */;
INSERT INTO sprints.sprint (`id`, `sprint`, `refined_SP`) VALUES
	(1, 'Sprint 57', '{"BASIC": 100, "FUTURE": 0, "ADVANCED": 28, "COMMERCIAL": 0}'),
	(2, 'Sprint 58', '{"BASIC": 74, "FUTURE": 0, "ADVANCED": 16, "COMMERCIAL": 0}'),
	(3, 'Sprint 59', '{"BASIC": 10, "FUTURE": 0, "ADVANCED": 47, "COMMERCIAL": 0}'),
	(4, 'Sprint 60', '{"BASIC": 13, "FUTURE": 0, "ADVANCED": 18, "COMMERCIAL": 0}'),
	(5, 'Sprint 56', '{"BASIC": 192, "FUTURE": 0, "ADVANCED": 34, "COMMERCIAL": 0}');
/*!40000 ALTER TABLE `sprint` ENABLE KEYS */;

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
  `time_estimation` decimal(3,0) DEFAULT '0',
  `time_planned` decimal(3,0) DEFAULT '0',
  `time_spent` decimal(3,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `delta_sp` double(5,2) DEFAULT '0.00',
  `planned_sp_closed` double(5,2) DEFAULT '0.00',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_black: ~1 rows (approximately)
/*!40000 ALTER TABLE sprints.team_black DISABLE KEYS */;
INSERT INTO sprints.team_black (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 56', 'Black', 4, 71, 78, 34, 44, 14, 30, 26, 8, 490, 539, 456, 0, 0.10, 0.48, '2020-04-02 00:00:00', '2020-04-20 00:00:00', '{"BASIC": 34, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["No spillovers"]');
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
  `time_estimation` decimal(3,0) DEFAULT '0',
  `time_planned` decimal(3,0) DEFAULT '0',
  `time_spent` decimal(3,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `delta_sp` double(5,2) DEFAULT '0.00',
  `planned_sp_closed` double(5,2) DEFAULT '0.00',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_blue: ~1 rows (approximately)
/*!40000 ALTER TABLE sprints.team_blue DISABLE KEYS */;
INSERT INTO sprints.team_blue (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 56', 'Blue', 2, 43, 44, 22, 22, 3, 19, 21, 1, 383, 385, 268, 0, 0.02, 0.51, '2020-04-02 00:00:00', '2020-04-20 00:00:00', '{"BASIC": 22, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '[""]');
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
  `time_estimation` decimal(3,0) DEFAULT '0',
  `time_planned` decimal(3,0) DEFAULT '0',
  `time_spent` decimal(3,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `delta_sp` double(5,2) DEFAULT '0.00',
  `planned_sp_closed` double(5,2) DEFAULT '0.00',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_green: ~1 rows (approximately)
/*!40000 ALTER TABLE sprints.team_green DISABLE KEYS */;
INSERT INTO sprints.team_green (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 56', 'Green', 1, 60, 61, 32, 29, 0, 29, 22, 10, 472, 477, 381, 1, 0.02, 0.53, '2020-04-02 00:00:00', '2020-04-20 00:00:00', '{"BASIC": 32, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}', '["Show SW-Update widget (SW-Update)", "gs-update: implement status machine (SW-Update)", "make unit tests of init component green again (Bugfix)", "make unit tests of global settings component green again (Bugfix)", "report staged SWPFs (SW-Update)", "implement SWPF pre-distribution checks (SW-Update)", "fix alarm list (Bugfix)", "adjust access to system controller for BSP 0.16.0 (Bugfix)"]');
/*!40000 ALTER TABLE sprints.team_green ENABLE KEYS */;

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
  `time_estimation` decimal(3,0) DEFAULT '0',
  `time_planned` decimal(3,0) DEFAULT '0',
  `time_spent` decimal(3,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `delta_sp` double(5,2) DEFAULT '0.00',
  `planned_sp_closed` double(5,2) DEFAULT '0.00',
  `sprint_start` datetime DEFAULT NULL,
  `sprint_end` datetime DEFAULT NULL,
  `finished_sp` json DEFAULT NULL,
  `sprint_goals` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_red: ~1 rows (approximately)
/*!40000 ALTER TABLE sprints.team_red DISABLE KEYS */;
INSERT INTO sprints.team_red (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `sprint_start`, `sprint_end`, `finished_sp`, `sprint_goals`) VALUES
	(1, 'Sprint 56', 'Red', 0, 44, 47, 31, 16, 0, 16, 26, 5, 296, 304, 204, 0, 0.07, 0.70, '2020-04-02 00:00:00', '2020-04-20 00:00:00', '{"BASIC": 15, "FUTURE": 0, "ADVANCED": 16, "COMMERCIAL": 0}', '["We will start the implementation of Trends-Feature. Gaps are filled with stabilisations and bug fixing."]');
/*!40000 ALTER TABLE sprints.team_red ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
