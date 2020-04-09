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
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.issue.entity.Sprint;
import com.issue.enums.FeatureScope;
import com.issue.iface.Dao4DB;

/**
 * The Class SprintDao4DBImpl.
 *
 * @author benito
 */
public class SprintDao4DBImpl implements Dao4DB<Sprint> {

	/** The Constant DB_TABLE. */
	private static final String DB_TABLE = "sprints";

	/** The Constant REFINED_SP_COLUMN. */
	private static final String REFINED_SP_COLUMN = "refined_SP";

	/** The Constant SPRINT_COLUMN. */
	private static final String SPRINT_COLUMN = "sprint";

	/** The Constant VARCHAR_64_DEFAULT_NULL. */
	private static final String VARCHAR_64_DEFAULT_NULL = "VARCHAR(64) DEFAULT NULL";

	/** The logger. */
	static Logger logger = LogManager.getLogger(SprintDao4DBImpl.class);

	/** The connection. */
	private final Connection connection;

	/**
	 * Instantiates a new sprint dao 4 DB impl.
	 *
	 * @param connection the connection
	 */
	public SprintDao4DBImpl(final Connection connection) {
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
	 * Column 4 creation.
	 *
	 * @param name the name
	 * @param declaration the declaration
	 * @return the string
	 */
	private static final String column4Creation(final String name, final String declaration) {
		return new StringBuilder("`").append(name).append("` ").append(declaration).append(", ").toString();
	}

	/**
	 * Finished SP 2 json.
	 *
	 * @param sprint the sprint
	 * @return the string
	 */
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

	/**
	 * Params 4 update.
	 *
	 * @param stmt the stmt
	 * @param sprint the sprint
	 * @throws SQLException the SQL exception
	 */
	private void params4Update(PreparedStatement stmt, final Sprint sprint) throws SQLException {
		stmt.setString(1, finishedSP2Json(sprint));
		stmt.setString(2, sprint.getSprintLabel());
		stmt.addBatch();
	}

	/**
	 * Params 4 insertion.
	 *
	 * @param stmt the stmt
	 * @param sprint the sprint
	 * @throws SQLException the SQL exception
	 */
	private void params4Insertion(PreparedStatement stmt, final Sprint sprint) throws SQLException {
		stmt.setString(1, sprint.getSprintLabel());
		stmt.setString(2, finishedSP2Json(sprint));
		stmt.addBatch();
	}

	/**
	 * Statement 4 update.
	 *
	 * @return the string
	 */
	private String statement4Update() {
		return "UPDATE " + DB_TABLE + " SET " + column4Update(REFINED_SP_COLUMN) + " WHERE " + SPRINT_COLUMN + "= ?";
	}

	/**
	 * Statement 4 table creation.
	 *
	 * @return the string
	 */
	private String statement4TableCreation() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS ").append(DB_TABLE).append(" ( ")
				.append(column4Creation("id", "INT(11) NOT NULL AUTO_INCREMENT"))
				.append(column4Creation(SPRINT_COLUMN, VARCHAR_64_DEFAULT_NULL))
				.append(column4Creation(REFINED_SP_COLUMN, "JSON DEFAULT NULL")).append("PRIMARY KEY (`id`)) ")
				.append("ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");

		return sb.toString();
	}

	/**
	 * Statment 4 insertion.
	 *
	 * @return the string
	 */
	private String statment4Insertion() {
		StringJoiner sj = new StringJoiner(", ", " (", ") ");
		sj.add(SPRINT_COLUMN);
		sj.add(REFINED_SP_COLUMN);

		return "INSERT INTO " + DB_TABLE + sj.toString() + "VALUES " + "(?,?)";
	}

	/**
	 * Checks if is table row available.
	 *
	 * @param sprint the sprint
	 * @return true, if is table row available
	 */
	private boolean isTableRowAvailable(final String sprint) {
		logger.info("Checking data availbility for sprint '{}' in table '{}'.", sprint, DB_TABLE);

		// Check particular data availability
		String checkQuery = "SELECT sprint FROM " + DB_TABLE + " WHERE sprint='" + sprint + "'";

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

	/**
	 * Update entity.
	 *
	 * @param sprint the sprint
	 */
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

	/**
	 * Creates the table.
	 */
	private void createTable() {
		// Create table if doesn't exists
		logger.info("Creating new table '{}' if doesn't exists.", DB_TABLE);

		// Create statement for table creation
		String createTableQuery = statement4TableCreation();

		// Execute SQL query for table creation
		try (Statement statement = connection.createStatement()) {
			// Execute SQL query
			statement.executeUpdate(createTableQuery);
			logger.info("Table '{}' exists or created successfully.", DB_TABLE);

		} catch (SQLException e) {
			logger.error("DB table {} creation failed!", DB_TABLE);
		}
	}

	/**
	 * Insert entity.
	 *
	 * @param sprint the sprint
	 */
	private void insertEntity(final Sprint sprint) {
		// Insert a new sprint data
		logger.info("Inserting a new sprint data for '{}' into table '{}'.", sprint.getSprintLabel(), DB_TABLE);

		// Create statement for data insertion
		String insertDataQuery = statment4Insertion();

		try (PreparedStatement statement = connection.prepareStatement(insertDataQuery);) {
			// Prepare statement for data insertion
			params4Insertion(statement, sprint);

			// Execute SQL query for data insertion
			statement.executeBatch();
			logger.info("New sprint data insertion successfull.");

			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
				logger.error("Error during rollback");
			}
			logger.error("Data insertion into table '{}' failed!", DB_TABLE);
		}
	}

	/**
	 * Save or update.
	 *
	 * @param sprint the sprint
	 */
	@Override
	public void saveOrUpdate(Sprint sprint) {
		// Save data for particular sprint
		if (isTableRowAvailable(sprint.getSprintLabel())) {
			// Update existing data record
			updateEntity(sprint);
		} else {
			// Create new data record
			createTable();
			insertEntity(sprint);
		}
	}

}
