package com.classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

public class DigitalSignature {

	public static void SendFile(String filename) throws Exception {

		String input = FileOptions.ReadOriginal(filename+".txt").trim();

		MessageDigest m1 = MessageDigest.getInstance("MD5");
		byte[] inputByte = input.getBytes();
		m1.update(inputByte);
		byte[] digest = m1.digest();

		BigInteger dig = new BigInteger(1, digest);
		KeyGen key = new KeyGen();
		BigInteger biKey = key.encrypt(dig);

		String finalText = biKey + "\n" + input;

		FileOptions.WriteFile(filename+".txt.signed", finalText);

	}

	public static void ReceiveFile(String filename) {
		try{
		KeyGen key = new KeyGen();

		String text = FileOptions.ReadFile(filename+".txt.signed");

		BigInteger encDigest = new BigInteger(text.substring(0, text.indexOf('\n')));
		String input = text.substring(text.indexOf('\n'), text.length()).trim();

		MessageDigest m1 = MessageDigest.getInstance("MD5");
		byte[] inputByte = input.getBytes();
		m1.update(inputByte);
		byte[] digest = m1.digest();

		byte[] orDig = key.decrypt(encDigest).toByteArray();

		// Drop sign bit
		orDig = Arrays.copyOfRange(orDig, 1, orDig.length);

		if (MessageDigest.isEqual(digest, orDig)) {
			System.out.println("Message is Original.");
		} else {
			System.out.println("Message was tampered.");
		}
		}catch(Exception ex){
			System.out.println("Error : " + ex.getMessage());
			System.out.println("Message was tampered.");
		}
	}

}
