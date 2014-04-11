package com.edubrite.api.example.plugins.common;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class OltSymmetricKeyHandler {
	public static void main(String[] args) throws Exception {
		// generate();
		Pair<OltPublicKey, OltPrivateKey> keys = generateBaseKeyPair();
		byte[] secretKeyBytes = generateSignedSecretKey(keys.getFirst());
		//System.out.println("Signed Secret key length = "
		//		+ secretKeyBytes.length);

		Key secretKey = generateSecretKey();
		//System.out.println("UnSigned Secret key length = "
		//		+ secretKey.getEncoded().length);
		secretKeyBytes = encryptSecretKeyAsBase64(secretKey, keys.getFirst())
				.getBytes();
		//System.out.println("Signed Secret key length = "
		//		+ secretKeyBytes.length);

		Key unencryptedKey = decryptSecretKeyFromEncryptedBase64(Base64
				.encode(secretKeyBytes), keys.getSecond());
		//System.out.println("decrypted Secret key length = "
		//		+ unencryptedKey.getEncoded().length);

	}

	public static Pair<OltPublicKey, OltPrivateKey> generateBaseKeyPair()
			throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.genKeyPair();

		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();

		return Pair.of(new OltPublicKey(publicKey), new OltPrivateKey(
				privateKey));
	}

	public static Key generateSecretKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
		keyGenerator.init(128);
		Key blowfishKey = keyGenerator.generateKey();
		return blowfishKey;
	}

	public static String secretKeyToBase64String(Key key) {
		return Base64.encode(key.getEncoded());
	}

	public static Key parseSecretKeyFromBase64String(String str64) {
		byte[] bytes = Base64.decode(str64);
		SecretKey newBlowfishKey = new SecretKeySpec(bytes, "Blowfish");
		return newBlowfishKey;
	}

	public static Key parseSecretKeyFromBase64(String str64) {
		byte[] bytes = Base64.decode(str64);
		SecretKey newBlowfishKey = new SecretKeySpec(bytes, "Blowfish");
		return newBlowfishKey;
	}

	public static String encryptSecretKeyAsBase64(Key blowfishKey,
			OltPublicKey publicKey) throws Exception {
		byte[] blowfishKeyBytes = blowfishKey.getEncoded();

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey.getPublicKey());
		byte[] cipherText = cipher.doFinal(blowfishKeyBytes);
		// System.out.println("Encrypted Secret key length = "+cipherText.length);
		return Base64.encode(cipherText);

	}

	public static Key decryptSecretKeyFromEncryptedBase64(String str64,
			OltPrivateKey privateKey) throws Exception {
		byte[] bytes = Base64.decode(str64);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey.getPrivateKey());
		byte[] decryptedKeyBytes = cipher.doFinal(bytes);
		// System.out.println("Blowfish key length = "+decryptedKeyBytes.length);
		SecretKey newBlowfishKey = new SecretKeySpec(decryptedKeyBytes,
				"Blowfish");
		return newBlowfishKey;
	}

	public static byte[] generateSignedSecretKey(OltPublicKey key)
			throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
		keyGenerator.init(128);
		Key blowfishKey = keyGenerator.generateKey();

		byte[] blowfishKeyBytes = blowfishKey.getEncoded();
		// System.out.println("Secret key length = "+blowfishKeyBytes.length);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key.getPublicKey());
		byte[] cipherText = cipher.doFinal(blowfishKeyBytes);
		// System.out.println("Encrypted Secret key length = "+cipherText.length);
		return cipherText;
	}

	public static byte[] decryptSignedSecretKey(byte[] bytes, OltPrivateKey key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key.getPrivateKey());
		byte[] decryptedKeyBytes = cipher.doFinal(bytes);
		return decryptedKeyBytes;
	}

	public static String getEncryptedAndBase64Data(String plainData, Key blowfishKey)throws Exception{
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, blowfishKey);
		byte [] bytes = cipher.doFinal(plainData.getBytes("UTF-8"));
		return Base64.encode(bytes);
	}
	
	public static String getDecryptedDataFromBase64AndEncrypted(String str64Enc, Key blowfishKey) throws Exception{
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, blowfishKey);
		byte [] encBytes = Base64.decode(str64Enc);
		byte [] bytes = cipher.doFinal(encBytes);
		return new String(bytes, "UTF-8");
	}
}
