package com.edubrite.api.plugins.service;

import java.util.Map;

import com.edubrite.api.plugins.staticdata.RolesEnum;
import com.edubrite.api.plugins.vo.PagedList;

public interface UserApiService {
	
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
	public String list(String searchStr, String groupId, Boolean inactive, Boolean enrolled, PagedList pagination);

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
	 * @return response string
	 */
	public String create(String userName, String password, String email, String firstName, String lastName);

	/**
	 * Get a specific user by userName
	 * 
	 * @param userName
	 *            userName of the user to be fetched
	 * @return response string
	 */
	public String get(String userName);

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
	public String update(String userName, String firstName, String lastName, String email, RolesEnum siteRole,
			Map<String, String> customProperties);

	/**
	 * Deactivates user from the site
	 * 
	 * @param userName
	 *            userName of the user to be deactivated
	 * @return response string
	 */
	public String deactivate(String userName);

	/**
	 * Activates deactivated user the site
	 * 
	 * @param userName
	 *            userName of the user to be activated
	 * @return response string
	 */
	public String activate(String userName);

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
	public String addToGroups(String userName, RolesEnum groupRole, String... groupIds);

	/**
	 * Removes a user from group(s)
	 * 
	 * @param userName
	 *            userName of the user to be removed from group(s)
	 * @param groupIds
	 *            id(s) of the group from which user needs to be removed
	 * @return response string
	 */
	public String removeFromGroups(String userName, String... groupIds);

	/**
	 * Gets all user sessions
	 * 
	 * @return response string
	 */
	public String getSession();

}
