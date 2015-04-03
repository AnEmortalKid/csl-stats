package com.anemortalkid.csl.data.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.anemortalkid.csl.data.records.ICSLMatchRecord;

/**
 * A wrapper for a List of {@link ICSLMatchRecord} which makes up the history
 * for a team in regards to a match
 * 
 * @author JMonterrubio
 *
 */
public class CSLTeamMatchRecordHistory {

	private List<ICSLMatchRecord> matchRecordHistory;
	private String teamID;

	public CSLTeamMatchRecordHistory(String teamID) {
		this.teamID = teamID;
	}

	public String getTeamID() {
		return teamID;
	}

	private List<ICSLMatchRecord> getMatchRecords() {
		return matchRecordHistory == null ? matchRecordHistory = new ArrayList<ICSLMatchRecord>()
				: matchRecordHistory;
	}

	public List<ICSLMatchRecord> getMatchRecordHistory() {
		return Collections.unmodifiableList(getMatchRecords());
	}

	public boolean addMatchRecord(ICSLMatchRecord matchRecord) {
		return getMatchRecords().add(matchRecord);
	}
}
