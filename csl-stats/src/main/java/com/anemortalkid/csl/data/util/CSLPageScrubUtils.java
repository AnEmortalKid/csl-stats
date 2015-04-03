package com.anemortalkid.csl.data.util;

import org.jsoup.nodes.Element;

import com.anemortalkid.csl.data.CSLPlayer;

public class CSLPageScrubUtils {

	/**
	 * Given an &lt;li&gt; tag with the following form:
	 * 
	 * <pre>
	 * &lt;li class="right-player right-zerg"&gt;&lt;a href="/users/7200"&gt;ZergGirl&lt;/a&gt;&lt;/li&gt;
	 * 
	 * <pre>
	 * this method creates a new CSLPlayer object with the data contained in the li tag particularly, the player name and the player id (numeric value)
	 * @param playerLI the list item element with the player info
	 * @return a
	 * {@link CSLPlayer} with the info extracted from the anchor within the li
	 */
	public static CSLPlayer getCSLPlayerFromLI(Element playerLI) {
		Element anchor = playerLI.getElementsByTag("a").first();
		if (anchor == null)
			return null;
		return getCSLPlayerFromA(anchor);
	}

	/**
	 * Given an &lt;a&gt; tag with the following form:
	 * 
	 * <pre>
	 * &lt;a class="player left-protoss" href="/users/10185"&gt;aznflawless&lt;/a&gt;
	 * </pre>
	 * 
	 * this method creates a new CSLPlayer object with the data contained in the
	 * a tag particularly, the player name and the player id (numeric value)
	 * 
	 * @param playerA
	 *            the anchor element with the player info
	 * @return a {@link CSLPlayer} with the info extracted from the anchor
	 */
	public static CSLPlayer getCSLPlayerFromA(Element playerA) {
		String anchorHref = playerA.attr("href");
		String playerName = playerA.text();
		String playerId = anchorHref.replaceAll("/users/", "");
		CSLPlayer player = new CSLPlayer(playerId, playerName);
		return player;
	}

}
