/**
 * 
 */
package com.issue.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.issue.entity.Engineer;
import com.issue.iface.Dao4DB;

/**
 * The Class EngineerDao4DBImpl.
 *
 * @author benito
 */
public class EngineerDao4DBImpl implements Dao4DB<Engineer> {

	/** The Constant DB_TABLE. */
	private static final String DB_TABLE = "engineer";

	/** The Constant ENGINEER_NAME_COLUMN. */
	private static final String ENGINEER_NAME_COLUMN = "engineer_name";

	/** The Constant SPRINT_COLUMN. */
	private static final String SPRINT_COLUMN = "sprint";

	/** The Constant FINISHED_SP_COLUMN. */
	private static final String FINISHED_SP_COLUMN = "finished_sp";

	/** The Constant NOT_FINISHED_SP_COLUMN. */
	private static final String NOT_FINISHED_SP_COLUMN = "not_finished_sp";

	/** The logger. */
	static Logger logger = LogManager.getLogger(EngineerDao4DBImpl.class);

	/** The connection. */
	private final Connection connection;

	/**
	 * Instantiates a new engineer DAO for DB implementation.
	 *
	 * @param connection the connection
	 */
	public EngineerDao4DBImpl(final Connection connection) {
		this.connection = Optional.ofNullable(connection).orElseThrow(IllegalArgumentException::new);
	}

	/**
	 * Column 4 update.
	 *
	 * @param name the name
	 * @return the string
	 */
	private static final String column4Update(final String name) {
		return new StringBuilder(name).append(" = ?").toString();
	}

	/**
	 * Statement 4 update.
	 *
	 * @return the string
	 */
	private String statement4Update() {
		StringJoiner sj = new StringJoiner(", ");
		sj.add(column4Update(FINISHED_SP_COLUMN));
		sj.add(column4Update(NOT_FINISHED_SP_COLUMN));

		return "UPDATE " + DB_TABLE + " SET " + sj.toString() + " WHERE " + ENGINEER_NAME_COLUMN + "= ? AND "
				+ SPRINT_COLUMN + "= ?";
	}

	/**
	 * Params 4 update.
	 *
	 * @param stmt the stmt
	 * @param engineer the engineer
	 * @throws SQLException the SQL exception
	 */
	private void params4Update(PreparedStatement stmt, final Engineer engineer) throws SQLException {
		stmt.setInt(1, engineer.getFinishedStoryPoints().orElse(0));
		stmt.setInt(2, engineer.getNotFinishedStoryPoints().orElse(0));
		stmt.setString(3, engineer.getName());
		stmt.setString(4, engineer.getSprintLabel());
		stmt.addBatch();
	}

	/**
	 * Checks if is table row available.
	 *
	 * @param theEngineer the the engineer
	 * @return true, if is table row available
	 */
	private boolean isTableRowAvailable(final Engineer theEngineer) {
		String engineer = theEngineer.getName();
		String sprint = theEngineer.getSprintLabel();

		logger.info("Checking data availbility for engineer '{}' and sprint '{}' in table '{}'.", engineer, sprint,
				DB_TABLE);

		// Check particular data availability
		String checkQuery = "SELECT sprint FROM " + DB_TABLE + " WHERE engineer='" + engineer + "' AND sprint='"
				+ sprint + "'";

		// Execute checking query
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(checkQuery);) {

			if (resultSet.first()) {
				logger.info("Data for engineer '{}' and sprint '{}' in table '{}' are available.", engineer, sprint,
						DB_TABLE);
				return true;
			} else {
				logger.info("No data for engineer '{}' and sprint '{}' in table '{}' found.", engineer, sprint,
						DB_TABLE);
				return false;
			}

		} catch (SQLException e) {
			logger.error("Data availbility check in table '{}' failed!", DB_TABLE);
		}

		return false;
	}

	/**
	 * Update entity.
	 *
	 * @param engineer the engineer
	 */
	private void updateEntity(final Engineer engineer) {
		// Update existing sprint related engineer data
		logger.info("Updating existing data for '{}' and '{}' in table '{}'.", engineer.getName(),
				engineer.getSprintLabel(), DB_TABLE);

		// Create statement for data updating
		String updateDataQuery = statement4Update();

		try (PreparedStatement statement = connection.prepareStatement(updateDataQuery);) {
			// Prepare statement for data update
			params4Update(statement, engineer);

			// Execute SQL query for data update
			statement.executeBatch();
			logger.info("Existing sprint related engineer data update successfull.");

			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				logger.error("Error during rollback");
			}
			logger.error("Data update in table '{}' failed!", DB_TABLE);
		}
	}

	/**
	 * Save or update.
	 *
	 * @param engineer the engineer
	 */
	@Override
	public void saveOrUpdate(final Engineer engineer) {
		// Save data for particular engineer
		if (isTableRowAvailable(engineer)) {
			// Update existing data record
			updateEntity(engineer);
		} else {
			// Create new data record
			// createTable();
			// insertEntity(engineer);
		}
	}
}
