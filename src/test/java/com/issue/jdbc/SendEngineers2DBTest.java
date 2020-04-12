package com.issue.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Engineer;
import com.issue.iface.Dao4DB;
import com.issue.iface.EngineerDao;
import com.issue.repository.EngineerDao4DBImpl;
import com.issue.repository.EngineerDaoImpl;
import com.issue.utils.Utils;

public class SendEngineers2DBTest {

	private EngineerDao<String, Engineer> prepareEngineers(GlobalParams globalParams) {
		EngineerDao<String, Engineer> engineersRepo = new EngineerDaoImpl();

		Map<String, Engineer> engineers = Map.of("Jane Doe", new Engineer("Jane Doe", "Test1"), "George of the Jungle",
				new Engineer("George of the Jungle", "Test2"));

		engineersRepo.saveAll(engineers);

		return engineersRepo;
	}

	private void sendEngineers2DB(EngineerDao<String, Engineer> engineers, Connection conn) {
		engineers.getAll().values().stream().forEach(engineer -> {
			// Create new engineers's database repository object
			Dao4DB<Engineer> engineerDao = new EngineerDao4DBImpl(conn);

			// Send engineer repository to data base
			engineerDao.saveOrUpdate(engineer);
		});
	}

	@Test
	void testPositiveSprintRepoSending2InMemoryDB() throws IOException, SQLException {
		// Write into DB
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		EngineerDao<String, Engineer> engineers = prepareEngineers(globalParams);

		// Get a connection to database
		try (Connection conn = DriverManager.getConnection(globalParams.getDbUri(), globalParams.getDbUsername(),
				globalParams.getDbPassword());) {

			sendEngineers2DB(engineers, conn);
		}

		assertThat(engineers.getAll().size()).isGreaterThan(0);
	}
}
