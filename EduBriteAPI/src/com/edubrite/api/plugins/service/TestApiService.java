package com.edubrite.api.plugins.service;

import com.edubrite.api.plugins.vo.PagedList;

public interface TestApiService {
	public String getTest(String testId);
	public String getTestInstance(String testInstanceId);
	public String getTestCollections(String groupNamePattern, PagedList pagination);
	
	public String getSubscribedExamsList(PagedList pagination);
	public String getNotSubscribedExamsList(PagedList pagination);
	public String subscribe(String eventId); 
	
	public String getTestInstanceList(PagedList pagination, String userName);
	public String getTestStats(String testId, String eventId, String eventItemId);
	public String getTestQuestionStats(String testId, String eventId, String eventItemId, PagedList pagination);
	public String getTestAttempts(String testId, String eventId, String eventItemId, PagedList pagination);
	public String getAllTestAttempts(PagedList pagination);
	
	/*Not Implemented*/
	public String getTestList(String groupId);
	public String getTestSolution(String testId);
	public String getTestInstanceList(PagedList pagination);
}
