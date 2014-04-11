package com.edubrite.api.plugins.common;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;

public class OltPrivateKey extends OltKey{
	transient protected PrivateKey privateKey;
	
	public OltPrivateKey(String encoded){
		super(encoded);
	}
	
	public OltPrivateKey(PrivateKey privateKey) {
		try {
			this.privateKey = privateKey;
			KeyFactory fact = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec pri = fact.getKeySpec(privateKey,
					RSAPrivateKeySpec.class);
			this.modulus = pri.getModulus();
			this.exponent = pri.getPrivateExponent();
			return;
		} catch (Exception e) {
			invalidKey = true;
			exception = e;
		}
	}
	
	public PrivateKey getPrivateKey() {
		if (privateKey == null) {
			try {
				RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus,
						exponent);
				KeyFactory fact = KeyFactory.getInstance("RSA");
				privateKey = fact.generatePrivate(keySpec);
			} catch (Exception e) {
				e.printStackTrace();
				invalidKey = true;
				exception = e;
			}
		}
		return privateKey;
	}
}
