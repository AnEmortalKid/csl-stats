package com.anemortalkid.csl.data.wrappers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.anemortalkid.csl.data.CSLPlayer;

/**
 * A wrapper for the Set of {@link CSLPlayer} which composes the roster of a
 * CSLTeam
 * 
 * @author JMonterrubio
 *
 */
public class CSLTeamRoster {

	private Set<CSLPlayer> teamRoster;
	private String teamID;

	public CSLTeamRoster(String teamID) {
		this.teamID = teamID;
	}

	public String getTeamID() {
		return teamID;
	}

	private Set<CSLPlayer> getPlayers() {
		return teamRoster == null ? teamRoster = new HashSet<CSLPlayer>()
				: teamRoster;
	}

	public Set<CSLPlayer> getTeamRoster() {
		return Collections.unmodifiableSet(getPlayers());
	}

	public boolean addPlayer(CSLPlayer player) {
		return getPlayers().add(player);
	}

	public boolean addPlayer(String playerID, String playerName) {
		return getPlayers().add(new CSLPlayer(playerID, playerName));
	}
}
