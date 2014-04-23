package com.edubrite.api.plugins.connector;

import java.util.List;
import java.util.Map;

import com.edubrite.api.plugins.common.Pair;
import com.edubrite.api.plugins.staticdata.RolesEnum;
import com.edubrite.api.plugins.vo.PagedList;

public interface EduBriteConnector {
	public String getEduBriteConnectUrl(String action);
	public boolean connect();
	public void disconnect();
	public void reset();
	public String getTest(String testId);
	public String getTestInstance(String testInstanceId);
	public String getTestList(String groupId);
	
	public String getGroupRef(String groupId);
	public String getTestCollections(String groupNamePattern, PagedList pagination);
	public String getSiteGroupList(String groupNamePattern, PagedList pagination);
	/*
	 * Sync Groups/GroupMembers:
	 * addMemberOperation : add | remove 
	 * role : STUDENT
	 * 
	 * groupUsersMap: Map of <Group Name, List<UserInfo> >
	 * UserInfo: userName|email|firstName lastName
	 * 
	 <b>Example</b>: Add users to two groups. If any user is not specified below and is existing in any group, do not remove them from the group
	 	Map<String, List<String>> groupUsersMap = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("user1|user1@ee.com|Fname1 Lname1");
		list.add("user2|user2@ee.com|Fname2 Lname2");
		list.add("user3|user3@ee.com|Fname3 Lname3");
		groupUsersMap.put("Grp1", list);
		groupUsersMap.put("Grp2", list);
		boolean result = connector.addRemoveGroupAndMembers(groupUsersMap, RolesEnum.STUDENT, "add");
	  
	 <b>Example</b>: Remove those users from group, which are not specified in the call. 
	  Map<String, List<String>> groupUsersMap = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("user1");
		list.add("user2");
		groupUsersMap.put("Grp1", list);
		boolean result = connector.addRemoveGroupAndMembers(groupUsersMap, RolesEnum.STUDENT, "remove");
	  
	 */
	public boolean addRemoveGroupAndMembers(Map<String, List<String>> groupUsersMap, RolesEnum role, String addMemberOperation);
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
	public String getUserDetails(String userName);
	public String updateUserDetails(String userName, String firstName, String lastName, String email);
	public String getUserList(String searchStr, String groupId, Boolean inactive, Boolean enrolled, PagedList pagination);
	public String deactivateUser(String userName);
	public String activateUser(String userName);
	
	public boolean isConnected();
	public void shutdown();
	
	public Pair<Integer, String> getLastCommunicationError();
}
