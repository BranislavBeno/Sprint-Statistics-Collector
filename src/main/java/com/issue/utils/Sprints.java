/**
 * 
 */
package com.issue.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Feature;
import com.issue.entity.Sprint;
import com.issue.entity.Story;
import com.issue.iface.FeatureDao;
import com.issue.iface.SprintDao;
import com.issue.iface.StoryDao;
import com.issue.repository.SprintDaoImpl;
import com.issue.repository.StoryDaoImpl;

/**
 * The Class Utils.
 *
 * @author branislav.beno
 */
public class Sprints {

	/** The logger. */
	static Logger logger = LogManager.getLogger(Sprints.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private Sprints() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates the queries.
	 *
	 * @param queries     the queries
	 * @param sprintLabel the sprint label
	 * @param sprintNr    the sprint nr
	 */
	private static void createQueries(Map<Integer, Set<String>> queries, final String sprintLabel,
			final Integer sprintNr) {
		Integer sprint = sprintNr;
		for (int i = 0; i <= 3; i++) {
			sprint++;

			// Create new sprint label
			String sprintLbl = sprintLabel.replaceFirst("(\\d+)", sprint.toString());

			// Create new query
			String query = "issuetype = Story AND Sprint in ('" + sprintLbl + "')";

			// Set subsequent set
			Set<String> qSet = queries.get(sprint);

			if (qSet == null)
				qSet = new HashSet<>();

			qSet.add(query);

			// Add query into set
			queries.put(sprint, qSet);

			logger.debug("New created query is: {}", query);
		}
	}

	/**
	 * Creates the refinement queries.
	 *
	 * @param globalParams the global params
	 */
	public static void createRefinementQueries(GlobalParams globalParams) {
		if (globalParams.getCompletedSprints() != null) {
			// Initialize hash set for queries
			Map<Integer, Set<String>> queries = new HashMap<>();

			// Run through queries for finished sprint(s)
			for (String jql : globalParams.getCompletedSprints()) {

				// Search for particular string inside query
				Pattern pattern = Pattern.compile("'(.+)'");
				Matcher matcher = pattern.matcher(jql);

				if (matcher.find()) {
					// Gather sprint label
					String sprintLabel = matcher.group(1);

					// Set new regex
					pattern = Pattern.compile("(\\d+)");
					matcher = pattern.matcher(sprintLabel);

					if (matcher.find()) {
						// Gather sprint number
						Integer sprint = Optional.ofNullable(matcher.group(1)).map(Integer::valueOf).orElse(0);

						// Create map of queries related to particular sprint
						createQueries(queries, sprintLabel, sprint);
					}
				}
			}

			// Add new queries
			globalParams.setRefinements(queries);
			logger.info("Added new queries for refined stories");
		}
	}

	/**
	 * Creates the sprint repo.
	 *
	 * @param globalParams the global params
	 * @return the sprint dao
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static SprintDao<String, Sprint> createSprintRepo(final GlobalParams globalParams)
			throws IOException, InterruptedException {
		// Initialize sprints repository
		SprintDao<String, Sprint> sprintsRepo = null;

		// Create new queries for refined stories
		Sprints.createRefinementQueries(globalParams);

		if (globalParams.getRefinements() != null) {
			// Create features repository
			FeatureDao<String, Feature> features = Features.createFeaturesRepo(globalParams);
			logger.info("{} features processed.", features.getAll().size());

			sprintsRepo = new SprintDaoImpl();

			// Run through refined and planned sprints
			for (Entry<Integer, Set<String>> entry : globalParams.getRefinements().entrySet()) {
				// Initialize stories repo
				StoryDao<Story> stories = new StoryDaoImpl();

				// Get queries set
				Set<String> queries = entry.getValue();

				// Run through queries
				for (String query : queries) {
					// Create stories repository
					stories.saveAll(Stories.createStoriesRepo(globalParams.getUsername(), globalParams.getPassword(),
							globalParams.getIssueTrackerUri(), query).getAll());
				}

				// Initialize sprint object
				Sprint sprint = new Sprint("Sprint " + entry.getKey());

				// Count story points from stories refined for particular sprint
				sprint.setRefinedStoryPoints(Utils.countFeatureFocus(features, stories));

				// Save refined sprint analysis
				sprintsRepo.save(sprint);
			}
		}

		return sprintsRepo;
	}
}
