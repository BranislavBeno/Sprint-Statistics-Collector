package com.issue.iface;

import java.util.Map;
import java.util.Optional;

/**
 * The Interface Dao.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface FeatureDao<K, V> {

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the optional
	 */
	Optional<V> get(K key);

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

	/**
	 * Save all.
	 *
	 * @param map the map
	 */
	void saveAll(Map<K, V> map);
}
