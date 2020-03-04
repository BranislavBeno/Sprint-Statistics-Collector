/**
 * 
 */
package com.issue.iface;

import java.util.List;

/**
 * The Interface StoryDao.
 *
 * @author branislav.beno
 * @param <T> the generic type
 */
public interface StoryDao<T> {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<T> getAll();

	/**
	 * Save.
	 *
	 * @param t the t
	 */
	void save(T t);

	/**
	 * Save all.
	 *
	 * @param list the list
	 */
	void saveAll(List<T> list);
}
