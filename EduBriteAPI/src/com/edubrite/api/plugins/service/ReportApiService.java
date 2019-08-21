package com.edubrite.api.plugins.service;

import java.util.Date;

import com.edubrite.api.plugins.staticdata.ProgramStatus;
import com.edubrite.api.plugins.vo.PagedList;

public interface ReportApiService {
	/**
	 * This API can be used to get program members report (Program members
	 * listing). The API is a restrictive one and can be called by an admin of
	 * the site only. Any other user trying to invoke this API would result in
	 * an error being returned by the API.
	 * 
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
	public String listProgramMembers(String userId, String openId, String programId, String child0Id,
			ProgramStatus status, String userNameSearch, Date fromDate, Date toDate, Date compFromDate, Date compToDate,
			Date expFromDate, Date expToDate, PagedList pagination);

	/**
	 * 
	 * This API can be used to get learners report (Course session members
	 * listing). The API is a restrictive one and can be called by an admin of
	 * the site only. Any other user trying to invoke this API would result in
	 * an error being returned by the API.
	 * 
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
	public String listCourseSessionMembers(String userId, String openId, String courseId, String courseSessionId,
			String groupId, Boolean awarded, Boolean started, String userNameSearch, Date fromDate, Date toDate,
			Date awardFromDate, Date awardToDate, PagedList pagination);
	
	/**
	 * This API return enrollment count of course, either enrolled or completed
	 * 
	 * @param fromDate
	 * 			start date (null for current month and year)
	 * @param period      
	 * 			to enable date range
	 * @param toDate     
	 * 			end date of range if period enabled
	 * @param completed   
	 * 			true: completed count, <br/>
	 * 			false: enrolled count
	 * @param notDistinct true: will return all enrollments of each user even if multiple, <br/>
	 * 			false: return only one enrollement of a user
	 * @param expand comma separated list of
	 *		<ul>
	 *			<li><strong>info</strong>: includes course info such as description, access, status etc</li>
	 *			<li><strong>items</strong>: includes count of different type of items in the course such as lessons, tests, scorms etc.</li>
	 *			<li><strong>logo</strong>: includes logo details of the course</li>
	 *		</ul>
	 * @param pagination  <br/>
	 * 			sortColumn : <br/>
	 * 				"1" - for name, <br/>
	 * 				"2" - enrollment count <br/>
	 * 				"3" - for course id, <br/>
	 * 				"4" - completed count <br/>
	 * 				(default: 2)
	 * @return response string
	 */
	public String listCoursesEnrollmentsSummary(Date fromDate, boolean period, Date toDate, boolean completed, boolean notDistinct, String expand, PagedList pagination);
	
	/**
	 * This API return enrollment count of program, either enrolled or completed
	 * 
	 * @param fromDate
	 * 			start date (null for current month and year)
	 * @param period      
	 * 			to enable date range
	 * @param toDate     
	 * 			end date of range if period enabled
	 * @param completed   
	 * 			true: completed count, <br/>
	 * 			false: enrolled count
	 * @param notDistinct true: will return all enrollments of each user even if multiple, <br/>
	 * 			false: return only one enrollement of a user
	 * @param expand comma separated list of
	 *       <ul>
	 *			<li><strong>info</strong>: includes program info such as description, group name etc</li>
	 *			<li><strong>credits</strong>: includes program credits&#39; details such as totla credits, min. credits etc.</li>
	 *			<li><strong>logo</strong>: includes program&#39;s logo details</li>
	 *			<li><strong>courses</strong>:<strong> </strong>includes program&#39;s courses count</li>
	 *			<li><strong>badge</strong>: includes program&#39;s badge details</li>
	 *			<li><strong>customProps</strong>: includes program&#39;s custom properties</li>
	 *		</ul>
	 * @param pagination  <br/>
	 * 			sortColumn : <br/>
	 * 				"1" - for name, <br/>
	 * 				"2" - enrollment count <br/>
	 * 				"3" - for course id, <br/>
	 * 				"4" - completed count <br/>
	 * 				(default: 2)
	 * @return response string
	 */
	public String listProgramsEnrollmentsSummary(Date fromDate, boolean period, Date toDate, boolean completed, boolean notDistinct, String expand, PagedList pagination);
	
}
