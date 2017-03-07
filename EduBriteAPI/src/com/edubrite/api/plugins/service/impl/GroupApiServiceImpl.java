package com.edubrite.api.plugins.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.edubrite.api.plugins.common.StringUtils;
import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;
import com.edubrite.api.plugins.service.GroupApiService;
import com.edubrite.api.plugins.staticdata.RolesEnum;
import com.edubrite.api.plugins.vo.PagedList;

public class GroupApiServiceImpl extends AbstractApiService implements GroupApiService {
	private static final Logger log = Logger.getLogger(GroupApiServiceImpl.class.getName());

	public GroupApiServiceImpl(EduBriteRemoteConnector eduBriteRemoteConnector) {
		connector = eduBriteRemoteConnector;
	}

	/**
	 * Fetches a group by given id
	 * 
	 * @param groupId
	 *            id of the group
	 * @return response string
	 */
	@Override
	public String get(String groupId) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "getGroupRef");
		parameters.put("id", groupId);
		parameters.put("xml", String.valueOf(true));
		String response = connector.invokeApi("group.do", parameters);
		log.debug("Response=" + response);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

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
	@Override
	public String list(String parentId, String groupNamePattern, PagedList pagination) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "list");
		parameters.put("xml", String.valueOf(true));
		if (!StringUtils.isBlankNull(parentId)) {
			parameters.put("parentId", parentId);
		}
		if (!StringUtils.isBlankNull(groupNamePattern)) {
			parameters.put("groupName", groupNamePattern.trim());
		}
		if (pagination != null) {
			parameters.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination.getNumItems()));
		}
		String response = connector.invokeApi("groupService.do", parameters);

		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

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
	@Override
	public String getMyGroupList(String parentId, String groupNamePattern, PagedList pagination) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "listMyGroups");
		parameters.put("xml", String.valueOf(true));
		if (!StringUtils.isBlankNull(parentId)) {
			parameters.put("parentId", parentId);
		}
		if (!StringUtils.isBlankNull(groupNamePattern)) {
			parameters.put("groupName", groupNamePattern.trim());
		}
		if (pagination != null) {
			parameters.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("recordsCount", String.valueOf(pagination.getNumItems()));
		}
		String response = connector.invokeApi("groupService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

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
	@Override
	public String addRemoveGroupAndMembers(Map<String, List<String>> groupUsersMap, RolesEnum role,
			String addMemberOperation) {
		connector.ensureConnection();
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
		String response = connector.invokeApi("group.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

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
	@Override
	public String create(String templateName, String parentId, String groupName, String description) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "createGroupAPI");
		parameters.put("xml", String.valueOf(true));
		parameters.put("templateName", templateName);
		if (!StringUtils.isBlankNull(parentId)) {
			parameters.put("parentId", parentId);
		}
		parameters.put("groupName", groupName);
		parameters.put("description", description);

		String response = connector.invokeApi("group.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Removes group(s) by given id(s)
	 * 
	 * @param groupIds
	 *            id(s) of the group
	 * @return response string
	 */
	@Override
	public String removeByIds(String... groupIds) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "deleteGroupsByIds");
		parameters.put("xml", String.valueOf(true));

		int i = 0;
		for (String groupId : groupIds) {
			parameters.put("groupId" + i, groupId);
			i++;
		}
		String response = connector.invokeApi("group.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Removes group(s) by name(s)
	 * 
	 * @param parentId
	 *            id of the parent group
	 * @param groupNames
	 *            name(s) of the group(s) to be removed
	 * @return response string
	 */
	@Override
	public String removeByNames(String parentId, String... groupNames) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "deleteGroups");
		parameters.put("xml", String.valueOf(true));
		if (parentId != null) {
			parameters.put("parentId", parentId);
		}
		int i = 0;
		for (String groupName : groupNames) {
			parameters.put("groupName" + i, groupName);
			i++;
		}
		String response = connector.invokeApi("group.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}
	
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
			Map<String, String> customProperties){
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "updateGroup");
		parameters.put("xml", String.valueOf(true));
		parameters.put("id", groupId);
		parameters.put("groupName", groupName);
		parameters.put("domain", domain);
		parameters.put("description", description);
		if (customProperties != null && !customProperties.isEmpty()) {
			for (Map.Entry<String, String> entry : customProperties.entrySet()) {
				parameters.put("customUpdPropMap['" + entry.getKey() + "']", entry.getValue());
			}
		}
		
		String response = connector.invokeApi("groupService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param groupId
	 * @param parentId
	 * @param removeNonExclusive
	 * @return response string
	 */
	public String changeParentGroup(String groupId, String parentId, Boolean removeNonExclusive){
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "changeParentGroup");
		parameters.put("xml", String.valueOf(true));
		
		parameters.put("id", groupId);
		parameters.put("parentId", parentId);
		if(removeNonExclusive!=null){
			parameters.put("removeNonExclusive", String.valueOf(removeNonExclusive));
		}
		String response = connector.invokeApi("groupService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}
	
}
