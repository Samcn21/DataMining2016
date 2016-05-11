import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TeamSorter {

	private static ArrayList<String> CSVtoArray(String csv) {
		ArrayList<String> result = new ArrayList<String>();
		if(csv != null) {
			String[] splitData = csv.split("\\s*;\\s*");
			for(int i = 0; i < splitData.length; i++) {
				if(!(splitData[i] == null) || !(splitData[i].length() == 0)) {
					result.add(splitData[i].trim());
				}
			}
		}
		return result;

	}
	public static void main(String[] args) {
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

		BufferedReader buffer = null;

		try {
			String line;
			buffer = new BufferedReader(new FileReader("../FullData.csv"));
			while((line = buffer.readLine()) != null) {
				rows.add(CSVtoArray(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Modify Heroes Blue and Red
		for(ArrayList<String> a : rows) {
			String[] bHeroes = a.get(1).split(", ");
			String[] rHeroes = a.get(2).split(", ");
			Arrays.sort(bHeroes);
			Arrays.sort(rHeroes);

			a.set(1, String.join(", ", bHeroes));
			a.set(2, String.join(", ", rHeroes));
		}
		System.out.println("Done");

		// Write back to a new CSV file
		try{

			//File for CSV
			PrintWriter outCSV = new PrintWriter(new File("FullData_heroes_sorted.csv"));
			for(ArrayList<String> a : rows) {
				outCSV.write(String.join(";", a) + "\n");
			}
			outCSV.close();




		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
