package com.edubrite.api.example.plugins.common;

import java.math.BigInteger;

public class OltKey {
	protected BigInteger modulus;
	protected BigInteger exponent;
	protected static final int RADIX = 36;
	transient protected boolean invalidKey;
	transient protected Exception exception;
	
	protected OltKey(){
		
	}

	public OltKey(BigInteger m, BigInteger e) {
		this.modulus = m;
		this.exponent = e;
	}
	
	public OltKey(String m, String e) {
		this.modulus = new BigInteger(m, RADIX);
		this.exponent = new BigInteger(e, RADIX);
	}

	public OltKey(String encoded) {
		String[] tokens = encoded.split(",");
		modulus = new BigInteger(tokens[0], RADIX);
		exponent = new BigInteger(tokens[1], RADIX);
	}

	public String getEncoded() {
		return toString();
	}
	
	public Pair<String, String> getEncodedPair() {
		return Pair.of(this.modulus.toString(RADIX), this.exponent.toString(RADIX));
	}

	public String toString() {
		return this.modulus.toString(RADIX) + ","
				+ this.exponent.toString(RADIX);
	}

	public boolean isInvalidKey() {
		return invalidKey;
	}

	public Exception getException() {
		return exception;
	}
}
