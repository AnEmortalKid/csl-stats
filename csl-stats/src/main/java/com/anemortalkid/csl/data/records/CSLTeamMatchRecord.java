package com.anemortalkid.csl.data.records;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.anemortalkid.csl.data.CSLPlayer;
import com.anemortalkid.csl.data.SC2Race;

public class CSLTeamMatchRecord extends AbstractCSLMatchRecord {

	private Set<CSLPlayer> players;
	private Set<SC2Race> opponentRaces;
	private Set<String> playerIds;

	public CSLTeamMatchRecord(CSLPlayer p1, CSLPlayer p2,
			SC2Race... opponentRaces) {
		super(MatchRecordType.TWO_VS_TWO);
		playerIds = new HashSet<String>();
		players = new HashSet<CSLPlayer>();
		players.add(p1);
		players.add(p2);
		playerIds.add(p1.getID());
		playerIds.add(p2.getID());
		this.opponentRaces = new HashSet<SC2Race>();
		this.opponentRaces.addAll(Arrays.asList(opponentRaces));
	}

	public Set<CSLPlayer> getPlayers() {
		return players;
	}

	public Set<SC2Race> getOpponentRaces() {
		return opponentRaces;
	}

	public Set<String> getPlayerIds() {
		return playerIds;
	}

	@Override
	public String toString() {
		StringBuilder playersBuilder = new StringBuilder();
		Iterator<CSLPlayer> iterator = players.iterator();
		playersBuilder.append("(");
		while (iterator.hasNext()) {
			CSLPlayer player = iterator.next();
			playersBuilder.append(player.getCslName());
			if (iterator.hasNext())
				playersBuilder.append(" & ");
		}
		playersBuilder.append(")");
		return playersBuilder.toString()
				+ (wonMatch() ? " won vs:" : " loss vs:")
				+ " "
				+ (opponentRaces.isEmpty() ? "FORFEIT" : opponentRaces
						.toString());
	}
}
