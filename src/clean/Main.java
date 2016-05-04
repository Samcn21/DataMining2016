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
			writeHeader.append(';');
			writeHeader.append("blueHeroes"); //2
			writeHeader.append(';');
			writeHeader.append("redHeroes"); //3
			writeHeader.append(';');
			writeHeader.append("blueResult"); //4
			writeHeader.append(';');
			writeHeader.append("redResult"); //5
			writeHeader.append(';');
			writeHeader.append("bluePlayers"); //5
			writeHeader.append(';');
			writeHeader.append("redPlayers"); //6
			writeHeader.append(';');
			writeHeader.append("mapTitle"); //8
			writeHeader.append(';');
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
				writer.append(';');
					
					
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
						//blueResult = String.valueOf(result);
						if(result == 1){
							blueResult = "True";
						} else if(result == 2){
							blueResult = "False";
						}
						bluePlayersList.add(resultsNick);
					} else if(teamId==1){
						redHeroesList.add(results);
						//redResult = String.valueOf(result);
						if(result == 1){
							redResult = "True";
						} else if( result == 2){
							redResult = "False";
						}
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
			case "\\u00ED\\u0083\\u009C\\u00EC\\u0082\\u00AC\\u00EB\\u008B\\u00A4\\u00EB\\u00A5\\u00B4":
				englishName = "Tassadar";
				break;
			case "\\u00EB\\u0088\\u0084\\u00EB\\u008D\\u0094\\u00EA\\u00B8\\u00B0":
				englishName = "Stitches";
				break;
			case "\\u00EC\\u008A\\u00A4\\u00EB\\u009E\\u0084":
				englishName = "Thrall";
				break;
			case "\\u00EB\\u00B0\\u009C\\u00EB\\u009D\\u00BC":
				englishName = "Valla";
				break;
			case "\\u00EC\\u00A0\\u009C\\u00EB\\u009D\\u00BC\\u00ED\\u0088\\u00B4":
				englishName = "Zeratul";
				break;
			case "\\u00EA\\u00B7\\u00B8\\u00EB\\u00A0\\u0088\\u00EC\\u009D\\u00B4\\u00EB\\u00A9\\u0094\\u00EC\\u009D\\u00B8":
				englishName = "Greymane";
				break;
			case "\\u00ED\\u008B\\u00B0\\u00EB\\u00A6\\u00AC\\u00EC\\u0097\\u0098":
				englishName = "Tyrael";
				break;
			case "\\u00EC\\u009A\\u00B0\\u00EC\\u0084\\u009C":
				englishName = "Uther";
				break;
			case "\\u00ED\\u008F\\u00B4\\u00EC\\u008A\\u00A4\\u00ED\\u0083\\u0080\\u00ED\\u008A\\u00B8":
				englishName = "Falstad";
				break;
			case "\\u00EC\\u0086\\u008C\\u00EB\\u0083\\u0090":
				englishName = "Sonya";
				break;
			case "\\u00EB\\u00A0\\u0088\\u00EA\\u00B0\\u0080\\u00EB\\u00A5\\u00B4":
				englishName = "Rehgar";
				break;
			case "\\u00EC\\u009A\\u0094\\u00ED\\u0095\\u009C\\u00EB\\u0082\\u0098":
				englishName = "Johanna";
				break;
			case "\\u00EC\\u008B\\u00A4\\u00EB\\u00B0\\u0094\\u00EB\\u0082\\u0098\\u00EC\\u008A\\u00A4":
				englishName = "Sylvanas";
				break;
			case "\\u00EB\\u00A6\\u00AC\\u00EB\\u00B0\\u008D":
				englishName = "Li-Ming";
				break;
			case "\\u00EC\\u00A4\\u0084":
				englishName = "Xul";
				break;
			case "\\u00EB\\u00AC\\u00B4\\u00EB\\u009D\\u00BC\\u00EB\\u0094\\u0098":
				englishName = "Muradin";
				break;
			case "\\u00ED\\u008B\\u00B0\\u00EB\\u009E\\u0080\\u00EB\\u008D\\u00B0":
				englishName = "Tyrande";
				break;
			case "\\u00EC\\u00A0\\u0095\\u00EC\\u0098\\u0088 \\u00ED\\u0083\\u0080\\u00EC\\u009A\\u00B0\\u00EB\\u00A0\\u008C \\u00EC\\u00A1\\u00B1\\u00EC\\u009E\\u00A5":
				englishName = "E.T.C.";
				break;
			case "\\u00EC\\u00A0\\u009C\\u00EC\\u009D\\u00B4\\u00EB\\u0082\\u0098":
				englishName = "Jaina";
				break;
			case "\\u00EC\\u009E\\u0090\\u00EA\\u00B0\\u0080\\u00EB\\u009D\\u00BC":
				englishName = "Zagara";
				break;
			case "\\u00EC\\u00BC\\u0080\\u00EB\\u00A6\\u00AC\\u00EA\\u00B1\\u00B4":
				englishName = "Kerrigan";
				break;
			case "\\u00EB\\u0094\\u0094\\u00EC\\u0095\\u0084\\u00EB\\u00B8\\u0094\\u00EB\\u00A1\\u009C":
				englishName = "Diablo";
				break;
			case "\\u00EB\\u00A0\\u0088\\u00EC\\u0098\\u00A4\\u00EB\\u00A6\\u00AD":
				englishName = "Leoric";
				break;
			case "\\u00EB\\u00AA\\u00A8\\u00EB\\u009E\\u0084\\u00EB\\u00A0\\u0088\\u00EC\\u008A\\u00A4 \\u00EC\\u00A4\\u0091\\u00EC\\u009C\\u0084":
				englishName = "Lt. Morales";
				break;
			case "\\u00EC\\u00B9\\u00B4\\u00EB\\u009D\\u00BC\\u00EC\\u00A7\\u0090":
				englishName = "Kharazim";
				break;
			case "\\u00EB\\u00A8\\u00B8\\u00ED\\u0082\\u00A4":
				englishName = "Murky";
				break;
			case "\\u00EC\\u0095\\u0084\\u00EB\\u00B0\\u0094\\u00ED\\u0088\\u00AC\\u00EB\\u00A5\\u00B4":
				englishName = "Abathur";
				break;
			case "\\u00EC\\u009D\\u00BC\\u00EB\\u00A6\\u00AC\\u00EB\\u008B\\u00A8":
				englishName = "Illidan";
				break;
			case "\\u00EA\\u00B8\\u00B8 \\u00EC\\u009E\\u0083\\u00EC\\u009D\\u0080 \\u00EB\\u00B0\\u0094\\u00EC\\u009D\\u00B4\\u00ED\\u0082\\u00B9":
				englishName = "The Lost Vikings";
				break;
			case "\\u00EC\\u0095\\u0084\\u00EB\\u00A5\\u00B4\\u00ED\\u0083\\u0080\\u00EB\\u008B\\u0088\\u00EC\\u008A\\u00A4":
				englishName = "Artanis";
				break;
			case "\\u00EC\\u00B2\\u00B8":
				englishName = "Chen";
			case "\\u00EC\\u00BA\\u0098\\u00ED\\u0083\\u0080\\u00EC\\u008A\\u00A4":
				englishName = "Kael'thas";
				break;
			case "Anub\\u00E2\\u0080\\u0099arak":
				englishName = "Anub'arak";
				break;
			case "Luisaile":
				englishName = "Brightwing";
				break;
			case "Bourbie":
				englishName = "Murky";
				break;
			case "Sgt Marteau":
				englishName = "Sgt. Hammer";
				break;
			case "Alachiara":
				englishName = "Brightwing";
				break;
			case "Vichinghi Sperduti":
				englishName = "The Lost Vikings";
				break;
			case "\\u00D0\\u00A3\\u00D1\\u0082\\u00D0\\u00B5\\u00D1\\u0080":
				englishName = "Uther";
				break;
			case "\\u00D0\\u0092\\u00D0\\u00B0\\u00D0\\u00BB\\u00D0\\u00BB\\u00D0\\u00B0":
				englishName = "Valla";
				break;
			case "\\u00D0\\u009D\\u00D0\\u00B0\\u00D0\\u00B7\\u00D0\\u00B8\\u00D0\\u00B1\\u00D0\\u00BE":
				englishName = "Nazeebo";
				break;
			case "\\u00D0\\u0090\\u00D0\\u00BD\\u00D1\\u0083\\u00D0\\u00B1'\\u00D0\\u00B0\\u00D1\\u0080\\u00D0\\u00B0\\u00D0\\u00BA":
				englishName = "Anub'arak";
				break;
			case "\\u00D0\\u0094\\u00D0\\u00B6\\u00D0\\u00BE\\u00D0\\u00B0\\u00D0\\u00BD\\u00D0\\u00BD\\u00D0\\u00B0":
				englishName = "Johanna";
				break;
			case "\\u00D0\\u0090\\u00D0\\u00B1\\u00D0\\u00B0\\u00D1\\u0082\\u00D1\\u0083\\u00D1\\u0080":
				englishName = "Abathur";
				break;
			case "\\u00D0\\u0097\\u00D0\\u00B5\\u00D1\\u0080\\u00D0\\u00B0\\u00D1\\u0082\\u00D1\\u0083\\u00D0\\u00BB":
				englishName = "Zeratul";
				break;
			case "\\u00D0\\u00A2\\u00D0\\u00B8\\u00D1\\u0080\\u00D0\\u00B0\\u00D1\\u008D\\u00D0\\u00BB\\u00D1\\u008C":
				englishName = "Tyrael";
				break;
			case "\\u00D0\\u00A4\\u00D0\\u00B0\\u00D0\\u00BB\\u00D1\\u0081\\u00D1\\u0082\\u00D0\\u00B0\\u00D0\\u00B4":
				englishName = "Falstad";
				break;
			case "\\u00D0\\u009C\\u00D0\\u00B0\\u00D0\\u00BB\\u00D1\\u0084\\u00D1\\u0083\\u00D1\\u0080\\u00D0\\u00B8\\u00D0\\u00BE\\u00D0\\u00BD":
				englishName = "Malfurion";
				break;
			case "Nasibo":
				englishName = "Nazeebo";
				break;
			case "L\\u00C3\\u00A9oric":
				englishName = "Leoric";
				break;
			case "\\u00D0\\u00A0\\u00D0\\u00B5\\u00D0\\u00B3\\u00D0\\u00B0\\u00D1\\u0080":
				englishName = "Rehgar";
				break;
			case "\\u00D0\\u0090\\u00D0\\u00B7\\u00D0\\u00BC\\u00D0\\u00BE\\u00D0\\u00B4\\u00D0\\u00B0\\u00D0\\u00BD":
				englishName = "Azmodan";
				break;
			case "\\u00D0\\u0094\\u00D0\\u00B6\\u00D0\\u00B0\\u00D0\\u00B9\\u00D0\\u00BD\\u00D0\\u00B0":
				englishName = "Jaina";
				break;
			case "\\u00D0\\u0097\\u00D0\\u00B0\\u00D0\\u00B3\\u00D0\\u00B0\\u00D1\\u0080\\u00D0\\u00B0":
				englishName = "Zagara";
				break;
			case "\\u00D0\\u0090\\u00D1\\u0080\\u00D1\\u0082\\u00D0\\u00B0\\u00D1\\u0081":
				englishName = "Arthas";
				break;
			case "\\u00D0\\u009A\\u00D0\\u00B5\\u00D0\\u00BB\\u00D1\\u008C'\\u00D1\\u0082\\u00D0\\u00B0\\u00D1\\u0081":
				englishName = "Kael'thas";
				break;
			case "\\u00D0\\u009B\\u00D0\\u00B5\\u00D0\\u00BE\\u00D1\\u0080\\u00D0\\u00B8\\u00D0\\u00BA":
				englishName = "Leoric";
				break;
			case "\\u00D0\\u0098\\u00D0\\u00BB\\u00D0\\u00BB\\u00D0\\u00B8\\u00D0\\u00B4\\u00D0\\u00B0\\u00D0\\u00BD":
				englishName = "Illidan";
				break;
			case "\\u00D0\\u00A0\\u00D0\\u00B5\\u00D0\\u00B9\\u00D0\\u00BD\\u00D0\\u00BE\\u00D1\\u0080":
				englishName = "Raynor";
				break;
			case "\\u00D0\\u0090\\u00D1\\u0080\\u00D1\\u0082\\u00D0\\u00B0\\u00D0\\u00BD\\u00D0\\u00B8\\u00D1\\u0081":
				englishName = "Artanis";
				break;
			case "\\u00D0\\u00A2\\u00D0\\u00B0\\u00D1\\u0081\\u00D1\\u0081\\u00D0\\u00B0\\u00D0\\u00B4\\u00D0\\u00B0\\u00D1\\u0080":
				englishName = "Tassadar";
				break;
			case "\\u00D0\\u00A1\\u00D0\\u00B2\\u00D0\\u00B5\\u00D1\\u0082\\u00D0\\u00B8\\u00D0\\u00BA":
				englishName = "Brightwing";
				break;
			case "\\u00D0\\u009C\\u00D1\\u0083\\u00D1\\u0080\\u00D0\\u00B0\\u00D0\\u00B4\\u00D0\\u00B8\\u00D0\\u00BD":
				englishName = "Muradin";
				break;
			case "\\u00D0\\u00A2\\u00D0\\u00B8\\u00D1\\u0080\\u00D0\\u00B0\\u00D0\\u00BD\\u00D0\\u00B4\\u00D0\\u00B0":
				englishName = "Tyrande";
				break;
			case "\\u00D0\\u009D\\u00D0\\u00BE\\u00D0\\u00B2\\u00D0\\u00B0":
				englishName = "Nova";
				break;
			case "\\u00D0\\u009C\\u00D1\\u008F\\u00D1\\u0081\\u00D0\\u00BD\\u00D0\\u00B8\\u00D0\\u00BA":
				englishName = "The Butcher";
				break;
			case "\\u00D0\\u0093\\u00D0\\u00B0\\u00D0\\u00B7\\u00D0\\u00BB\\u00D0\\u00BE\\u00D1\\u0083":
				englishName = "Gazlowe";
				break;
			case "\\u00D0\\u009B\\u00D0\\u00B5\\u00D0\\u00B9\\u00D1\\u0082\\u00D0\\u00B5\\u00D0\\u00BD\\u00D0\\u00B0\\u00D0\\u00BD\\u00D1\\u0082 \\u00D0\\u009C\\u00D0\\u00BE\\u00D1\\u0080\\u00D0\\u00B0\\u00D0\\u00BB\\u00D0\\u00B5\\u00D1\\u0081":
				englishName = "Lt. Morales";
				break;
			case "\\u00D0\\u00A1\\u00D0\\u00B8\\u00D0\\u00BB\\u00D1\\u008C\\u00D0\\u00B2\\u00D0\\u00B0\\u00D0\\u00BD\\u00D0\\u00B0":
				englishName = "Sylvanas";
				break;
			case "\\u00D0\\u00A1\\u00D0\\u00BE\\u00D0\\u00BD\\u00D1\\u008F":
				englishName = "Sonya";
				break;
			case "\\u00D0\\u0094\\u00D0\\u00B8\\u00D0\\u00B0\\u00D0\\u00B1\\u00D0\\u00BB\\u00D0\\u00BE":
				englishName = "Diablo";
				break;
			case "\\u00E9\\u009B\\u00B7\\u00E8\\u00AF\\u00BA":
				englishName = "Raynor";
				break;
			case "\\u00E9\\u009B\\u00B7\\u00E5\\u008A\\u00A0\\u00E5\\u00B0\\u0094":
				englishName = "Rehgar";
				break;
			case "\\u00E6\\u00B3\\u00B0\\u00E5\\u0085\\u00B0\\u00E5\\u00BE\\u00B7":
				englishName = "Tyrande";
				break;
			case "\\u00E8\\u0090\\u00A8\\u00E5\\u00B0\\u0094":
				englishName = "Thrall";
				break;
			case "\\u00E7\\u00BC\\u009D\\u00E5\\u0090\\u0088\\u00E6\\u0080\\u00AA":
				englishName = "Stitches";
				break;
			case "\\u00E7\\u00B2\\u00BE\\u00E8\\u008B\\u00B1\\u00E7\\u0089\\u009B\\u00E5\\u00A4\\u00B4\\u00E4\\u00BA\\u00BA\\u00E9\\u0085\\u008B\\u00E9\\u0095\\u00BF":
				englishName = "E.T.C.";
				break;
			case "\\u00E4\\u00B9\\u0094\\u00E6\\u00B1\\u0089\\u00E5\\u00A8\\u009C":
				englishName = "Johanna";
				break;
			case "\\u00E5\\u00BC\\u0097\\u00E6\\u0096\\u00AF\\u00E5\\u00A1\\u0094\\u00E5\\u00BE\\u00B7":
				englishName = "Falstad";
				break;
			case "\\u00E5\\u0090\\u0089\\u00E5\\u00AE\\u0089\\u00E5\\u00A8\\u009C":
				englishName = "Jaina";
				break;
			case "\\u00E5\\u008D\\u00A1\\u00E6\\u008B\\u0089\\u00E8\\u00BE\\u009B\\u00E5\\u00A7\\u0086":
				englishName = "Kharazim";
				break;
			case "\\u00E6\\u00B3\\u00B0\\u00E7\\u0091\\u009E\\u00E5\\u00B0\\u0094":
				englishName = "Tyrael";
				break;
			case "\\u00E5\\u0087\\u00AF\\u00E5\\u00B0\\u0094\\u00E8\\u0090\\u00A8\\u00E6\\u0096\\u00AF":
				englishName = "Kael'thas";
				break;
			case "\\u00E9\\u0099\\u0088":
				englishName = "Chen";
				break;
			case "\\u00E8\\u00BF\\u00AA\\u00E4\\u00BA\\u009A\\u00E6\\u00B3\\u00A2\\u00E7\\u00BD\\u0097":
				englishName = "Diablo";
				break;
			case "Graum\\u00C3\\u00A4hne":
				englishName = "Greymane";
				break;
			case "\\u00D0\\u009B\\u00D0\\u00B8-\\u00D0\\u009C\\u00D0\\u00B8\\u00D0\\u00BD":
				englishName = "Li-Ming";
				break;
			case "\\u00D0\\u00A0\\u00D0\\u00B5\\u00D0\\u00BA\\u00D1\\u0081\\u00D0\\u00B0\\u00D1\\u0080":
				englishName = "Rexxar";
				break;
			case "\\u00D0\\u009B\\u00D1\\u0083\\u00D0\\u00BD\\u00D0\\u00B0\\u00D1\\u0080\\u00D0\\u00B0":
				englishName = "Lunara";
				break;
			case "\\u00D0\\u00A2\\u00D1\\u0080\\u00D0\\u00B0\\u00D0\\u00BB\\u00D0\\u00BB":
				englishName = "Thrall";
				break;
			case "\\u00D0\\u009A\\u00D0\\u00B0\\u00D1\\u0080\\u00D0\\u00B0\\u00D0\\u00B7\\u00D0\\u00B8\\u00D0\\u00BC":
				englishName = "Kharazim";
				break;
			case "\\u00D0\\u00A1\\u00D0\\u00B5\\u00D0\\u00B4\\u00D0\\u00BE\\u00D0\\u00B3\\u00D1\\u0080\\u00D0\\u00B8\\u00D0\\u00B2":
				englishName = "Greymane";
				break;
			case "\\u00D0\\u00A1\\u00D1\\u0082\\u00D0\\u00B5\\u00D0\\u00B6\\u00D0\\u00BE\\u00D0\\u00BA":
				englishName = "Stitches";
				break;
			case "\\u00ED\\u0095\\u00B4\\u00EB\\u00A8\\u00B8 \\u00EC\\u0083\\u0081\\u00EC\\u0082\\u00AC":
				englishName = "Sgt. Hammer";
				break;
			case "Tyra\\u00C3\\u00ABl":
				englishName = "Tyrael";
				break;
			case "Jasnoskrzyd\\u00C5\\u0082a":
				englishName = "Brightwing";
				break;
			case "\\u00D0\\u00A7\\u00D1\\u008D\\u00D0\\u00BD\\u00D1\\u008C":
				englishName = "Chen";
				break;
			case "\\u00E9\\u0098\\u00BF\\u00E5\\u008A\\u00AA\\u00E5\\u00B7\\u00B4\\u00E6\\u008B\\u0089\\u00E5\\u0085\\u008B":
				englishName = "Anub'arak";
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
			case "\\u00ED\\u0095\\u0098\\u00EB\\u008A\\u0098 \\u00EC\\u0082\\u00AC\\u00EC\\u009B\\u0090":
				englishName = "Sky Temple";
				break;
			case "\\u00EA\\u00B1\\u00B0\\u00EB\\u00AF\\u00B8 \\u00EC\\u0097\\u00AC\\u00EC\\u0099\\u0095\\u00EC\\u009D\\u0098 \\u00EB\\u00AC\\u00B4\\u00EB\\u008D\\u00A4":
				englishName = "Tomb of the Spider Queen";
				break;
			case "\\u00EC\\u009A\\u00A9\\u00EC\\u009D\\u0098 \\u00EB\\u0091\\u00A5\\u00EC\\u00A7\\u0080":
				englishName = "Dragon Shire";
				break;
			case "\\u00EB\\u00B6\\u0088\\u00EC\\u00A7\\u0080\\u00EC\\u0098\\u00A5 \\u00EC\\u008B\\u00A0\\u00EB\\u008B\\u00A8":
				englishName = "Infernal Shrines";
				break;
			case "\\u00EC\\u0098\\u0081\\u00EC\\u009B\\u0090\\u00EC\\u009D\\u0098 \\u00EC\\u00A0\\u0084\\u00EC\\u009F\\u0081\\u00ED\\u0084\\u00B0":
				englishName = "Battlefield of Eternity";
				break;
			case "\\u00ED\\u008C\\u008C\\u00EB\\u00A9\\u00B8\\u00EC\\u009D\\u0098 \\u00ED\\u0083\\u0091":
				englishName = "Towers of Doom";
				break;
			case "\\u00EC\\u00A0\\u0080\\u00EC\\u00A3\\u00BC\\u00EB\\u00B0\\u009B\\u00EC\\u009D\\u0080 \\u00EA\\u00B3\\u00A8\\u00EC\\u00A7\\u009C\\u00EA\\u00B8\\u00B0":
				englishName = "Cursed Hollow";
				break;
			case "Comt\\u00C3\\u00A9 du Dragon":
				englishName = "Dragon Shire";
				break;
			case "Valle Maledetta":
				englishName = "Cursed Hollow";
				break;
			case "Contea del Drago":
				englishName = "Dragon Shire";
				break;
			case "Grobowiec Paj\\u00C4\\u0099czej Kr\\u00C3\\u00B3lowej":
				englishName = "Tomb of the Spider Queen";
				break;
			case "Grabkammer der Spinnenk\\u00C3\\u00B6nigin":
				englishName = "Tomb of the Spider Queen";
				break;
			case "Dracheng\\u00C3\\u00A4rten":
				englishName = "Dragon Shire";
				break;
			case "\\u00D0\\u009F\\u00D1\\u0080\\u00D0\\u00BE\\u00D0\\u00BA\\u00D0\\u00BB\\u00D1\\u008F\\u00D1\\u0082\\u00D0\\u00B0\\u00D1\\u008F \\u00D0\\u00BB\\u00D0\\u00BE\\u00D1\\u0089\\u00D0\\u00B8\\u00D0\\u00BD\\u00D0\\u00B0":
				englishName = "Cursed Hollow";
				break;
			case "\\u00D0\\u0093\\u00D1\\u0080\\u00D0\\u00BE\\u00D0\\u00B1\\u00D0\\u00BD\\u00D0\\u00B8\\u00D1\\u0086\\u00D0\\u00B0 \\u00D0\\u00BA\\u00D0\\u00BE\\u00D1\\u0080\\u00D0\\u00BE\\u00D0\\u00BB\\u00D0\\u00B5\\u00D0\\u00B2\\u00D1\\u008B \\u00D0\\u00BF\\u00D0\\u00B0\\u00D1\\u0083\\u00D0\\u00BA\\u00D0\\u00BE\\u00D0\\u00B2":
				englishName = "Tomb of the Spider Queen";
				break;
			case "\\u00D0\\u009D\\u00D0\\u00B5\\u00D0\\u00B1\\u00D0\\u00B5\\u00D1\\u0081\\u00D0\\u00BD\\u00D1\\u008B\\u00D0\\u00B9 \\u00D1\\u0085\\u00D1\\u0080\\u00D0\\u00B0\\u00D0\\u00BC":
				englishName = "Sky Temple";
				break;
			case "\\u00D0\\u009E\\u00D1\\u0081\\u00D0\\u00BA\\u00D0\\u00B2\\u00D0\\u00B5\\u00D1\\u0080\\u00D0\\u00BD\\u00D0\\u00B5\\u00D0\\u00BD\\u00D0\\u00BD\\u00D1\\u008B\\u00D0\\u00B5 \\u00D1\\u0081\\u00D0\\u00B2\\u00D1\\u008F\\u00D1\\u0082\\u00D0\\u00B8\\u00D0\\u00BB\\u00D0\\u00B8\\u00D1\\u0089\\u00D0\\u00B0":
				englishName = "Infernal Shrines";
				break;
			case "\\u00D0\\u0094\\u00D1\\u0080\\u00D0\\u00B0\\u00D0\\u00BA\\u00D0\\u00BE\\u00D0\\u00BD\\u00D0\\u00B8\\u00D0\\u00B9 \\u00D0\\u00BA\\u00D1\\u0080\\u00D0\\u00B0\\u00D0\\u00B9":
				englishName = "Dragon Shire";
				break;
			case "\\u00E5\\u00B7\\u00A8\\u00E9\\u00BE\\u0099\\u00E9\\u0095\\u0087":
				englishName = "Dragon Shire";
				break;
			case "\\u00E6\\u009C\\u00AB\\u00E6\\u0097\\u00A5\\u00E5\\u00A1\\u0094":
				englishName = "Towers of Doom";
				break;
			case "\\u00D0\\u0092\\u00D0\\u00B5\\u00D1\\u0087\\u00D0\\u00BD\\u00D0\\u00B0\\u00D1\\u008F \\u00D0\\u00B1\\u00D0\\u00B8\\u00D1\\u0082\\u00D0\\u00B2\\u00D0\\u00B0":
				englishName = "Battlefield of Eternity";
				break;
			case "\\u00EB\\u00B8\\u0094\\u00EB\\u009E\\u0099\\u00ED\\u0095\\u0098\\u00ED\\u008A\\u00B8 \\u00ED\\u0095\\u00AD\\u00EB\\u00A7\\u008C":
				englishName = "Blackheart's Bay";
				break;
			case "Wie\\u00C5\\u00BCe Zag\\u00C5\\u0082ady":
				englishName = "Towers of Doom";
				break;
			case "Tombe de la Reine araign\\u00C3\\u00A9e":
				englishName = "Tomb of the Spider Queen";
				break;
			case "\\u00D0\\u0091\\u00D0\\u00B0\\u00D1\\u0088\\u00D0\\u00BD\\u00D0\\u00B8 \\u00D0\\u00A0\\u00D0\\u00BE\\u00D0\\u00BA\\u00D0\\u00B0":
				englishName = "Towers of Doom";
				break;
				
				
			default:
				englishName = name;
				break;
		}
		
		return englishName;
	}

}
