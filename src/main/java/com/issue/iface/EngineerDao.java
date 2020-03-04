package com.issue.iface;

import java.util.Map;

/**
 * The Interface Dao.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface EngineerDao<K, V> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	Map<K, V> getAll();

	/**
	 * Save all.
	 *
	 * @param map the map
	 */
	void saveAll(Map<K, V> map);
}
