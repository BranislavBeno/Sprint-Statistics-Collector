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
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.issue.entity.Team;
import com.issue.enums.FeatureScope;
import com.issue.iface.Dao4DB;
import com.issue.iface.TeamDao;

/**
 * The Class TeamDao4DBImpl.
 *
 * @author benito
 */
public class TeamDao4DBImpl implements Dao4DB {

	/** The Constant DOUBLE_5_2_DEFAULT_0. */
	private static final String DOUBLE_5_2_DEFAULT_0 = "DOUBLE(5,2) DEFAULT 0";

	/** The Constant DECIMAL_3_DEFAULT_0. */
	private static final String DECIMAL_3_DEFAULT_0 = "DECIMAL(3) DEFAULT 0";

	/** The Constant DECIMAL_2_DEFAULT_0. */
	private static final String DECIMAL_2_DEFAULT_0 = "DECIMAL(2) DEFAULT 0";

	/** The Constant VARCHAR_64_DEFAULT_NULL. */
	private static final String VARCHAR_64_DEFAULT_NULL = "VARCHAR(64) DEFAULT NULL";

	/** The Constant FINISHED_SP_COLUMN. */
	private static final String FINISHED_SP_COLUMN = "finished_sp";

	/** The Constant PLANNED_SP_CLOSED_COLUMN. */
	private static final String PLANNED_SP_CLOSED_COLUMN = "planned_sp_closed";

	/** The Constant DELTA_SP_COLUMN. */
	private static final String DELTA_SP_COLUMN = "delta_sp";

	/** The Constant NOT_CLOSED_HIGH_PRIOR_STORIES_COLUMN. */
	private static final String NOT_CLOSED_HIGH_PRIOR_STORIES_COLUMN = "not_closed_high_prior_stories";

	/** The Constant TIME_SPENT_COLUMN. */
	private static final String TIME_SPENT_COLUMN = "time_spent";

	/** The Constant TIME_PLANNED_COLUMN. */
	private static final String TIME_PLANNED_COLUMN = "time_planned";

	/** The Constant TIME_ESTIMATION_COLUMN. */
	private static final String TIME_ESTIMATION_COLUMN = "time_estimation";

	/** The Constant FINISHED_BUGS_SP_SUM_COLUMN. */
	private static final String FINISHED_BUGS_SP_SUM_COLUMN = "finished_bugs_sp_sum";

	/** The Constant FINISHED_STORIES_SP_SUM_COLUMN. */
	private static final String FINISHED_STORIES_SP_SUM_COLUMN = "finished_stories_sp_sum";

	/** The Constant IN_PROGRESS_SP_SUM_COLUMN. */
	private static final String IN_PROGRESS_SP_SUM_COLUMN = "in_progress_sp_sum";

	/** The Constant TO_DO_SP_SUM_COLUMN. */
	private static final String TO_DO_SP_SUM_COLUMN = "to_do_sp_sum";

	/** The Constant NOT_FINISHED_SP_SUM_COLUMN. */
	private static final String NOT_FINISHED_SP_SUM_COLUMN = "not_finished_sp_sum";

	/** The Constant FINISHED_SP_SUM_COLUMN. */
	private static final String FINISHED_SP_SUM_COLUMN = "finished_sp_sum";

	/** The Constant ON_END_PLANNED_SP_SUM_COLUMN. */
	private static final String ON_END_PLANNED_SP_SUM_COLUMN = "on_end_planned_sp_sum";

	/** The Constant ON_BEGIN_PLANNED_SP_SUM_COLUMN. */
	private static final String ON_BEGIN_PLANNED_SP_SUM_COLUMN = "on_begin_planned_sp_sum";

	/** The Constant TEAM_MEMBER_COUNT_COLUMN. */
	private static final String TEAM_MEMBER_COUNT_COLUMN = "team_member_count";

	/** The Constant TEAM_NAME_COLUMN. */
	private static final String TEAM_NAME_COLUMN = "team_name";

	/** The Constant SPRINT_COLUMN. */
	private static final String SPRINT_COLUMN = "sprint";

	/** The logger. */
	static Logger logger = LogManager.getLogger(TeamDao4DBImpl.class);

	/** The connection. */
	private final Connection connection;

	/** The teams. */
	private final TeamDao<String, Team> teams;

	/** The sprint. */
	private final String sprint;

	/** The table. */
	private String table = null;

	/**
	 * Instantiates a new team dao 4 DB impl.
	 *
	 * @param connection the connection
	 * @param teams the teams
	 * @param sprint     the sprint
	 */
	public TeamDao4DBImpl(final Connection connection, final TeamDao<String, Team> teams, final String sprint) {
		this.connection = connection;
		this.sprint = sprint;
		this.teams = teams;
	}

	/**
	 * Column 4 creation.
	 *
	 * @param name        the name
	 * @param declaration the declaration
	 * @return the string
	 */
	private static final String column4Creation(final String name, final String declaration) {
		return new StringBuilder("`").append(name).append("` ").append(declaration).append(", ").toString();
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
	 * Finished SP 2 json.
	 *
	 * @param team the team
	 * @return the string
	 */
	private String finishedSP2Json(Team team) {
		String finishedSP = "";
		try {
			finishedSP = new ObjectMapper()
					.writeValueAsString(team.getFinishedStoryPoints().orElse(new EnumMap<>(FeatureScope.class)));
		} catch (JsonProcessingException e) {
			logger.error("Json processing interrupted with exception.");
		}
		return finishedSP;
	}

	/**
	 * Statement 4 table creation.
	 *
	 * @return the string
	 */
	private String statement4TableCreation() {
		StringBuilder sb = new StringBuilder();
		sb.append("create table if not exists ").append(table).append(" ( ")
				.append(column4Creation("id", "INT(11) NOT NULL AUTO_INCREMENT"))
				.append(column4Creation(SPRINT_COLUMN, VARCHAR_64_DEFAULT_NULL))
				.append(column4Creation(TEAM_NAME_COLUMN, VARCHAR_64_DEFAULT_NULL))
				.append(column4Creation(TEAM_MEMBER_COUNT_COLUMN, DECIMAL_2_DEFAULT_0))
				.append(column4Creation(ON_BEGIN_PLANNED_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(ON_END_PLANNED_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(FINISHED_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(NOT_FINISHED_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(TO_DO_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(IN_PROGRESS_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(FINISHED_STORIES_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(FINISHED_BUGS_SP_SUM_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(TIME_ESTIMATION_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(TIME_PLANNED_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(TIME_SPENT_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(NOT_CLOSED_HIGH_PRIOR_STORIES_COLUMN, DECIMAL_3_DEFAULT_0))
				.append(column4Creation(DELTA_SP_COLUMN, DOUBLE_5_2_DEFAULT_0))
				.append(column4Creation(PLANNED_SP_CLOSED_COLUMN, DOUBLE_5_2_DEFAULT_0))
				.append(column4Creation(FINISHED_SP_COLUMN, "JSON DEFAULT NULL")).append("PRIMARY KEY (`id`)) ")
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
		sj.add(TEAM_NAME_COLUMN);
		sj.add(TEAM_MEMBER_COUNT_COLUMN);
		sj.add(ON_BEGIN_PLANNED_SP_SUM_COLUMN);
		sj.add(ON_END_PLANNED_SP_SUM_COLUMN);
		sj.add(FINISHED_SP_SUM_COLUMN);
		sj.add(NOT_FINISHED_SP_SUM_COLUMN);
		sj.add(TO_DO_SP_SUM_COLUMN);
		sj.add(IN_PROGRESS_SP_SUM_COLUMN);
		sj.add(FINISHED_STORIES_SP_SUM_COLUMN);
		sj.add(FINISHED_BUGS_SP_SUM_COLUMN);
		sj.add(TIME_ESTIMATION_COLUMN);
		sj.add(TIME_PLANNED_COLUMN);
		sj.add(TIME_SPENT_COLUMN);
		sj.add(NOT_CLOSED_HIGH_PRIOR_STORIES_COLUMN);
		sj.add(DELTA_SP_COLUMN);
		sj.add(PLANNED_SP_CLOSED_COLUMN);
		sj.add(FINISHED_SP_COLUMN);

		return "insert into " + table + sj.toString() + "values " + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	/**
	 * Statement 4 update.
	 *
	 * @return the string
	 */
	private String statement4Update() {
		StringJoiner sj = new StringJoiner(", ");
		sj.add(column4Update(TEAM_NAME_COLUMN));
		sj.add(column4Update(TEAM_MEMBER_COUNT_COLUMN));
		sj.add(column4Update(ON_BEGIN_PLANNED_SP_SUM_COLUMN));
		sj.add(column4Update(ON_END_PLANNED_SP_SUM_COLUMN));
		sj.add(column4Update(FINISHED_SP_SUM_COLUMN));
		sj.add(column4Update(NOT_FINISHED_SP_SUM_COLUMN));
		sj.add(column4Update(TO_DO_SP_SUM_COLUMN));
		sj.add(column4Update(IN_PROGRESS_SP_SUM_COLUMN));
		sj.add(column4Update(FINISHED_STORIES_SP_SUM_COLUMN));
		sj.add(column4Update(FINISHED_BUGS_SP_SUM_COLUMN));
		sj.add(column4Update(TIME_ESTIMATION_COLUMN));
		sj.add(column4Update(TIME_PLANNED_COLUMN));
		sj.add(column4Update(TIME_SPENT_COLUMN));
		sj.add(column4Update(NOT_CLOSED_HIGH_PRIOR_STORIES_COLUMN));
		sj.add(column4Update(DELTA_SP_COLUMN));
		sj.add(column4Update(PLANNED_SP_CLOSED_COLUMN));
		sj.add(column4Update(FINISHED_SP_COLUMN));

		return "update " + table + " set " + sj.toString() + " where " + SPRINT_COLUMN + "= ?";
	}

	/**
	 * Params 4 insertion.
	 *
	 * @param stmt the stmt
	 * @param team the team
	 * @throws SQLException the SQL exception
	 */
	private void params4Insertion(PreparedStatement stmt, Team team) throws SQLException {
		stmt.setString(1, sprint);
		stmt.setString(2, team.getTeamName().orElse(""));
		stmt.setInt(3, team.getTeamMemberCount());
		stmt.setInt(4, team.getOnBeginPlannedStoryPointsSum());
		stmt.setInt(5, team.getOnEndPlannedStoryPointsSum());
		stmt.setInt(6, team.getFinishedStoryPointsSum());
		stmt.setInt(7, team.getNotFinishedStoryPointsSum());
		stmt.setInt(8, team.getToDoStoryPointsSum());
		stmt.setInt(9, team.getInProgressStoryPointsSum());
		stmt.setInt(10, team.getFinishedStoriesSPSum());
		stmt.setInt(11, team.getFinishedBugsSPSum());
		stmt.setLong(12, team.getTimeEstimation());
		stmt.setLong(13, team.getTimePlanned());
		stmt.setLong(14, team.getTimeSpent());
		stmt.setInt(15, team.getNotClosedHighPriorStoriesCount());
		stmt.setDouble(16, team.getDeltaStoryPoints());
		stmt.setDouble(17, team.getPlannedStoryPointsClosed());
		stmt.setString(18, finishedSP2Json(team));
		stmt.addBatch();
	}

	/**
	 * Params 4 update.
	 *
	 * @param stmt the stmt
	 * @param team the team
	 * @throws SQLException the SQL exception
	 */
	private void params4Update(PreparedStatement stmt, Team team) throws SQLException {
		stmt.setString(1, team.getTeamName().orElse(""));
		stmt.setInt(2, team.getTeamMemberCount());
		stmt.setInt(3, team.getOnBeginPlannedStoryPointsSum());
		stmt.setInt(4, team.getOnEndPlannedStoryPointsSum());
		stmt.setInt(5, team.getFinishedStoryPointsSum());
		stmt.setInt(6, team.getNotFinishedStoryPointsSum());
		stmt.setInt(7, team.getToDoStoryPointsSum());
		stmt.setInt(8, team.getInProgressStoryPointsSum());
		stmt.setInt(9, team.getFinishedStoriesSPSum());
		stmt.setInt(10, team.getFinishedBugsSPSum());
		stmt.setLong(11, team.getTimeEstimation());
		stmt.setLong(12, team.getTimePlanned());
		stmt.setLong(13, team.getTimeSpent());
		stmt.setInt(14, team.getNotClosedHighPriorStoriesCount());
		stmt.setDouble(15, team.getDeltaStoryPoints());
		stmt.setDouble(16, team.getPlannedStoryPointsClosed());
		stmt.setString(17, finishedSP2Json(team));
		stmt.setString(18, sprint);
		stmt.addBatch();
	}

	/**
	 * Checks if is table row available.
	 *
	 * @return true, if is table row available
	 */
	private boolean isTableRowAvailable() {
		logger.info("Checking data availbility for sprint '{}' in table '{}'.", sprint, table);

		// Check particular data availability
		String checkQuery = "select sprint from " + table + " where sprint='" + sprint + "'";

		// Execute checking query
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(checkQuery);) {

			if (resultSet.first()) {
				logger.info("Data for sprint '{}' in table '{}' are available.", sprint, table);
				return true;
			} else {
				logger.info("No data for sprint '{}' in table '{}' found.", sprint, table);
				return false;
			}

		} catch (SQLException e) {
			logger.error("Data availbility check in table '{}' failed!", table);
		}

		return false;
	}

	/**
	 * Creates the table.
	 */
	private void createTable() {
		// Create table if doesn't exists
		logger.info("Creating new table '{}' if doesn't exists.", table);

		// Create statement for table creation
		String createTableQuery = statement4TableCreation();

		// Execute SQL query for table creation
		try (Statement statement = connection.createStatement()) {
			// Execute SQL query
			statement.executeUpdate(createTableQuery);
			logger.info("Table '{}' exists or created successfully.", table);

		} catch (SQLException e) {
			logger.error("DB table {} creation failed!", table);
		}
	}

	/**
	 * Insert entity.
	 *
	 * @param team the team
	 */
	private void insertEntity(final Team team) {
		// Insert a new sprint data
		logger.info("Inserting a new sprint data for '{}' into table '{}'.", sprint, table);

		// Create statement for data insertion
		String insertDataQuery = statment4Insertion();

		try (PreparedStatement statement = connection.prepareStatement(insertDataQuery);) {
			// Prepare statement for data insertion
			params4Insertion(statement, team);

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
			logger.error("Data insertion into table '{}' failed!", table);
		}
	}

	/**
	 * Update entity.
	 *
	 * @param team the team
	 */
	private void updateEntity(final Team team) {
		// Update existing sprint data
		logger.info("Updating existing data for '{}' in table '{}'.", sprint, table);

		// Create statement for data updating
		String updateDataQuery = statement4Update();

		try (PreparedStatement statement = connection.prepareStatement(updateDataQuery);) {
			// Prepare statement for data update
			params4Update(statement, team);

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
			logger.error("Data update in table '{}' failed!", table);
		}
	}

	/**
	 * Send stats.
	 *
	 * @param team the team
	 */
	private void sendStats(final Team team) {
		// Set database table name
		this.table = "team_" + team.getTeamName().orElse("unknown").toLowerCase();

		// Save sprint data for particular team
		if (isTableRowAvailable()) {
			// Update existing data record
			updateEntity(team);
		} else {
			// Create new data record
			createTable();
			insertEntity(team);
		}
	}

	/**
	 * Send stats.
	 */
	@Override
	public void sendStats() {
		// Run over all team related sprint data
		teams.getAll().values().stream().forEach(this::sendStats);
	}

}
