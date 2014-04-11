package com.edubrite.api.example;

import com.edubrite.api.plugins.common.PluginConfig;
import com.edubrite.api.plugins.common.PluginConfigManager;
import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;

public class Main {
	public static void main(String []args)throws Exception{
		PluginConfig config = PluginConfigManager.getConfig();
		config.setEnableEncryption(true);
		
		EduBriteRemoteConnector connector = new EduBriteRemoteConnector();
		connector.connect();
		System.out.println("Connected " +connector.isConnected());
	}
}
