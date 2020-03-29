/**
 * 
 */
package com.issue.configuration;

import java.util.Map;
import java.util.Set;

/**
 * The Class GlobalParams.
 *
 * @author branislav.beno
 */
public class GlobalParams {

	/** The instance. */
	private static GlobalParams instance;

	/** The sprint label. */
	private String sprintLabel;

	/** The db uri. */
	private String dbUri;

	/** The db username. */
	private String dbUsername;

	/** The db password. */
	private String dbPassword;

	/** The issue tracker uri. */
	private String issueTrackerUri;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The xlsx output. */
	private boolean xlsxOutput;

	/** The output file name 4 xlsx. */
	private String outputFileName4Xlsx;

	/** The features jql. */
	private String featuresJql;

	/** The completed sprints. */
	private Set<String> completedSprints = null;

	/** The completed outside sprints. */
	private Set<String> completedOutsideSprints = null;

	/** The not completed sprints. */
	private Set<String> notCompletedSprints = null;

	/** The added after sprint start. */
	private Set<String> addedAfterSprintStart = null;

	/** The removed after sprint start. */
	private Set<String> removedAfterSprintStart = null;

	/** The refinements. */
	private Map<Integer, Set<String>> refinements = null;

	/**
	 * Instantiates a new global params.
	 */
	private GlobalParams() {
		// Basic constructor is empty
	}

	/**
	 * Gets the single instance of GlobalParams.
	 *
	 * @return single instance of GlobalParams
	 */
	public static GlobalParams getInstance() {
		if (instance == null) {
			instance = new GlobalParams();
		}
		return instance;
	}

	/**
	 * Gets the sprint label.
	 *
	 * @return the sprint label
	 */
	public String getSprintLabel() {
		return sprintLabel;
	}

	/**
	 * Sets the sprint label.
	 *
	 * @param sprintLabel the new sprint label
	 */
	public void setSprintLabel(String sprintLabel) {
		this.sprintLabel = sprintLabel;
	}

	/**
	 * Gets the db uri.
	 *
	 * @return the db uri
	 */
	public String getDbUri() {
		return dbUri;
	}

	/**
	 * Sets the db uri.
	 *
	 * @param dbUri the new db uri
	 */
	public void setDbUri(String dbUri) {
		this.dbUri = dbUri;
	}

	/**
	 * Gets the db username.
	 *
	 * @return the db username
	 */
	public String getDbUsername() {
		return dbUsername;
	}

	/**
	 * Sets the db username.
	 *
	 * @param dbUsername the new db username
	 */
	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	/**
	 * Gets the db password.
	 *
	 * @return the db password
	 */
	public String getDbPassword() {
		return dbPassword;
	}

	/**
	 * Sets the db password.
	 *
	 * @param dbPassword the new db password
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	/**
	 * Gets the issue tracker uri.
	 *
	 * @return the issueTrackerUri
	 */
	public String getIssueTrackerUri() {
		return issueTrackerUri;
	}

	/**
	 * Sets the issue tracker uri.
	 *
	 * @param issueTrackerUri the issueTrackerUri to set
	 */
	public void setIssueTrackerUri(String issueTrackerUri) {
		this.issueTrackerUri = issueTrackerUri;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Checks if is xlsx output.
	 *
	 * @return true, if is xlsx output
	 */
	public boolean isXlsxOutput() {
		return xlsxOutput;
	}

	/**
	 * Sets the xlsx output.
	 *
	 * @param xlsxOutput the new xlsx output
	 */
	public void setXlsxOutput(boolean xlsxOutput) {
		this.xlsxOutput = xlsxOutput;
	}

	/**
	 * Gets the output file name 4 xlsx.
	 *
	 * @return the outputFileName4Xlsx
	 */
	public String getOutputFileName4Xlsx() {
		return outputFileName4Xlsx;
	}

	/**
	 * Sets the output file name 4 xlsx.
	 *
	 * @param outputFileName4Xlsx the outputFileName4Xlsx to set
	 */
	public void setOutputFileName4Xlsx(String outputFileName4Xlsx) {
		this.outputFileName4Xlsx = outputFileName4Xlsx;
	}

	/**
	 * Gets the features jql.
	 *
	 * @return the featuresJql
	 */
	public String getFeaturesJql() {
		return featuresJql;
	}

	/**
	 * Sets the features jql.
	 *
	 * @param featuresJql the featuresJql to set
	 */
	public void setFeaturesJql(String featuresJql) {
		this.featuresJql = featuresJql;
	}

	/**
	 * Gets the completed sprints.
	 *
	 * @return the completedSprints
	 */
	public Set<String> getCompletedSprints() {
		return completedSprints;
	}

	/**
	 * Sets the completed sprints.
	 *
	 * @param completedSprints the completedSprints to set
	 */
	public void setCompletedSprints(Set<String> completedSprints) {
		this.completedSprints = completedSprints;
	}

	/**
	 * Gets the completed outside sprints.
	 *
	 * @return the completedOutsideSprints
	 */
	public Set<String> getCompletedOutsideSprints() {
		return completedOutsideSprints;
	}

	/**
	 * Sets the completed outside sprints.
	 *
	 * @param completedOutsideSprints the completedOutsideSprints to set
	 */
	public void setCompletedOutsideSprints(Set<String> completedOutsideSprints) {
		this.completedOutsideSprints = completedOutsideSprints;
	}

	/**
	 * Gets the not completed sprints.
	 *
	 * @return the notCompletedSprints
	 */
	public Set<String> getNotCompletedSprints() {
		return notCompletedSprints;
	}

	/**
	 * Sets the not completed sprints.
	 *
	 * @param notCompletedSprints the notCompletedSprints to set
	 */
	public void setNotCompletedSprints(Set<String> notCompletedSprints) {
		this.notCompletedSprints = notCompletedSprints;
	}

	/**
	 * Gets the added after sprint start.
	 *
	 * @return the addedAfterSprintStart
	 */
	public Set<String> getAddedAfterSprintStart() {
		return addedAfterSprintStart;
	}

	/**
	 * Sets the added after sprint start.
	 *
	 * @param addedAfterSprintStart the addedAfterSprintStart to set
	 */
	public void setAddedAfterSprintStart(Set<String> addedAfterSprintStart) {
		this.addedAfterSprintStart = addedAfterSprintStart;
	}

	/**
	 * Gets the removed after sprint start.
	 *
	 * @return the removedAfterSprintStart
	 */
	public Set<String> getRemovedAfterSprintStart() {
		return removedAfterSprintStart;
	}

	/**
	 * Sets the removed after sprint start.
	 *
	 * @param removedAfterSprintStart the removedAfterSprintStart to set
	 */
	public void setRemovedAfterSprintStart(Set<String> removedAfterSprintStart) {
		this.removedAfterSprintStart = removedAfterSprintStart;
	}

	/**
	 * Gets the refinements.
	 *
	 * @return the refinements
	 */
	public Map<Integer, Set<String>> getRefinements() {
		return refinements;
	}

	/**
	 * Sets the refinements.
	 *
	 * @param refinements the refinements to set
	 */
	public void setRefinements(Map<Integer, Set<String>> refinements) {
		this.refinements = refinements;
	}
}
