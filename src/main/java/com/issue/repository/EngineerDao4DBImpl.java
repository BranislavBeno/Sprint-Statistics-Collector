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

	/** The Constant DECIMAL_3_DEFAULT_0. */
	private static final String DECIMAL_3_DEFAULT_0 = "DECIMAL(3) DEFAULT 0";

	/** The Constant VARCHAR_64_DEFAULT_NULL. */
	private static final String VARCHAR_64_DEFAULT_NULL = "VARCHAR(64) DEFAULT NULL";

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
	 * Statement 4 table creation.
	 *
	 * @return the string
	 */
	private String statement4TableCreation() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS ").append(DB_TABLE).append(" ( ")
				.append(column4Creation("id", "INT(11) NOT NULL AUTO_INCREMENT"))
				.append(column4Creation(ENGINEER_NAME_COLUMN, VARCHAR_64_DEFAULT_NULL))
				.append(column4Creation(SPRINT_COLUMN, VARCHAR_64_DEFAULT_NULL))
				.append(column4Creation(FINISHED_SP_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(NOT_FINISHED_SP_COLUMN, DECIMAL_3_DEFAULT_0)).append("PRIMARY KEY (`id`)) ")
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
		sj.add(ENGINEER_NAME_COLUMN);
		sj.add(SPRINT_COLUMN);
		sj.add(FINISHED_SP_COLUMN);
		sj.add(NOT_FINISHED_SP_COLUMN);

		return "INSERT INTO " + DB_TABLE + sj.toString() + "VALUES " + "(?,?,?,?)";
	}

	/**
	 * Params 4 update.
	 *
	 * @param stmt     the stmt
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
	 * Params 4 insertion.
	 *
	 * @param stmt the stmt
	 * @param engineer the engineer
	 * @throws SQLException the SQL exception
	 */
	private void params4Insertion(PreparedStatement stmt, final Engineer engineer) throws SQLException {
		stmt.setString(1, engineer.getName());
		stmt.setString(2, engineer.getSprintLabel());
		stmt.setInt(3, engineer.getFinishedStoryPoints().orElse(0));
		stmt.setInt(4, engineer.getNotFinishedStoryPoints().orElse(0));
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
	 * @param engineer the engineer
	 */
	private void insertEntity(final Engineer engineer) {
		// Insert a new sprint data
		logger.info("Inserting a new sprint related engineer data for '{}' and '{}' into table '{}'.",
				engineer.getName(), engineer.getSprintLabel(), DB_TABLE);

		// Create statement for data insertion
		String insertDataQuery = statment4Insertion();

		try (PreparedStatement statement = connection.prepareStatement(insertDataQuery);) {
			// Prepare statement for data insertion
			params4Insertion(statement, engineer);

			// Execute SQL query for data insertion
			statement.executeBatch();
			logger.info("New sprint related engineer data insertion successfull.");

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
			createTable();
			insertEntity(engineer);
		}
	}
}
