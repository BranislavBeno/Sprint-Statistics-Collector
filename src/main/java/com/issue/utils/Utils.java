/**
 * 
 */
package com.issue.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Engineer;
import com.issue.entity.Feature;
import com.issue.entity.Sprint;
import com.issue.entity.Story;
import com.issue.entity.Team;
import com.issue.enums.FeatureScope;
import com.issue.iface.EngineerDao;
import com.issue.iface.FeatureDao;
import com.issue.iface.SprintDao;
import com.issue.iface.StoryDao;
import com.issue.iface.TeamDao;

/**
 * The Class Utils.
 *
 * @author branislav.beno
 */
public class Utils {

	/** The logger. */
	static Logger logger = LogManager.getLogger(Utils.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private Utils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Provide properties.
	 *
	 * @param propFile the prop file
	 * @return the properties
	 * @throws FileNotFoundException the file not found exception
	 */
	private static Properties provideProperties(final String propFile) throws FileNotFoundException {
		// Create new properties object
		Properties properties = new Properties();
		// Load properties from file
		try {
			properties.load(new FileInputStream(propFile));
		} catch (IOException e) {
			logger.error("Propreties file {} was not found!", propFile);
			throw new FileNotFoundException();
		}

		return properties;
	}

	/**
	 * Parses the queries.
	 *
	 * @param propName the prop name
	 * @param props    the props
	 * @return the sets the
	 */
	private static Set<String> parseQueries(final String propName, final Properties props) {
		int count = 1;
		String querieName = propName + count;
		Set<String> queries = null;
		while (Optional.ofNullable(props.getProperty(querieName)).isPresent()) {
			if (queries == null)
				queries = new HashSet<>();
			queries.add(props.getProperty(querieName));
			querieName = propName + ++count;
		}
		return queries;
	}

	/**
	 * Provide global params.
	 *
	 * @param propFile the prop file
	 * @return the global params
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static GlobalParams provideGlobalParams(final String propFile) throws IOException {
		// Create empty global parameters
		GlobalParams globalParams = GlobalParams.getInstance();

		// Get properties
		Properties props = provideProperties(propFile);

		// Get content from properties
		globalParams.setIssueTrackerUri(props.getProperty("issueTrackerUri", ""));
		globalParams.setOutputFileName4Xlsx(props.getProperty("xlsxOutputFile", null));
		globalParams.setFeaturesJql(props.getProperty("featuresJql", "issuetype = Feature"));

		// Get set of requests for issues finished within sprint
		globalParams.setCompletedSprints(parseQueries("complete", props));

		// Get set of requests for issues finished outside of sprint
		globalParams.setCompletedOutsideSprints(parseQueries("completeOutside", props));

		// Get set of requests for issues not finished within sprint
		globalParams.setNotCompletedSprints(parseQueries("incomplete", props));

		// Get set of requests for issues added after sprint start
		globalParams.setAddedAfterSprintStart(parseQueries("addedAfterStart", props));

		// Get set of requests for issues removed after sprint start
		globalParams.setRemovedAfterSprintStart(parseQueries("removedAfterStart", props));

		return globalParams;
	}

	/**
	 * Basic auth.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the string
	 */
	private static String basicAuth(final String username, final String password) {
		return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	}

	/**
	 * Gather json string.
	 *
	 * @param username the username
	 * @param password the password
	 * @param uri      the uri
	 * @return the string
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static String gatherJsonString(final String username, final String password, final String uri)
			throws IOException, InterruptedException {
		// Create HTTP Client
		HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

		// Create HTTP Request
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).version(HttpClient.Version.HTTP_2)
				.header("Authorization", basicAuth(username, password)).header("Content-Type", "application/json").GET()
				.build();

		try {
			// Get HTTP response
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			// Get response status code
			int responseStatusCode = response.statusCode();

			// Get response body
			if (responseStatusCode == 200) {
				logger.debug("Json output exported succesfully.");
				return response.body();
			}

			logger.error("HTTP Error {} occured!", responseStatusCode);

			if (responseStatusCode == 400) {
				// Inform user that issue tracker server has not responded.
				logger.error("Issue tracker server has not responded.");
				logger.error("Json output wasn't exported!");
			} else if (responseStatusCode == 401) {
				// Inform user that authentication to issue tracker server has failed.
				logger.error("Authentication to issue tracker server has failed.");
			}

		} catch (

		ConnectException e) {
			logger.error("Connection timed out: Issue tracker tool not reachable!");
			throw new ConnectException();
		}
		return null;
	}

	/**
	 * Prepare url.
	 *
	 * @param text the text
	 * @return the string
	 */
	public static String prepareUrl(String text) {
		StringBuilder sb = new StringBuilder();
		for (char c : text.toCharArray()) {
			sb.append("%" + Integer.toHexString((byte) c));
		}
		return sb.toString();
	}

	/**
	 * Parses the story points.
	 *
	 * @param features the features
	 * @param stories  the stories
	 * @return the map
	 */
	public static Map<FeatureScope, Integer> countFeatureFocus(final FeatureDao<String, Feature> features,
			StoryDao<Story> stories) {
		// Initialize list
		Map<FeatureScope, Integer> storyPoints = new EnumMap<>(FeatureScope.class);
		for (FeatureScope scope : FeatureScope.values())
			storyPoints.put(scope, 0);

		for (Story story : stories.getAll()) {
			// Get epic id
			String epicKey = story.getEpic().orElse("");

			// Get particular feature
			Feature feature = features.getAll().get(epicKey);

			// Set default scope
			FeatureScope scope = FeatureScope.BASIC;
			// Get particular scope
			if (feature != null)
				scope = feature.getFeatureScope();

			// Get actual story point value for particular scope
			int scopeValue = storyPoints.get(scope);

			// Increment story point value for particular scope
			scopeValue += story.getStoryPoints().orElse(0);

			// Set new story point value for particular scope
			storyPoints.replace(scope, scopeValue);
		}

		return Map.copyOf(storyPoints);
	}

	/**
	 * Run stats.
	 *
	 * @param user   the user
	 * @param passwd the passwd
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static void runStats(String user, String passwd) throws IOException, InterruptedException {
		// Start processing.
		logger.info("Processing started.");

		// Kick off progress thread. // Provide global parameters
		GlobalParams globalParams = Utils.provideGlobalParams("application.properties");

		// Set user name for issue tracker authentication
		globalParams.setUsername(user);

		// Set password for issue tracker authentication
		globalParams.setPassword(passwd);

		// Get teams repository
		TeamDao<String, Team> teams = Teams.createTeamRepo(globalParams);
		Optional.ofNullable(teams).ifPresent(t -> logger.info("{} teams processed.", t.getAll().size()));

		// Get engineers repository
		EngineerDao<String, Engineer> engineers = Engineers.createEngineersRepo(globalParams);
		Optional.ofNullable(engineers).ifPresent(e -> logger.info("{} engineers processed.", e.getAll().size()));

		// Get sprints repository
		SprintDao<String, Sprint> sprints = Sprints.createSprintRepo(globalParams);
		Optional.ofNullable(sprints).ifPresent(s -> logger.info("{} sprint refinements processed.", s.getAll().size()));

		// Create XLSX output
		try {
			OutputCreators.createXlsxOutput(globalParams, teams, sprints, engineers);
			logger.info("File {} with XLSX content created successfully.", globalParams.getOutputFileName4Xlsx());
		} catch (IllegalArgumentException e) {
			logger.info("No file name for XLSX output in properties file defined.");
		}

		// Processing finished.
		logger.info("Processing finished.");
	}
}
