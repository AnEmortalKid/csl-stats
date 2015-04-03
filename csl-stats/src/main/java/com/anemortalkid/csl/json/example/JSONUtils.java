package com.anemortalkid.csl.json.example;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.anemortalkid.csl.data.CSLTeamData;

public class JSONUtils {

	private static final String FILE_PATH = "/csl-stats/src/main/resources/com/anemortalkid/csl/json/";

	public static void writeToFile(CSLTeamData cslTeamData) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			// convert user object to json string, and save to a file
			File file = new File(FILE_PATH + cslTeamData.getTeamID() + ".json");
			mapper.writeValue(file, cslTeamData);

			// display to console
			System.out.println(mapper.writeValueAsString(cslTeamData));

		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (JsonMappingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
}
