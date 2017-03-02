package com.edubrite.api.plugins.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.edubrite.api.plugins.common.StringUtils;
import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;
import com.edubrite.api.plugins.service.UserApiService;
import com.edubrite.api.plugins.staticdata.RolesEnum;
import com.edubrite.api.plugins.vo.PagedList;

/**
 * User specific API service
 */
public class UserApiServiceImpl extends AbstractApiService implements UserApiService {
	public UserApiServiceImpl(EduBriteRemoteConnector eduBriteRemoteConnector) {
		connector = eduBriteRemoteConnector;
	}


	/**
	 * Creates a new user.
	 * 
	 * @param userName
	 *            user name
	 * @param password
	 *            password for the new user
	 * @param email
	 *            email for the new user
	 * @param firstName
	 *            first name for the new user
	 * @param lastName
	 *            last name for the new user
	 * @param siteRole
	 *            role for the new user in the site
	 * @param customProperties
	 *            custom properties map
	 * @return response string
	 */
	@Override
	public String create(String userName, String password, String email, String firstName, String lastName, 
			Map<String, String> customProperties) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "create");
		parameters.put("xml", String.valueOf(true));
		parameters.put("userName", userName);
		parameters.put("email", email);

		if (!StringUtils.isBlankNull(password)) {
			parameters.put("parentId", password);
		}

		if (customProperties != null && !customProperties.isEmpty()) {
			for (Map.Entry<String, String> entry : customProperties.entrySet()) {
				parameters.put("customUpdPropMap['" + entry.getKey() + "']", entry.getValue());
			}
		}
		
		parameters.put("firstName", firstName);
		parameters.put("lastName", lastName);
		String response = connector.invokeApi("userService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Updates user details.
	 * 
	 * @param userName
	 *            userName of the user to be updated
	 * @param firstName
	 *            new first name of the user/ null if not to be updated
	 * @param lastName
	 *            new last name of the user/ null if not to be updated
	 * @param email
	 *            new email of the user/null if not to be updated
	 * @param siteRole
	 *            new site role of the user/null if not to be updated
	 * @param customProperties
	 *            custom properties map
	 * @return response string
	 */
	@Override
	public String update(String userName, String firstName, String lastName, String email, RolesEnum siteRole,
			Map<String, String> customProperties) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "update");
		parameters.put("xml", String.valueOf(true));
		parameters.put("userName", userName);
		parameters.put("firstName", firstName);
		parameters.put("lastName", lastName);
		parameters.put("email", email);
		if (siteRole != null) {
			parameters.put("siteRole", siteRole.name());
		}
		if (customProperties != null && !customProperties.isEmpty()) {
			for (Map.Entry<String, String> entry : customProperties.entrySet()) {
				parameters.put("customUpdPropMap['" + entry.getKey() + "']", entry.getValue());
			}
		}
		String response = connector.invokeApi("userService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * List the users based on given criteria from given group
	 * 
	 * @param searchStr
	 *            search string
	 * @param groupId
	 *            id of the group
	 * @param inactive
	 *            whether inactive(true) or active(false) users to be fetched
	 * @param enrolled
	 *            whether enrolled useres to be fetched
	 * @param pagination
	 *            pagination
	 * @return response string
	 */
	@Override
	public String list(String searchStr, String groupId, Boolean inactive, Boolean enrolled, PagedList pagination) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "list");
		parameters.put("xml", String.valueOf(true));
		if (!StringUtils.isBlankNull(searchStr)) {
			parameters.put("search", searchStr);
		}
		if (!StringUtils.isBlankNull(groupId)) {
			parameters.put("groupId", groupId);
		}
		if (inactive != null) {
			parameters.put("inactive", String.valueOf(inactive));
		}
		if (enrolled != null) {
			parameters.put("activeEnrollment", String.valueOf(enrolled));
		}
		addPagination(pagination, parameters);
		String response = connector.invokeApi("userService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Deactivates user from the site
	 * 
	 * @param userName
	 *            userName of the user to be deactivated
	 * @return response string
	 */
	@Override
	public String deactivate(String userName) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "deactivate");
		parameters.put("xml", String.valueOf(true));
		parameters.put("userName", userName);
		String response = connector.invokeApi("userService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Activates deactivated user the site
	 * 
	 * @param userName
	 *            userName of the user to be activated
	 * @return response string
	 */
	@Override
	public String activate(String userName) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "activate");
		parameters.put("xml", String.valueOf(true));
		parameters.put("userName", userName);
		String response = connector.invokeApi("userService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Get a specific user by userName
	 * 
	 * @param userName
	 *            userName of the user to be fetched
	 * @return response string
	 */
	@Override
	public String get(String userName) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "profile");
		parameters.put("xml", String.valueOf(true));
		parameters.put("userName", userName);
		String response = connector.invokeApi("profile.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Adds a user to group(s) with given role
	 * 
	 * @param userName
	 *            userName of the user to be added in group(s)
	 * @param groupRole
	 *            role of the user in the group
	 * @param groupIds
	 *            id(s) of the group in which user needs to be added
	 * @return response string
	 */
	public String addToGroups(String userName, RolesEnum groupRole, String... groupIds) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "addToGroups");
		parameters.put("xml", String.valueOf(true));
		parameters.put("userName", userName);
		parameters.put("groupRole", groupRole.name());
		StringBuilder groupIdsStr = new StringBuilder();
		for (String id : groupIds) {
			if (groupIdsStr.length() > 0) {
				groupIdsStr.append(",");
			}
			groupIdsStr.append(id);
		}
		parameters.put("groupId", groupIdsStr.toString());
		String response = connector.invokeApi("userService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Removes a user from group(s)
	 * 
	 * @param userName
	 *            userName of the user to be removed from group(s)
	 * @param groupIds
	 *            id(s) of the group from which user needs to be removed
	 * @return response string
	 */
	public String removeFromGroups(String userName, String... groupIds) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "removeFromGroups");
		parameters.put("xml", String.valueOf(true));
		parameters.put("userName", userName);
		StringBuilder groupIdsStr = new StringBuilder();
		for (String id : groupIds) {
			if (groupIdsStr.length() > 0) {
				groupIdsStr.append(",");
			}
			groupIdsStr.append(id);
		}
		parameters.put("groupId", groupIdsStr.toString());
		String response = connector.invokeApi("userService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

	/**
	 * Gets all user sessions
	 * 
	 * @return response string
	 */
	@Override
	public String getSession() {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "createSessionApi");
		parameters.put("xml", String.valueOf(true));
		String response = connector.invokeApi("signin.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}
}
