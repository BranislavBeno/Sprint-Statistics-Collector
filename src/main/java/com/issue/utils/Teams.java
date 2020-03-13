package com.issue.utils;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Feature;
import com.issue.entity.Story;
import com.issue.entity.Team;
import com.issue.enums.StoriesCounter;
import com.issue.iface.FeatureDao;
import com.issue.iface.StoryDao;
import com.issue.iface.TeamDao;
import com.issue.repository.TeamDaoImpl;

/**
 * The Class Teams.
 */
public class Teams {

	/** The logger. */
	static Logger logger = LogManager.getLogger(Teams.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private Teams() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Collect team members.
	 *
	 * @param stories the stories
	 * @param team    the team
	 */
	public static void collectTeamMembers(final StoryDao<Story> stories, Team team) {
		team.addTeamMembers(stories.getAll().stream().filter(s -> !s.getStoryOwner().orElse("").isBlank())
				.map(s -> s.getStoryOwner().orElse("")).collect(Collectors.toSet()));
	}

	/**
	 * Creates the from querires.
	 *
	 * @param setOfQuries the set of quries
	 * @param teamsRepo   the teams repo
	 */
	private static void createFromQuerires(final Set<String> setOfQuries, TeamDao<String, Team> teamsRepo) {
		// Run through queries for finished sprint(s)
		for (String query : setOfQuries) {

			// Search for particular string inside query
			Pattern pattern = Pattern.compile("Sprint\\s+\\d+\\s+(\\w+)");
			Matcher matcher = pattern.matcher(query);

			if (matcher.find()) {
				// Set team name
				Team team = new Team(matcher.group(1));

				// Save particular team into repo
				teamsRepo.save(team);
			}
		}
	}

	/**
	 * Creates the empty team repo.
	 *
	 * @param setOfQuries the set of quries
	 * @param repo        the repo
	 * @return the team dao
	 */
	public static TeamDao<String, Team> createEmptyTeamRepo(final Set<String> setOfQuries, TeamDao<String, Team> repo) {
		if (setOfQuries != null) {
			// Create new repo for teams
			TeamDao<String, Team> teamsRepo = new TeamDaoImpl();

			// Create team repo from queries
			createFromQuerires(setOfQuries, teamsRepo);

			// Enhance new repo with existing teams
			Optional.ofNullable(repo).ifPresent(r -> r.getAll().values().forEach(teamsRepo::save));

			return teamsRepo;
		}

		return repo;
	}

	/**
	 * Initialize team repo.
	 *
	 * @param globalParams the global params
	 * @return the team dao
	 */
	public static TeamDao<String, Team> initializeTeamRepo(final GlobalParams globalParams) {
		// Create empty repo for teams from completed stories queries
		TeamDao<String, Team> teamsRepo = createEmptyTeamRepo(globalParams.getCompletedSprints(), null);

		// Create empty repo for teams from not completed stories queries
		teamsRepo = createEmptyTeamRepo(globalParams.getNotCompletedSprints(), teamsRepo);

		// Create empty repo for teams from stories completed outside sprint queries
		teamsRepo = createEmptyTeamRepo(globalParams.getCompletedOutsideSprints(), teamsRepo);

		// Create empty repo for teams from stories added after sprint started queries
		teamsRepo = createEmptyTeamRepo(globalParams.getAddedAfterSprintStart(), teamsRepo);

		// Create empty repo for teams from stories removed after sprint started queries
		teamsRepo = createEmptyTeamRepo(globalParams.getRemovedAfterSprintStart(), teamsRepo);

		return teamsRepo;
	}

	/**
	 * Creates the team repo.
	 *
	 * @param globalParams the global params
	 * @return the team dao
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static TeamDao<String, Team> createTeamRepo(final GlobalParams globalParams)
			throws IOException, InterruptedException {
		// Create features repository
		FeatureDao<String, Feature> features = Features.createFeaturesRepo(globalParams);
		logger.info("{} features processed.", features.getAll().size());

		// Initialize new repo for teams
		TeamDao<String, Team> teamsRepo = initializeTeamRepo(globalParams);

		if (teamsRepo != null) {
			// Summarize finished stories
			Stories.gatherFinishedStories(globalParams, features, teamsRepo);
			// Summarize not finished stories
			StoriesCounter.NOT_FINISHED.gatherStories(globalParams, teamsRepo);
			// Summarize stories added after sprint start
			StoriesCounter.ADDED_REMOVED.gatherStories(globalParams, teamsRepo);
		}

		return teamsRepo;
	}

	/**
	 * Count finished bugs SP sum.
	 *
	 * @param stories the stories
	 * @return the int
	 */
	public static int countFinishedBugsSPSum(StoryDao<Story> stories) {
		return stories.getAll().stream().filter(s -> s.getStoryType().orElse("").contains("Bugfix"))
				.map(s -> s.getStoryPoints().orElse(0)).collect(Collectors.toList()).stream()
				.collect(Collectors.summingInt(Integer::intValue)).intValue();
	}

	/**
	 * Count time estimation.
	 *
	 * @param stories the stories
	 * @return the long
	 */
	public static long summarizeTimeEstimation(StoryDao<Story> stories) {
		return stories.getAll().stream().map(s -> s.getTimeEstimation().orElse(0)).collect(Collectors.toList()).stream()
				.collect(Collectors.summingInt(Integer::intValue)).longValue() / 3600;
	}

	/**
	 * Summarize time spent.
	 *
	 * @param stories the stories
	 * @return the long
	 */
	public static long summarizeTimeSpent(StoryDao<Story> stories) {
		return stories.getAll().stream().map(s -> s.getTimeSpent().orElse(0)).collect(Collectors.toList()).stream()
				.collect(Collectors.summingInt(Integer::intValue)).longValue() / 3600;
	}
}
