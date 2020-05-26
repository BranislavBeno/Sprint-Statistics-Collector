/**
 * 
 */
package com.issue.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Sprint;
import com.issue.entity.Team;
import com.issue.iface.SprintDao;
import com.issue.iface.TeamDao;
import com.issue.repository.SprintDaoImpl;
import com.issue.repository.TeamDaoImpl;
import com.issue.utils.Utils;

/**
 * The Class Send2DBTest.
 *
 * @author benito
 */
class Send2DBTest {

	/**
	 * Prepare team repository.
	 *
	 * @return the team dao
	 */
	private TeamDao<String, Team> prepareTeamRepo() {
		// Prepare one team
		Team team = new Team("Banana", "Test");

		// Prepare team repository
		TeamDao<String, Team> teams = new TeamDaoImpl();
		teams.save(team);

		return teams;
	}

	/**
	 * Prepare sprint repo.
	 *
	 * @return the sprint dao
	 */
	private SprintDao<String, Sprint> prepareSprintRepo() {
		// Prepare one sprint
		Sprint sprint = new Sprint("Test");

		// Prepare sprint repository
		SprintDao<String, Sprint> sprints = new SprintDaoImpl();
		sprints.save(sprint);

		return sprints;
	}

	/**
	 * Test no object created.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether no object is created and IllegalArgumentException is raised")
	void testNoObjectCreated() throws SQLException {
		assertThrows(IllegalArgumentException.class, () -> new Send2DB(null, null, null));
	}

	/**
	 * Test object created but data base connection data are false.
	 *
	 * @throws SQLException the SQL exception
	 * @throws IOException  Signals that an I/O exception has occurred.
	 */
	@Test
	@DisplayName("Test whether object is created but database connection parameters are false")
	void testObjectCreatedButDataBaseConnectionDataAreFalse() throws SQLException, IOException {
		// Prepare parameters for database connection
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative1_application.properties");

		// Create object for database connection
		Send2DB send2DB = new Send2DB(globalParams, null, null);
		// Send data to database
		send2DB.sendStats2DB();

		assertThat(send2DB).isNotNull();
	}

	/**
	 * Test object created but data to send are null.
	 *
	 * @throws SQLException the SQL exception
	 * @throws IOException  Signals that an I/O exception has occurred.
	 */
	@Test
	@DisplayName("Test whether object is created but data for sending to database are null")
	void testObjectCreatedButDataToSendAreNull() throws SQLException, IOException {
		// Prepare parameters for database connection
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Create object for database connection
		Send2DB send2DB = new Send2DB(globalParams, null, null);
		// Send data to database
		send2DB.sendStats2DB();

		assertThat(send2DB).isNotNull();
	}

	/**
	 * Test data are sent to DB.
	 *
	 * @throws SQLException the SQL exception
	 * @throws IOException  Signals that an I/O exception has occurred.
	 */
	@Test
	@DisplayName("Test whether data are sent to database")
	void testDataAreSentToDB() throws SQLException, IOException {
		// Prepare parameters for database connection
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Prepare team repository
		TeamDao<String, Team> teams = prepareTeamRepo();

		// Prepare sprint repository
		SprintDao<String, Sprint> sprints = prepareSprintRepo();

		// Create object for database connection
		Send2DB send2DB = new Send2DB(globalParams, teams, sprints);
		// Send data to database
		send2DB.sendStats2DB();

		assertThat(send2DB).isNotNull();
	}
}
