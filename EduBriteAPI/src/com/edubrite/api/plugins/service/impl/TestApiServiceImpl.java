package com.edubrite.api.plugins.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.edubrite.api.plugins.common.StringUtils;
import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;
import com.edubrite.api.plugins.service.TestApiService;
import com.edubrite.api.plugins.vo.PagedList;

public class TestApiServiceImpl extends AbstractApiService implements TestApiService {
	private static final Logger log = Logger.getLogger(TestApiServiceImpl.class.getName());

	public TestApiServiceImpl(EduBriteRemoteConnector eduBriteRemoteConnector) {
		connector = eduBriteRemoteConnector;
	}

	/**
	 * Fetches test by given id
	 * 
	 * @param testId
	 *            id of the test
	 * @return response string
	 */
	@Override
	public String getTest(String testId) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "show");
		parameters.put("id", testId);
		String response = connector.invokeApi("testdetailXml.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Fetches test instance by given id
	 * 
	 * @param testInstanceId
	 *            test instance id
	 * @return response string
	 */
	@Override
	public String getTestInstance(String testInstanceId) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "showTestInstance");
		parameters.put("id", testInstanceId);
		String response = connector.invokeApi("testdetailXml.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Fetches test collection of group(s)
	 * 
	 * @param groupNamePattern
	 *            name pattern of the group
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	@Override
	public String getTestCollections(String groupNamePattern, PagedList pagination) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "listMyGroupsWithTests");
		parameters.put("xml", String.valueOf(true));
		if (!StringUtils.isBlankNull(groupNamePattern)) {
			parameters.put("groupName", groupNamePattern.trim());
		}
		if (pagination != null) {
			parameters.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination.getNumItems()));
		}
		String response = connector.invokeApi("group.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Fetches list of subscribed exams
	 * 
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	@Override
	public String getSubscribedExamsList(PagedList pagination) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "eventCalendar");
		parameters.put("xml", String.valueOf(true));
		parameters.put("viewType", "ALL");
		parameters.put("subscriptionType", "SUBSCRIBED");
		parameters.put("listStyle", "CBT");
		parameters.put("showCal", String.valueOf(false));
		addPagination(pagination, parameters);
		String response = connector.invokeApi("event.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Fetches list of exams not subscribed to
	 * 
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	@Override
	public String getNotSubscribedExamsList(PagedList pagination) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "eventCalendar");
		parameters.put("xml", String.valueOf(true));
		parameters.put("viewType", "PRESENT_FUTURE");
		parameters.put("subscriptionType", "NOT_SUBSCRIBED");
		parameters.put("listStyle", "CBT");
		parameters.put("showCal", String.valueOf(false));
		addPagination(pagination, parameters);
		String response = connector.invokeApi("event.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Subscribe to an event
	 * 
	 * @param eventId
	 *            id of the event
	 * @return response string
	 */
	@Override
	public String subscribe(String eventId) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "subscribe");
		parameters.put("xml", String.valueOf(true));
		parameters.put("eventId", eventId);

		String response = connector.invokeApi("event.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Fetches list of test instances
	 * 
	 * @param pagination
	 *            pagination
	 * @param userName
	 *            userName of the user
	 * @return response string
	 */
	@Override
	public String getTestInstanceList(PagedList pagination, String userName) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "list");
		parameters.put("xml", String.valueOf(true));
		if (!StringUtils.isBlankNull(userName)) {
			parameters.put("userName", userName);
		}
		if (pagination != null) {
			parameters.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination.getNumItems()));
		}

		String response = connector.invokeApi("testhistory.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Fetches status of the test
	 * 
	 * @param testId
	 *            id of the test
	 * @param eventId
	 *            id of the event
	 * @param eventItemId
	 *            id of the event item
	 * @return response string
	 */
	@Override
	public String getTestStats(String testId, String eventId, String eventItemId) {
		log.debug("Sending getTestStats");
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("testId", testId);
		if (!StringUtils.isBlankNull(eventId)) {
			parameters.put("eventId", eventId);
		}
		if (!StringUtils.isBlankNull(eventItemId)) {
			parameters.put("eventItemId", eventItemId);
		}
		parameters.put("dispatch", "showStats");
		parameters.put("xml", String.valueOf(true));
		String response = connector.invokeApi("testdetail.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Fetches attempts made for given test
	 * 
	 * @param testId
	 *            id of the test
	 * @param eventId
	 *            id of the event
	 * @param eventItemId
	 *            id of the event item
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	@Override
	public String getTestAttempts(String testId, String eventId, String eventItemId, PagedList pagination) {
		log.debug("Sending getTestAttempts");
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();

		if (!StringUtils.isBlankNull(testId)) {
			parameters.put("id", testId);
		}
		if (!StringUtils.isBlankNull(eventId)) {
			parameters.put("eventId", eventId);
		}
		if (!StringUtils.isBlankNull(eventItemId)) {
			parameters.put("eventItemId", eventItemId);
		}
		parameters.put("dispatch", "getRanks");
		parameters.put("xml", String.valueOf(true));

		if (pagination != null) {
			parameters.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination.getNumItems()));
			parameters.put("numItems", String.valueOf(pagination.getNumItems()));
		}

		String response = connector.invokeApi("testdetail.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Fetches test questions' status
	 * 
	 * @param testId
	 *            id of the test
	 * @param eventId
	 *            id of the event
	 * @param eventItemId
	 *            id of the event item
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	@Override
	public String getTestQuestionStats(String testId, String eventId, String eventItemId, PagedList pagination) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "showTestQuestionStats");
		parameters.put("xml", String.valueOf(true));
		parameters.put("testId", testId);
		if (!StringUtils.isBlankNull(eventId)) {
			parameters.put("eventId", eventId);
		}
		if (!StringUtils.isBlankNull(eventItemId)) {
			parameters.put("eventItemId", eventItemId);
		}

		if (pagination != null) {
			parameters.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination.getNumItems()));
			parameters.put("numItems", String.valueOf(pagination.getNumItems()));
		}

		String response = connector.invokeApi("testdetail.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Fetches all test attempts
	 * 
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	@Override
	public String getAllTestAttempts(PagedList pagination) {
		log.debug("Sending getAllTestAttempts");
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "getRecentlyTaken");
		parameters.put("xml", String.valueOf(true));
		if (pagination != null) {
			parameters.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination.getNumItems()));
			parameters.put("numItems", String.valueOf(pagination.getNumItems()));
		}

		String response = connector.invokeApi("testdetail.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * *NOT IMPLEMENTED* Fetches test list
	 * 
	 * @param groupId
	 *            id of the group
	 * @return response string
	 */
	@Override
	public String getTestList(String groupId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * *NOT IMPLEMENTED* Fetches test solution
	 * 
	 * @param testId
	 *            id of the test
	 * @return response string
	 */
	@Override
	public String getTestSolution(String testId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * *NOT IMPLEMENTED* Fetches test instance list
	 * 
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	@Override
	public String getTestInstanceList(PagedList pagination) {
		// TODO Auto-generated method stub
		return null;
	}

}
