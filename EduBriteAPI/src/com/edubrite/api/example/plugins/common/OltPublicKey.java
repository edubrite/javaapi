package com.edubrite.api.example.plugins.common;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

public class OltPublicKey extends OltKey{
	transient protected PublicKey publicKey;
	public OltPublicKey(PublicKey publicKey) {
		try {
			this.publicKey = publicKey;
			KeyFactory fact = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec pub = fact.getKeySpec(publicKey,
					RSAPublicKeySpec.class);
			this.modulus = pub.getModulus();
			this.exponent = pub.getPublicExponent();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			invalidKey = true;
			exception = e;
		}
	}
	
	public OltPublicKey(String encoded){
		super(encoded);
	}
	
	public PublicKey getPublicKey() {
		if (publicKey == null) {
			try {
				RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus,
						exponent);
				KeyFactory fact = KeyFactory.getInstance("RSA");
				publicKey = fact.generatePublic(keySpec);
			} catch (Exception e) {
				e.printStackTrace();
				invalidKey = true;
				exception = e;
			}
		}
		return publicKey;
	}

}
