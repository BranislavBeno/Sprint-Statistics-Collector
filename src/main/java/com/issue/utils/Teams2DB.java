package com.issue.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Team;
import com.issue.iface.Dao4DB;
import com.issue.iface.TeamDao;
import com.issue.repository.TeamDao4DBImpl;

/**
 * The Class Teams2DB.
 */
public class Teams2DB {

	/** The logger. */
	static Logger logger = LogManager.getLogger(Teams2DB.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private Teams2DB() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Save or update.
	 *
	 * @param tableName    the table name
	 * @param team         the team
	 * @param globalParams the global params
	 */
	private static void saveOrUpdate(final String tableName, final Team team, GlobalParams globalParams) {
		// Get a connection to database
		try (Connection conn = DriverManager.getConnection(globalParams.getDbUri(), globalParams.getDbUsername(),
				globalParams.getDbPassword());) {

			// Create new team's database repository object
			Dao4DB dao = new TeamDao4DBImpl(conn, team, tableName, globalParams.getSprintLabel());

			// Save or update team's database
			dao.saveOrUpdate();
		} catch (SQLException e) {
			logger.error("Data sending to table {} failed!", tableName);
			logger.error("Check whether database is connected.");
		}
	}

	/**
	 * Send stats.
	 *
	 * @param team the team
	 * @param globalParams the global params
	 */
	private static void sendStats(final Team team, final GlobalParams globalParams) {
		// Set database table name
		String tableName = "team_" + team.getTeamName().orElse("unknown").toLowerCase();

		// Save sprint data for particular team
		saveOrUpdate(tableName, team, globalParams);
	}

	/**
	 * Send stats.
	 *
	 * @param teams        the teams
	 * @param globalParams the global params
	 */
	public static void sendStats(final TeamDao<String, Team> teams, final GlobalParams globalParams) {
		// Run over all team related sprint data
		teams.getAll().values().stream().forEach(team -> sendStats(team, globalParams));
	}
}
