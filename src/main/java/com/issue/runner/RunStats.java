/**
 * 
 */
package com.issue.runner;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.entity.Feature;
import com.issue.entity.Story;
import com.issue.entity.Team;
import com.issue.iface.FeatureDao;
import com.issue.iface.StoryDao;
import com.issue.jdbc.DbHandler;
import com.issue.repository.FeatureDaoImpl;
import com.issue.utils.Features;
import com.issue.utils.Stories;
import com.issue.utils.Teams;
import com.issue.utils.Utils;

import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

/**
 * The Class RunStats.
 *
 * @author branislav.beno
 */
public class RunStats implements Runnable {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(RunStats.class);

	/** The user. */
	@Option(names = { "-u", "--user" })
	private String user;

	/** The password. */
	@Option(names = { "-p", "--password" })
	private String password;

	/** The database connection. */
	@Option(names = { "-d", "--dbconnect" })
	private boolean dbConnect = false;

	/** The spec. */
	@Spec
	CommandSpec spec;

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
	 * @return the team
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static Team provideTeam() throws IOException {
		// Create new team
		Team team = new Team("Red");

		// Create empty features list
		FeatureDao<String, Feature> features = new FeatureDaoImpl();
		// Get Json string
		String featuresJson = readFileContent("features.json");
		// Extract features from json
		features.saveAll(Features.extractFeatures(featuresJson).getAll());

		// Get Json string
		String storiesJson = readFileContent("CompleteSprint.json");
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
	 * Insert into DB.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	private static void insertIntoDB() throws IOException, SQLException {
		Team team = provideTeam();

		String tableName = "team_" + team.getTeamName().orElse("");

		String sprintLabel = "Sprint 4";

		DbHandler.insertIntoDB(tableName.toLowerCase(), sprintLabel, team);
	}

	/**
	 * Run.
	 */
	public void run() {
		if (user != null && password != null) {
			try {
				Utils.runStats(user, password);

			} catch (IOException | InterruptedException e) {
				logger.error("Processing interrupted with exception.");
				logger.error(
						"Check whether application.properties file is available, or whether connection to issue tracker server is established.");
				// Restore interrupted state...
				Thread.currentThread().interrupt();
			}
		} else if (dbConnect) {
			try {
				insertIntoDB();
			} catch (IOException | SQLException e) {
				logger.error("DB insertion failed!");
			}
		} else {
			throw new ParameterException(spec.commandLine(), "Password required");
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// Run sprint statistics gathering
		System.exit(new CommandLine(new RunStats()).execute(args));
	}
}
