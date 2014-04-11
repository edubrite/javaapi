package com.edubrite.api.example.plugins.connector;

import java.util.List;
import java.util.Map;

import com.edubrite.api.example.plugins.common.Pair;
import com.edubrite.api.example.plugins.staticdata.RolesEnum;
import com.edubrite.api.example.plugins.vo.PagedList;

public interface EduBriteConnector {
	public String getEduBriteConnectUrl(String action);
	public boolean connect();
	public void disconnect();
	public void reset();
	public String getTest(String testId);
	public String getTestInstance(String testInstanceId);
	public String getTestList(String groupId);
	
	public String getGroupRef(String groupId);
	public String getGroupList(PagedList pagination);
	public String getSiteGroupList(PagedList pagination);
	public boolean addGroups(Map<String, List<String>> groupUsersMap, RolesEnum role, String addMemberOperation);
	public boolean removeGroups(String[] groupNames);
	
	public String getSubscribedExamsList(PagedList pagination);
	public String getNotSubscribedExamsList(PagedList pagination);
	public String subscribe(String eventId); 
	public String getTestInstanceList(PagedList pagination);
	public String getTestInstanceList(PagedList pagination, String userName);
	public String getTestSolution(String testId);
	public String getTestStats(String testId, String eventId, String eventItemId);
	public String getTestQuestionStats(String testId, String eventId, String eventItemId, PagedList pagination);
	public String getTestAttempts(String testId, String eventId, String eventItemId, PagedList pagination);
	public String getAllTestAttempts(PagedList pagination);
	public String getUserSession();
	public String getCourseList(PagedList pagination);
	public String getCourseSessionList(PagedList pagination, boolean enrolled);
	public String getUsersCourseSessionList(PagedList pagination, String userName, boolean completed);
	
	public String enrollCourseSession(String courseSessionId); 
	
	public boolean isConnected();
	public void shutdown();
	
	public Pair<Integer, String> getLastCommunicationError();
}
