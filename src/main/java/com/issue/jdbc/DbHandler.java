/**
 * 
 */
package com.issue.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.issue.entity.Team;

/**
 * @author benito
 *
 */
public class DbHandler {

	public static void checkDBAvailbility() {
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/demo?useSSL=false";
		String usr = "benito";
		String psswrd = "benito";
		String sqlQuery = "select * from red_team";

		// 1. Get a connection to database
		// 2. Create a statement
		// 3. Execute SQL query
		try (Connection myConn = DriverManager.getConnection(dbUrl, usr, psswrd);
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery(sqlQuery);) {

			System.out.println("Database connection successful!\n");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertIntoDB(String table, String sprint, Team team) throws SQLException {
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/demo?useSSL=false";

		String user = "benito";
		String pass = "benito";

		String values = team.toString();

		String updateStr = "insert into " + table
				+ " (sprint, team_member_count, on_begin_planned_sp_sum, on_end_planned_sp_sum, finished_sp_sum) "
				+ "values " + "('" + sprint + "', " + values + ")";
		String sqlQuery = "select * from " + table + " order by sprint";

		ResultSet myRs = null;

		// 1. Get a connection to database
		// 2. Create a statement
		// 3. Execute SQL query
		try (Connection myConn = DriverManager.getConnection(dbUrl, user, pass);
				Statement myStmt = myConn.createStatement();) {

			System.out.println("Database connection successful!\n");

			// 3. Insert a new employee
			System.out.println("Inserting a new sprint into database\n");

			int rowsAffected = myStmt.executeUpdate(updateStr);

			// 4. Verify this by getting a list of employees
			myRs = myStmt.executeQuery(sqlQuery);

			// 5. Process the result set
			System.out.println("Rows affected by DB update: " + rowsAffected + "\n");
			while (myRs.next()) {
				System.out.println(myRs.getString("sprint") + ", " + myRs.getString("finished_sp_sum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			myRs.close();
		}
	}
}
