package com.edubrite.api.example.plugins;

import com.edubrite.api.example.plugins.connector.impl.EduBriteRemoteConnector;

public class Main {
	public static void main(String []args)throws Exception{
		PluginConfig config = PluginConfigManager.getConfig();
		config.setEnableEncryption(true);
		
		EduBriteRemoteConnector connector = new EduBriteRemoteConnector();
		connector.connect();
		System.out.println("Connected " +connector.isConnected());
	}
}
