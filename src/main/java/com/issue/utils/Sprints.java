/**
 * 
 */
package com.issue.utils;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.entity.Sprint;
import com.issue.entity.Team;
import com.issue.enums.FeatureScope;
import com.issue.iface.SprintDao;
import com.issue.iface.TeamDao;
import com.issue.repository.SprintDaoImpl;

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
	 * Creates the sprint repository.
	 *
	 * @param globalParams the global parameters
	 * @return the sprint dao
	 */
	public static SprintDao<String, Sprint> createSprintRepo(final TeamDao<String, Team> teams) {
		// Initialize sprints repository
		SprintDao<String, Sprint> sprintsRepo = new SprintDaoImpl();

		// Run through team data
		if (teams != null) {
			for (Entry<String, Team> teamEntry : teams.getAll().entrySet()) {
				// Get team
				Team team = teamEntry.getValue();

				// Get map of refined story points
				if (team.getRefinedStoryPoints() != null) {
					SprintDao<String, Sprint> sprints = team.getRefinedStoryPoints();

					for (Entry<String, Sprint> sprintEntry : sprints.getAll().entrySet()) {
						// Get particular sprint
						Sprint sprint = sprintEntry.getValue();
						String sprintLabel = sprint.getSprintLabel();

						if (sprintsRepo.getAll().containsKey(sprintLabel)) {
							// Get team related refined story points
							Map<FeatureScope, Integer> teamSP = sprint.getRefinedStoryPoints()
									.orElse(new EnumMap<>(FeatureScope.class));

							// Get summary of refined story points
							Map<FeatureScope, Integer> sumSP = sprintsRepo.getAll().get(sprintLabel)
									.getRefinedStoryPoints().orElse(new EnumMap<>(FeatureScope.class));

							sprint.setRefinedStoryPoints(countRefinedSP(teamSP, sumSP));
						}

						// Create new sprint item
						sprintsRepo.save(sprint);
					}
				}
			}
		}

		return sprintsRepo;
	}

	private static Map<FeatureScope, Integer> countRefinedSP(final Map<FeatureScope, Integer> teamSP,
			final Map<FeatureScope, Integer> sumSP) {
		// Initialize new map of refined story points
		Map<FeatureScope, Integer> newSP = new EnumMap<>(FeatureScope.class);

		// Count summary of refined story points
		for (Entry<FeatureScope, Integer> spEntry : sumSP.entrySet()) {
			// Get scope as key
			FeatureScope key = spEntry.getKey();

			// Add team related story points to summarized story points
			int count = spEntry.getValue() + teamSP.get(key);

			// Set new counted story points value
			newSP.put(key, count);
		}
		return newSP;
	}
}
