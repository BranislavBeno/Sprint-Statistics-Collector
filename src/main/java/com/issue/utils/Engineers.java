package com.issue.utils;

import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Engineer;
import com.issue.entity.Story;
import com.issue.enums.EngineersParser;
import com.issue.iface.EngineerDao;
import com.issue.iface.StoryDao;
import com.issue.repository.EngineerDaoImpl;

/**
 * The Class Engineers.
 */
public class Engineers {

	/** The logger. */
	static Logger logger = LogManager.getLogger(Engineers.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private Engineers() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates the engineers repo.
	 *
	 * @param globalParams the global params
	 * @return the engineer dao
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static EngineerDao<String, Engineer> createEngineersRepo(final GlobalParams globalParams)
			throws IOException, InterruptedException {
		// Summarize engineers from finished stories
		EngineerDao<String, Engineer> engineersRepo = EngineersParser.FINISHED.gatherEngineers(globalParams);

		// Summarize not finished stories
		engineersRepo.saveAll(EngineersParser.NOT_FINISHED.gatherEngineers(globalParams).getAll());

		return engineersRepo;
	}

	/**
	 * Collect finished 4 engineers.
	 *
	 * @param stories the stories
	 * @return the engineer dao
	 */
	public static EngineerDao<String, Engineer> collectFinished4Engineers(final StoryDao<Story> stories) {
		// Initialize map
		EngineerDao<String, Engineer> engineers = new EngineerDaoImpl();

		// Iterate over stories
		for (Story story : stories.getAll()) {
			// Get story owners name
			String owner = story.getStoryOwner().orElse("");

			if (!owner.isBlank()) {
				// Get finished story points
				int newFinished = story.getStoryPoints().orElse(0);

				// Initialize old story points counter
				int oldFinished = 0;
				int oldNotFinished = 0;

				Engineer engineer = engineers.getAll().get(owner);
				if (Optional.ofNullable(engineer).isPresent()) {
					oldFinished = engineer.getFinishedStoryPoints().orElse(0);
					oldNotFinished = engineer.getNotFinishedStoryPoints().orElse(0);
				}

				engineers.getAll().put(owner, new Engineer(owner, newFinished + oldFinished, oldNotFinished));
			}
		}

		return engineers;
	}

	/**
	 * Collect not finished 4 engineers.
	 *
	 * @param stories the stories
	 * @return the engineer dao
	 */
	public static EngineerDao<String, Engineer> collectNotFinished4Engineers(final StoryDao<Story> stories) {
		// Initialize map
		EngineerDao<String, Engineer> engineers = new EngineerDaoImpl();

		// Iterate over stories
		for (Story story : stories.getAll()) {
			// Get story owners name
			String owner = story.getStoryOwner().orElse("");

			if (!owner.isBlank()) {
				// Get not finished story points
				int newNotFinished = story.getStoryPoints().orElse(0);

				// Initialize old story points counter
				int oldFinished = 0;
				int oldNotFinished = 0;

				Engineer engineer = engineers.getAll().get(owner);
				if (Optional.ofNullable(engineer).isPresent()) {
					oldFinished = engineer.getFinishedStoryPoints().orElse(0);
					oldNotFinished = engineer.getNotFinishedStoryPoints().orElse(0);
				}

				engineers.getAll().put(owner, new Engineer(owner, oldFinished, newNotFinished + oldNotFinished));
			}
		}

		return engineers;
	}
}
