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
import com.issue.entity.Engineer;
import com.issue.entity.Story;
import com.issue.iface.EngineerDao;
import com.issue.iface.Parseable;
import com.issue.iface.StoryDao;
import com.issue.repository.EngineerDaoImpl;
import com.issue.utils.Engineers;
import com.issue.utils.Stories;

/**
 * The Enum EngineersParser.
 */
public enum EngineersParser implements Parseable {

	/** The finished. */
	FINISHED {
		@Override
		public EngineerDao<String, Engineer> gatherEngineers(final GlobalParams globalParams)
				throws IOException, InterruptedException {
			// Create and initialize new engineers repo
			EngineerDao<String, Engineer> engineerRepo = new EngineerDaoImpl();

			if (globalParams.getCompletedSprints() != null) {
				// Run through queries for finished sprint(s)
				for (String finishedInSprintJql : globalParams.getCompletedSprints()) {

					// Create stories repository
					StoryDao<Story> stories = Stories.createStoriesRepo(globalParams.getUsername(),
							globalParams.getPassword(), globalParams.getIssueTrackerUri(), finishedInSprintJql);

					// Search for particular string inside query
					Pattern pattern = Pattern.compile("(Sprint\\s+\\d+\\s+\\w+)");
					Matcher matcher = pattern.matcher(finishedInSprintJql);

					if (matcher.find()) {
						// Gather sprint label
						String sprintLabel = matcher.group(1);

						// Add issues finished outside of the sprint which are related to sprint id
						Stories.addStoriesFinishedOutOfSprint(globalParams, sprintLabel, stories);
					}

					// Collect engineers with their finished story points
					EngineerDao<String, Engineer> eRepo = Engineers.collectFinished4Engineers(stories,
							globalParams.getSprintLabel());

					if (eRepo != null) {
						engineerRepo.saveAll(eRepo.getAll());
					}
				}
			}

			logger.info("Collected {} engineers from finished stories.", engineerRepo.getAll().size());

			return engineerRepo;
		}
	},

	/** The not finished. */
	NOT_FINISHED {
		@Override
		public EngineerDao<String, Engineer> gatherEngineers(final GlobalParams globalParams)
				throws IOException, InterruptedException {
			// Create and initialize new engineers repo
			EngineerDao<String, Engineer> engineerRepo = new EngineerDaoImpl();

			if (globalParams.getNotCompletedSprints() != null) {
				// Run through queries for not finished sprint(s)
				for (String notFinishedInSprintJql : globalParams.getNotCompletedSprints()) {

					// Create stories repository
					StoryDao<Story> stories = Stories.createStoriesRepo(globalParams.getUsername(),
							globalParams.getPassword(), globalParams.getIssueTrackerUri(), notFinishedInSprintJql);

					// Collect engineers with their not finished story points
					EngineerDao<String, Engineer> eRepo = Engineers.collectNotFinished4Engineers(stories,
							globalParams.getSprintLabel());

					if (eRepo != null) {
						engineerRepo.saveAll(eRepo.getAll());
					}
				}
			}
			logger.info("Collected {} engineers from not finished stories.", engineerRepo.getAll().size());

			return engineerRepo;
		}
	};

	/** The logger. */
	static Logger logger = LogManager.getLogger(EngineersParser.class);
}
