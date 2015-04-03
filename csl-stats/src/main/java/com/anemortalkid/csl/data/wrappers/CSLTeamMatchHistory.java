package com.anemortalkid.csl.data.wrappers;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * The {@link CSLTeamMatchHistory} is a wrapper object that contains a Set of
 * ID's belonging to the matches a particular team has played
 * 
 * @author JMonterrubio
 *
 */
public class CSLTeamMatchHistory {

	private Set<String> matchIDs;
	private String teamID;

	/**
	 * 
	 * @param teamID
	 *            the numeric ID for the team, found in the team URL link
	 *            {@code http://cstarleague.com/sc2/teams/####} where ### is the
	 *            id for a given team
	 */
	public CSLTeamMatchHistory(String teamID) {
		this.teamID = teamID;
	}

	public String getTeamID() {
		return teamID;
	}

	public Set<String> getTeamMatchHistory() {
		return Collections.unmodifiableSet(getMatchIds());
	}

	private Set<String> getMatchIds() {
		return matchIDs == null ? matchIDs = new TreeSet<String>() : matchIDs;
	}

	public boolean addMatchId(String matchId) {
		return getMatchIds().add(matchId);
	}
}
