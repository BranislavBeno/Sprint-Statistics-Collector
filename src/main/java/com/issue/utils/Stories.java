/**
 * 
 */
package com.issue.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.issue.configuration.GlobalParams;
import com.issue.entity.Feature;
import com.issue.entity.Sprint;
import com.issue.entity.Story;
import com.issue.entity.Team;
import com.issue.enums.FeatureScope;
import com.issue.iface.FeatureDao;
import com.issue.iface.SprintDao;
import com.issue.iface.StoryDao;
import com.issue.iface.TeamDao;
import com.issue.repository.SprintDaoImpl;
import com.issue.repository.StoryDaoImpl;

/**
 * The Class Stories.
 *
 * @author branislav.beno
 */
public class Stories {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(Stories.class);

	/** The Constant STORY_POINTS_FIELD_ID. */
	private static final String STORY_POINTS_FIELD_ID = "customfield_10002";

	/** The Constant STORY_TYPE_FIELD_ID. */
	private static final String STORY_TYPE_FIELD_ID = "customfield_10540";

	/** The Constant EPIC_LINK_FIELD_ID. */
	private static final String EPIC_LINK_FIELD_ID = "customfield_12640";

	/** The Constant STORY_OWNER_FIELD_ID. */
	private static final String STORY_OWNER_FIELD_ID = "customfield_16040";

	/** The Constant SPRINT_LABEL_PATTERN. */
	public static final String SPRINT_LABEL_PATTERN = "(Sprint\\s+\\d+)\\s+(\\w+)";

	/**
	 * Utility classes should not have public constructors.
	 */
	private Stories() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates the request uri.
	 *
	 * @param issueTrackerUri the issue tracker uri
	 * @param featuresJql     the features jql
	 * @param fields          the fields
	 * @return the string
	 */
	public static String createRequestUri(String issueTrackerUri, String featuresJql, String fields) {
		StringBuilder sb = new StringBuilder();
		sb.append(issueTrackerUri).append("?").append("jql=").append(Utils.prepareUrl(featuresJql))
				.append("&maxResults=1000").append("&fields=").append(fields);
		return sb.toString();
	}

	/**
	 * Ask issue tracker.
	 *
	 * @param username        the username
	 * @param password        the password
	 * @param issueTrackerUri the issue tracker uri
	 * @param query           the features jql
	 * @return the string
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	private static String askIssueTracker(String username, String password, String issueTrackerUri, String query)
			throws IOException, InterruptedException {
		String request = createRequestUri(issueTrackerUri, query,
				"assignee,issuetype,status,resolution,priority,aggregatetimeoriginalestimate,aggregatetimespent,"
						+ STORY_POINTS_FIELD_ID + "," + EPIC_LINK_FIELD_ID + "," + STORY_OWNER_FIELD_ID + ","
						+ STORY_TYPE_FIELD_ID);

		return Utils.gatherJsonString(username, password, request);
	}

	/**
	 * Parses the story points.
	 *
	 * @param issueFields the issue fields
	 * @return the int
	 */
	private static int parseStoryPoints(JsonNode issueFields) {
		// Get json node story points
		JsonNode storyPoints = Optional.ofNullable(issueFields.get(STORY_POINTS_FIELD_ID)).orElse(new ObjectNode(null));

		return storyPoints.asInt(0);
	}

	/**
	 * Parses the time estimation.
	 *
	 * @param issueFields the issue fields
	 * @return the int
	 */
	private static int parseTimeEstimation(JsonNode issueFields) {
		// Get json node aggregate time original estimate
		JsonNode timeEstimation = Optional.ofNullable(issueFields.get("aggregatetimeoriginalestimate"))
				.orElse(new ObjectNode(null));

		return timeEstimation.asInt(0);
	}

	/**
	 * Parses the time spent.
	 *
	 * @param issueFields the issue fields
	 * @return the int
	 */
	private static int parseTimeSpent(JsonNode issueFields) {
		// Get json node aggregate time spent
		JsonNode timeSpent = Optional.ofNullable(issueFields.get("aggregatetimespent")).orElse(new ObjectNode(null));

		return timeSpent.asInt(0);
	}

	/**
	 * Parses the epic link.
	 *
	 * @param issueFields the issue fields
	 * @return the string
	 */
	private static String parseEpicLink(JsonNode issueFields) {
		// Initialize epic link text
		String link = "";

		// Get json node epic link
		if (issueFields.get(EPIC_LINK_FIELD_ID) != null) {
			link = issueFields.get(EPIC_LINK_FIELD_ID).asText();
		}

		return link;
	}

	/**
	 * Parses the story owner.
	 *
	 * @param issueFields the issue fields
	 * @return the string
	 */
	private static String parseStoryOwner(JsonNode issueFields) {
		// Initialize story owner name
		JsonNode storyOwnerName = null;

		// Get assignee field
		JsonNode storyOwnerField = issueFields.get("assignee");

		// Is assignee found?
		if (storyOwnerField != null) {
			storyOwnerName = storyOwnerField.get("displayName");
		} else {
			// Get story owner field inn case that no assignee was found
			storyOwnerField = issueFields.get(STORY_OWNER_FIELD_ID);

			if (storyOwnerField != null)
				storyOwnerName = storyOwnerField.get("displayName");
		}

		// Get story owner
		JsonNode owner = Optional.ofNullable(storyOwnerName).orElse(new ObjectNode(null));

		return owner.asText();
	}

	/**
	 * Parses the story priority.
	 *
	 * @param issueFields the issue fields
	 * @return the string
	 */
	private static String parseStoryPriority(JsonNode issueFields) {
		// Initialize priority name json node
		JsonNode priorityName = null;

		// Get json node priority
		JsonNode priorityField = issueFields.get("priority");

		if (priorityField != null) {
			priorityName = priorityField.get("name");
		}

		return Optional.ofNullable(priorityName).orElse(new ObjectNode(null)).asText();
	}

	/**
	 * Parses the story type.
	 *
	 * @param issueFields the issue fields
	 * @return the string
	 */
	private static String parseStoryType(JsonNode issueFields) {
		// Initialize story type name json node
		JsonNode storyTypeName = null;

		// Get json node story type
		JsonNode storyTypeField = issueFields.get(STORY_TYPE_FIELD_ID);

		if (storyTypeField != null) {
			storyTypeName = storyTypeField.get("value");
		}

		return Optional.ofNullable(storyTypeName).orElse(new ObjectNode(null)).asText();
	}

	/**
	 * Parses the story status.
	 *
	 * @param issueFields the issue fields
	 * @return the string
	 */
	private static String parseStoryStatus(JsonNode issueFields) {
		// Initialize status name json node
		JsonNode statusName = null;

		// Get json node status
		JsonNode statusField = issueFields.get("status");

		if (statusField != null) {
			statusName = statusField.get("name");
		}

		return Optional.ofNullable(statusName).orElse(new ObjectNode(null)).asText();
	}

	/**
	 * Parses the story resolution.
	 *
	 * @param issueFields the issue fields
	 * @return the string
	 */
	private static String parseStoryResolution(JsonNode issueFields) {
		// Initialize resolution name json node
		JsonNode resolutionName = null;

		// Get json node resolution
		JsonNode resolutionField = issueFields.get("resolution");

		if (resolutionField != null) {
			resolutionName = resolutionField.get("name");
		}

		return Optional.ofNullable(resolutionName).orElse(new ObjectNode(null)).asText();
	}

	/**
	 * Parses the issue type.
	 *
	 * @param issueFields the issue fields
	 * @return the string
	 */
	private static String parseIssueType(JsonNode issueFields) {
		// Initialize issue type name JSON node
		JsonNode issuetypeName = null;

		// Get JSON node issue type
		JsonNode issuetypeField = issueFields.get("issuetype");

		if (issuetypeField != null) {
			issuetypeName = issuetypeField.get("name");
		}

		return Optional.ofNullable(issuetypeName).orElse(new ObjectNode(null)).asText();
	}

	/**
	 * Extract stories.
	 *
	 * @param jsonString the JSON string
	 * @return the story DAO
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static StoryDao<Story> extractStories(final String jsonString) throws IOException {
		// Create empty stories list
		StoryDao<Story> stories = new StoryDaoImpl();

		// Get json node "issues"
		JsonNode issuesNode = null;
		if (jsonString != null)
			issuesNode = new ObjectMapper().readTree(jsonString).get("issues");

		// run through issues
		Optional.ofNullable(issuesNode).ifPresent(in -> in.forEach(issue -> {
			// Get json node "fields"
			JsonNode issueFields = issue.get("fields");

			if (issueFields != null) {

				// Get story points
				int sp = parseStoryPoints(issueFields);

				// Get aggregate time original estimate
				int timeEstimation = parseTimeEstimation(issueFields);

				// Get aggregate time spent
				int timeSpent = parseTimeSpent(issueFields);

				// Get epic link
				String epic = parseEpicLink(issueFields);

				// Get story owner
				String storyOwner = parseStoryOwner(issueFields);

				// Get priority name
				String priority = parseStoryPriority(issueFields);

				// Get story type
				String storyType = parseStoryType(issueFields);

				// Get story status
				String status = parseStoryStatus(issueFields);

				// Get story resolution
				String resolution = parseStoryResolution(issueFields);

				// Get story issue type
				String issueType = parseIssueType(issueFields);

				// Add new story into list
				stories.save(new Story.Builder().epic(epic).storyPoints(sp).storyOwner(storyOwner).priority(priority)
						.timeEstimation(timeEstimation).timeSpent(timeSpent).storyType(storyType).status(status)
						.resolution(resolution).issueType(issueType).build());
			}
		}));

		return stories;
	}

	/**
	 * Creates the stories repo.
	 *
	 * @param username        the username
	 * @param password        the password
	 * @param issueTrackerUri the issue tracker uri
	 * @param storiesJql      the stories jql
	 * @return the story dao
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static StoryDao<Story> createStoriesRepo(String username, String password, String issueTrackerUri,
			String storiesJql) throws IOException, InterruptedException {

		// Create empty stories list
		StoryDao<Story> stories = new StoryDaoImpl();

		// Get json response
		String jsonStories = askIssueTracker(username, password, issueTrackerUri, storiesJql);

		// Save extracted stories into repository
		stories.saveAll(Stories.extractStories(jsonStories).getAll());

		return stories;
	}

	/**
	 * Summarize story points.
	 *
	 * @param storyPoints the story points
	 * @return the int
	 */
	public static int summarizeStoryPoints(Map<FeatureScope, Integer> storyPoints) {
		return storyPoints.values().stream().collect(Collectors.summingInt(Integer::intValue));
	}

	/**
	 * Summarize story points.
	 *
	 * @param stories the stories
	 * @return the int
	 */
	public static int summarizeStoryPoints(StoryDao<Story> stories) {
		return stories.getAll().stream().map(s -> s.getStoryPoints().orElse(0)).collect(Collectors.toList()).stream()
				.collect(Collectors.summingInt(Integer::intValue));
	}

	/**
	 * Summarize in progress story points.
	 *
	 * @param stories the stories
	 * @return the int
	 */
	public static int summarizeInProgressStoryPoints(StoryDao<Story> stories) {
		// Prepare set of statuses in progress
		Set<String> statuses = Set.of("In Progress", "Ready for Test", "In Test");

		// Compute summary of story points for status in progress
		return stories.getAll().stream().filter(s -> statuses.contains(s.getStatus().orElse("")))
				.map(s -> s.getStoryPoints().orElse(0)).collect(Collectors.toList()).stream()
				.collect(Collectors.summingInt(Integer::intValue));
	}

	/**
	 * Summarize high prior stories count.
	 *
	 * @param stories the stories
	 * @return the int
	 */
	public static int summarizeHighPriorStoriesCount(StoryDao<Story> stories) {
		// Prepare set of high prior statuses
		Set<String> priorities = Set.of("High", "Critical", "Blocking");

		// Count stories with high prior status
		return stories.getAll().stream().filter(s -> priorities.contains(s.getPriority().orElse("")))
				.collect(Collectors.counting()).intValue();
	}

	/**
	 * Calculate delta.
	 *
	 * @param onBeginPlanned the on begin planned
	 * @param onEndPlanned   the on end planned
	 * @return the double
	 */
	public static double calculateDelta(final int onBeginPlanned, final int onEndPlanned) {
		return Math.abs(onEndPlanned - onBeginPlanned) / (double) onBeginPlanned;
	}

	/**
	 * Planned story points closed.
	 *
	 * @param planned  the planned
	 * @param finished the finished
	 * @return the double
	 */
	public static double plannedStoryPointsClosed(final int planned, final int finished) {
		return finished / (double) planned;
	}

	/**
	 * Calculate success rate.
	 *
	 * @param notFinished the not finished
	 * @param finished    the finished
	 * @return the double
	 */
	public static double calculateSuccessRate(final int notFinished, final int finished) {
		return (double) finished / (finished + notFinished);
	}

	/**
	 * Adds the stories finished out of sprint.
	 *
	 * @param globalParams the global params
	 * @param sprintId     the sprint id
	 * @param stories      the stories
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static void addStoriesFinishedOutOfSprint(final GlobalParams globalParams, final String sprintId,
			StoryDao<Story> stories) throws IOException, InterruptedException {
		if (globalParams.getCompletedOutsideSprints() != null) {
			for (String finishedOutsideSprintJql : globalParams.getCompletedOutsideSprints()) {
				if (finishedOutsideSprintJql.contains(sprintId)) {
					// Create stories repository
					StoryDao<Story> additionalStories = createStoriesRepo(globalParams.getUsername(),
							globalParams.getPassword(), globalParams.getIssueTrackerUri(), finishedOutsideSprintJql);
					stories.saveAll(additionalStories.getAll());
					break;
				}
			}
		}
	}

	/**
	 * Removes the canceled stories.
	 *
	 * @param stories the stories
	 */
	public static void removeCanceledStories(StoryDao<Story> stories) {
		// Get list of stories
		List<Story> storiesList = stories.getAll();

		// Prepare set of story resolutions
		Set<String> resolutions = Set.of("Canceled");

		// Prepare empty list of canceled stories
		List<Story> canceledStories = new ArrayList<>();

		// Iterate through stories list
		for (Story story : storiesList) {
			// Get story resolution
			String resolution = story.getResolution().orElse("");

			// Add canceled story into list of canceled stories
			if (!resolution.isBlank() && resolutions.contains(resolution))
				canceledStories.add(story);
		}

		// Remove all canceled stories
		stories.getAll().removeAll(canceledStories);
	}

	/**
	 * Gather finished stories.
	 *
	 * @param globalParams the global params
	 * @param features     the features
	 * @param teamsRepo    the teams repo
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static void gatherFinishedStories(final GlobalParams globalParams,
			final FeatureDao<String, Feature> features, TeamDao<String, Team> teamsRepo)
			throws IOException, InterruptedException {
		if (globalParams.getCompletedSprints() != null) {

			// Run through queries for finished sprint(s)
			for (String finishedInSprintJql : globalParams.getCompletedSprints()) {

				// Create stories repository
				StoryDao<Story> stories = createStoriesRepo(globalParams.getUsername(), globalParams.getPassword(),
						globalParams.getIssueTrackerUri(), finishedInSprintJql);

				String teamName = "";

				// Search for particular string inside query
				Pattern pattern = Pattern.compile(SPRINT_LABEL_PATTERN);
				Matcher matcher = pattern.matcher(finishedInSprintJql);

				if (matcher.find()) {
					// Gather sprint label
					String sprintLabel = matcher.group(1);

					// Get team
					teamName = matcher.group(2);
					Team team = teamsRepo.getAll().get(teamName);

					// Set sprint id for query pairing
					String sprintId = sprintLabel + " " + team.getTeamName();

					// Add issues finished outside of the sprint which are related to sprint id
					addStoriesFinishedOutOfSprint(globalParams, sprintId, stories);

					// Remove canceled stories
					removeCanceledStories(stories);

					// Summarize story points from all issue types finished within sprint(s)
					team.setFinishedStoryPoints(Utils.countFeatureFocus(features, stories));

					// Summarize story points from bug fix related issues finished within sprint(s)
					team.setFinishedBugsSPSum(Teams.countFinishedBugsSPSum(stories));

					// Summarize story points from non bug fix related issues finished within
					// sprint(s)
					team.setFinishedStoriesSPSum(team.getFinishedStoryPointsSum() - team.getFinishedBugsSPSum());

					// Summarize story points planned for sprint on begin of sprint
					team.setOnBeginPlannedStoryPointsSum(team.getFinishedStoryPointsSum());

					// Summarize story points planned for sprint on end of sprint
					team.setOnEndPlannedStoryPointsSum(team.getOnBeginPlannedStoryPointsSum());

					// Set delta number of story points start vs. close
					team.setDeltaStoryPoints(Stories.calculateDelta(team.getOnBeginPlannedStoryPointsSum(),
							team.getOnEndPlannedStoryPointsSum()));

					// Set planned story points closed
					team.setPlannedStoryPointsClosed(Stories.plannedStoryPointsClosed(
							team.getOnBeginPlannedStoryPointsSum(), team.getFinishedStoryPointsSum()));

					// Summarize finished high prior stories within sprint(s)
					team.setClosedHighPriorStoriesCount(Stories.summarizeHighPriorStoriesCount(stories));

					// Calculate high priority stories closed out success rate
					team.setClosedHighPriorStoriesSuccessRate(
							Stories.calculateSuccessRate(0, team.getClosedHighPriorStoriesCount()));

					// Summarize time estimation
					team.setTimeEstimation(Teams.summarizeTimeEstimation(stories));

					// Summarize planned time
					team.setTimePlanned(team.getTimeEstimation());

					// Summarize time spent
					team.setTimeSpent(Teams.summarizeTimeSpent(stories));

					// Collect team members participating on sprint(s)
					Teams.collectTeamMembers(stories, team);

					// Collect refined story points
					team.setRefinedStoryPoints(collectRefinedStoryPoints(globalParams, features, finishedInSprintJql));
					logger.info("Collected refined story points for team {}", team.getTeamName());

					// Save particular team into repository
					teamsRepo.save(team);
				}

				logger.info("{} finished stories for Team {} processed.", stories.getAll().size(), teamName);
			}
		}
	}

	/**
	 * Collect refined story points.
	 *
	 * @param globalParams        the global params
	 * @param features            the features
	 * @param finishedInSprintJql the finished in sprint jql
	 * @return the sprint dao
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	private static SprintDao<String, Sprint> collectRefinedStoryPoints(final GlobalParams globalParams,
			final FeatureDao<String, Feature> features, String finishedInSprintJql)
			throws IOException, InterruptedException {
		// Initialize new repository for refined story points
		SprintDao<String, Sprint> sprintsRepo = new SprintDaoImpl();

		// Create list of queries for gathering refined story points
		Map<Integer, String> queries = Teams.createRefinementQueries(finishedInSprintJql);
		logger.debug("Added new queries for refined stories");

		// Run through refined and planned sprints
		for (Entry<Integer, String> entry : queries.entrySet()) {
			// Initialize repository for refined stories
			StoryDao<Story> refinementStories = new StoryDaoImpl();

			// Get query
			String query = entry.getValue();

			// Create stories repository
			refinementStories.saveAll(Stories.createStoriesRepo(globalParams.getUsername(), globalParams.getPassword(),
					globalParams.getIssueTrackerUri(), query).getAll());

			// Initialize sprint object
			Sprint sprint = new Sprint("Sprint " + entry.getKey());

			// Count story points from stories refined for particular sprint
			sprint.setRefinedStoryPoints(Utils.countFeatureFocus(features, refinementStories));

			// Save refined sprint analysis
			sprintsRepo.save(sprint);
		}

		return sprintsRepo;
	}
}
