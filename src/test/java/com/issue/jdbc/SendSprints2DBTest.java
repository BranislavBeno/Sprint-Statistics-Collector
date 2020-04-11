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
import com.issue.entity.Sprint;
import com.issue.iface.Dao4DB;
import com.issue.iface.SprintDao;
import com.issue.repository.SprintDao4DBImpl;
import com.issue.repository.SprintDaoImpl;
import com.issue.utils.Utils;

/**
 * The Class SendSprints2DBTest.
 *
 * @author benito
 */
public class SendSprints2DBTest {

	/**
	 * Prepare sprints.
	 *
	 * @param globalParams the global params
	 * @return the sprint dao
	 */
	private SprintDao<String, Sprint> prepareSprints(GlobalParams globalParams) {
		SprintDao<String, Sprint> sprintsRepo = new SprintDaoImpl();
		sprintsRepo.save(new Sprint("Test1"));
		sprintsRepo.save(new Sprint("Test2"));
		return sprintsRepo;
	}

	/**
	 * Send sprints 2 DB.
	 *
	 * @param sprints the sprints
	 * @param conn the conn
	 */
	private void sendSprints2DB(SprintDao<String, Sprint> sprints, Connection conn) {
		sprints.getAll().values().stream().forEach(sprint -> {
			// Create new sprint's database repository object
			Dao4DB<Sprint> SprintDao = new SprintDao4DBImpl(conn);

			// Send sprint repository to data base
			SprintDao.saveOrUpdate(sprint);
		});
	}

	/**
	 * Send sprints 2 DB.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void sendSprints2DB() throws IOException, SQLException {
		// Write into DB
		GlobalParams globalParams = Utils.provideGlobalParams("src/test/resources/test_real_application.properties");

		// Prepare sprints
		SprintDao<String, Sprint> sprints = prepareSprints(globalParams);

		// Get a connection to database
		try (Connection conn = DriverManager.getConnection(globalParams.getDbUri(), globalParams.getDbUsername(),
				globalParams.getDbPassword());) {

			sendSprints2DB(sprints, conn);
		}
	}

	/**
	 * Test negative sprint repo sending 2 not connected DB.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	@Test
	void testNegativeSprintRepoSending2NotConnectedDB() throws IOException, SQLException {
		assertThrows(SQLException.class, () -> sendSprints2DB());
	}

	/**
	 * Test positive sprint repo sending 2 in memory DB.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	@Test
	void testPositiveSprintRepoSending2InMemoryDB() throws IOException, SQLException {
		// Write into DB
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		SprintDao<String, Sprint> sprints = prepareSprints(globalParams);

		// Get a connection to database
		try (Connection conn = DriverManager.getConnection(globalParams.getDbUri(), globalParams.getDbUsername(),
				globalParams.getDbPassword());) {

			sendSprints2DB(sprints, conn);
		}

		assertThat(sprints.getAll().size()).isGreaterThan(0);
	}
}
