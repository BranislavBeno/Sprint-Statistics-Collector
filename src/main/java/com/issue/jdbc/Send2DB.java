/**
 * 
 */
package com.issue.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Sprint;
import com.issue.entity.Team;
import com.issue.iface.Dao4DB;
import com.issue.iface.SprintDao;
import com.issue.iface.TeamDao;
import com.issue.repository.SprintDao4DBImpl;
import com.issue.repository.TeamDao4DBImpl;

/**
 * The Class Send2DB.
 */
public class Send2DB {

	/** The logger. */
	static Logger logger = LogManager.getLogger(Send2DB.class);

	/** The teams. */
	private TeamDao<String, Team> teams;

	/** The sprints. */
	private SprintDao<String, Sprint> sprints;
	/** The global parameters. */
	private GlobalParams globalParams;

	/**
	 * Instantiates a new send 2 DB.
	 *
	 * @param globalParams the global parameters
	 * @param teams        the teams
	 * @param sprints the sprints
	 */
	public Send2DB(GlobalParams globalParams, TeamDao<String, Team> teams, SprintDao<String, Sprint> sprints){
		this.globalParams = Optional.ofNullable(globalParams).orElseThrow(IllegalArgumentException::new);
		this.teams = teams;
		this.sprints = sprints;
	}

	/**
	 * Send teams 2 DB.
	 *
	 * @param conn the conn
	 */
	private void sendTeams2DB(Connection conn) {
		teams.getAll().values().stream().forEach(team -> {
			// Create new team's database repository object
			Dao4DB<Team> teamDao = new TeamDao4DBImpl(conn);

			// Send team repository to data base
			teamDao.saveOrUpdate(team);
		});
	}

	/**
	 * Send sprints 2 DB.
	 *
	 * @param conn the connection
	 */
	private void sendSprints2DB(Connection conn) {
		sprints.getAll().values().stream().forEach(sprint -> {
			// Create new sprint's database repository object
			Dao4DB<Sprint> sprintDao = new SprintDao4DBImpl(conn);

			// Send sprint repository to data base
			sprintDao.saveOrUpdate(sprint);
		});
	}

	/**
	 * Send statistics 2 DB.
	 */
	public void sendStats2DB() {

		// Get a connection to database
		try (Connection conn = DriverManager.getConnection(globalParams.getDbUri(), globalParams.getDbUsername(),
				globalParams.getDbPassword());) {

			// Send teams repository to data base
			if (teams != null)
				sendTeams2DB(conn);
			else
				logger.error("Team data are null, hence no team data send to database.");

			// Send sprints repository to data base
			if (sprints != null)
				sendSprints2DB(conn);
			else
				logger.error("Sprint data are null, hence no sprint data send to database.");

		} catch (SQLException e) {
			logger.error("Sending data to database failed!");
			logger.error("Check whether proper connection parameters are provided or database is connected.");
		}
	}
}
