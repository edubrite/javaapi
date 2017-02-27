package com.edubrite.api.plugins.connector.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.edubrite.api.plugins.common.Pair;
import com.edubrite.api.plugins.common.PluginConfig;
import com.edubrite.api.plugins.common.PluginConfigManager;
import com.edubrite.api.plugins.common.ResponseParser;
import com.edubrite.api.plugins.connector.CommunicationError;
import com.edubrite.api.plugins.connector.EduBriteConnection;
import com.edubrite.api.plugins.connector.EduBriteConnector;
import com.edubrite.api.plugins.service.CourseApiService;
import com.edubrite.api.plugins.service.GroupApiService;
import com.edubrite.api.plugins.service.TestApiService;
import com.edubrite.api.plugins.service.UserApiService;
import com.edubrite.api.plugins.service.impl.CourseApiServiceImpl;
import com.edubrite.api.plugins.service.impl.GroupApiServiceImpl;
import com.edubrite.api.plugins.service.impl.TestApiServiceImpl;
import com.edubrite.api.plugins.service.impl.UserApiServiceImpl;
import com.edubrite.api.plugins.staticdata.ResponseType;

public class EduBriteRemoteConnector implements EduBriteConnector {
	private static final Logger log = Logger.getLogger(EduBriteRemoteConnector.class.getName());
	private EduBriteConnection connection;
	private ResponseType responseType = ResponseType.XML;
	
	private EduBriteConnection getConnection() {
		if (connection == null) {
			PluginConfig config = PluginConfigManager.getConfig();
			connection = new EduBriteHttpConnection(config.getUrl(), config.getUserName(), config.getPassword());
		}
		return connection;
	}

	public String getEduBriteConnectUrl(String action) {
		return connection.getEduBriteConnectUrl(action);
	}

	/**
	 * Connects to the remote server and setup authentication.
	 */
	public boolean connect() {
		getConnection();
		if (connection.isConnected()) {
			return true;
		}
		return connection.connect();
	}

	/**
	 * Resets connection
	 */
	public void reset() {
		if (connection != null) {
			connection.disconnect();
			connection = null;
		}
		getConnection();
	}

	/**
	 * Disconnect current connection
	 */
	public void disconnect() {
		getConnection();
		if (connection.isConnected()) {
			connection.disconnect();
		}
	}

	/**
	 * Returns if the connection is live
	 */
	public boolean isConnected() {
		getConnection();
		return connection.isConnected();
	}

	/**
	 * Ensures current connection is live
	 */
	public void ensureConnection() {
		getConnection();
		if (!connection.isConnected()) {
			connect();
		}

		if (!connection.isConnected()) {
			throw new IllegalStateException(connection.getLastError().name());
		}
	}

	/**
	 * Shuts down connection
	 */
	public void shutdown() {
		if (connection.isConnected()) {
			connection.release();
		}
	}

	/**
	 * Fetches last communication error if any
	 */
	public Pair<Integer, String> getLastCommunicationError() {
		getConnection();
		CommunicationError error = connection.getLastError();
		return Pair.of(error.getCode(), error.name());
	}

	/**
	 * Checks if the response had any error
	 * @return whether error exists or not
	 */
	public boolean hasError() {
		log.debug("Error=" + connection.getLastError());
		return connection.getLastError() != null && connection.getLastError() != CommunicationError.NO_ERROR;
	}
	
	public ResponseType getResponseType() {
		return responseType;
	}
	
	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	/**
	 * Invokes given api with the request parameters provided
	 * @param api name of the api
	 * @param parameters parameters to be passed in request
	 * @return response string
	 */
	public String invokeApi(String api, Map<String, String> parameters) {
		String response = connection.invokeApi(api, parameters);
		return ResponseParser.parseResponseByType(getResponseType(), response);
	}

	/**
	 * User specific services
	 */
	@Override
	public UserApiService userSvc() {
		return new UserApiServiceImpl(this);
	}

	/**
	 * Group specific services
	 */
	@Override
	public GroupApiService groupSvc() {
		return new GroupApiServiceImpl(this);
	}

	/**
	 * Test/Exam specific services
	 */
	@Override
	public TestApiService testSvc() {
		return new TestApiServiceImpl(this);
	}

	/**
	 * Course specific services
	 */
	@Override
	public CourseApiService courseSvc() {
		return new CourseApiServiceImpl(this);
	}
}
