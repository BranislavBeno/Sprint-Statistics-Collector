/**
 * 
 */
package com.issue.iface;

/**
 * The Interface Dao4DB.
 *
 * @param <T> the generic type
 */
public interface Dao4DB<T> {

	/**
	 * Send stats.
	 *
	 * @param t the t
	 */
	void saveOrUpdate(final T t);
}
