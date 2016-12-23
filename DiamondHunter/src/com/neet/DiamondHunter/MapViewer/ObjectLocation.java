package com.neet.DiamondHunter.MapViewer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ObjectLocation {

	private static File locationFile = new File("Object.location");

	private static final String[] diamond_location = { "20,20", "12,36", "28,4", "4,34", "28,19", "35,26", "38,36",
			"27,28", "20,30", "14,25", "4,21", "9,14", "4,3", "20,14", "13,20" };

	// To prevent auto overwriting at launch
	public static boolean overwriteMode = false;

	/**
	 * Check if location file exists. If yes, does nothing.
	 * If file does not exist, creates a file with default coordinates.
	 */
	public static void checkExist(){
		// If the file does not exist in the specified path
		if (!locationFile.exists() || locationFile.isDirectory()){
			try{
				// Create the file with default value first. If this fails,
				// NullPointerException will be thrown
				if (locationFile.createNewFile()){
					BufferedWriter writeLocation = new BufferedWriter(new FileWriter(locationFile));
					writeLocation.write("26,37,12,4"); // items default coordinates
					writeLocation.newLine();
					writeLocation.write("17,17"); // player default coordinate
					writeLocation.newLine();
					for (int i = 0; i < diamond_location.length; i++) {
						writeLocation.write(diamond_location[i]);
						if (i != diamond_location.length)
							writeLocation.newLine();
					}
					writeLocation.close();
				}
			}
			catch (IOException e){
				System.err.println("Unable to create or write file");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param line The line of coordinates which correspond to the entity to be retrieved
	 * @return coordinates
	 */
	public static int[] getLocation(int line) {
		checkExist();
		// File exist and is ready to be read
		if (locationFile.canRead()){

			try{
				FileInputStream readLocation = new FileInputStream(locationFile);
				byte[] data = new byte[(int) locationFile.length()];

				// Read the entire file
				readLocation.read(data);
				readLocation.close();
				String[] strLines = new String(data, "UTF-8").split("\n");
				String[] strLocation;
				String[] temp = new String[2];
				//Get only the locations for axe/boat coordinates or player
				if (line == 1) //Axe and boat
					strLocation = new String(strLines[line - 1]).trim().split(",");
				else if(line == 2) //Player
					strLocation = new String(strLines[line - 1]).trim().split(",");
				else { //Diamonds
					int k = 0;
					strLocation = new String[strLines.length * 2 - 4];
					for(int i = line - 1; i < strLines.length; i++) {
						temp = new String(strLines[i]).trim().split(",");
						for(int j = 0; j < temp.length; j++) {
							strLocation[k++] = temp[j];
						}
					}
				}

				// Get the coordinates
				int[] location = new int[strLocation.length];
				for (int i = 0; i < strLocation.length; i++) {
					if(strLocation[i] != null) {
						location[i] = Integer.parseInt(strLocation[i]);
					}
				}
				return location;
			}
			catch (FileNotFoundException e){
				System.err.println("File does not exist");
				e.printStackTrace();
			}
			catch (IOException e){
				System.err.println("No read access to file");
				e.printStackTrace();
			}
		}else{
			System.err.println("Error in reading file");
			return null;
		}
		return null;
	}

	/**
	 * Overwrite file to update position of items or player
	 * @param data The updated coordinates
	 * @param line The line of coordinates to be overwritten
	 */
	public static void overwriteFile(String data, int line) {
		checkExist();
		try {
			BufferedReader oldFile = new BufferedReader(new FileReader("Object.location"));
			int count = 0;
			String input = "";
			String l = "";
			while ((l = oldFile.readLine()) != null) {
				if (count == (line - 1)) {
					l = l.replace(l, data);
				}
				input += l + "\n";
				count++;
			}
			oldFile.close();

			if(overwriteMode) {
				PrintWriter pw = new PrintWriter(locationFile);
				pw.close();

				FileWriter fw = new FileWriter(locationFile);
				fw.write(input);
				fw.close();
				overwriteMode = false;
			}
		}
		catch (FileNotFoundException e){
			System.err.println("File does not exist");
			e.printStackTrace();
		}
		catch (IOException e){
			System.err.println("No read access to file");
			e.printStackTrace();
		}
	}
}
