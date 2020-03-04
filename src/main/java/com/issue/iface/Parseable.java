/**
 * 
 */
package com.issue.iface;

import java.io.IOException;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Engineer;

/**
 * The Interface Parseable.
 *
 * @author branislav.beno
 */
public interface Parseable {

	/**
	 * Gather engineers.
	 *
	 * @param globalParams the global params
	 * @return the engineer dao
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	EngineerDao<String, Engineer> gatherEngineers(final GlobalParams globalParams)
			throws IOException, InterruptedException;
}
