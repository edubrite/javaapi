package com.edubrite.api.plugins.service;

import com.edubrite.api.plugins.vo.PagedList;

public interface CourseApiService {
	public String getCourseList(PagedList pagination);
	public String getCourseSessionList(PagedList pagination, boolean enrolled);
	public String getUsersCourseSessionList(PagedList pagination, String userName, boolean completed);
	
	public String enrollCourseSession(String courseSessionId); 
}
