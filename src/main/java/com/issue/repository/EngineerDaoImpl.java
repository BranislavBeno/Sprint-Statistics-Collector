package com.issue.repository;

import java.util.Map;
import java.util.TreeMap;

import com.issue.entity.Engineer;
import com.issue.iface.EngineerDao;

/**
 * The Class EngineerDaoImpl.
 */
public class EngineerDaoImpl implements EngineerDao<String, Engineer> {

	/** The engineers. */
	private Map<String, Engineer> engineers = new TreeMap<>();

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public Map<String, Engineer> getAll() {
		return engineers;
	}

	/**
	 * Save all.
	 *
	 * @param map the map
	 */
	@Override
	public void saveAll(Map<String, Engineer> map) {
		engineers.putAll(map);
	}
}
