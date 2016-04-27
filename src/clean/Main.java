package clean;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {

	public static void main(String[] args) {
		//Used to get the all the files with extension .json in the folder specified
		File[] fileList = GetFiles.getFileList("Resources/Data/Test/");
		
		//Uses external library json-simple 
		JSONParser parser = new JSONParser();
		
		try{
			
			//File for CSV
			PrintWriter testCSV = new PrintWriter(new File("Resources/Data/Test/test.csv"));
		
			//String for CSV header
			StringBuilder writeHeader = new StringBuilder();
		
			//Build the header string of the CSV
			writeHeader.append("id"); //1
			writeHeader.append(',');
			writeHeader.append("blueHeroes"); //2
			writeHeader.append(',');
			writeHeader.append("redHeroes"); //3
			writeHeader.append(',');
			writeHeader.append("blueResult"); //4
			writeHeader.append(',');
			writeHeader.append("redResult"); //5
			writeHeader.append(',');
			writeHeader.append("bluePlayers"); //5
			writeHeader.append(',');
			writeHeader.append("redPlayers"); //6
			writeHeader.append(',');
			writeHeader.append("mapTitle"); //8
			writeHeader.append(',');
			writeHeader.append("timeStamp"); //9
			writeHeader.append('\n');
		
		
			//Write header to CSV
			testCSV.write(writeHeader.toString());
		
			//For every file
			for(File file : fileList){
				
				//Create lists and holders for attributes
				List<String> blueHeroesList = new ArrayList<String>();
				List<String> redHeroesList = new ArrayList<String>();
				String blueResult = "";
				String redResult = "";
				List<String> bluePlayersList = new ArrayList<String>();
				List<String> redPlayersList = new ArrayList<String>();
				StringBuilder writer = new StringBuilder();
				
				//Parse the json file
				Object obj = parser.parse(new FileReader("Resources/Data/Test/"+file.getName()));
				JSONObject root = (JSONObject) obj;
				JSONArray playerList = (JSONArray) root.get("m_playerList");
					
				String mapName = (String) root.get("m_title");
				String resultsMap = StringEscapeUtils.escapeJava(mapName);
				resultsMap = replaceMapNames(resultsMap);
					
				long timestamp = (long) root.get("m_timeUTC");
					
					
					
				writer.append(file.getName()); //1
				writer.append(',');
					
					
				//For every hero
				for(int i=0; i<playerList.size();i++){
					//Create the list of players
					JSONObject hero = (JSONObject) playerList.get(i);
					
					//Get the hero name attribute
					String heroName = (String) hero.get("m_hero");
					
					//Escape UTF chars works with apache commons lang
					String results = StringEscapeUtils.escapeJava(heroName);
					results = replaceNames(results);
						
					String nickName = (String) hero.get("m_name");
					String resultsNick = StringEscapeUtils.escapeJava(nickName);
					
						
					long result = (long) hero.get("m_result");
					long teamId = (long) hero.get("m_teamId");
					
					
					if(teamId==0){
						blueHeroesList.add(results);
						blueResult = String.valueOf(result);
						bluePlayersList.add(resultsNick);
					} else if(teamId==1){
						redHeroesList.add(results);
						redResult = String.valueOf(result);
						redPlayersList.add(resultsNick);
					}	
				}
					
				//Build the string containing attributes
				writer.append(blueHeroesList.toString().replace("[", "").replace("]", ""));
				writer.append(';');
				writer.append(redHeroesList.toString().replace("[", "").replace("]", ""));
				writer.append(';');
				writer.append(blueResult);
				writer.append(';');
				writer.append(redResult);
				writer.append(';');
				writer.append(bluePlayersList.toString().replace("[", "").replace("]", ""));
				writer.append(';');
				writer.append(redPlayersList.toString().replace("[", "").replace("]", ""));
				writer.append(';');
				writer.append(resultsMap);
				writer.append(';');
				writer.append(timestamp);
				writer.append('\n');
				
				//Write the string to the CSV
				testCSV.write(writer.toString());
			}
			
		//Close CSV file
		testCSV.close();
		} catch (Exception e){
					e.printStackTrace();
		}		
	}
	
	private static String replaceNames(String name){
		String englishName = "";
		
		switch(name){
			case "Kael\\u00E2\\u0080\\u0099thas":
				englishName = "Kael'thas";
				break;
			case "Rze\\u00C5\\u00BAnik":
				englishName = "The Butcher";
				break;
			case "Por. Morales":
				englishName = "Lt. Morales";
				break;
			case "Sylwana":
				englishName = "Sylvanas";
				break;
			case "Leoryk":
				englishName = "Leoric";
				break;
			case "Sonia":
				englishName = "Sonya";
				break;
			case "Szarogrzywy":
				englishName = "Greymane";
				break;
			case "Kleiner":
				englishName = "Stitches";
				break;
			default:
				englishName = name;
				break;
		}
	
		return englishName;
		
	}
	
	private static String replaceMapNames(String name){
		String englishName = "";
		
		switch(name){
			case "Przekl\\u00C4\\u0099ta Kotlina":
				englishName = "Cursed Hollow";
				break;
			case "Pole Bitewne Wieczno\\u00C5\\u009Bci":
				englishName = "Battlefield of Eternity";
				break;
			case "T\\u00C3\\u00BCrme des Unheils":
				englishName = "Towers of Doom";
				break;
			case "H\\u00C3\\u00B6llenschreine":
				englishName = "Infernal Shrines";
				break;
			case "Champs de l\\u00E2\\u0080\\u0099\\u00C3\\u0089ternit\\u00C3\\u00A9":
				englishName = "Battlefield of Eternity";
				break;
			default:
				englishName = name;
				break;
		}
		
		return englishName;
	}

}
