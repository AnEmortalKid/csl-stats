package com.anemortalkid.csl.data.records;

import com.anemortalkid.csl.data.SC2Race;

public class RaceRecord extends AbstractCSLFilteredRecord {

	private SC2Race race;

	public RaceRecord(SC2Race race) {
		this.race = race;
	}

	@Override
	public String toString() {
		return "vs " + race + ": " + super.toString();
	}

	public SC2Race getRace() {
		return race;
	}

}
