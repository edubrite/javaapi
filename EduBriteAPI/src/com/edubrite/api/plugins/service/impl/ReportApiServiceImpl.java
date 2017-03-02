package com.edubrite.api.plugins.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.edubrite.api.plugins.common.StringUtils;
import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;
import com.edubrite.api.plugins.service.ReportApiService;
import com.edubrite.api.plugins.staticdata.ProgramStatus;
import com.edubrite.api.plugins.vo.PagedList;

public class ReportApiServiceImpl extends AbstractApiService implements ReportApiService {

	public ReportApiServiceImpl(EduBriteRemoteConnector eduBriteRemoteConnector) {
		connector = eduBriteRemoteConnector;
	}

	/**
	 * @param userId
	 *            return data for a specific user corresponding to specified
	 *            userId
	 * @param openId
	 *            return data for a specific user corresponding to specified
	 *            openId
	 * @param courseId
	 *            for a specific course id
	 * @param courseSessionId
	 *            for a specific course session id
	 * @param groupId
	 *            Group Id of the group for which to fetch the learners data
	 * @param awarded
	 *            empty, true or false, indicates user's completion status in
	 *            course session
	 * @param started
	 *            empty, true or false, indicates user's activity status in
	 *            course session
	 * @param userNameSearch
	 *            matching username, first/last name, email
	 * @param fromDate
	 *            min enrolled/start date of learner in course session
	 * @param toDate
	 *            max enrolled/start date of learner in course session
	 * @param awardFromDate
	 *            min award date (completion date) of learner in course session
	 * @param awardToDate
	 *            max award date (completion date) of learner in course session
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	@Override
	public String listCourseSessionMembers(String userId, String openId, String courseId, String courseSessionId,
			String groupId, Boolean awarded, Boolean started, String userNameSearch, Date fromDate, Date toDate,
			Date awardFromDate, Date awardToDate, PagedList pagination) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "listCourseSessionMembers");
		parameters.put("xml", String.valueOf(true));

		parameters.put("userId", userId);
		parameters.put("openId", openId);
		parameters.put("courseId", courseId);
		parameters.put("courseSessionId", courseSessionId);
		parameters.put("groupId", groupId);
		if (awarded != null) {
			parameters.put("awarded", String.valueOf(awarded));
		}
		if (started != null) {
			parameters.put("started", String.valueOf(started));
		}
		parameters.put("userNameSearch", userNameSearch);
		parameters.put("fromDate", StringUtils.dateToString(fromDate));
		parameters.put("toDate", StringUtils.dateToString(toDate));
		parameters.put("awardFromDate", StringUtils.dateToString(awardFromDate));
		parameters.put("awardToDate", StringUtils.dateToString(awardToDate));

		addPagination(pagination, parameters);
		String response = connector.invokeApi("reportService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * @param userId
	 *            return data for a specific user corresponding to specified
	 *            userId
	 * @param openId
	 *            return data for a specific user corresponding to specified
	 *            openId
	 * @param programId
	 *            for a specific program id
	 * @param child0Id
	 *            Group Id of the group for which to fetch the learners data
	 * @param status
	 *            COMPLETED or ENROLLED indicates user's completion status in
	 *            program
	 * @param userNameSearch
	 *            matching username, first/last name, email
	 * @param fromDate
	 *            min enrolled/start date of learner in program
	 * @param toDate
	 *            max enrolled/start date of learner in program
	 * @param compFromDate
	 *            min award date (completion date) of learner in program
	 * @param compToDate
	 *            max award date (completion date) of learner in program
	 * @param expFromDate
	 *            min award validity expiration date
	 * @param expToDate
	 *            max award validity expiration date
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	@Override
	public String listProgramMembers(String userId, String openId, String programId, String child0Id,
			ProgramStatus status, String userNameSearch, Date fromDate, Date toDate, Date compFromDate, Date compToDate,
			Date expFromDate, Date expToDate, PagedList pagination) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "listProgramMembers");
		parameters.put("xml", String.valueOf(true));

		parameters.put("userId", userId);
		parameters.put("openId", openId);
		parameters.put("programId", programId);
		parameters.put("child0Id", child0Id);
		if (status != null) {
			parameters.put("status", status.toString());
		}
		parameters.put("userNameSearch", userNameSearch);
		parameters.put("fromDate", StringUtils.dateToString(fromDate));
		parameters.put("toDate", StringUtils.dateToString(toDate));
		parameters.put("compFromDate", StringUtils.dateToString(compFromDate));
		parameters.put("compToDate", StringUtils.dateToString(compToDate));
		parameters.put("expFromDate", StringUtils.dateToString(expFromDate));
		parameters.put("expToDate", StringUtils.dateToString(expToDate));

		addPagination(pagination, parameters);
		String response = connector.invokeApi("reportService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

}
