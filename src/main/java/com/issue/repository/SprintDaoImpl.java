package com.issue.repository;

import java.util.Map;
import java.util.TreeMap;

import com.issue.entity.Sprint;
import com.issue.iface.SprintDao;

/**
 * The Class FeatureDao.
 *
 * @author branislav.beno
 */
public class SprintDaoImpl implements SprintDao<String, Sprint> {

	/** The sprints. */
	private Map<String, Sprint> sprints = new TreeMap<>();

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public Map<String, Sprint> getAll() {
		return sprints;
	}

	/**
	 * Save.
	 *
	 * @param theSprint the the Sprint
	 */
	@Override
	public void save(Sprint theSprint) {
		sprints.put(theSprint.getSprintLabel(), theSprint);
	}
}
