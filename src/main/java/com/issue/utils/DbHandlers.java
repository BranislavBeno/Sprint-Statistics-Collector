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
 * The Class DbHandlers.
 */
public class DbHandlers {

	/** The logger. */
	static Logger logger = LogManager.getLogger(DbHandlers.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private DbHandlers() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Save or update.
	 *
	 * @param sprint       the sprint
	 * @param team         the team
	 * @param globalParams the global params
	 * @throws SQLException the SQL exception
	 */
	private static void saveOrUpdate(final String sprint, final Team team, GlobalParams globalParams)
			throws SQLException {
		// Get a connection to database
		try (Connection conn = DriverManager.getConnection(globalParams.getDbUri(), globalParams.getDbUsername(),
				globalParams.getDbPassword());) {

			// Create new team's database repository object
			Dao4DB dao = new TeamDao4DBImpl(conn, sprint, team);

			// Save or update team's database
			dao.saveOrUpdate();

		} catch (SQLException e) {
			logger.error("DB update failed!");
		}
	}

	/**
	 * Send stats 4 one team.
	 *
	 * @param globalParams the global params
	 * @param sprint       the sprint
	 * @param team         the team
	 */
	private static void sendStats4OneTeam(final GlobalParams globalParams, final String sprint, Team team) {
		try {
			saveOrUpdate(sprint, team, globalParams);
		} catch (SQLException e) {
			logger.error("Data sending into table {} failed!", "team_" + team.getTeamName().orElse("").toLowerCase());
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

		teams.getAll().values().stream().forEach(team -> sendStats4OneTeam(globalParams, sprint, team));
	}
}
