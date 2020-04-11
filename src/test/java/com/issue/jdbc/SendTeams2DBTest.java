/**
 * 
 */
package com.issue.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Team;
import com.issue.iface.Dao4DB;
import com.issue.iface.TeamDao;
import com.issue.repository.TeamDao4DBImpl;
import com.issue.repository.TeamDaoImpl;
import com.issue.utils.Utils;

/**
 * The Class SendTeams2DBTest.
 */
class SendTeams2DBTest {

	/**
	 * Provide team.
	 *
	 * @param teamName    the team name
	 * @param sprintLabel the sprint label
	 * @return the team
	 */
	private static Team provideTeam(String teamName, String sprintLabel) {
		return new Team(teamName, sprintLabel);
	}

	/**
	 * Prepare teams.
	 *
	 * @param globalParams the global params
	 * @return the team dao
	 */
	private TeamDao<String, Team> prepareTeams(GlobalParams globalParams) {
		TeamDao<String, Team> teamsRepo = new TeamDaoImpl();
		teamsRepo.save(provideTeam("Banana", "Test"));
		teamsRepo.save(provideTeam("Apple", "Test"));
		return teamsRepo;
	}

	/**
	 * Send teams 2 DB.
	 *
	 * @param teams the teams
	 * @param conn  the conn
	 */
	private void sendTeams2DB(TeamDao<String, Team> teams, Connection conn) {
		teams.getAll().values().stream().forEach(team -> {
			// Create new team's database repository object
			Dao4DB<Team> teamDao = new TeamDao4DBImpl(conn);

			// Send team repository to data base
			teamDao.saveOrUpdate(team);
		});
	}

	/**
	 * Send teams 2 DB.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void sendTeams2DB() throws IOException, SQLException {
		// Write into DB
		GlobalParams globalParams = Utils.provideGlobalParams("src/test/resources/test_real_application.properties");

		// Prepare teams
		TeamDao<String, Team> teams = prepareTeams(globalParams);

		// Get a connection to database
		try (Connection conn = DriverManager.getConnection(globalParams.getDbUri(), globalParams.getDbUsername(),
				globalParams.getDbPassword());) {

			sendTeams2DB(teams, conn);
		}
	}

	/**
	 * Test negative team repo sending to not connected DB.
	 *
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	@Test
	void testNegativeTeamRepoSending2NotConnectedDB() throws IOException, SQLException {
		assertThrows(SQLException.class, () -> sendTeams2DB());
	}

	/**
	 * Test positive team repo sending 2 in memory DB.
	 *
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	@Test
	void testPositiveTeamRepoSending2InMemoryDB() throws IOException, SQLException {
		// Write into DB
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		TeamDao<String, Team> teams = prepareTeams(globalParams);

		// Get a connection to database
		try (Connection conn = DriverManager.getConnection(globalParams.getDbUri(), globalParams.getDbUsername(),
				globalParams.getDbPassword());) {

			sendTeams2DB(teams, conn);
		}

		assertThat(teams.getAll().size()).isGreaterThan(0);
	}
}
