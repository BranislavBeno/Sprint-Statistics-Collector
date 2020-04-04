/**
 * 
 */
package com.issue.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
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

	/** The mocked cnnection. */
	@Mock
	private Connection mockedCnnection;

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

	Dao4DB<Team> dao;

	/**
	 * Inits the.
	 *
	 * @throws SQLException the SQL exception
	 */
	@BeforeEach
	public void init() throws SQLException {
		MockitoAnnotations.initMocks(this);

		Mockito.when(mockedCnnection.createStatement()).thenReturn(mockedStatement);
		Mockito.when(mockedStatement.executeQuery(Mockito.anyString())).thenReturn(rs);

		// Create new team
		team = new Team("Apple", "First");

		// Create new team repo
		dao = new TeamDao4DBImpl(mockedCnnection);
	}

	@Test
	public void testNegativeIsTableRowAvailable() throws SQLException {
		Mockito.when(rs.first()).thenReturn(false);

		dao.saveOrUpdate(team);

		Mockito.when(mockedCnnection.prepareStatement(Mockito.anyString())).thenReturn(mockedPreparedStatement);
		Mockito.when(mockedPreparedStatement.executeBatch()).thenReturn(new int[] { 1 });

		assertThat(dao).isNotNull();
	}

	@Test
	public void testPositiveIsTableRowAvailable() throws SQLException {
		Mockito.when(rs.first()).thenReturn(true);
		Mockito.when(rs.getString(1)).thenReturn(team.getSprintLabel());

		dao.saveOrUpdate(team);

		assertThat(dao).isNotNull();
	}
}
