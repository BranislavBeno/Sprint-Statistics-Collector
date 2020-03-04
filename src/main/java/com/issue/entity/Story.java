/**
 * 
 */
package com.issue.entity;

import java.util.Optional;

/**
 * The Class StoryImpl.
 *
 * @author branislav.beno
 */
public class Story {

	/** The epic. */
	private String epic;

	/** The story points. */
	private Integer storyPoints;

	/** The story owner. */
	private String storyOwner;

	/** The priority. */
	private String priority;

	/** The story type. */
	private String storyType;

	/** The time estimation. */
	private Integer timeEstimation;

	/** The status. */
	private String status;

	/**
	 * Instantiates a new story.
	 *
	 * @param builder the builder
	 */
	public Story(Builder builder) {
		this.epic = Optional.ofNullable(builder.epic).orElse("");
		this.storyPoints = builder.storyPoints;
		this.storyOwner = builder.storyOwner;
		this.priority = builder.priority;
		this.storyType = builder.storyType;
		this.timeEstimation = builder.timeEstimation;
		this.status = builder.status;
	}

	/**
	 * Gets the epic.
	 *
	 * @return the epic
	 */
	public Optional<String> getEpic() {
		return Optional.ofNullable(epic);
	}

	/**
	 * Gets the story points.
	 *
	 * @return the story points
	 */
	public Optional<Integer> getStoryPoints() {
		return Optional.ofNullable(storyPoints);
	}

	/**
	 * Gets the story owner.
	 *
	 * @return the storyOwner
	 */
	public Optional<String> getStoryOwner() {
		return Optional.ofNullable(storyOwner);
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public Optional<String> getPriority() {
		return Optional.ofNullable(priority);
	}

	/**
	 * Gets the story type.
	 *
	 * @return the storyType
	 */
	public Optional<String> getStoryType() {
		return Optional.ofNullable(storyType);
	}

	/**
	 * Gets the time estimation.
	 *
	 * @return the timeEstimation
	 */
	public Optional<Integer> getTimeEstimation() {
		return Optional.ofNullable(timeEstimation);
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Optional<String> getStatus() {
		return Optional.ofNullable(status);
	}

	/**
	 * The Class Builder.
	 */
	public static class Builder {

		/** The epic. */
		private String epic;

		/** The story points. */
		private Integer storyPoints = null;

		/** The story owner. */
		private String storyOwner;

		/** The priority. */
		private String priority;

		/** The story type. */
		private String storyType;

		/** The time estimation. */
		private Integer timeEstimation;

		/** The status. */
		private String status;

		/**
		 * Epic.
		 *
		 * @param theEpic the the epic
		 * @return the builder
		 */
		public Builder epic(String theEpic) {
			this.epic = theEpic;
			return this;
		}

		/**
		 * Story points.
		 *
		 * @param theStoryPoints the the story points
		 * @return the builder
		 */
		public Builder storyPoints(int theStoryPoints) {
			this.storyPoints = theStoryPoints;
			return this;
		}

		/**
		 * Story owner.
		 *
		 * @param theStoryOwner the the story owner
		 * @return the builder
		 */
		public Builder storyOwner(String theStoryOwner) {
			this.storyOwner = theStoryOwner;
			return this;
		}

		/**
		 * Priority.
		 *
		 * @param thePriority the the priority
		 * @return the builder
		 */
		public Builder priority(String thePriority) {
			this.priority = thePriority;
			return this;
		}

		/**
		 * Story type.
		 *
		 * @param theStoryType the the story type
		 * @return the builder
		 */
		public Builder storyType(String theStoryType) {
			this.storyType = theStoryType;
			return this;
		}

		/**
		 * Time estimation.
		 *
		 * @param theTimeEstimation the the time estimation
		 * @return the builder
		 */
		public Builder timeEstimation(int theTimeEstimation) {
			this.timeEstimation = theTimeEstimation;
			return this;
		}

		/**
		 * Status.
		 *
		 * @param status the status
		 * @return the builder
		 */
		public Builder status(String status) {
			this.status = status;
			return this;
		}

		/**
		 * Builds the.
		 *
		 * @return the story
		 */
		public Story build() {
			return new Story(this);
		}
	}
}
