package com.edubrite.api.example.plugins.connector;

import com.edubrite.api.example.plugins.connector.impl.EduBriteRemoteConnector;

public class EduBriteConnectorFactory {
	private static EduBriteConnectorFactory singleton = new EduBriteConnectorFactory();
	
	private final EduBriteConnector connector;
	
	public static EduBriteConnectorFactory getInstance(){
		return singleton;
	}
	
	private EduBriteConnectorFactory() {
		connector = new EduBriteRemoteConnector();	
	}
	
	public EduBriteConnector getConnector(){
		return connector;
	}
}
