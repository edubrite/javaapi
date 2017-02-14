package com.edubrite.api.plugins.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;
import com.edubrite.api.plugins.service.CourseApiService;
import com.edubrite.api.plugins.vo.PagedList;

public class CourseApiServiceImpl extends AbstractApiService implements CourseApiService {

	public CourseApiServiceImpl(EduBriteRemoteConnector eduBriteRemoteConnector) {
		connector = eduBriteRemoteConnector;
	}

	/**
	 * Fetches list of courses
	 * @param pagination pagination
	 * @return response string
	 */
	@Override
	public String getCourseList(PagedList pagination) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "list");
		parameters.put("view", "ALL");
		parameters.put("xml", String.valueOf(true));
		addPagination(pagination, parameters);
		String response = connector.invokeApi("course.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}
	
	/**
	 * Fetches user course sessions
	 * @param pagination pagination
	 * @param userName userName of the user
	 * @param completed whether to fetch completed courses only
	 * @return response string
	 */
	@Override
	public String getUsersCourseSessionList(PagedList pagination, String userName, boolean completed) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "listCourseSessions");
		parameters.put("viewType", "USER");
		parameters.put("userName", userName);
		if (completed) {
			parameters.put("award", String.valueOf(completed));
		}
		parameters.put("xml", String.valueOf(true));
		addPagination(pagination, parameters);
		String response = connector.invokeApi("program.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Fetches course session list
	 * @param pagination pagination
	 * @param enrolled whether to fetch only enrolled courses
	 * @return response string
	 */
	@Override
	public String getCourseSessionList(PagedList pagination, boolean enrolled) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "listCourseSessions");
		if (enrolled) {
			parameters.put("viewType", "USER");
			// parameters.put("role", "STUDENT");
		} else {
			parameters.put("viewType", "OPEN_TO_ENROLL");
		}
		parameters.put("xml", String.valueOf(true));
		addPagination(pagination, parameters);
		String response = connector.invokeApi("program.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Fetches enrolled course session by id
	 * @param courseSessionId id of the enrolled course
	 * @return response string
	 */
	@Override
	public String enrollCourseSession(String courseSessionId){
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "selfEnrollCourseSession");
		parameters.put("xml", String.valueOf(true));
		parameters.put("id", courseSessionId);
		String response = connector.invokeApi("program.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

}
