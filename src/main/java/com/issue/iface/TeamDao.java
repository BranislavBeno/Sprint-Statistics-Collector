/**
 * 
 */
package com.issue.iface;

import java.util.Map;

/**
 * The Interface TeamDao.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface TeamDao<K, V> {

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
