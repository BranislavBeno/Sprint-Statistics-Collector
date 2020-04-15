/**
 * 
 */
package com.issue.runner;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.utils.Utils;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;

/**
 * The Class RunStats.
 *
 * @author branislav.beno
 */
@Command(mixinStandardHelpOptions = true, version = "Sprint statistics collector R2.2.0", description = "Console application SprintStats, "
		+ "is used for automated sprint statistics gathering from issue tracker tool. "
		+ "Collected results are according to tool settings sent to database and/or to Excel file. "
		+ "Precondition for automated data gathering is, that issue tracker tool allows communication over REST API.")
public class RunStats implements Runnable {

	/** The Constant PROCESSING_INTERRUPTED_WITH_EXCEPTION. */
	private static final String PROCESSING_INTERRUPTED_WITH_EXCEPTION = "Processing interrupted with exception.";

	/** The logger. */
	private static Logger logger = LogManager.getLogger(RunStats.class);

	/** The user. */
	@Option(names = { "-u",
			"--user" }, required = true, description = "Defines user name for connection to issue tracker tool.")
	private String user;

	/** The password. */
	@Option(names = { "-p",
			"--password" }, required = true, description = "Defines password for connection to issue tracker tool.")
	private String password;

	/** The database connection. */
	@Option(names = { "-d",
			"--dbconnect" }, description = "Collected data are send to database, when this parameter is used.")
	private boolean dbConnect = false;

	/** The spec. */
	@Spec
	CommandSpec spec;

	/**
	 * Run.
	 */
	public void run() {
		// Run statistics gathering
		if (user != null && password != null) {
			try {
				Utils.runStats(user, password, dbConnect);

			} catch (IOException e) {
				logger.error(PROCESSING_INTERRUPTED_WITH_EXCEPTION);
				logger.error("Check whether application.properties file is available.");
				// Restore interrupted state...
				Thread.currentThread().interrupt();
			} catch (InterruptedException e) {
				logger.error(PROCESSING_INTERRUPTED_WITH_EXCEPTION);
				logger.error("Check whether connection to issue tracker server is established.");
				// Restore interrupted state...
				Thread.currentThread().interrupt();
			}
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
