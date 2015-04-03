package com.anemortalkid.csl.data.records;

import com.anemortalkid.csl.data.CSLPlayer;
import com.anemortalkid.csl.data.SC2Race;

public class CSLSingleMatchRecord extends AbstractCSLMatchRecord {

	private CSLPlayer player;
	private SC2Race opponentRace;

	/**
	 * @param player
	 * @param opponentRace
	 * @param wins
	 * @param loss
	 */
	public CSLSingleMatchRecord(CSLPlayer player, SC2Race opponentRace) {
		super(MatchRecordType.ONE_VS_ONE);
		this.player = player;
		this.opponentRace = opponentRace;
	}

	public CSLPlayer getPlayer() {
		return player;
	}

	public SC2Race getOpponentRace() {
		return opponentRace;
	}

	@Override
	public String toString() {
		return player.getCslName() + (wonMatch() ? " won vs:" : " loss vs:")
				+ " " + opponentRace;
	}

}
