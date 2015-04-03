package com.anemortalkid.csl.data.records;

public abstract class AbstractCSLFilteredRecord implements ICSLFilteredRecord {

	private int wins;
	private int losses;

	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public void addWin() {
		wins++;
	}

	public void addLoss() {
		losses++;
	}

	@Override
	public String toString() {
		return "(" + wins + "-" + losses + ")";
	}
}
