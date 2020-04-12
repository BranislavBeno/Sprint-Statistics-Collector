package com.issue.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.issue.entity.Sprint;
import com.issue.iface.Dao4DB;

/**
 * The Class SprintDao4DBImplTest.
 */
public class SprintDao4DBImplTest {

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

	/** The sprint. */
	private Sprint sprint;

	/** The dao. */
	private Dao4DB<Sprint> dao;

	/**
	 * Inits the.
	 *
	 * @throws SQLException the SQL exception
	 */
	@BeforeEach
	public void init() throws SQLException {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test negative no connection established.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether no established connection to database raises IllegalArgumentException")
	public void testNegativeNoConnectionEstablished() throws SQLException {
		assertThrows(IllegalArgumentException.class, () -> new SprintDao4DBImpl(null));
	}

	/**
	 * Test negative non existing prepared statement hence no new table row
	 * creation.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether non existing prepared statement for database row insertion will be properly handled")
	public void testNegativeNonExistingPreparedStatementHenceNoNewTableRowCreation() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(false);

		// Create new sprint
		sprint = new Sprint("First");

		// Create new sprint repository
		dao = new SprintDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(sprint);

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
	public void testNegativeNonExistingPreparedStatementHenceNoExistingTableRowUpdate() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(true);

		// Create new sprint
		sprint = new Sprint("First");

		// Create new Sprint repository
		dao = new SprintDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(sprint);

		Mockito.verify(mockedStatement).executeQuery(Mockito.anyString());
	}

	/**
	 * Test positive new table row creation.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether database new row insertion will be successfull")
	public void testPositiveNewTableRowCreation() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(false);

		Mockito.when(mockedConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(new int[] { 1 });

		// Create new sprint
		sprint = new Sprint("First");

		// Create new sprint repository
		dao = new SprintDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(sprint);

		Mockito.verify(mockedPreparedStatement).executeBatch();
	}

	/**
	 * Test positive existing table row update.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether database existing row update will be successfull")
	public void testPositiveExistingTableRowUpdate() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(true);

		// Create new sprint
		sprint = new Sprint("First");

		Mockito.when(mockedResultSet.getString(1)).thenReturn(sprint.getSprintLabel());

		Mockito.when(mockedConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(null);

		// Create new sprint repo
		dao = new SprintDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(sprint);

		Mockito.verify(mockedPreparedStatement).executeBatch();
	}
}
