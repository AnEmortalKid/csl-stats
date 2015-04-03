package com.anemortalkid.csl.data.records;

public abstract class AbstractCSLMatchRecord implements ICSLMatchRecord {

	private boolean matchWon;
	private MatchRecordType matchRecordType;

	protected AbstractCSLMatchRecord(MatchRecordType matchRecordType){
		this.matchRecordType = matchRecordType;
	}
	
	public boolean wonMatch() {
		return matchWon;
	}

	public void setMatchWon(boolean matchWon) {
		this.matchWon = matchWon;
	}

	public MatchRecordType getMatchRecordType() {
		return matchRecordType;
	}

}
