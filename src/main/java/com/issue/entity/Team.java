/**
 * 
 */
package com.issue.entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.issue.enums.FeatureScope;
import com.issue.utils.Stories;

/**
 * The Class TeamImpl.
 *
 * @author benito
 */
public class Team {

	/** The team name. */
	private final String teamName;

	/** The sprint label. */
	private final String sprintLabel;

	/** The team members. */
	private Set<String> teamMembers;

	/** The team members count. */
	private int teamMemberCount;

	/** The finished story points. */
	private Map<FeatureScope, Integer> finishedStoryPoints;

	/** The not finished story points summary. */
	private int notFinishedStoryPointsSum;

	/** The on begin planned story points summary. */
	private int onBeginPlannedStoryPointsSum = 0;

	/** The on end planned story points summary. */
	private int onEndPlannedStoryPointsSum = 0;

	/** The to do story points sum. */
	private int toDoStoryPointsSum = 0;

	/** The in progress story points sum. */
	private int inProgressStoryPointsSum = 0;

	/** The finished story points summary. */
	private int finishedStoryPointsSum = 0;

	/** The delta story points. */
	private double deltaStoryPoints = 0;

	/** The planned story points closed. */
	private double plannedStoryPointsClosed = 0;

	/** The not closed high prior stories count. */
	private int notClosedHighPriorStoriesCount = 0;

	/** The finished stories SP sum. */
	private int finishedStoriesSPSum = 0;

	/** The finished bugs SP sum. */
	private int finishedBugsSPSum = 0;

	/** The time estimation. */
	private long timeEstimation = 0L;

	/** The time planned. */
	private long timePlanned = 0L;

	/** The time spent. */
	private long timeSpent = 0L;

	/**
	 * Instantiates a new team impl.
	 *
	 * @param teamName    the team name
	 * @param sprintLabel the sprint label
	 */
	public Team(final String teamName, final String sprintLabel) {
		this.teamName = Optional.ofNullable(teamName).orElseThrow();
		this.sprintLabel = Optional.ofNullable(sprintLabel).orElseThrow();
	}

	/**
	 * Gets the team name.
	 *
	 * @return the team name
	 */
	public String getTeamName() {
		return teamName;
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
	 * Adds the team members.
	 *
	 * @param members the members
	 */
	public void addTeamMembers(Set<String> members) {
		// Initialize new empty set
		if (Optional.ofNullable(teamMembers).isEmpty())
			this.teamMembers = new HashSet<>();

		// Add new team members
		this.teamMembers.addAll(members);

		// Increment team member counter
		this.teamMemberCount = teamMembers.stream().filter(t -> !t.isBlank()).collect(Collectors.counting()).intValue();
	}

	/**
	 * Gets the team member count.
	 *
	 * @return the teamMemberCount
	 */
	public int getTeamMemberCount() {
		return teamMemberCount;
	}

	/**
	 * Gets the finished story points.
	 *
	 * @return the finished story points
	 */
	public Optional<Map<FeatureScope, Integer>> getFinishedStoryPoints() {
		return Optional.ofNullable(finishedStoryPoints);
	}

	/**
	 * Sets the finished story points.
	 *
	 * @param finishedStoryPoints the finishedStoryPoints to set
	 */
	public void setFinishedStoryPoints(Map<FeatureScope, Integer> finishedStoryPoints) {
		// Set map of finished story points
		this.finishedStoryPoints = finishedStoryPoints;

		// Set summary of finished story points
		this.finishedStoryPointsSum = Stories.summarizeStoryPoints(this.finishedStoryPoints);
	}

	/**
	 * Gets the finished story points summary.
	 *
	 * @return the finishedStoryPointsSum
	 */
	public int getFinishedStoryPointsSum() {
		return finishedStoryPointsSum;
	}

	/**
	 * Gets the not finished story points summary.
	 *
	 * @return the notFinishedStoryPointsSum
	 */
	public int getNotFinishedStoryPointsSum() {
		return notFinishedStoryPointsSum;
	}

	/**
	 * Sets the not finished story points summary.
	 *
	 * @param notFinishedStoryPointsSum the notFinishedStoryPointsSum to set
	 */
	public void setNotFinishedStoryPointsSum(int notFinishedStoryPointsSum) {
		this.notFinishedStoryPointsSum = notFinishedStoryPointsSum;
	}

	/**
	 * Gets the on begin planned story points summary.
	 *
	 * @return the onBeginPlannedStoryPointsSum
	 */
	public int getOnBeginPlannedStoryPointsSum() {
		return onBeginPlannedStoryPointsSum;
	}

	/**
	 * Sets the on begin planned story points summary.
	 *
	 * @param onBeginPlannedStoryPointsSum the onBeginPlannedStoryPointsSum to set
	 */
	public void setOnBeginPlannedStoryPointsSum(int onBeginPlannedStoryPointsSum) {
		// Set summary on sprint start planned story points
		this.onBeginPlannedStoryPointsSum = onBeginPlannedStoryPointsSum;
	}

	/**
	 * Gets the on end planned story points summary.
	 *
	 * @return the onEndPlannedStoryPointsSum
	 */
	public int getOnEndPlannedStoryPointsSum() {
		return onEndPlannedStoryPointsSum;
	}

	/**
	 * Sets the on end planned story points count.
	 *
	 * @param onEndPlannedStoryPointsSum the onEndPlannedStoryPointsCount to set
	 */
	public void setOnEndPlannedStoryPointsSum(int onEndPlannedStoryPointsSum) {
		// Set summary on sprint close planned story points
		this.onEndPlannedStoryPointsSum = onEndPlannedStoryPointsSum;
	}

	/**
	 * Gets the to do story points sum.
	 *
	 * @return the toDoStoryPointsSum
	 */
	public int getToDoStoryPointsSum() {
		return toDoStoryPointsSum;
	}

	/**
	 * Sets the to do story points sum.
	 *
	 * @param toDoStoryPointsSum the toDoStoryPointsSum to set
	 */
	public void setToDoStoryPointsSum(int toDoStoryPointsSum) {
		this.toDoStoryPointsSum = toDoStoryPointsSum;
	}

	/**
	 * Gets the in progress story points sum.
	 *
	 * @return the inProgressStoryPointsSum
	 */
	public int getInProgressStoryPointsSum() {
		return inProgressStoryPointsSum;
	}

	/**
	 * Sets the in progress story points sum.
	 *
	 * @param inProgressStoryPointsSum the inProgressStoryPointsSum to set
	 */
	public void setInProgressStoryPointsSum(int inProgressStoryPointsSum) {
		this.inProgressStoryPointsSum = inProgressStoryPointsSum;
	}

	/**
	 * Gets the delta story points.
	 *
	 * @return the deltaStoryPoints
	 */
	public double getDeltaStoryPoints() {
		return deltaStoryPoints;
	}

	/**
	 * Sets the delta story points.
	 *
	 * @param deltaStoryPoints the deltaStoryPoints to set
	 */
	public void setDeltaStoryPoints(double deltaStoryPoints) {
		this.deltaStoryPoints = deltaStoryPoints;
	}

	/**
	 * Gets the planned story points closed.
	 *
	 * @return the plannedStoryPointsClosed
	 */
	public double getPlannedStoryPointsClosed() {
		return plannedStoryPointsClosed;
	}

	/**
	 * Sets the planned story points closed.
	 *
	 * @param plannedStoryPointsClosed the plannedStoryPointsClosed to set
	 */
	public void setPlannedStoryPointsClosed(double plannedStoryPointsClosed) {
		this.plannedStoryPointsClosed = plannedStoryPointsClosed;
	}

	/**
	 * Gets the not closed high prior stories count.
	 *
	 * @return the notClosedHighPriorStoriesCount
	 */
	public int getNotClosedHighPriorStoriesCount() {
		return notClosedHighPriorStoriesCount;
	}

	/**
	 * Sets the not closed high prior stories count.
	 *
	 * @param notClosedHighPriorStoriesCount the notClosedHighPriorStoriesCount to
	 *                                       set
	 */
	public void setNotClosedHighPriorStoriesCount(int notClosedHighPriorStoriesCount) {
		this.notClosedHighPriorStoriesCount = notClosedHighPriorStoriesCount;
	}

	/**
	 * Gets the finished stories SP sum.
	 *
	 * @return the finishedStoriesSPSum
	 */
	public int getFinishedStoriesSPSum() {
		return finishedStoriesSPSum;
	}

	/**
	 * Sets the finished stories SP sum.
	 *
	 * @param finishedStoriesSPSum the finishedStoriesSPSum to set
	 */
	public void setFinishedStoriesSPSum(int finishedStoriesSPSum) {
		this.finishedStoriesSPSum = finishedStoriesSPSum;
	}

	/**
	 * Gets the finished bugs SP sum.
	 *
	 * @return the finishedBugsSPSum
	 */
	public int getFinishedBugsSPSum() {
		return finishedBugsSPSum;
	}

	/**
	 * Sets the finished bugs SP sum.
	 *
	 * @param finishedBugsSPSum the finishedBugsSPSum to set
	 */
	public void setFinishedBugsSPSum(int finishedBugsSPSum) {
		this.finishedBugsSPSum = finishedBugsSPSum;
	}

	/**
	 * Gets the time estimation.
	 *
	 * @return the timeEstimation
	 */
	public long getTimeEstimation() {
		return timeEstimation;
	}

	/**
	 * Sets the time estimation.
	 *
	 * @param timeEstimation the timeEstimation to set
	 */
	public void setTimeEstimation(long timeEstimation) {
		this.timeEstimation = timeEstimation;
	}

	/**
	 * Gets the time planned.
	 *
	 * @return the timePlanned
	 */
	public long getTimePlanned() {
		return timePlanned;
	}

	/**
	 * Sets the time planned.
	 *
	 * @param timePlanned the timePlanned to set
	 */
	public void setTimePlanned(long timePlanned) {
		this.timePlanned = timePlanned;
	}

	/**
	 * Gets the time spent.
	 *
	 * @return the timeSpent
	 */
	public long getTimeSpent() {
		return timeSpent;
	}

	/**
	 * Sets the time spent.
	 *
	 * @param timeSpent the timeSpent to set
	 */
	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}
}
