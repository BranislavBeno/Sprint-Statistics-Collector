package com.issue.repository;

import java.util.HashMap;
import java.util.Map;

import com.issue.entity.Team;
import com.issue.iface.TeamDao;

/**
 * The Class FeatureDao.
 *
 * @author branislav.beno
 */
public class TeamDaoImpl implements TeamDao<String, Team> {

	/** The teams. */
	private Map<String, Team> teams = new HashMap<>();

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public Map<String, Team> getAll() {
		return teams;
	}

	/**
	 * Save.
	 *
	 * @param theTeam the the Team
	 */
	@Override
	public void save(Team theTeam) {
		teams.put(theTeam.getTeamName(), theTeam);
	}
}
