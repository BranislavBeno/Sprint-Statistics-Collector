/**
 * 
 */
package com.issue.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Feature;
import com.issue.entity.Story;
import com.issue.entity.Team;
import com.issue.iface.Dao4DB;
import com.issue.iface.FeatureDao;
import com.issue.iface.StoryDao;
import com.issue.iface.TeamDao;
import com.issue.repository.FeatureDaoImpl;
import com.issue.repository.TeamDao4DBImpl;
import com.issue.repository.TeamDaoImpl;

/**
 * The Class DbHandlersTest.
 *
 * @author benito
 */
class Teams2DBTest {

	/**
	 * Read all.
	 *
	 * @param reader the reader
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String readAll(final Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		return String.valueOf(sb);
	}

	/**
	 * Read file content.
	 *
	 * @param filePath the file path
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String readFileContent(final String filePath) throws IOException {
		String content = null;

		// Proceed only when file name is not null
		if (filePath != null) {
			File file = new File(filePath);
			try (Reader fileReader = new FileReader(file)) {
				content = readAll(fileReader);
			}
		}

		return content;
	}

	/**
	 * Provide team.
	 *
	 * @param teamName the team name
	 * @return the team
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static Team provideTeam(String teamName) throws IOException {
		// Create new team
		Team team = new Team(teamName);

		// Create empty features list
		FeatureDao<String, Feature> features = new FeatureDaoImpl();
		// Get Json string
		String featuresJson = readFileContent("src/test/resources/features.json");
		// Extract features from json
		features.saveAll(Features.extractFeatures(featuresJson).getAll());

		// Get Json string
		String storiesJson = readFileContent("src/test/resources/CompleteSprint.json");
		// Extract stories from json
		StoryDao<Story> stories = Stories.extractStories(storiesJson);

		// Summarize story points from stories finished within sprint(s)
		team.setFinishedStoryPoints(Utils.countFeatureFocus(features, stories));

		// Summarize story points from bug fixes finished within sprint(s)
		team.setFinishedBugsSPSum(Teams.countFinishedBugsSPSum(stories));

		// Set story points from pure stories (without bug fixes) finished within
		// sprint(s)
		team.setFinishedStoriesSPSum(team.getFinishedStoryPointsSum() - team.getFinishedBugsSPSum());

		// Summarize story points planned for sprint on begin of sprint
		team.setOnBeginPlannedStoryPointsSum(team.getFinishedStoryPointsSum());

		// Summarize story points planned for sprint on end of sprint
		team.setOnEndPlannedStoryPointsSum(team.getOnBeginPlannedStoryPointsSum());

		// Set delta number of story points start vs. close
		team.setDeltaStoryPoints(
				Stories.calculateDelta(team.getOnBeginPlannedStoryPointsSum(), team.getOnEndPlannedStoryPointsSum()));

		// Set planned story points closed
		team.setPlannedStoryPointsClosed(Stories.plannedStoryPointsClosed(team.getFinishedStoryPointsSum(),
				team.getOnBeginPlannedStoryPointsSum()));

		// Summarize time estimation
		team.setTimeEstimation(Teams.summarizeTimeEstimation(stories));

		// Summarize planned time
		team.setTimePlanned(team.getTimeEstimation());

		// Summarize time spent
		team.setTimeSpent(Teams.summarizeTimeSpent(stories));

		// Collect team members participating on sprint(s)
		Teams.collectTeamMembers(stories, team);

		return team;
	}

	/**
	 * Send 2 DB.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private void send2DB() throws IOException, SQLException {
		// Write into DB
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		TeamDao<String, Team> teamsRepo = new TeamDaoImpl();
		teamsRepo.save(provideTeam("Banana"));
		teamsRepo.save(provideTeam("Apple"));

		// Get a connection to database
		try (Connection conn = DriverManager.getConnection(globalParams.getDbUri(), globalParams.getDbUsername(),
				globalParams.getDbPassword());) {

			// Create new team's database repository object
			Dao4DB teamsDao = new TeamDao4DBImpl(conn, teamsRepo, globalParams.getSprintLabel());
			// Send teams repository to data base
			teamsDao.sendStats();
		}
	}

	/**
	 * Test positive team repo sending.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	@Test
	void testPositiveTeamRepoSending() throws IOException, SQLException {
		assertThrows(SQLException.class, () -> send2DB());
	}
}
