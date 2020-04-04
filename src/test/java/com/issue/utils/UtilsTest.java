/**
 * 
 */
package com.issue.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
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

/**
 * The Class UtilsTest.
 *
 * @author branislav.beno
 */
class UtilsTest {

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
	 * Send to persistent database.
	 *
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void send2PersistentDB() throws IOException, SQLException {
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
	 * Send 2 in memory DB.
	 *
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void send2InMemoryDB() throws IOException, SQLException {
		// Write into DB
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

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
		assertThrows(SQLException.class, () -> send2PersistentDB());
	}

	/**
	 * Test positive team repo sending 2 in memory DB.
	 *
	 * @throws IOException  Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	@Test
	void testPositiveTeamRepoSending2InMemoryDB() throws IOException, SQLException {
		send2InMemoryDB();
	}

	/**
	 * Test private constructors for code coverage.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<Utils> clazz = Utils.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}

	/**
	 * Test positive gather json string.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveGatherJsonString() throws IOException, InterruptedException {
		// Get json string
		String jsonString = Utils.gatherJsonString("postman", "password", "https://postman-echo.com/basic-auth");

		assertThat(jsonString).isEqualTo("{\"authenticated\":true}");
	}

	/**
	 * Test negative gather json string.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeGatherJsonString() throws IOException, InterruptedException {
		// Get json string
		String jsonString = Utils.gatherJsonString("postman", "passwd", "https://postman-echo.com/basic-auth");

		assertThat(jsonString).isNull();
	}

	/**
	 * Test full stats run with no connection exception.
	 */
	@Test
	void testFullStatsRunWithNoConnectionException() {
		// Start main routine
		assertThrows(ConnectException.class, () -> Utils.runStats("usr", "passwd", false));

	}
}
