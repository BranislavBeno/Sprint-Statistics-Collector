/**
 * 
 */
package com.issue.jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Engineer;
import com.issue.entity.Sprint;
import com.issue.entity.Team;
import com.issue.iface.EngineerDao;
import com.issue.iface.SprintDao;
import com.issue.iface.TeamDao;
import com.issue.repository.EngineerDaoImpl;
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
	 * Prepare team repo.
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
	 * Prepare engineer repo.
	 *
	 * @return the engineer dao
	 */
	private EngineerDao<String, Engineer> prepareEngineerRepo() {
		// Prepare one engineer
		Engineer engineer = new Engineer("John Doe", 0, 0);

		// Prepare map of engineers
		Map<String, Engineer> engMap = new HashMap<>();
		engMap.put(engineer.getName(), engineer);

		// Prepare engineer repository
		EngineerDao<String, Engineer> engineers = new EngineerDaoImpl();
		engineers.saveAll(engMap);

		return engineers;
	}

	/**
	 * Test no object created.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether no object is created and IllegalArgumentException is raised")
	public void testNoObjectCreated() throws SQLException {
		assertThrows(IllegalArgumentException.class, () -> new Send2DB(null, null, null, null));
	}

	/**
	 * Test object created but data base connection data are false.
	 *
	 * @throws SQLException the SQL exception
	 * @throws IOException  Signals that an I/O exception has occurred.
	 */
	@Test
	@DisplayName("Test whether object is created but database connection parameters are false")
	public void testObjectCreatedButDataBaseConnectionDataAreFalse() throws SQLException, IOException {
		// Prepare parameters for database connection
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative1_application.properties");

		// Create object for database connection
		Send2DB send2DB = new Send2DB(globalParams, null, null, null);
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
	public void testObjectCreatedButDataToSendAreNull() throws SQLException, IOException {
		// Prepare parameters for database connection
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Create object for database connection
		Send2DB send2DB = new Send2DB(globalParams, null, null, null);
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
	public void testDataAreSentToDB() throws SQLException, IOException {
		// Prepare parameters for database connection
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Prepare team repository
		TeamDao<String, Team> teams = prepareTeamRepo();

		// Prepare sprint repository
		SprintDao<String, Sprint> sprints = prepareSprintRepo();

		// Prepare engineer repository
		EngineerDao<String, Engineer> engineers = prepareEngineerRepo();

		// Create object for database connection
		Send2DB send2DB = new Send2DB(globalParams, teams, sprints, engineers);
		// Send data to database
		send2DB.sendStats2DB();

		assertThat(send2DB).isNotNull();
	}
}
