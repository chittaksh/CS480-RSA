package com.classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileOptions {

	// this function will write the key_private and key_public to the given
	// filename
	public static void WriteFile(String filename, String text) {

		try {

			FileOutputStream file = new FileOutputStream("Output\\" + filename);
			ObjectOutputStream stream = new ObjectOutputStream(file);

			stream.writeObject(text);

			stream.close();

		} catch (IOException e) {
			System.out.println("Error reading file '" + filename + "'");
		}
	}

	public static String ReadFile(String filename) {
		// This will reference one line at a time
		String content = null;

		try {
			FileInputStream fis = new FileInputStream("Output\\" + filename);
			ObjectInputStream ois = new ObjectInputStream(fis);

			content = (String) ois.readObject();
		}

		catch (FileNotFoundException ex) {
			System.out.println("No File with name '" + filename + "'");
		} catch (Exception ex) {
			System.out.println("Error reading file '" + filename + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}

		return content;
	}

	public static String ReadOriginal(String filename) {
		// This will reference one line at a time
		String line = null;
		StringBuilder sb = new StringBuilder();

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader("File\\" + filename);

			// Always wrap FileReader in BufferedReader.
			BufferedReader br = new BufferedReader(fileReader);

			while ((line = br.readLine()) != null) {

				sb.append(line + "\n");

			}

			// Always close files.
			br.close();
		}

		catch (FileNotFoundException ex) {
			System.out.println("No File with name '" + filename + "'");
		} catch (Exception ex) {
			System.out.println("Error reading file '" + filename + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
		String contents = sb.toString();
		// System.out.println(contents);
		return contents;
	}

	public static boolean checkFile(String filename){
		return (new File("Output\\"+filename)).exists();
		
	}
}
