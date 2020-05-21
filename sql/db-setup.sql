CREATE DATABASE IF NOT EXISTS `sprints`

USE `sprints`;

DROP TABLE IF EXISTS `sprint`;
CREATE TABLE IF NOT EXISTS `sprint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sprint` varchar(64) DEFAULT NULL,
  `refined_SP` json DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `team_dummy`;
CREATE TABLE IF NOT EXISTS `team_dummy` (
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

