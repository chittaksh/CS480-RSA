package com.main;

import java.util.Random;
import java.util.Scanner;

import com.classes.DigitalSignature;
import com.classes.FileOptions;

public class ChangeByte {
	
	public static void main(String[] args){
		
		try{
			int index = 0;
			String filename = null;
			Scanner reader = new Scanner(System.in);

			System.out.println("Hello!! Lets mess with the signed file.");
			System.out.print("Please enter the file name: ");
			
			filename  = reader.nextLine();
			
			String data = FileOptions.ReadFile(filename+".txt.signed");
			
			System.out.println(data);
			
			System.out.println("Please enter the index of the byte to change:");
			
			index = reader.nextInt();
			
			if(index<data.length()){
				ChangeText(filename, index);
				System.out.println("File tampered.");
			}else{
				System.out.println("Wrong input. Please run the program again.");
			}

		}catch(Exception ex){
			System.out.println("Error : " + ex + " : File tampering failed. Try running the program again.");
		}
	}

	public static boolean ChangeText(String filename, int index) {

		try {
			Random random = new Random();

			String text = FileOptions.ReadFile(filename+".txt.signed");

			String encDigest = text.substring(0, text.indexOf('\n'));
			String input = text.substring(text.indexOf('\n'), text.length()).trim();
			StringBuilder sb = new StringBuilder(input);

			sb.setCharAt(index, (char) random.nextInt());

			FileOptions.WriteFile(filename+".txt.signed", encDigest + '\n' + sb.toString());

			return true;
		} catch (Exception ex) {
			System.out.println("Error : " + ex.getMessage() + " : Failed to change byte.");
		}

		return false;
	}
}
