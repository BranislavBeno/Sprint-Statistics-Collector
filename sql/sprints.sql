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
  `finished_sp` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_black: ~3 rows (approximately)
/*!40000 ALTER TABLE sprints.team_black DISABLE KEYS */;
INSERT INTO sprints.team_black (`sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `finished_sp`) VALUES
	('Sprint 54', 'Black', 7, 70, 70, 53, 17, 14, 3, 39, 14, 461, 449, 471, 1, 0.00, 0.76, '{"BASIC": 40, "FUTURE": 0, "ADVANCED": 13, "COMMERCIAL": 0}'),
	('Sprint 55', 'Black', 7, 78, 83, 40, 43, 16, 27, 34, 6, 576, 626, 670, 1, 0.06, 0.51, '{"BASIC": 31, "FUTURE": 0, "ADVANCED": 9, "COMMERCIAL": 0}'),
	('Sprint 56', 'Black', 4, 71, 71, 4, 67, 28, 39, 3, 1, 490, 490, 234, 0, 0.00, 0.06, '{"BASIC": 1, "FUTURE": 0, "ADVANCED": 3, "COMMERCIAL": 0}');
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
  `finished_sp` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_blue: ~3 rows (approximately)
/*!40000 ALTER TABLE sprints.team_blue DISABLE KEYS */;
INSERT INTO sprints.team_blue (`sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `finished_sp`) VALUES
	('Sprint 54', 'Blue', 8, 25, 25, 25, 0, 0, 0, 25, 0, 184, 184, 127, 0, 0.00, 1.00, '{"BASIC": 25, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}'),
	('Sprint 55', 'Blue', 7, 42, 43, 43, 0, 0, 0, 41, 2, 298, 298, 291, 0, 0.02, 1.02, '{"BASIC": 43, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}'),
	('Sprint 56', 'Blue', 2, 43, 43, 0, 43, 16, 27, 0, 0, 399, 399, 63, 0, 0.00, 0.00, '{"BASIC": 0, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}');
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
  `finished_sp` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_green: ~3 rows (approximately)
/*!40000 ALTER TABLE sprints.team_green DISABLE KEYS */;
INSERT INTO sprints.team_green (`sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `finished_sp`) VALUES
	('Sprint 54', 'Green', 8, 58, 58, 44, 14, 11, 3, 37, 7, 501, 501, 571, 1, 0.00, 0.76, '{"BASIC": 44, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}'),
	('Sprint 55', 'Green', 8, 59, 59, 56, 3, 0, 3, 49, 7, 494, 496, 548, 0, 0.00, 0.95, '{"BASIC": 56, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}'),
	('Sprint 56', 'Green', 1, 60, 60, 3, 57, 17, 40, 1, 2, 472, 472, 112, 2, 0.00, 0.05, '{"BASIC": 3, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}');
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
  `finished_sp` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_red: ~3 rows (approximately)
/*!40000 ALTER TABLE sprints.team_red DISABLE KEYS */;
INSERT INTO sprints.team_red (`sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `finished_sp`) VALUES
	('Sprint 54', 'Red', 5, 43, 47, 47, 0, 0, 0, 41, 6, 300, 321, 242, 0, 0.09, 1.09, '{"BASIC": 16, "FUTURE": 0, "ADVANCED": 28, "COMMERCIAL": 3}'),
	('Sprint 55', 'Red', 5, 47, 51, 48, 3, 3, 0, 40, 8, 373, 395, 306, 0, 0.09, 1.02, '{"BASIC": 24, "FUTURE": 0, "ADVANCED": 24, "COMMERCIAL": 0}'),
	('Sprint 56', 'Red', 0, 44, 44, 3, 41, 21, 20, 3, 0, 296, 296, 54, 0, 0.00, 0.07, '{"BASIC": 1, "FUTURE": 0, "ADVANCED": 2, "COMMERCIAL": 0}');
/*!40000 ALTER TABLE sprints.team_red ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
