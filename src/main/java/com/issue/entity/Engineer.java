/**
 * 
 */
package com.issue.entity;

import java.util.Optional;

/**
 * The Class Engineer.
 */
public class Engineer {

	/** The name. */
	private String name;

	/** The finished story points. */
	private Integer finishedStoryPoints;

	/** The not finished story points. */
	private Integer notFinishedStoryPoints;

	/**
	 * Instantiates a new engineer.
	 *
	 * @param name the name
	 * @param finished the finished
	 * @param notFinished the not finished
	 */
	public Engineer(String name, int finished, int notFinished) {
		this.name = Optional.ofNullable(name).orElseThrow();
		this.finishedStoryPoints = finished;
		this.notFinishedStoryPoints = notFinished;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the finished story points.
	 *
	 * @return the finished story points
	 */
	public Optional<Integer> getFinishedStoryPoints() {
		return Optional.ofNullable(finishedStoryPoints);
	}

	/**
	 * Gets the not finished story points.
	 *
	 * @return the not finished story points
	 */
	public Optional<Integer> getNotFinishedStoryPoints() {
		return Optional.ofNullable(notFinishedStoryPoints);
	}
}
