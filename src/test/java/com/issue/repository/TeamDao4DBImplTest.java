/**
 * 
 */
package com.issue.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.issue.entity.Sprint;
import com.issue.entity.Team;
import com.issue.enums.FeatureScope;
import com.issue.iface.Dao4DB;
import com.issue.iface.SprintDao;

/**
 * The Class TeamDao4DBImplTest.
 *
 * @author benito
 */
class TeamDao4DBImplTest {

	/** The mocked connection. */
	@Mock
	private Connection mockedConnection;

	/** The mocked statement. */
	@Mock
	private Statement mockedStatement;

	/** The mocked prepared statement. */
	@Mock
	private PreparedStatement mockedPreparedStatement;

	/** The mocked result set. */
	@Mock
	private ResultSet mockedResultSet;

	/** The team. */
	private Team team;

	/** The dao. */
	private Dao4DB<Team> dao;

	/**
	 * Inits the.
	 *
	 * @throws SQLException the SQL exception
	 */
	@BeforeEach
	void init() throws SQLException {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test negative no connection established.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether no established connection to database raises IllegalArgumentException")
	void testNegativeNoConnectionEstablished() throws SQLException {
		assertThrows(IllegalArgumentException.class, () -> new TeamDao4DBImpl(null));
	}

	/**
	 * Test negative non existing prepared statement hence no new table row
	 * creation.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether non existing prepared statement for database row insertion will be properly handled")
	void testNegativeNonExistingPreparedStatementHenceNoNewTableRowCreation() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(false);

		// Create new team
		team = new Team("Apple", "First");

		// Create new team repo
		dao = new TeamDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(team);

		Mockito.verify(mockedStatement).executeQuery(Mockito.anyString());
	}

	/**
	 * Test negative non existing prepared statement hence no existing table row
	 * update.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether non existing prepared statement for database row update will be properly handled")
	void testNegativeNonExistingPreparedStatementHenceNoExistingTableRowUpdate() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(true);

		// Create new team
		team = new Team("Apple", "First");

		// Create new team repo
		dao = new TeamDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(team);

		Mockito.verify(mockedStatement).executeQuery(Mockito.anyString());
	}

	/**
	 * Test positive new table row creation.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether database new minimal row insertion will be successfull")
	void testPositiveNewMinimalTableRowCreation() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(false);

		Mockito.when(mockedConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(new int[] { 1 });

		// Create new team
		team = new Team("Apple", "First");

		// Create new team repo
		dao = new TeamDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(team);

		Mockito.verify(mockedPreparedStatement).executeBatch();
	}

	@Test
	@DisplayName("Test whether database new row insertion will be successfull")
	void testPositiveNewTableRowCreation() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(false);

		Mockito.when(mockedConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(new int[] { 1 });

		// Create new team
		team = new Team("Apple", "First");

		// Create map of refined story points
		Map<FeatureScope, Integer> map = new EnumMap<>(FeatureScope.class);
		map.put(FeatureScope.BASIC, 7);
		map.put(FeatureScope.ADVANCED, 5);
		map.put(FeatureScope.COMMERCIAL, 3);
		map.put(FeatureScope.FUTURE, 1);

		// Create sprint
		Sprint sprint = new Sprint("Test sprint");
		sprint.setRefinedStoryPoints(map);

		// Create sprint repository
		SprintDao<String, Sprint> refinedStoryPoints = new SprintDaoImpl();
		refinedStoryPoints.save(sprint);

		// Add refined story points
		team.setRefinedStoryPoints(refinedStoryPoints);

		// Create new team repo
		dao = new TeamDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(team);

		Mockito.verify(mockedPreparedStatement).executeBatch();
	}

	/**
	 * Test positive existing table row update.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether database existing row update will be successfull")
	void testPositiveExistingTableRowUpdate() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(true);

		// Create new team
		team = new Team("Apple", "First");

		Mockito.when(mockedResultSet.getString(1)).thenReturn(team.getSprintLabel());

		Mockito.when(mockedConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(null);

		// Create new team repo
		dao = new TeamDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(team);

		Mockito.verify(mockedPreparedStatement).executeBatch();
	}
}
