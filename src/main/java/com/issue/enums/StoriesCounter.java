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
					Pattern pattern = Pattern.compile(Stories.SPRINT_LABEL_PATTERN);
					Matcher matcher = pattern.matcher(notFinishedInSprintJql);

					if (matcher.find()) {
						// Get team
						teamName = matcher.group(2);
						Team team = teamsRepo.getAll().get(teamName);

						// Set story points count from stories not finished within sprint(s)
						team.setNotFinishedStoryPointsSum(Stories.summarizeStoryPoints(stories));

						// Set story points count from stories in progress within sprint(s)
						team.setInProgressStoryPointsSum(Stories.summarizeInProgressStoryPoints(stories));

						// Set story points count from stories to do within sprint(s)
						team.setToDoStoryPointsSum(
								team.getNotFinishedStoryPointsSum() - team.getInProgressStoryPointsSum());

						// Summarize story points planned for sprint on begin of sprint
						team.setOnBeginPlannedStoryPointsSum(
								team.getFinishedStoryPointsSum() + team.getNotFinishedStoryPointsSum());

						// Summarize story points planned for sprint on end of sprint
						team.setOnEndPlannedStoryPointsSum(team.getOnBeginPlannedStoryPointsSum());

						// Calculate delta number of story points start vs. close
						team.setDeltaStoryPoints(Stories.calculateDelta(team.getOnBeginPlannedStoryPointsSum(),
								team.getOnEndPlannedStoryPointsSum()));

						// Count planned story points closed
						team.setPlannedStoryPointsClosed(Stories.plannedStoryPointsClosed(
								team.getFinishedStoryPointsSum(), team.getOnBeginPlannedStoryPointsSum()));

						// Count count of not finished high prior stories within sprint(s)
						team.setNotClosedHighPriorStoriesCount(
								Stories.summarizeNotClosedHighPriorStoriesCount(stories));

						// Summarize time estimation
						team.setTimeEstimation(team.getTimeEstimation() + Teams.summarizeTimeEstimation(stories));

						// Summarize planned time
						team.setTimePlanned(team.getTimeEstimation());

						// Summarize time spent
						team.setTimeSpent(team.getTimeSpent() + Teams.summarizeTimeSpent(stories));

						// Collect team members participating on sprint(s)
						Teams.collectTeamMembers(stories, team);

						// Save particular team into repository
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
					Pattern pattern = Pattern.compile(Stories.SPRINT_LABEL_PATTERN);
					Matcher matcher = pattern.matcher(addedAfterSprintStartJql);

					if (matcher.find()) {
						// Gather sprint label
						String sprintLabel = matcher.group(1);

						// Get team
						teamName = matcher.group(2);
						Team team = teamsRepo.getAll().get(teamName);

						// Set sprint id for query pairing
						String sprintId = sprintLabel + " " + team.getTeamName();

						// Set story points count planned at sprint start
						// subtract story points count for stories added after sprint start
						addedAfterSprintStart = Stories.summarizeStoryPoints(stories);

						// Summarize story points count for stories removed after sprint start
						removedAfterSprintStart = countStoryPointsFromStoriesRemovedAfterSprintStart(globalParams,
								sprintId);

						// Summarize story points planned for sprint on begin of sprint
						team.setOnBeginPlannedStoryPointsSum(team.getOnBeginPlannedStoryPointsSum()
								- addedAfterSprintStart + removedAfterSprintStart);

						// Calculate delta number of story points start vs. close
						team.setDeltaStoryPoints(Stories.calculateDelta(team.getOnBeginPlannedStoryPointsSum(),
								team.getOnEndPlannedStoryPointsSum()));

						// Summarize planned story points closed
						team.setPlannedStoryPointsClosed(Stories.plannedStoryPointsClosed(
								team.getFinishedStoryPointsSum(), team.getOnBeginPlannedStoryPointsSum()));

						// Summarize time estimation
						team.setTimeEstimation(team.getTimeEstimation() - Teams.summarizeTimeEstimation(stories)
								+ countTimeEstimationFromStoriesRemovedAfterSprintStart(globalParams, sprintId));

						// Collect team members participating on sprint(s)
						Teams.collectTeamMembers(stories, team);

						// Save particular team into repository
						teamsRepo.save(team);
					}

					logger.info("{} story points added and {} story points removed after sprint start for Team {}.",
							addedAfterSprintStart, removedAfterSprintStart, teamName);
				}
			}
		}
	};

	/** The logger. */
	private static Logger logger = LogManager.getLogger(StoriesCounter.class);
}
