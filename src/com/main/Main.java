package com.main;

import java.awt.SecondaryLoop;
import java.util.Scanner;

import com.classes.DigitalSignature;
import com.classes.KeyGen;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			String input = "";
			String filename = null;
			Scanner reader = new Scanner(System.in);

			System.out.println("Hello!! Welcome to RSA based Digitl Signature System.");

			while (!input.equals("exit")) {
				System.out.println("What would you like to do?");
				System.out.println("1. Send (type Send)");
				System.out.println("2. Receive (type receive)");
				System.out.println("3. Exit");

				input = reader.nextLine();

				switch (input.toLowerCase()) {

				//Code to send file.
				case "send":
					System.out.print("Please enter file name : ");
					filename = reader.nextLine();
					
					if(filename!=null)
					{
						System.out.println("Generating signed file.");
						DigitalSignature.SendFile("text");
						System.out.println("File generation done.");
					}else{
						System.out.println("Sorry we were unable to process your request. please try again.");
					}
					break;

					//Code to Receive file.
				case "receive":
					System.out.print("Please enter file name : ");
					filename = reader.nextLine();
					
					if(filename!=null)
					{
						System.out.println("Receiving and validating file.");
						DigitalSignature.ReceiveFile("text");
					}else{
						System.out.println("Sorry we were unable to process your request. please try again.");
					}
					break;

					// exit code.
				case "exit":
						System.out.println("Bye!!");
					break;

					// If user inputs something wrong.
				default:
					System.out.println("Sorry we were unable to process your request. please try again.");
				}

			}

		} catch (Exception ex) {
			System.out.println("Error : " + ex.getMessage() + " : Main Program Crashed.");
		}

	}

}
