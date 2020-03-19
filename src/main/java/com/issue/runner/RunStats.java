/**
 * 
 */
package com.issue.runner;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.utils.Utils;

import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

/**
 * The Class RunStats.
 *
 * @author branislav.beno
 */
public class RunStats implements Runnable {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(RunStats.class);

	/** The user. */
	@Option(names = { "-u", "--user" })
	String user;

	/** The password. */
	@Option(names = { "-p", "--password" })
	String password;

	/** The spec. */
	@Spec
	CommandSpec spec;

	/**
	 * Run.
	 */
	public void run() {
		if (user != null && password != null) {
			try {
				Utils.runStats(user, password);

			} catch (IOException | InterruptedException e) {
				logger.error("Processing interrupted with exception.");
				logger.error(
						"Check whether application.properties file is available, or whether connection to issue tracker server is established.");
				// Restore interrupted state...
				Thread.currentThread().interrupt();
			}
		} else {
			throw new ParameterException(spec.commandLine(), "Password required");
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// Run sprint statistics gathering
		System.exit(new CommandLine(new RunStats()).execute(args));
	}
}
