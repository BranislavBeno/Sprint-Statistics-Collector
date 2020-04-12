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

-- Dumping data for table sprints.engineer: ~34 rows (approximately)
/*!40000 ALTER TABLE sprints.engineer DISABLE KEYS */;
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
/*!40000 ALTER TABLE sprints.engineer ENABLE KEYS */;

-- Dumping structure for table sprints.sprint
CREATE TABLE IF NOT EXISTS sprints.sprint (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `refined_SP` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.sprint: ~5 rows (approximately)
/*!40000 ALTER TABLE sprints.sprint DISABLE KEYS */;
INSERT INTO sprints.sprint (`id`, `sprint`, `refined_SP`) VALUES
	(1, 'Sprint 57', '{"BASIC": 100, "FUTURE": 0, "ADVANCED": 28, "COMMERCIAL": 0}'),
	(2, 'Sprint 58', '{"BASIC": 74, "FUTURE": 0, "ADVANCED": 16, "COMMERCIAL": 0}'),
	(3, 'Sprint 59', '{"BASIC": 10, "FUTURE": 0, "ADVANCED": 47, "COMMERCIAL": 0}'),
	(4, 'Sprint 60', '{"BASIC": 13, "FUTURE": 0, "ADVANCED": 18, "COMMERCIAL": 0}'),
	(5, 'Sprint 56', '{"BASIC": 192, "FUTURE": 0, "ADVANCED": 34, "COMMERCIAL": 0}');
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
  `time_estimation` decimal(3,0) DEFAULT '0',
  `time_planned` decimal(3,0) DEFAULT '0',
  `time_spent` decimal(3,0) DEFAULT '0',
  `not_closed_high_prior_stories` decimal(3,0) DEFAULT '0',
  `delta_sp` double(5,2) DEFAULT '0.00',
  `planned_sp_closed` double(5,2) DEFAULT '0.00',
  `finished_sp` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_black: ~3 rows (approximately)
/*!40000 ALTER TABLE sprints.team_black DISABLE KEYS */;
INSERT INTO sprints.team_black (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `finished_sp`) VALUES
	(1, 'Sprint 54', 'Black', 7, 70, 70, 53, 17, 14, 3, 39, 14, 461, 449, 471, 1, 0.00, 0.76, '{"BASIC": 40, "FUTURE": 0, "ADVANCED": 13, "COMMERCIAL": 0}'),
	(2, 'Sprint 55', 'Black', 7, 78, 83, 40, 43, 24, 19, 34, 6, 576, 626, 721, 1, 0.06, 0.51, '{"BASIC": 31, "FUTURE": 0, "ADVANCED": 9, "COMMERCIAL": 0}'),
	(3, 'Sprint 56', 'Black', 4, 71, 77, 25, 52, 20, 32, 18, 7, 490, 533, 348, 0, 0.08, 0.35, '{"BASIC": 25, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table sprints.team_blue: ~3 rows (approximately)
/*!40000 ALTER TABLE sprints.team_blue DISABLE KEYS */;
INSERT INTO sprints.team_blue (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `finished_sp`) VALUES
	(1, 'Sprint 54', 'Blue', 8, 25, 25, 25, 0, 0, 0, 25, 0, 184, 184, 127, 0, 0.00, 1.00, '{"BASIC": 25, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}'),
	(2, 'Sprint 55', 'Blue', 7, 42, 43, 43, 0, 0, 0, 41, 2, 298, 298, 291, 0, 0.02, 1.02, '{"BASIC": 43, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}'),
	(3, 'Sprint 56', 'Blue', 2, 43, 44, 11, 33, 8, 25, 10, 1, 399, 401, 146, 0, 0.02, 0.26, '{"BASIC": 11, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}');
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
INSERT INTO sprints.team_green (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `finished_sp`) VALUES
	(1, 'Sprint 54', 'Green', 8, 58, 58, 44, 14, 11, 3, 37, 7, 501, 501, 571, 1, 0.00, 0.76, '{"BASIC": 44, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}'),
	(2, 'Sprint 55', 'Green', 8, 59, 59, 56, 3, 0, 3, 49, 7, 494, 496, 555, 0, 0.00, 0.95, '{"BASIC": 56, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}'),
	(3, 'Sprint 56', 'Green', 1, 60, 61, 12, 49, 12, 37, 4, 8, 472, 477, 232, 1, 0.02, 0.20, '{"BASIC": 12, "FUTURE": 0, "ADVANCED": 0, "COMMERCIAL": 0}');
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
INSERT INTO sprints.team_red (`id`, `sprint`, `team_name`, `team_member_count`, `on_begin_planned_sp_sum`, `on_end_planned_sp_sum`, `finished_sp_sum`, `not_finished_sp_sum`, `to_do_sp_sum`, `in_progress_sp_sum`, `finished_stories_sp_sum`, `finished_bugs_sp_sum`, `time_estimation`, `time_planned`, `time_spent`, `not_closed_high_prior_stories`, `delta_sp`, `planned_sp_closed`, `finished_sp`) VALUES
	(1, 'Sprint 54', 'Red', 5, 43, 47, 47, 0, 0, 0, 41, 6, 300, 321, 242, 0, 0.09, 1.09, '{"BASIC": 16, "FUTURE": 0, "ADVANCED": 28, "COMMERCIAL": 3}'),
	(2, 'Sprint 55', 'Red', 5, 47, 51, 48, 3, 3, 0, 40, 8, 373, 395, 309, 0, 0.09, 1.02, '{"BASIC": 24, "FUTURE": 0, "ADVANCED": 24, "COMMERCIAL": 0}'),
	(3, 'Sprint 56', 'Red', 0, 44, 44, 11, 33, 16, 17, 9, 2, 296, 296, 130, 0, 0.00, 0.25, '{"BASIC": 9, "FUTURE": 0, "ADVANCED": 2, "COMMERCIAL": 0}');
/*!40000 ALTER TABLE sprints.team_red ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
