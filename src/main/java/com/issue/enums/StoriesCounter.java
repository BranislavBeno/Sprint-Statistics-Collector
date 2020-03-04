/**
 * 
 */
package com.issue.enums;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Story;
import com.issue.entity.Team;
import com.issue.iface.Countable;
import com.issue.iface.StoryDao;
import com.issue.iface.TeamDao;
import com.issue.utils.Stories;
import com.issue.utils.Teams;

/**
 * The Enum StoriesHandler.
 *
 * @author branislav.beno
 */
public enum StoriesCounter implements Countable {

	/** The not finished. */
	NOT_FINISHED {
		@Override
		public void gatherStories(final GlobalParams globalParams, TeamDao<String, Team> teamsRepo)
				throws IOException, InterruptedException {
			if (globalParams.getNotCompletedSprints() != null) {

				// Run through queries for not finished stories in sprint(s)
				for (String notFinishedInSprintJql : globalParams.getNotCompletedSprints()) {

					// Create stories repository
					StoryDao<Story> stories = Stories.createStoriesRepo(globalParams.getUsername(),
							globalParams.getPassword(), globalParams.getIssueTrackerUri(), notFinishedInSprintJql);

					String teamName = "";

					// Search for particular string inside query
					Pattern pattern = Pattern.compile("Sprint\\s+\\d+\\s+(\\w+)");
					Matcher matcher = pattern.matcher(notFinishedInSprintJql);

					if (matcher.find()) {
						// Get team
						teamName = matcher.group(1);
						Team team = teamsRepo.getAll().get(teamName);

						// Set story points count from stories not finished within sprint(s)
						team.setNotFinishedStoryPointsSum(Stories.summarizeStoryPoints(stories));

						// Set story points count from stories in progress within sprint(s)
						team.setInProgressStoryPointsSum(Stories.summarizeInProgressStoryPoints(stories));

						// Set story points count from stories to do within sprint(s)
						team.setToDoStoryPointsSum(
								team.getNotFinishedStoryPointsSum() - team.getInProgressStoryPointsSum());

						// Count story points planned for sprint on begin of sprint
						team.setOnBeginPlannedStoryPointsSum(
								team.getFinishedStoryPointsSum() + team.getNotFinishedStoryPointsSum());

						// Count story points planned for sprint on end of sprint
						team.setOnEndPlannedStoryPointsSum(team.getOnBeginPlannedStoryPointsSum());

						// Set delta number of story points start vs. close
						team.setDeltaStoryPoints(Stories.calculateDelta(team.getOnBeginPlannedStoryPointsSum(),
								team.getOnEndPlannedStoryPointsSum()));

						// Set planned story points closed
						team.setPlannedStoryPointsClosed(Stories.plannedStoryPointsClosed(
								team.getFinishedStoryPointsSum(), team.getOnBeginPlannedStoryPointsSum()));

						// Set count of not finished high prior stories within sprint(s)
						team.setNotClosedHighPriorStoriesCount(
								Stories.summarizeNotClosedHighPriorStoriesCount(stories));

						// Count time estimation
						team.setTimeEstimation(team.getTimeEstimation() + Teams.summarizeTimeEstimation(stories));

						// Count team members participating on sprint(s)
						Teams.collectTeamMembers(stories, team);

						// Save particular team into repo
						teamsRepo.save(team);
					}

					logger.info("{} not finished stories for Team {} processed.", stories.getAll().size(), teamName);
				}
			}
		}
	},

	/** The added removed. */
	ADDED_REMOVED {
		private int countStoryPointsFromStoriesRemovedAfterSprintStart(final GlobalParams globalParams,
				final String sprintId) throws IOException, InterruptedException {
			if (globalParams.getRemovedAfterSprintStart() != null) {
				for (String removedAfterSprintStartJql : globalParams.getRemovedAfterSprintStart()) {
					if (removedAfterSprintStartJql.contains(sprintId)) {
						// Create stories repository
						StoryDao<Story> stories = Stories.createStoriesRepo(globalParams.getUsername(),
								globalParams.getPassword(), globalParams.getIssueTrackerUri(),
								removedAfterSprintStartJql);
						return Stories.summarizeStoryPoints(stories);
					}
				}
			}

			return 0;
		}

		private long countTimeEstimationFromStoriesRemovedAfterSprintStart(final GlobalParams globalParams,
				final String sprintId) throws IOException, InterruptedException {
			if (globalParams.getRemovedAfterSprintStart() != null) {
				for (String removedAfterSprintStartJql : globalParams.getRemovedAfterSprintStart()) {
					if (removedAfterSprintStartJql.contains(sprintId)) {
						// Create stories repository
						StoryDao<Story> stories = Stories.createStoriesRepo(globalParams.getUsername(),
								globalParams.getPassword(), globalParams.getIssueTrackerUri(),
								removedAfterSprintStartJql);
						return Teams.summarizeTimeEstimation(stories);
					}
				}
			}

			return 0;
		}

		@Override
		public void gatherStories(final GlobalParams globalParams, TeamDao<String, Team> teamsRepo)
				throws IOException, InterruptedException {
			if (globalParams.getAddedAfterSprintStart() != null) {

				// Run through queries for stories added after sprint start
				for (String addedAfterSprintStartJql : globalParams.getAddedAfterSprintStart()) {

					// Create stories repository
					StoryDao<Story> stories = Stories.createStoriesRepo(globalParams.getUsername(),
							globalParams.getPassword(), globalParams.getIssueTrackerUri(), addedAfterSprintStartJql);

					int addedAfterSprintStart = 0;
					int removedAfterSprintStart = 0;
					String teamName = "";

					// Search for particular string inside query
					Pattern pattern = Pattern.compile("(Sprint\\s+\\d+)\\s+(\\w+)");
					Matcher matcher = pattern.matcher(addedAfterSprintStartJql);

					if (matcher.find()) {
						// Gather sprint label
						String sprintLabel = matcher.group(1);

						// Get team
						teamName = matcher.group(2);
						Team team = teamsRepo.getAll().get(teamName);

						// Set sprint id for query pairing
						String sprintId = sprintLabel + " " + team.getTeamName().orElse("");

						// Set story points count planned at sprint start
						// subtract story points count for stories added after sprint start
						// add story points count for stories removed after sprint start
						addedAfterSprintStart = Stories.summarizeStoryPoints(stories);
						removedAfterSprintStart = countStoryPointsFromStoriesRemovedAfterSprintStart(globalParams,
								sprintId);

						// Count story points planned for sprint on begin of sprint
						team.setOnBeginPlannedStoryPointsSum(team.getOnBeginPlannedStoryPointsSum()
								- addedAfterSprintStart + removedAfterSprintStart);

						// Set delta number of story points start vs. close
						team.setDeltaStoryPoints(Stories.calculateDelta(team.getOnBeginPlannedStoryPointsSum(),
								team.getOnEndPlannedStoryPointsSum()));

						// Set planned story points closed
						team.setPlannedStoryPointsClosed(Stories.plannedStoryPointsClosed(
								team.getFinishedStoryPointsSum(), team.getOnBeginPlannedStoryPointsSum()));

						// Count time estimation
						team.setTimeEstimation(team.getTimeEstimation() - Teams.summarizeTimeEstimation(stories)
								+ countTimeEstimationFromStoriesRemovedAfterSprintStart(globalParams, sprintId));

						// Count team members participating on sprint(s)
						Teams.collectTeamMembers(stories, team);

						// Save particular team into repo
						teamsRepo.save(team);
					}

					logger.info("{} story points added and {} story points removed after sprint start for Team {}.",
							addedAfterSprintStart, removedAfterSprintStart, teamName);
				}
			}
		}
	};

	/** The logger. */
	static Logger logger = LogManager.getLogger(StoriesCounter.class);
}
