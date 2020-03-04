/**
 * 
 */
package com.issue.iface;

import java.util.Map;

/**
 * The Interface SprintDao.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface SprintDao<K, V> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	Map<K, V> getAll();

	/**
	 * Save.
	 *
	 * @param v the v
	 */
	void save(V v);
}
