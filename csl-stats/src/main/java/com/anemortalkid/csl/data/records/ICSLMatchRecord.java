package com.anemortalkid.csl.data.records;

public interface ICSLMatchRecord {

	boolean wonMatch();

	void setMatchWon(boolean matchWon);

	MatchRecordType getMatchRecordType();

	enum MatchRecordType {
		// for 1v1
		ONE_VS_ONE,
		// for 2v2
		TWO_VS_TWO;
	}
}