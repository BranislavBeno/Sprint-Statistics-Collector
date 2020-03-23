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
	 * Insert into DB.
	 *
	 * @param table the table
	 * @param sprint the sprint
	 * @param team the team
	 * @throws SQLException the SQL exception
	 */
	public static void insertIntoDB(final String table, final String sprint, final Team team) throws SQLException {
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/sprint_stats?useSSL=false";

		String user = "benito";
		String pass = "benito";

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
		String fields = sj.toString();

		String updateStr = "insert into " + table + fields + "values " + "('" + sprint + "', " + team.toString() + ")";
		String sqlQuery = "select * from " + table + " order by sprint";

		ResultSet myRs = null;

		// 1. Get a connection to database
		// 2. Create a statement
		// 3. Execute SQL query
		try (Connection myConn = DriverManager.getConnection(dbUrl, user, pass);
				Statement myStmt = myConn.createStatement();) {

			logger.info("Database connection successful.");

			// 3. Insert a new employee
			logger.info("Inserting a new data for '{}' into database.", sprint);

			int rowsAffected = myStmt.executeUpdate(updateStr);

			// 4. Verify this by getting a list of employees
			myRs = myStmt.executeQuery(sqlQuery);

			// 5. Announce the result
			logger.info("Rows affected by DB update: {}", rowsAffected);
			logger.info("New data insertion for '{}' into database finished successfuly.", sprint);

		} catch (SQLException e) {
			logger.error("DB insertion failed!");
		} finally {
			myRs.close();
		}
	}
}
