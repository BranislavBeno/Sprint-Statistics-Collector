/**
 * 
 */
package com.issue.repository;

import java.util.ArrayList;
import java.util.List;

import com.issue.entity.Story;
import com.issue.iface.StoryDao;

/**
 * The Class StoryDaoImpl.
 *
 * @author branislav.beno
 */
public class StoryDaoImpl implements StoryDao<Story> {

	/** The stories. */
	private List<Story> stories = new ArrayList<>();

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<Story> getAll() {
		return stories;
	}

	/**
	 * Save.
	 *
	 * @param story the story
	 */
	@Override
	public void save(Story story) {
		stories.add(story);
	}

	/**
	 * Save all.
	 *
	 * @param list the list
	 */
	@Override
	public void saveAll(List<Story> list) {
		stories.addAll(list);
	}
}
