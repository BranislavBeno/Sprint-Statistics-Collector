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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.issue.entity.Engineer;
import com.issue.iface.Dao4DB;

/**
 * @author benito
 *
 */
public class EngineerDao4DBImplTest {

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

	private Engineer engineer;

	private Dao4DB<Engineer> dao;

	@BeforeEach
	public void init() throws SQLException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	@DisplayName("Test whether non existing connection to database raises IllegalArgumentException")
	public void testNegativeNoConnectionEstablished() throws SQLException {
		assertThrows(IllegalArgumentException.class, () -> new TeamDao4DBImpl(null));
	}

	@Test
	@DisplayName("Test whether non existing prepared statement for database row insertion will be properly handled")
	public void testNegativeNonExistingPreparedStatementHenceNoNewTableRowCreation() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(false);

		// Create new engineer
		engineer = new Engineer("John Doe", "First");

		// Create new engineer repository
		dao = new EngineerDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(engineer);

		Mockito.verify(mockedStatement).executeQuery(Mockito.anyString());
	}

	@Test
	@DisplayName("Test whether non existing prepared statement for database row update will be properly handled")
	public void testNegativeNonExistingPreparedStatementHenceNoExistingTableRowUpdate() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(true);

		// Create new engineer
		engineer = new Engineer("John Doe", "First");

		// Create new engineer repository
		dao = new EngineerDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(engineer);

		Mockito.verify(mockedStatement).executeQuery(Mockito.anyString());
	}

	@Test
	@DisplayName("Test whether database new row insertion will be successfull")
	public void testPositiveNewTableRowCreation() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(false);

		Mockito.when(mockedConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(new int[] { 1 });

		// Create new engineer
		engineer = new Engineer("John Doe", "First");

		// Create new engineer repository
		dao = new EngineerDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(engineer);

		Mockito.verify(mockedPreparedStatement).executeBatch();
	}

	@Test
	@DisplayName("Test whether database existing row update will be successfull")
	public void testPositiveExistingTableRowUpdate() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(mockedResultSet);
		Mockito.when(mockedResultSet.first()).thenReturn(true);

		// Create new engineer
		engineer = new Engineer("John Doe", "First");

		Mockito.when(mockedResultSet.getString(1)).thenReturn(engineer.getSprintLabel());

		Mockito.when(mockedConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(null);

		// Create new engineer repository
		dao = new EngineerDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(engineer);

		Mockito.verify(mockedPreparedStatement).executeBatch();
	}
}
