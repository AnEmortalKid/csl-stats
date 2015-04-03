package com.anemortalkid.csl.data.records;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.anemortalkid.csl.data.SC2Race;

public class TeamRecord extends AbstractCSLFilteredRecord {

	private Set<SC2Race> races;

	public TeamRecord(SC2Race... races) {
		this.races = new HashSet<SC2Race>();
		this.races.addAll(Arrays.asList(races));
	}

	public TeamRecord(Set<SC2Race> races) {
		this.races = new HashSet<SC2Race>();
		for (SC2Race r : races)
			this.races.add(r);
	}

	public Set<SC2Race> getRaces() {
		return races;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		Iterator<SC2Race> iterator = races.iterator();
		while (iterator.hasNext()) {
			SC2Race race = iterator.next();
			sb.append(race);
			if (iterator.hasNext())
				sb.append(",");
		}
		sb.append(")");
		return "vs " + sb.toString() + ": " + super.toString();
	}
}
