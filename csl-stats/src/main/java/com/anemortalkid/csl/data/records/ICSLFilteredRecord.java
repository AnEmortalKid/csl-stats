package com.anemortalkid.csl.data.records;

/**
 * A record for a particular race or group of races
 * 
 * @author JMonterrubio
 *
 */
public interface ICSLFilteredRecord {

	int getWins();

	int getLosses();

	void addWin();

	void addLoss();

}
