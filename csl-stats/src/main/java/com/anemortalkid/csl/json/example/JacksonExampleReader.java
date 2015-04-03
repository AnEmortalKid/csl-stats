package com.anemortalkid.csl.json.example;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonExampleReader {
	public static void main(String[] args) {

		ObjectMapper mapper = new ObjectMapper();

		try {

			// read from file, convert it to user class
			User user = mapper.readValue(new File(
					"C:\\Users\\JMonterrubio\\Desktop\\user.json"), User.class);

			// display to console
			System.out.println(user);

		} catch (JsonGenerationException e) {

			e.printStackTrace();

		} catch (JsonMappingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
}
