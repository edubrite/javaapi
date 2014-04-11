package com.edubrite.api.example.plugins;

import java.security.Key;

import com.edubrite.api.example.plugins.common.OltPrivateKey;
import com.edubrite.api.example.plugins.common.OltPublicKey;
import com.edubrite.api.example.plugins.common.OltSymmetricKeyHandler;
import com.edubrite.api.example.plugins.common.Pair;
import com.edubrite.api.example.plugins.connector.EduBriteConnectorFactory;
import com.edubrite.api.example.plugins.connector.impl.EduBriteRemoteConnector;

public class MainWithEncryption {
	public static void main(String []args)throws Exception{
		PluginConfig config = PluginConfigManager.getConfig();
		config.setEnableEncryption(true);
		Pair<OltPublicKey, OltPrivateKey> keys = OltSymmetricKeyHandler
				.generateBaseKeyPair();
		Key secretKey = OltSymmetricKeyHandler.generateSecretKey();
		config.setKeys(keys);
		config.setSecretKey64(OltSymmetricKeyHandler.secretKeyToBase64String(secretKey));
		EduBriteConnectorFactory.getInstance().getConnector().disconnect();
		
		EduBriteRemoteConnector connector = new EduBriteRemoteConnector();
		connector.connect();
		System.out.println("Connected " +connector.isConnected());
		
	}
}
