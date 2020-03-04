/**
 * 
 */
package com.issue.entity;

import java.util.Map;
import java.util.Optional;

import com.issue.enums.FeatureScope;

/**
 * The Class Sprint.
 */
public class Sprint {

	/** The sprint label. */
	private String sprintLabel;

	/** The refined story points. */
	private Map<FeatureScope, Integer> refinedStoryPoints;

	/**
	 * Instantiates a new sprint.
	 *
	 * @param sprintLabel the sprint label
	 */
	public Sprint(String sprintLabel) {
		this.sprintLabel = Optional.ofNullable(sprintLabel).orElseThrow();
	}

	/**
	 * Gets the sprint label.
	 *
	 * @return the sprintLabel
	 */
	public Optional<String> getSprintLabel() {
		return Optional.ofNullable(sprintLabel);
	}

	/**
	 * Gets the refined story points.
	 *
	 * @return the refinedStoryPoints
	 */
	public Optional<Map<FeatureScope, Integer>> getRefinedStoryPoints() {
		return Optional.ofNullable(refinedStoryPoints);
	}

	/**
	 * Sets the refined story points.
	 *
	 * @param refinedStoryPoints the refinedStoryPoints to set
	 */
	public void setRefinedStoryPoints(Map<FeatureScope, Integer> refinedStoryPoints) {
		this.refinedStoryPoints = refinedStoryPoints;
	}

}
