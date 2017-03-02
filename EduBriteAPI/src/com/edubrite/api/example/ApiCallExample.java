package com.edubrite.api.example;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edubrite.api.plugins.common.OltPrivateKey;
import com.edubrite.api.plugins.common.OltPublicKey;
import com.edubrite.api.plugins.common.OltSymmetricKeyHandler;
import com.edubrite.api.plugins.common.Pair;
import com.edubrite.api.plugins.common.PluginConfig;
import com.edubrite.api.plugins.common.PluginConfigManager;
import com.edubrite.api.plugins.connector.EduBriteConnector;
import com.edubrite.api.plugins.connector.EduBriteConnectorFactory;
import com.edubrite.api.plugins.staticdata.ProgramStatus;
import com.edubrite.api.plugins.staticdata.ResponseType;
import com.edubrite.api.plugins.staticdata.RolesEnum;
import com.edubrite.api.plugins.vo.User;

public class ApiCallExample {

	public static void main(String []args)throws Exception{
		PluginConfig config = PluginConfigManager.getConfig();
		config.setEnableEncryption(false);
		Pair<OltPublicKey, OltPrivateKey> keys = OltSymmetricKeyHandler.generateBaseKeyPair();
		Key secretKey = OltSymmetricKeyHandler.generateSecretKey();
		config.setKeys(keys);
		config.setSecretKey64(OltSymmetricKeyHandler.secretKeyToBase64String(secretKey));
		EduBriteConnectorFactory.getInstance().getConnector().disconnect();
		
		EduBriteConnector connector = EduBriteConnectorFactory.getInstance().getConnector();
		connector.connect();
		
		System.out.println("Connected " +connector.isConnected() + "\n");
		PluginConfigManager.getInstance().setApplicationUser(new User("<username>"));
		
		//For xml response
		//connector.setResponseType(ResponseType.XML);
		//For json response
		//connector.setResponseType(ResponseType.JSON);
		
		String response = null;
		
		//user svc
		//response = connector.userSvc().list("<searchStr>", "<groupId>", "<inactive>", "<enrolled>", "<pagination>");
		//response = connector.userSvc().get("<username>");
		//response = connector.userSvc().addToGroups("<username>", "<userrole>", "<groupid>", "[groupid...]");
		//response = connector.userSvc().removeFromGroups("<username>", "<groupid>", "[groupid...]");
		//response = connector.userSvc().deactivate("<username>");
		//response = connector.userSvc().activate("<username>");
		//response = connector.userSvc().getSession();

		//Map<String, String> customProperties = new HashMap<String, String>();
		//customProperties.put("CUSTOM_PROP_1", "VALUE_1");
		//customProperties.put("CUSTOM_PROP_2", "VALUE_2");
		//customProperties.put("CUSTOM_PROP_3", "VALUE_3");
		
		//response = connector.userSvc().create("<username>", "<password>", "<email>", "<firstname>", "<lastname>", <customProperties>);
		//response = connector.userSvc().update("<userName>", "<newfirstName>", "<newlastName>", "<newemail>", "<siteRole>", "<customProperties>");
		
		
		//group svc
		//response = connector.groupSvc().get("<groupid>");
		//response = connector.groupSvc().getSiteGroupList(null, null, null);
		//response = connector.groupSvc().getMyGroupList(null, null, null);
		//response = connector.groupSvc().create("<templateName>", "<parentid>", "<groupName>", "<description>");
		//response = connector.groupSvc().removeByIds(null, "<groupid>");
		//response = connector.groupSvc().removeByNames(null, "<groupname>");
		
		//Map<String, List<String>> groupUsersMap = new HashMap<String, List<String>>();
		//List<String> list = new ArrayList<String>();
		//list.add("user1|user1@ee.com|Fname1 Lname1");
		//list.add("user2|user2@ee.com|Fname2 Lname2");
		//list.add("user3|user3@ee.com|Fname3 Lname3");
		//groupUsersMap.put("Grp1", list);
		//groupUsersMap.put("Grp2", list);
		//response = connector.groupSvc().addRemoveGroupAndMembers(groupUsersMap, RolesEnum.STUDENT, "add");
		
		
		//Map<String, List<String>> groupUsersMap = new HashMap<String, List<String>>();
		//List<String> list = new ArrayList<String>();
		//list.add("conf1");
		//list.add("conf2");
		//groupUsersMap.put("Grp1", list);
		//response = connector.groupSvc().addRemoveGroupAndMembers(groupUsersMap, RolesEnum.STUDENT, "remove");
		
		
		//report svc
		//response = connector.reportSvc().listCourseSessionMembers(null, null, null, null, null, null, null, null, null, null, null, null, null);
		
		/*Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.YEAR, 2017);
		
		response = connector.reportSvc().listProgramMembers(null, null, null, null, null, null, 
				null, new Date(cal.getTimeInMillis()), null, null, null, null, null);
		*/
		
		/*
		//Pagination sample
		PagedList pagination = new PagedList();
		pagination.setPageSize(5);
		pagination.setCurrPage(2);
		response = connector.getSiteGroupList(null, pagination);
		System.out.println(response);
		
		PagedList pagination = new PagedList();
		pagination.setSortColumn("date");
		pagination.setSortAsc(false);
		pagination.setPageSize(10);
		//pagination.setNumItems(1293);
		//pagination.setCurrPage(1);
		response = connector.getUserList(null, null, false, null, pagination);
		System.out.println(response);
		*/
		
		System.out.println(response);
	}	

}
