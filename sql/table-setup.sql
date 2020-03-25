create table if not exists `team_red` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `sprint` VARCHAR(64) DEFAULT NULL,
  `team_name` VARCHAR(64) DEFAULT NULL,
  `team_member_count` DECIMAL(2) DEFAULT 0,
  `on_begin_planned_sp_sum` DECIMAL(3) DEFAULT 0,
  `on_end_planned_sp_sum` DECIMAL(3) DEFAULT 0,
  `finished_sp_sum` DECIMAL(3) DEFAULT 0,
  `not_finished_sp_sum` DECIMAL(3) DEFAULT 0,
  `to_do_sp_sum` DECIMAL(3) DEFAULT 0,
  `in_progress_sp_sum` DECIMAL(3) DEFAULT 0,
  `finished_stories_sp_sum` DECIMAL(3) DEFAULT 0,
  `finished_bugs_sp_sum` DECIMAL(3) DEFAULT 0,
  `time_estimation` DECIMAL(3) DEFAULT 0,
  `time_planned` DECIMAL(3) DEFAULT 0,
  `time_spent` DECIMAL(3) DEFAULT 0,
  `not_closed_high_prior_stories` DECIMAL(3) DEFAULT 0,
  `delta_sp` DOUBLE(5,2) DEFAULT 0,
  `planned_sp_closed` DOUBLE(5,2) DEFAULT 0,
  `finished_sp` JSON DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

