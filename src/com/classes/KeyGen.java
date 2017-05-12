package com.classes;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;

//Key Gen to generate RSA Code.

public class KeyGen {

	// public key is public
	public static KeyPair publicKey;

	// private key is private
	private static KeyPair privateKey;

	public KeyGen() throws IOException {

		if (!FileOptions.checkFile("") || !FileOptions.checkFile("")) {
			keyGeneration(512);

			// writing the keys in the file using Write Function
			// FileOptions.WriteKeys(filenamePublic, filenamePrivate, publicKey,
			// privateKey);
			FileOptions.WriteFile("Privkey.rsa", "d:" + privateKey.getValue() + ", \n N:" + privateKey.getN() + ",");
			FileOptions.WriteFile("Pubkey.rsa", "e:" + publicKey.getValue() + ", \n N:" + publicKey.getN() + ",");

		}

		// Reads the keys stored in rsa files.
		String privatekey = FileOptions.ReadFile("Privkey.rsa");
		String publickey = FileOptions.ReadFile("Pubkey.rsa");

		BigInteger e = new BigInteger(publickey.substring(publickey.indexOf('e') + 2, publickey.indexOf(',')));

		BigInteger N = new BigInteger(
				publickey.substring(publickey.indexOf('N') + 2, publickey.indexOf(',', publickey.indexOf('N') + 2)));

		publicKey = new KeyPair(e, N);

		BigInteger d = new BigInteger(privatekey.substring(privatekey.indexOf('d') + 2,
				privatekey.indexOf(',', privatekey.indexOf('d') + 2)));

		privateKey = new KeyPair(d, N);

		printKeys();
	}

	// RSA(n) (constructor): generates a public (N; e) and private (N; d) RSA
	// key pair, where N; e; d are numbers of approximately n bits in length.
	// Also, stores the file in pairs.
	public KeyGen(int n) throws IOException {

		keyGeneration(n);

		// writing the keys in the file using Write Function
		// FileOptions.WriteKeys(filenamePublic, filenamePrivate, publicKey,
		// privateKey);
		FileOptions.WriteFile("Privkey.rsa", "d:" + privateKey.getValue() + ", \n N:" + privateKey.getN() + ",");
		FileOptions.WriteFile("Pubkey.rsa", "e:" + publicKey.getValue() + ", \n N:" + publicKey.getN() + ",");
	}

	/*
	 * c = encrypt(m, N, e): for a given integer m < N and public key (N; e)
	 * return the encrypted message c = m^e(mod N).
	 */
	public static BigInteger encrypt(BigInteger m) {
		// encrypted message c = m^e(mod N)
		BigInteger c = ModularArithmetic.modexp(m, publicKey.getValue(), publicKey.getN());
		return c;
	}

	/*
	 * m = decrypt(c): for an integer c < N, use the private key to return the
	 * decrypted message m = c^d(mod N)
	 */
	public static BigInteger decrypt(BigInteger c) {
		// decrypted message m = c^d(mod N)
		BigInteger m = ModularArithmetic.modexp(c, privateKey.getValue(), privateKey.getN());
		return m;
	}

	private void keyGeneration(int k) {

		BigInteger e;
		BigInteger N;
		BigInteger d;
		BigInteger phi;

		BigInteger p = BigInteger.probablePrime(k, new Random());
		BigInteger q = BigInteger.probablePrime(k, new Random());

		// Incase, if p and q are of same value
		while (p.equals(q)) {
			q = BigInteger.probablePrime(k, new Random());
		}

		// N = p x q
		N = p.multiply(q);

		// phi(N) = (p-1) x (q-1)
		phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

		do {
			Random rand = new Random();
			e = new BigInteger(k, rand);

			if (e.gcd(phi).equals(BigInteger.ONE) && e.compareTo(phi) < 0 && !e.equals(BigInteger.ONE))
				break;
		} while (true);

		// calculate d = e-1 mod phi
		d = e.modInverse(phi);

		publicKey = new KeyPair(e, N);
		privateKey = new KeyPair(d, N);
	}

	private void printKeys() {
		// Display only the public keys
		System.out.println("e: " + publicKey.getValue());
		System.out.println("d: " + privateKey.getValue());
		System.out.println("N: " + publicKey.getN());
	}

}

class ModularArithmetic {

	// c = modadd(a, b, N) : returns c = a + b (mod N)
	public static BigInteger modadd(BigInteger a, BigInteger b, BigInteger N) {
		BigInteger c;
		a = a.mod(N);
		b = b.mod(N);
		c = (a.add(b)).mod(N);
		return c;
	}

	// c = modmult(a, b, N): returns c = a * b (mod N)
	public static BigInteger modmult(BigInteger a, BigInteger b, BigInteger N) {
		BigInteger c;
		a = a.mod(N);
		b = b.mod(N);
		c = (a.multiply(b)).mod(N);
		return c;
	}

	// c = moddiv(a, b, N): returns c = a/b (mod N)
	public static BigInteger moddiv(BigInteger a, BigInteger b, BigInteger N) {
		BigInteger c;
		a = a.mod(N);
		b = b.mod(N);

		c = (a.divide(b)).mod(N);

		return c;
	}

	// c = modexp(a, b, N): returns c = a^b (mod N)
	public static BigInteger modexp(BigInteger a, BigInteger b, BigInteger N) {
		BigInteger c = (a.modPow(b, N));
		return c;
	}

	// b = isPrime(N, k): returns true if N is prime with probability 1/2^k, or
	// false if N is not prime
	public static Boolean isPrime(BigInteger N, int k) {

		// returns if the number is prime or not with a probability 1/2^k
		return N.isProbablePrime(k);
	}

	// this function calculates the phi value for a given number n
	public static BigInteger phiN(BigInteger n) {
		BigInteger phi = BigInteger.ONE;

		// if n is 1, return 0
		if (n.equals(BigInteger.ONE))
			return BigInteger.ZERO;

		BigInteger i = BigInteger.valueOf(2);

		// calculate until n becomes 1
		while (!n.equals(BigInteger.ZERO)) {

			int c = 0;

			while (n.mod(i).equals(BigInteger.ZERO)) {
				n = n.divide(i);
				c++;
			}

			if (c != 0) {

				// using the formula,
				// (p^e - p^(e-1))
				BigInteger t = i.pow(c).subtract(i.pow(c - 1));

				// multiply it to the phi value
				phi = phi.multiply(t);
			}

			i = i.add(BigInteger.ONE);
		}

		return phi;
	}

}
