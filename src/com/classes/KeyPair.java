package com.classes;

//Storage for key value pair.

import java.math.BigInteger;

public class KeyPair {

	private BigInteger value;
	private BigInteger N;

	public KeyPair(BigInteger value, BigInteger n) {
		super();
		this.value = value;
		N = n;
	}

	public void setValue(BigInteger value) {
		this.value = value;
	}
	
	public BigInteger getValue() {
		return value;
	}

	public BigInteger getN() {
		return N;
	}

	public void setN(BigInteger n) {
		N = n;
	}

}
