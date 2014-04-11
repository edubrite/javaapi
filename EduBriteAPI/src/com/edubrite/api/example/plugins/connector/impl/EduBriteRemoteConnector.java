package com.edubrite.api.example.plugins.connector.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.edubrite.api.example.plugins.PluginConfig;
import com.edubrite.api.example.plugins.PluginConfigManager;
import com.edubrite.api.example.plugins.common.Pair;
import com.edubrite.api.example.plugins.common.StringUtils;
import com.edubrite.api.example.plugins.connector.CommunicationError;
import com.edubrite.api.example.plugins.connector.EduBriteConnection;
import com.edubrite.api.example.plugins.connector.EduBriteConnector;
import com.edubrite.api.example.plugins.staticdata.RolesEnum;
import com.edubrite.api.example.plugins.vo.PagedList;

public class EduBriteRemoteConnector implements EduBriteConnector {
	private static final Logger log = Logger.getLogger(EduBriteRemoteConnector.class.getName());
	private EduBriteConnection connection;
	private static final int PAGE_SIZE = 20;

	public EduBriteRemoteConnector() {
	}

	private EduBriteConnection getConnection() {
		if (connection == null) {
			PluginConfig config = PluginConfigManager.getConfig();
			connection = new EduBriteHttpConnection(config.getUrl(), config
					.getUserName(), config.getPassword());
		}
		return connection;
	}

	public String getEduBriteConnectUrl(String action){
		return connection.getEduBriteConnectUrl(action);
	}
	
	public boolean connect() {
		getConnection();
		if (connection.isConnected()) {
			return true;
		}
		return connection.connect();
	}

	public void reset() {
		if (connection != null) {
			connection.disconnect();
			connection = null;
		}
		getConnection();
	}

	public void disconnect() {
		getConnection();
		if (connection.isConnected()) {
			connection.disconnect();
		}
	}

	public boolean isConnected() {
		getConnection();
		return connection.isConnected();
	}

	private void ensureConnection() {
		getConnection();
		if (!connection.isConnected()) {
			connect();
		}

		if (!connection.isConnected()) {
			throw new IllegalStateException(connection.getLastError().name());
		}
	}

	public String getGroupRef(String groupId){
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "getGroupRef");
		parameters.put("id", groupId);
		String response = connection.invokeApi("group.do", parameters);
		logDebug("Response=" + response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			logDebug("In error blob");
			return response;
		} else {
			return null;
		}
	}
	public String getTest(String testId) {
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "show");
		parameters.put("id", testId);
		String response = connection.invokeApi("testdetailXml.do", parameters);
		logDebug("Response=" + response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			logDebug("In error blob");
			return response;
		} else {
			return null;
		}
	}

	public String getTestInstance(String testInstanceId) {
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "showTestInstance");
		parameters.put("id", testInstanceId);
		String response = connection.invokeApi("testdetailXml.do", parameters);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}

	
	public String getGroupList(PagedList pagination) {
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "listMyGroupsWithTests");
		parameters.put("xml", String.valueOf(true));
		if (pagination != null) {
			parameters
					.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters
					.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters
					.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination
					.getNumItems()));
		}
		String response = connection.invokeApi("group.do", parameters);
		log.debug("**** >> 2 -- " + response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public String getSiteGroupList(PagedList pagination) {
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "listSiteGroups");
		parameters.put("xml", String.valueOf(true));
		if (pagination != null) {
			parameters.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination.getNumItems()));
		}
		String response = connection.invokeApi("group.do", parameters);
		
		if (connection.getLastError() == null || connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public boolean addGroups(Map<String, List<String>> groupUsersMap, RolesEnum role, String addMemberOperation) {
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "createGroups");
		parameters.put("role", role.name());
		parameters.put("addMemberOperation", addMemberOperation);
		parameters.put("xml", String.valueOf(true));
		int i = 0;
		Set<String> groupNames = groupUsersMap.keySet();
		for (String groupName : groupNames) {
			String paramValue = groupName;
			List<String> userNames = groupUsersMap.get(groupName);
			for (String userName : userNames) {
				paramValue += "," + userName;
			}
			parameters.put("groupName" + i, paramValue);
			i++;
		}
		connection.invokeApi("group.do", parameters);
		
		logDebug("Error=" + connection.getLastError());
		
		if (connection.getLastError() == null || connection.getLastError() == CommunicationError.NO_ERROR) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeGroups(String[] groupNames) {
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "deleteGroups");
		parameters.put("xml", String.valueOf(true));
		int i = 0;
		for (String groupName : groupNames) {
			parameters.put("groupName" + i, groupName);
			i++;
		}
		connection.invokeApi("group.do", parameters);
		
		logDebug("Error=" + connection.getLastError());
		
		if (connection.getLastError() == null || connection.getLastError() == CommunicationError.NO_ERROR) {
			return true;
		} else {
			return false;
		}
	}

	public String getSubscribedExamsList(PagedList pagination) {
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "eventCalendar");
		parameters.put("xml", String.valueOf(true));
		parameters.put("viewType", "ALL");
		parameters.put("subscriptionType", "SUBSCRIBED");
		parameters.put("listStyle", "CBT");
		parameters.put("showCal", String.valueOf(false));
		if(pagination != null){
			parameters
					.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters
					.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters
					.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("numItems", String.valueOf(pagination
					.getNumItems()));
		}
		String response = connection.invokeApi("event.do", parameters);
		log.debug("**** >> 2 -- " + response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}

	public String getNotSubscribedExamsList(PagedList pagination) {
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "eventCalendar");
		parameters.put("xml", String.valueOf(true));
		parameters.put("viewType", "PRESENT_FUTURE");
		parameters.put("subscriptionType", "NOT_SUBSCRIBED");
		parameters.put("listStyle", "CBT");
		parameters.put("showCal", String.valueOf(false));
		if(pagination != null){
			parameters
					.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters
					.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters
					.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("numItems", String.valueOf(pagination
					.getNumItems()));
		}
		String response = connection.invokeApi("event.do", parameters);
		log.debug("**** >> 2 -- " + response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public String subscribe(String eventId){
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "subscribe");
		parameters.put("xml", String.valueOf(true));
		parameters.put("eventId", eventId);

		String response = connection.invokeApi("event.do", parameters);
		log.debug("**** >> 2 -- " + response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public String getTestInstanceList(PagedList pagination, String userName){
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "list");
		parameters.put("xml", String.valueOf(true));
		if(!StringUtils.isBlankNull(userName)){
			parameters.put("userName", userName);
		}
		if (pagination != null) {
			parameters
					.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters
					.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters
					.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination
					.getNumItems()));
		}

		String response = connection.invokeApi("testhistory.do", parameters);
		
		log.debug("Response = "+response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}

	public String getTestStats(String testId, String eventId, String eventItemId){
		log.debug("Sending getTestStats");
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("testId", testId);
		if(!StringUtils.isBlankNull(eventId)){
			parameters.put("eventId", eventId);
		}
		if(!StringUtils.isBlankNull(eventItemId)){
			parameters.put("eventItemId", eventItemId);
		}
		parameters.put("dispatch", "showStats");
		parameters.put("xml", String.valueOf(true));
		String response = connection.invokeApi("testdetail.do", parameters);
		log.debug("resp getTestStats = " + response);

		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public String getTestAttempts(String testId, String eventId, String eventItemId, PagedList pagination){
		log.debug("Sending getTestAttempts");
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		
		if(!StringUtils.isBlankNull(testId)){
			parameters.put("id", testId);
		}
		if(!StringUtils.isBlankNull(eventId)){
			parameters.put("eventId", eventId);
		}
		if(!StringUtils.isBlankNull(eventItemId)){
			parameters.put("eventItemId", eventItemId);
		}
		parameters.put("dispatch", "getRanks");
		parameters.put("xml", String.valueOf(true));
		
		if (pagination != null) {
			parameters
					.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters
					.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters
					.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination
					.getNumItems()));
			parameters.put("numItems", String.valueOf(pagination
					.getNumItems()));
		}
		
		String response = connection.invokeApi("testdetail.do", parameters);
		log.debug("resp getTestAttempts = " + response);

		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public String getAllTestAttempts(PagedList pagination){
		log.debug("Sending getAllTestAttempts");
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "getRecentlyTaken");
		parameters.put("xml", String.valueOf(true));
		if (pagination != null) {
			parameters
					.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters
					.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters
					.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination
					.getNumItems()));
			parameters.put("numItems", String.valueOf(pagination
					.getNumItems()));
		}
		
		String response = connection.invokeApi("testdetail.do", parameters);
		log.debug("resp getAllTestAttempts = " + response);

		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public String getTestQuestionStats(String testId, String eventId, String eventItemId, PagedList pagination){
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "showTestQuestionStats");
		parameters.put("xml", String.valueOf(true));
		parameters.put("testId", testId);
		if(!StringUtils.isBlankNull(eventId)){
			parameters.put("eventId", eventId);
		}
		if(!StringUtils.isBlankNull(eventItemId)){
			parameters.put("eventItemId", eventItemId);
		}
		
		if (pagination != null) {
			parameters
					.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters
					.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters
					.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination
					.getNumItems()));
			parameters.put("numItems", String.valueOf(pagination
					.getNumItems()));
		}

		String response = connection.invokeApi("testdetail.do", parameters);
		
		log.debug("Response = "+response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public String getUserSession(){
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "createSessionApi");
		parameters.put("xml", String.valueOf(true));
		String response = connection.invokeApi("signin.do", parameters);
		
		log.debug("Response = "+response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	
	public String getCourseList(PagedList pagination) {
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "list");
		parameters.put("view", "ALL");
		parameters.put("xml", String.valueOf(true));
		if (pagination != null) {
			parameters
					.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters
					.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters
					.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("numItems", String.valueOf(pagination
					.getNumItems()));
		}
		String response = connection.invokeApi("course.do", parameters);
		log.debug("**** >> 2 -- " + response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public String getUsersCourseSessionList(PagedList pagination, String userName, boolean completed) {
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "listCourseSessions");
		parameters.put("viewType", "USER");
		parameters.put("userName", userName);
		if(completed){
			parameters.put("award", String.valueOf(completed));
		}
		parameters.put("xml", String.valueOf(true));
		if (pagination != null) {
			parameters
					.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters
					.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters
					.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("numItems", String.valueOf(pagination
					.getNumItems()));
		}
		String response = connection.invokeApi("program.do", parameters);
		log.debug("**** >> 2 -- " + response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public String getCourseSessionList(PagedList pagination, boolean enrolled) {
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "listCourseSessions");
		if(enrolled){
			parameters.put("viewType", "USER");
			//parameters.put("role", "STUDENT");
		}else{
			parameters.put("viewType", "OPEN_TO_ENROLL");
		}
		parameters.put("xml", String.valueOf(true));
		if (pagination != null) {
			parameters
					.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters
					.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters
					.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("numItems", String.valueOf(pagination
					.getNumItems()));
		}
		String response = connection.invokeApi("program.do", parameters);
		log.debug("**** >> 2 -- " + response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public String enrollCourseSession(String courseSessionId){
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "selfEnrollCourseSession");
		parameters.put("xml", String.valueOf(true));
		parameters.put("id", courseSessionId);

		String response = connection.invokeApi("program.do", parameters);
		log.debug("**** >> 2 -- " + response);
		if (connection.getLastError() == null
				|| connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}
	
	public void shutdown() {
		if (connection.isConnected()) {
			connection.release();
		}
	}
	
	public Pair<Integer, String> getLastCommunicationError() {
		getConnection();
		CommunicationError error = connection.getLastError();
		return Pair.of(error.getCode(), error.name());
	}
	
	private void logDebug(String s){
		System.out.println(s);
	}

	@Override
	public String getTestList(String groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTestInstanceList(PagedList pagination) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTestSolution(String testId) {
		// TODO Auto-generated method stub
		return null;
	}
}
