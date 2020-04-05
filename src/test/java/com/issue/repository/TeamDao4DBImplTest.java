/**
 * 
 */
package com.issue.repository;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.issue.entity.Team;
import com.issue.iface.Dao4DB;

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

	/** The rs. */
	@Mock
	private ResultSet rs;

	/** The team. */
	private Team team;

	/** The dao. */
	Dao4DB<Team> dao;

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
		assertThrows(IllegalArgumentException.class, () -> new TeamDao4DBImpl(null));
	}

	/**
	 * Test negative non existing prepared statement hence no new table row creation.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether non existing prepared statement for database row insertion will be properly handled")
	public void testNegativeNonExistingPreparedStatementHenceNoNewTableRowCreation() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(rs);
		Mockito.when(rs.first()).thenReturn(false);

		// Create new team
		team = new Team("Apple", "First");

		// Create new team repo
		dao = new TeamDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(team);

		assertThat(dao).isNotNull();
	}

	/**
	 * Test negative non existing prepared statement hence no existing table row update.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	@DisplayName("Test whether non existing prepared statement for database row update will be properly handled")
	public void testNegativeNonExistingPreparedStatementHenceNoExistingTableRowUpdate() throws SQLException {
		Mockito.when(mockedConnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(rs);
		Mockito.when(rs.first()).thenReturn(true);

		// Create new team
		team = new Team("Apple", "First");

		// Create new team repo
		dao = new TeamDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(team);

		assertThat(dao).isNotNull();
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
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(rs);
		Mockito.when(rs.first()).thenReturn(false);

		Mockito.when(mockedConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(new int[] { 1 });

		// Create new team
		team = new Team("Apple", "First");

		// Create new team repo
		dao = new TeamDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(team);

		assertThat(dao).isNotNull();
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
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(rs);
		Mockito.when(rs.first()).thenReturn(true);

		// Create new team
		team = new Team("Apple", "First");

		Mockito.when(rs.getString(1)).thenReturn(team.getSprintLabel());

		Mockito.when(mockedConnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(new int[] { 1 });

		// Create new team repo
		dao = new TeamDao4DBImpl(mockedConnection);
		dao.saveOrUpdate(team);

		assertThat(dao).isNotNull();
	}
}
