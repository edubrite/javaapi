package com.edubrite.api.plugins.service;

import java.util.List;
import java.util.Map;

import com.edubrite.api.plugins.staticdata.RolesEnum;
import com.edubrite.api.plugins.vo.PagedList;

public interface GroupApiService {
	/* New Service */
	/**
	 * Fetches a group by given id
	 * 
	 * @param groupId
	 *            id of the group
	 * @return response string
	 */
	public String get(String groupId);
	
	/**
	 * Fetches all the groups of the site
	 * 
	 * @param parentId
	 *            group's parent id
	 * @param groupNamePattern
	 *            name pattern of the group to filter
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	public String list(String parentId, String groupNamePattern, PagedList pagination);
	
	/**
	 * Fetches user's groups
	 * 
	 * @param parentId
	 *            group's parent id
	 * @param groupNamePattern
	 *            name pattern of the group to filter
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	public String getMyGroupList(String parentId, String groupNamePattern, PagedList pagination);
	
	/* Old Action */
	/**
	 * Creates a new group
	 * 
	 * @param templateName
	 *            name of the template
	 * @param parentId
	 *            parent group id
	 * @param groupName
	 *            name of the group to be created
	 * @param description
	 *            description of the group
	 * @return response string
	 */
	public String create(String templateName, String parentId, String groupName, String description);
	
	/**
	 * Removes group(s) by given id(s)
	 * 
	 * @param groupIds
	 *            id(s) of the group
	 * @return response string
	 */
	public String removeByIds(String... groupIds);

	/**
	 * Removes group(s) by name(s)
	 * 
	 * @param parentId
	 *            id of the parent group
	 * @param groupNames
	 *            name(s) of the group(s) to be removed
	 * @return response string
	 */
	public String removeByNames(String parentId, String... groupNames);
	
	/*
	 * Sync Groups/GroupMembers:
	 * addMemberOperation : add | remove 
	 * role : STUDENT
	 * 
	 * groupUsersMap: Map of <Group Name, List<UserInfo> >
	 * UserInfo: userName|email|firstName lastName
	 * 
	 *<b>Example</b>: Add users to two groups. If any user is not specified below and is existing in any group, do not remove them from the group
	 *	Map<String, List<String>> groupUsersMap = new HashMap<String, List<String>>();
	 *	List<String> list = new ArrayList<String>();
	 *	list.add("user1|user1@ee.com|Fname1 Lname1");
	 *	list.add("user2|user2@ee.com|Fname2 Lname2");
	 *	list.add("user3|user3@ee.com|Fname3 Lname3");
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
	/**
	 * Adds/removes user into/from given group(s)
	 * 
	 * @param groupUsersMap
	 *            map of group and list of users to add/remove
	 * @param role
	 *            group role of the users
	 * @param addMemberOperation
	 *            ad/remove flag
	 * @return response string
	 */
	public String addRemoveGroupAndMembers(Map<String, List<String>> groupUsersMap, RolesEnum role, String addMemberOperation);

	/**
	 * Updated group details
	 * @param groupId id of the group to update
	 * @param groupName new/updated name 
	 * @param domain new/updated domain
	 * @param description new/updated description
	 * @param customProperties new/updated custom properties
	 * @return response string
	 */
	public String update(String groupId, String groupName, String domain, String description,
			Map<String, String> customProperties);

	/**
	 * 
	 * @param groupId
	 * @param parentId
	 * @param removeNonExclusive
	 * @return response string
	 */
	public String changeParentGroup(String groupId, String parentId, Boolean removeNonExclusive);
	
}
