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
	private Integer finishedStoryPoints = 0;

	/** The not finished story points. */
	private Integer notFinishedStoryPoints = 0;

	/**
	 * Instantiates a new engineer.
	 *
	 * @param name        the name
	 */
	public Engineer(String name) {
		this.name = Optional.ofNullable(name).orElseThrow();
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
	 * Sets the finished story points.
	 *
	 * @param finishedStoryPoints the finishedStoryPoints to set
	 */
	public void setFinishedStoryPoints(Integer finishedStoryPoints) {
		this.finishedStoryPoints = finishedStoryPoints;
	}

	/**
	 * Gets the not finished story points.
	 *
	 * @return the not finished story points
	 */
	public Optional<Integer> getNotFinishedStoryPoints() {
		return Optional.ofNullable(notFinishedStoryPoints);
	}

	/**
	 * Sets the not finished story points.
	 *
	 * @param notFinishedStoryPoints the notFinishedStoryPoints to set
	 */
	public void setNotFinishedStoryPoints(Integer notFinishedStoryPoints) {
		this.notFinishedStoryPoints = notFinishedStoryPoints;
	}
}
