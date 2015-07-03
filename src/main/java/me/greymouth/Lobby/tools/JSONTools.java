package me.greymouth.Lobby.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import me.greymouth.Lobby.Lobby;
import me.greymouth.Lobby.Profile;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

public class JSONTools {

	private static String pluginFolder = Lobby.getPluginFolder();

	@SuppressWarnings("unchecked")
	public static void writeJSON(String fileName, String object, String value) {
		JSONObject main = new JSONObject();

		main.put(object, value);

		try {
			File file = new File(pluginFolder + File.separator + "data" + File.separator + fileName + ".json");
			File filePath = new File(pluginFolder + File.separator + "data");
			filePath.mkdirs();
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file); 
			fileWriter.write(object);
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {}
	}
	
	public static void writeProfilesToJSON(ArrayList<Profile> profiles) {
		JsonStructure j = new JsonStructure();
		
		j.profiles = new Profile[profiles.size()];
		for (int i = 0; i < profiles.size(); i++) {
			j.profiles[i] = profiles.get(i);
		}
		
		Gson g = new Gson();
		
		try {
			File file = new File(pluginFolder + File.separator + "data" + File.separator + "profiles" + ".json");
			File filePath = new File(pluginFolder + File.separator + "data");
			filePath.mkdirs();
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file); 
			fileWriter.write(g.toJson(j));
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {}
	}
	
	public static ArrayList<Profile> getProfilesFromJSON() {
		ArrayList<Profile> profiles = new ArrayList<Profile>();
		
		Gson g = new Gson();
		
		JsonStructure j = new JsonStructure();
		
		try {
			File filePath = new File(pluginFolder + File.separator + "data");
			filePath.mkdirs();
			
			File file = new File(pluginFolder + File.separator + "data" + File.separator + "profiles" + ".json");
			BufferedReader f = new BufferedReader(new FileReader(file));
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			j = g.fromJson(f, JsonStructure.class);
		} catch (Exception e) {}
		
		if (j != null) {
			for (int i = 0; i < j.profiles.length; i++) {
				profiles.add(j.profiles[i]);
			}
		}
		
		return profiles;
	}

	public static String readJSON(String fileName, String object) {
		String var = null;
		try {
			JSONParser parser = new JSONParser();

			File file = new File(pluginFolder + File.separator + "data" + File.separator + fileName + ".json");
			Object obj = parser.parse(new FileReader(file));

			JSONObject jsonObject = (JSONObject) obj;

			var = (String) jsonObject.get(object);

		} catch (Exception e) {}
		return var;
	}

}
