package com.anemortalkid.csl.datastore;

import static com.anemortalkid.csl.data.SC2Race.PROTOSS;
import static com.anemortalkid.csl.data.SC2Race.RANDOM;
import static com.anemortalkid.csl.data.SC2Race.TERRAN;
import static com.anemortalkid.csl.data.SC2Race.ZERG;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

import com.anemortalkid.csl.data.CSLPlayer;
import com.anemortalkid.csl.data.CSLTeamData;
import com.anemortalkid.csl.data.SC2Race;
import com.anemortalkid.csl.data.SC2TeamRaces;
import com.anemortalkid.csl.data.records.CSL1sPlayerRecord;
import com.anemortalkid.csl.data.records.CSL2sTeamRecord;
import com.anemortalkid.csl.data.records.ConcreteRecord;

public class CSLDataStore {

	private static final String FILE_PATH = "J:\\Workspaces\\CSLStatsGathering\\csl-stats\\src\\main\\resources\\com\\anemortalkid\\csl\\datastore\\";

	public static void saveToDatastore(CSLTeamData teamData) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			// convert user object to json string, and save to a file
			File file = new File(FILE_PATH + teamData.getTeamID() + ".json");
			mapper.writeValue(file, teamData);

			// display to console
			System.out.println(mapper.writeValueAsString(teamData));

		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (JsonMappingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	/**
	 * Returns a deserialized team data for the given team with the id
	 * specified, null if the team wasn't stored before
	 * 
	 * @param teamId
	 * @return
	 */
	public static CSLTeamData loadFromDatastore(String teamId) {
		URL resource = CSLDataStore.class.getResource(teamId + ".json");

		// XXX: logger
		if (resource == null)
			return null;

		String path = resource.getPath();
		// XXX : logger
		if (path == null)
			return null;

		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule("CSLTeamDataModule",
				new Version(0, 1, 0, "alpha"));
		module.addDeserializer(CSLTeamData.class, new CSLTeamDataDeserializer());
		mapper.registerModule(module);
		try {
			CSLTeamData teamData = mapper.readValue(new File(path),
					CSLTeamData.class);
			return teamData;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		CSLTeamData loaded = loadFromDatastore("3581");
		if (loaded == null)
			System.out.println("Oops");
		else
			System.out.println("-------------------\n"
					+ loaded.teamRecordToString());
	}

	private static class CSLTeamDataDeserializer extends
			JsonDeserializer<CSLTeamData> {

		@Override
		public CSLTeamData deserialize(JsonParser jp,
				DeserializationContext ctxt) throws IOException,
				JsonProcessingException {
			JsonNode node = jp.getCodec().readTree(jp);
			Iterator<JsonNode> teamRosterElems = node.get("teamRoster")
					.getElements();
			Set<CSLPlayer> teamRoster = new TreeSet<CSLPlayer>();
			while (teamRosterElems.hasNext()) {
				JsonNode teamRosterElem = teamRosterElems.next();
				teamRoster.add(extractPlayerFromNode(teamRosterElem));
			}

			JsonNode teamId = node.get("teamID");
			String teamIdStr = teamId.getTextValue();
			JsonNode teamName = node.get("teamName");
			String teamNameStr = teamName.getTextValue();

			JsonNode matchIDS = node.get("matchIDS");
			Iterator<JsonNode> matchIDElems = matchIDS.getElements();

			Set<String> matchIds = new TreeSet<String>();
			while (matchIDElems.hasNext()) {
				JsonNode matchIdElem = matchIDElems.next();
				matchIds.add(matchIdElem.getTextValue());
			}
			JsonNode singlesRecordsById = node.get("singlesRecordById");
			Map<String, CSL1sPlayerRecord> singlesRecordByIdMap = new HashMap<String, CSL1sPlayerRecord>();
			for (CSLPlayer player : teamRoster) {
				String playerId = player.getID();
				JsonNode playerRecordNode = singlesRecordsById.get(playerId);
				CSL1sPlayerRecord playerRecord = get1v1PlayerRecord(playerRecordNode);
				singlesRecordByIdMap.put(playerId, playerRecord);
			}

			JsonNode teamRecordsById = node.get("teamRecordByIds");
			Map<Set<String>, CSL2sTeamRecord> teamRecordByIdsMap = new HashMap<Set<String>, CSL2sTeamRecord>();
			Iterator<JsonNode> teamRecords = teamRecordsById.getElements();
			while (teamRecords.hasNext()) {
				JsonNode teamRecordNode = teamRecords.next();
				CSL2sTeamRecord teamRecord = get2v2TeamRecord(teamRecordNode);
				Set<String> playerIdsSet = new HashSet<String>();
				Set<CSLPlayer> players = teamRecord.getPlayers();
				for (CSLPlayer player : players)
					playerIdsSet.add(player.getID());
				teamRecordByIdsMap.put(playerIdsSet, teamRecord);
			}

			CSLTeamData teamData = new CSLTeamData(teamNameStr, teamIdStr);
			teamData.setMatchIDS(matchIds);
			teamData.setTeamRoster(teamRoster);
			teamData.setSinglesRecordById(singlesRecordByIdMap);
			teamData.setTeamRecordByIds(teamRecordByIdsMap);

			return teamData;
		}

		private CSL1sPlayerRecord get1v1PlayerRecord(JsonNode singleRecordNode) {
			JsonNode playerNode = singleRecordNode.get("player");
			CSLPlayer player = extractPlayerFromNode(playerNode);

			JsonNode totalLosses = singleRecordNode.get("totalLosses");
			JsonNode totalWins = singleRecordNode.get("totalWins");

			JsonNode recordsByRace = singleRecordNode.get("recordsByRace");
			CSL1sPlayerRecord pRecord = new CSL1sPlayerRecord(player);

			Map<SC2Race, ConcreteRecord> raceToRecord = new HashMap<SC2Race, ConcreteRecord>();
			raceToRecord.put(SC2Race.PROTOSS,
					getConcreteRecordFromRecordNode(recordsByRace
							.get(SC2Race.PROTOSS.toString())));
			raceToRecord.put(SC2Race.TERRAN,
					getConcreteRecordFromRecordNode(recordsByRace
							.get(SC2Race.TERRAN.toString())));
			raceToRecord.put(SC2Race.ZERG,
					getConcreteRecordFromRecordNode(recordsByRace
							.get(SC2Race.ZERG.toString())));
			raceToRecord.put(SC2Race.RANDOM,
					getConcreteRecordFromRecordNode(recordsByRace
							.get(SC2Race.RANDOM.toString())));
			pRecord.setTotalWins(totalWins.getIntValue());
			pRecord.setTotalLosses(totalLosses.getIntValue());
			pRecord.setRecordsByRace(raceToRecord);
			return pRecord;
		}

		private CSLPlayer extractPlayerFromNode(JsonNode playerNode) {
			JsonNode innerId = playerNode.get("id");
			JsonNode innerName = playerNode.get("cslName");
			CSLPlayer player = new CSLPlayer(innerId.getTextValue(),
					innerName.getTextValue());
			return player;
		}

		private ConcreteRecord getConcreteRecordFromRecordNode(JsonNode raceNode) {
			JsonNode wins = raceNode.get("wins");
			JsonNode losses = raceNode.get("losses");
			ConcreteRecord cr = new ConcreteRecord();
			cr.setWins(wins.getIntValue());
			cr.setLosses(losses.getIntValue());
			return cr;
		}

		private CSL2sTeamRecord get2v2TeamRecord(JsonNode teamRecordNode) {
			JsonNode playersNode = teamRecordNode.get("players");
			Set<CSLPlayer> players = new HashSet<CSLPlayer>();
			Iterator<JsonNode> playerNodes = playersNode.getElements();
			while (playerNodes.hasNext()) {
				JsonNode playerNode = playerNodes.next();
				players.add(extractPlayerFromNode(playerNode));
			}
			JsonNode totalWins = teamRecordNode.get("totalWins");
			JsonNode totalLosses = teamRecordNode.get("totalLosses");

			CSL2sTeamRecord teamRecord = new CSL2sTeamRecord(players);
			teamRecord.setTotalWins(totalWins.getIntValue());
			teamRecord.setTotalLosses(totalLosses.getIntValue());

			JsonNode recordsByRacesNode = teamRecordNode.get("recordsByRaces");
			Map<SC2TeamRaces, ConcreteRecord> concreteRecordsByRaces = new HashMap<SC2TeamRaces, ConcreteRecord>();
			// extract possiblsets
			JsonNode forfeitNode = recordsByRacesNode.get(SC2TeamRaces.FORFEIT
					.toString());
			if (forfeitNode != null) {
				ConcreteRecord forfeitRecord = getConcreteRecordFromRecordNode(forfeitNode);
				concreteRecordsByRaces.put(SC2TeamRaces.FORFEIT, forfeitRecord);
			}
			SC2TeamRaces zt = SC2Race.getTeamRacesFor(ZERG, TERRAN);
			JsonNode ztNode = recordsByRacesNode.get(zt.toString());
			if (ztNode != null) {
				ConcreteRecord ztRecord = getConcreteRecordFromRecordNode(ztNode);
				concreteRecordsByRaces.put(zt, ztRecord);
			}
			SC2TeamRaces zp = SC2Race.getTeamRacesFor(ZERG, PROTOSS);
			JsonNode zpNode = recordsByRacesNode.get(zp.toString());
			if (zpNode != null) {
				ConcreteRecord zpRecord = getConcreteRecordFromRecordNode(zpNode);
				concreteRecordsByRaces.put(zp, zpRecord);
			}
			SC2TeamRaces zr = SC2Race.getTeamRacesFor(ZERG, RANDOM);
			JsonNode zrNode = recordsByRacesNode.get(zr.toString());
			if (zrNode != null) {
				ConcreteRecord zrRecord = getConcreteRecordFromRecordNode(zrNode);
				concreteRecordsByRaces.put(zr, zrRecord);
			}
			SC2TeamRaces pt = SC2Race.getTeamRacesFor(PROTOSS, TERRAN);
			JsonNode ptNode = recordsByRacesNode.get(pt.toString());
			if (ptNode != null) {
				ConcreteRecord ptRecord = getConcreteRecordFromRecordNode(ptNode);
				concreteRecordsByRaces.put(pt, ptRecord);
			}
			SC2TeamRaces pr = SC2Race.getTeamRacesFor(PROTOSS, RANDOM);
			JsonNode prNode = recordsByRacesNode.get(pr.toString());
			if (prNode != null) {
				ConcreteRecord prRecord = getConcreteRecordFromRecordNode(prNode);
				concreteRecordsByRaces.put(pr, prRecord);
			}
			SC2TeamRaces tr = SC2Race.getTeamRacesFor(TERRAN, RANDOM);
			JsonNode trNode = recordsByRacesNode.get(tr.toString());
			if (trNode != null) {
				ConcreteRecord trRecord = getConcreteRecordFromRecordNode(trNode);
				concreteRecordsByRaces.put(tr, trRecord);
			}

			//
			teamRecord.setRecordsByRaces(concreteRecordsByRaces);
			return teamRecord;
		}

	}
}
