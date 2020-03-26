package com.issue.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Team;
import com.issue.iface.TeamDao;

/**
 * The Class DbHandlers.
 */
public class DbHandlers {

	/** The Constant DOUBLE_5_2_DEFAULT_0. */
	private static final String DOUBLE_5_2_DEFAULT_0 = "DOUBLE(5,2) DEFAULT 0";

	/** The Constant DECIMAL_3_DEFAULT_0. */
	private static final String DECIMAL_3_DEFAULT_0 = "DECIMAL(3) DEFAULT 0";

	/** The Constant DECIMAL_2_DEFAULT_0. */
	private static final String DECIMAL_2_DEFAULT_0 = "DECIMAL(2) DEFAULT 0";

	/** The Constant VARCHAR_64_DEFAULT_NULL. */
	private static final String VARCHAR_64_DEFAULT_NULL = "VARCHAR(64) DEFAULT NULL";

	/** The Constant FINISHED_SP_COLUMN. */
	private static final String FINISHED_SP_COLUMN = "finished_sp";

	/** The Constant PLANNED_SP_CLOSED_COLUMN. */
	private static final String PLANNED_SP_CLOSED_COLUMN = "planned_sp_closed";

	/** The Constant DELTA_SP_COLUMN. */
	private static final String DELTA_SP_COLUMN = "delta_sp";

	/** The Constant NOT_CLOSED_HIGH_PRIOR_STORIES_COLUMN. */
	private static final String NOT_CLOSED_HIGH_PRIOR_STORIES_COLUMN = "not_closed_high_prior_stories";

	/** The Constant TIME_SPENT_COLUMN. */
	private static final String TIME_SPENT_COLUMN = "time_spent";

	/** The Constant TIME_PLANNED_COLUMN. */
	private static final String TIME_PLANNED_COLUMN = "time_planned";

	/** The Constant TIME_ESTIMATION_COLUMN. */
	private static final String TIME_ESTIMATION_COLUMN = "time_estimation";

	/** The Constant FINISHED_BUGS_SP_SUM_COLUMN. */
	private static final String FINISHED_BUGS_SP_SUM_COLUMN = "finished_bugs_sp_sum";

	/** The Constant FINISHED_STORIES_SP_SUM_COLUMN. */
	private static final String FINISHED_STORIES_SP_SUM_COLUMN = "finished_stories_sp_sum";

	/** The Constant IN_PROGRESS_SP_SUM_COLUMN. */
	private static final String IN_PROGRESS_SP_SUM_COLUMN = "in_progress_sp_sum";

	/** The Constant TO_DO_SP_SUM_COLUMN. */
	private static final String TO_DO_SP_SUM_COLUMN = "to_do_sp_sum";

	/** The Constant NOT_FINISHED_SP_SUM_COLUMN. */
	private static final String NOT_FINISHED_SP_SUM_COLUMN = "not_finished_sp_sum";

	/** The Constant FINISHED_SP_SUM_COLUMN. */
	private static final String FINISHED_SP_SUM_COLUMN = "finished_sp_sum";

	/** The Constant ON_END_PLANNED_SP_SUM_COLUMN. */
	private static final String ON_END_PLANNED_SP_SUM_COLUMN = "on_end_planned_sp_sum";

	/** The Constant ON_BEGIN_PLANNED_SP_SUM_COLUMN. */
	private static final String ON_BEGIN_PLANNED_SP_SUM_COLUMN = "on_begin_planned_sp_sum";

	/** The Constant TEAM_MEMBER_COUNT_COLUMN. */
	private static final String TEAM_MEMBER_COUNT_COLUMN = "team_member_count";

	/** The Constant TEAM_NAME_COLUMN. */
	private static final String TEAM_NAME_COLUMN = "team_name";

	/** The Constant SPRINT_COLUMN. */
	private static final String SPRINT_COLUMN = "sprint";

	/** The logger. */
	static Logger logger = LogManager.getLogger(DbHandlers.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private DbHandlers() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Prepare insertion str.
	 *
	 * @param table  the table
	 * @param sprint the sprint
	 * @param team   the team
	 * @return the string
	 */
	private static String prepareInsertionStr(final String table, final String sprint, final Team team) {
		StringJoiner sj = new StringJoiner(", ", " (", ") ");
		sj.add(SPRINT_COLUMN);
		sj.add(TEAM_NAME_COLUMN);
		sj.add(TEAM_MEMBER_COUNT_COLUMN);
		sj.add(ON_BEGIN_PLANNED_SP_SUM_COLUMN);
		sj.add(ON_END_PLANNED_SP_SUM_COLUMN);
		sj.add(FINISHED_SP_SUM_COLUMN);
		sj.add(NOT_FINISHED_SP_SUM_COLUMN);
		sj.add(TO_DO_SP_SUM_COLUMN);
		sj.add(IN_PROGRESS_SP_SUM_COLUMN);
		sj.add(FINISHED_STORIES_SP_SUM_COLUMN);
		sj.add(FINISHED_BUGS_SP_SUM_COLUMN);
		sj.add(TIME_ESTIMATION_COLUMN);
		sj.add(TIME_PLANNED_COLUMN);
		sj.add(TIME_SPENT_COLUMN);
		sj.add(NOT_CLOSED_HIGH_PRIOR_STORIES_COLUMN);
		sj.add(DELTA_SP_COLUMN);
		sj.add(PLANNED_SP_CLOSED_COLUMN);
		sj.add(FINISHED_SP_COLUMN);

		return "insert into " + table + sj.toString() + "values " + "('" + sprint + "', " + team.toString() + ")";
	}

	/**
	 * Compose column.
	 *
	 * @param name        the name
	 * @param declaration the declaration
	 * @return the string
	 */
	private static String composeColumn(final String name, final String declaration) {
		return new StringBuilder("`").append(name).append("` ").append(declaration).append(", ").toString();
	}

	/**
	 * Prepare table creation.
	 *
	 * @param table the table
	 * @return the string
	 */
	private static String prepareTableCreation(final String table) {
		StringBuilder sb = new StringBuilder();
		sb.append("create table if not exists ").append(table).append(" ( ")
				.append(composeColumn("id", "INT(11) NOT NULL AUTO_INCREMENT"))
				.append(composeColumn(SPRINT_COLUMN, VARCHAR_64_DEFAULT_NULL))
				.append(composeColumn(TEAM_NAME_COLUMN, VARCHAR_64_DEFAULT_NULL))
				.append(composeColumn(TEAM_MEMBER_COUNT_COLUMN, DECIMAL_2_DEFAULT_0))
				.append(composeColumn(ON_BEGIN_PLANNED_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(ON_END_PLANNED_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(FINISHED_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(NOT_FINISHED_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(TO_DO_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(IN_PROGRESS_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(FINISHED_STORIES_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(FINISHED_BUGS_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(TIME_ESTIMATION_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(TIME_PLANNED_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(TIME_SPENT_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(NOT_CLOSED_HIGH_PRIOR_STORIES_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(composeColumn(DELTA_SP_COLUMN, DOUBLE_5_2_DEFAULT_0))
				.append(composeColumn(PLANNED_SP_CLOSED_COLUMN, DOUBLE_5_2_DEFAULT_0))
				.append(composeColumn(FINISHED_SP_COLUMN, "JSON DEFAULT NULL")).append("PRIMARY KEY (`id`)) ")
				.append("ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");

		return sb.toString();
	}

	private static void saveOrUpdate(final String table, final String sprint, final Team team,
			GlobalParams globalParams) throws SQLException {
		// Get a connection to database
		try (Connection myConn = DriverManager.getConnection(globalParams.getDbUri(), globalParams.getDbUsername(),
				globalParams.getDbPassword()); Statement myStmt = myConn.createStatement();) {

			// Announce connection result
			logger.info("Database connection successful.");

			// Create table if doesn't exists
			logger.info("Creating new database table '{}' if doesn't exists.", table);
			// Create statement for table creation
			String createTable = prepareTableCreation(table);
			// Execute SQL query for table creation
			myStmt.executeUpdate(createTable);

			// Insert a new sprint data
			logger.info("Inserting a new sprint data for '{}' into database table '{}'.", sprint, table);
			// Create statement for data insertion
			String insertData = prepareInsertionStr(table, sprint, team);
			// Execute SQL query for data insertion
			int rowsAffected = myStmt.executeUpdate(insertData);
			logger.info("Rows affected by DB update: {}", rowsAffected);

			// 7. Announce the result
			logger.info("New data insertion finished successfuly.");

		} catch (SQLException e) {
			logger.error("DB insertion failed!");
		}
	}

	/**
	 * Send stats 2 DB.
	 *
	 * @param teams        the teams
	 * @param globalParams the global params
	 */
	public static void sendStats2DB(final TeamDao<String, Team> teams, final GlobalParams globalParams) {

		final String sprint = globalParams.getOutputFileName4Xlsx().substring(0,
				globalParams.getOutputFileName4Xlsx().length() - 5);

		teams.getAll().values().stream().forEach(team -> {
			String tableName = "team_" + team.getTeamName().orElse("");
			try {
				saveOrUpdate(tableName.toLowerCase(), sprint, team, globalParams);
			} catch (SQLException e) {
				logger.error("Data sending into table {} failed!", tableName);
			}
		});
	}
}
