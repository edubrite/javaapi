package com.edubrite.api.plugins.connector;

import java.util.Map;

public interface EduBriteConnection {
	public String getEduBriteConnectUrl(String action);
	public boolean connect();
	public void disconnect();
	public boolean isConnected();
	public String getSessionId();
	public String getSessionInfo();
	public CommunicationError getLastError();
	public Exception getLastException();
	public String invokeApi(String uri, Map<String, String> parameters);
	public void release();
}
