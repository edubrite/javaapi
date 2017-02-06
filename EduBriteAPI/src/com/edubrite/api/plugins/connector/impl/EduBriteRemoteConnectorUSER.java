package com.edubrite.api.plugins.connector.impl;

import java.util.HashMap;
import java.util.Map;

import com.edubrite.api.plugins.common.StringUtils;
import com.edubrite.api.plugins.connector.CommunicationError;
import com.edubrite.api.plugins.staticdata.RolesEnum;

public class EduBriteRemoteConnectorUSER extends EduBriteRemoteConnector{
	
	@Override
	/**
	 * Used for creating a user.
	 * @param userName user name
	 * @param password password for the new user
	 * @param email email for the new user
	 * @param firstName first name for the new user
	 * @param lastName last name for the new user
	 * @param siteRole role for the new user in the site
	 * @return Response
	 */
	public String createUser(String userName, String password, String email, String firstName, String lastName, RolesEnum siteRole){
		ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "create");
		parameters.put("xml", String.valueOf(true));
		parameters.put("userName", userName);
		parameters.put("email", email);
		
		if(!StringUtils.isBlankNull(password)){
			parameters.put("parentId", password);
		}
		
		parameters.put("firstName", firstName);
		parameters.put("lastName", lastName);
		parameters.put("siteRole", siteRole.name());
		
		String response = connection.invokeApi("userService.do", parameters);
		
		logDebug("Error=" + connection.getLastError());
		
		if (connection.getLastError() == null || connection.getLastError() == CommunicationError.NO_ERROR) {
			return response;
		} else {
			return null;
		}
	}

}
