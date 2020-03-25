/**
 * 
 */
package com.issue.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.entity.Team;

/**
 * The Class DbHandler.
 *
 * @author benito
 */
public class DbHandler {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(DbHandler.class);

	/**
	 * Instantiates a new db handler.
	 */
	private DbHandler() {
	}

	/**
	 * Check DB availbility.
	 */
	public static void checkDBAvailbility() {
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/sprint_stats?useSSL=false";
		String usr = "benito";
		String psswrd = "benito";
		String sqlQuery = "select * from red_team";

		// 1. Get a connection to database
		// 2. Create a statement
		// 3. Execute SQL query
		try (Connection myConn = DriverManager.getConnection(dbUrl, usr, psswrd);
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sqlQuery);) {

			logger.info("Database connection successful.");

		} catch (SQLException e) {
			logger.error("Database connection failed!");
		}
	}

	/**
	 * Prepare insertion str.
	 *
	 * @param table the table
	 * @param sprint the sprint
	 * @param team the team
	 * @return the string
	 */
	private static String prepareInsertionStr(final String table, final String sprint, final Team team) {
		StringJoiner sj = new StringJoiner(", ", " (", ") ");
		sj.add("sprint");
		sj.add("team_name");
		sj.add("team_member_count");
		sj.add("on_begin_planned_sp_sum");
		sj.add("on_end_planned_sp_sum");
		sj.add("finished_sp_sum");
		sj.add("not_finished_sp_sum");
		sj.add("to_do_sp_sum");
		sj.add("in_progress_sp_sum");
		sj.add("finished_stories_sp_sum");
		sj.add("finished_bugs_sp_sum");
		sj.add("time_estimation");
		sj.add("time_planned");
		sj.add("time_spent");
		sj.add("not_closed_high_prior_stories");
		sj.add("delta_sp");
		sj.add("planned_sp_closed");
		sj.add("finished_sp");

		return "insert into " + table + sj.toString() + "values " + "('" + sprint + "', " + team.toString() + ")";
	}

	/**
	 * Prepare table creation.
	 *
	 * @param table the table
	 * @return the string
	 */
	private static String prepareTableCreation(final String table) {
		StringBuilder sb = new StringBuilder();
		sb.append("create table if not exists ").append(table).append(" ( `id` INT(11) NOT NULL AUTO_INCREMENT, ")
				.append("`sprint` VARCHAR(64) DEFAULT NULL, ").append("`team_name` VARCHAR(64) DEFAULT NULL, ")
				.append("`team_member_count` DECIMAL(2) DEFAULT 0, ")
				.append("`on_begin_planned_sp_sum` DECIMAL(3) DEFAULT 0, ")
				.append("`on_end_planned_sp_sum` DECIMAL(3) DEFAULT 0, ")
				.append("`finished_sp_sum` DECIMAL(3) DEFAULT 0, ")
				.append("`not_finished_sp_sum` DECIMAL(3) DEFAULT 0, ").append("`to_do_sp_sum` DECIMAL(3) DEFAULT 0, ")
				.append("`in_progress_sp_sum` DECIMAL(3) DEFAULT 0, ")
				.append("`finished_stories_sp_sum` DECIMAL(3) DEFAULT 0, ")
				.append("`finished_bugs_sp_sum` DECIMAL(3) DEFAULT 0, ")
				.append("`time_estimation` DECIMAL(3) DEFAULT 0, ").append("`time_planned` DECIMAL(3) DEFAULT 0, ")
				.append("`time_spent` DECIMAL(3) DEFAULT 0, ")
				.append("`not_closed_high_prior_stories` DECIMAL(3) DEFAULT 0, ")
				.append("`delta_sp` DOUBLE(5,2) DEFAULT 0, ").append("`planned_sp_closed` DOUBLE(5,2) DEFAULT 0, ")
				.append("`finished_sp` JSON DEFAULT NULL, ")
				.append("PRIMARY KEY (`id`)) " + "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");

		return sb.toString();
	}

	/**
	 * Insert into DB.
	 *
	 * @param table  the table
	 * @param sprint the sprint
	 * @param team   the team
	 * @throws SQLException the SQL exception
	 */
	public static void insertIntoDB(final String table, final String sprint, final Team team) throws SQLException {
		// Prepare database connection
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/sprints?useSSL=false";
		String user = "sprints";
		String pass = "sprints";

		// Get a connection to database
		try (Connection myConn = DriverManager.getConnection(dbUrl, user, pass);
				Statement myStmt = myConn.createStatement();) {

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
}
