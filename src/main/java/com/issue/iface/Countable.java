/**
 * 
 */
package com.issue.iface;

import java.io.IOException;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Team;

/**
 * The Interface Parseable.
 *
 * @author branislav.beno
 */
public interface Countable {

	/**
	 * Gather stories.
	 *
	 * @param globalParams the global params
	 * @param teamsRepo the teams repo
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	void gatherStories(final GlobalParams globalParams, TeamDao<String, Team> teamsRepo) throws IOException, InterruptedException;
}
