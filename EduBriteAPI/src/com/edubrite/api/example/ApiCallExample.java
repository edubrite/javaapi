package com.edubrite.api.example;

import java.security.Key;

import com.edubrite.api.plugins.common.OltPrivateKey;
import com.edubrite.api.plugins.common.OltPublicKey;
import com.edubrite.api.plugins.common.OltSymmetricKeyHandler;
import com.edubrite.api.plugins.common.Pair;
import com.edubrite.api.plugins.common.PluginConfig;
import com.edubrite.api.plugins.common.PluginConfigManager;
import com.edubrite.api.plugins.connector.EduBriteConnectorFactory;
import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;
import com.edubrite.api.plugins.vo.PagedList;
import com.edubrite.api.plugins.vo.User;

public class ApiCallExample {

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
		
		User user = new User();
		user.setName("corpdemoadmin");
		PluginConfigManager.getInstance().setApplicationUser(user);
		
		PagedList pagination = new PagedList();
		String response = connector.getGroupList(pagination);
		System.out.println(response);
		
	}	

}
