package com.anemortalkid.csl.data;

import java.util.Set;
import java.util.TreeSet;

public enum SC2Race {

	PROTOSS, TERRAN, ZERG, RANDOM;

	public static SC2TeamRaces getTeamRacesFor(SC2Race... races) {
		Set<SC2Race> raceSet = new TreeSet<SC2Race>();
		for (SC2Race race : races)
			raceSet.add(race);
		return SC2TeamRaces.getRacesFor(raceSet);
	}

	public static SC2TeamRaces getTeamRacesFor(Set<SC2Race> opponentRaces) {
		return SC2TeamRaces.getRacesFor(opponentRaces);
	}
}