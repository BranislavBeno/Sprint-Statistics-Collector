/**
 * 
 */
package com.issue.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.issue.entity.Sprint;
import com.issue.enums.FeatureScope;
import com.issue.iface.Dao4DB;

/**
 * @author benito
 *
 */
public class SprintDao4DBImpl implements Dao4DB<Sprint> {

	private static final String DB_TABLE = "sprints";

	private static final String REFINED_SP_COLUMN = "refined_SP";

	private static final String SPRINT_COLUMN = "sprint";

	private static final String VARCHAR_64_DEFAULT_NULL = "VARCHAR(64) DEFAULT NULL";

	/** The logger. */
	static Logger logger = LogManager.getLogger(SprintDao4DBImpl.class);

	/** The connection. */
	private final Connection connection;

	/**
	 * 
	 */
	public SprintDao4DBImpl(final Connection connection) {
		this.connection = Optional.ofNullable(connection).orElseThrow(IllegalArgumentException::new);
	}

	private static final String column4Update(final String name) {
		return new StringBuilder(name).append(" = ?").toString();
	}

	private String finishedSP2Json(final Sprint sprint) {
		String refinedSP = "";
		try {
			refinedSP = new ObjectMapper()
					.writeValueAsString(sprint.getRefinedStoryPoints().orElse(new EnumMap<>(FeatureScope.class)));
		} catch (JsonProcessingException e) {
			logger.error("Json processing interrupted with exception.");
		}
		return refinedSP;
	}

	private void params4Update(PreparedStatement stmt, final Sprint sprint) throws SQLException {
		stmt.setString(1, finishedSP2Json(sprint));
		stmt.setString(2, sprint.getSprintLabel());
		stmt.addBatch();
	}

	private String statement4Update() {
		return "update " + DB_TABLE + " set " + column4Update(REFINED_SP_COLUMN) + " where " + SPRINT_COLUMN + "= ?";
	}

	private boolean isTableRowAvailable(final String sprint) {
		logger.info("Checking data availbility for sprint '{}' in table '{}'.", sprint, DB_TABLE);

		// Check particular data availability
		String checkQuery = "select sprint from " + DB_TABLE + " where sprint='" + sprint + "'";

		// Execute checking query
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(checkQuery);) {

			if (resultSet.first()) {
				logger.info("Data for sprint '{}' in table '{}' are available.", sprint, DB_TABLE);
				return true;
			} else {
				logger.info("No data for sprint '{}' in table '{}' found.", sprint, DB_TABLE);
				return false;
			}

		} catch (SQLException e) {
			logger.error("Data availbility check in table '{}' failed!", DB_TABLE);
		}

		return false;
	}

	private void updateEntity(final Sprint sprint) {
		// Update existing sprint data
		logger.info("Updating existing data for '{}' in table '{}'.", sprint.getSprintLabel(), DB_TABLE);

		// Create statement for data updating
		String updateDataQuery = statement4Update();

		try (PreparedStatement statement = connection.prepareStatement(updateDataQuery);) {
			// Prepare statement for data update
			params4Update(statement, sprint);

			// Execute SQL query for data update
			statement.executeBatch();
			logger.info("Existing sprint data update successfull.");

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

	@Override
	public void saveOrUpdate(Sprint sprint) {
		// Save data for particular sprint
		if (isTableRowAvailable(sprint.getSprintLabel())) {
			// Update existing data record
			updateEntity(sprint);
		} else {
			// Create new data record
			//createTable();
			//insertEntity(sprint);
		}
	}

}
