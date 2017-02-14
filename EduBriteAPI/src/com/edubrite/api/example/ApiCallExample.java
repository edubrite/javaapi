package com.edubrite.api.example;

import java.security.Key;
import java.util.ArrayList;
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
		
		System.out.println("Connected " +connector.isConnected());
		PluginConfigManager.getInstance().setApplicationUser(new User("<userName>"));
		
		String response = null;
		
		//user svc
		//response = connector.userSvc().create("<username>", "<password>", "<email>", "<firstname>", "<lastname>");
		//response = connector.userSvc().list(null, null, null, null, null);
		//response = connector.userSvc().get("<username>");
		//response = connector.userSvc().addToGroups("<username>", "<userrole>", "<groupid>", "[groupid...]");
		//response = connector.userSvc().removeFromGroups("<username>", "<groupid>", "[groupid...]");
		//response = connector.userSvc().deactivate("<username>");
		//response = connector.userSvc().activate("<username>");
		//response = connector.userSvc().getSession();
		//response = connector.userSvc().update("<userName>", "<newfirstName>", "<newlastName>", "<newemail>", "<siteRole>", "<customProperties>");
		
		//Map<String, String> customProperties = new HashMap<String, String>();
		//customProperties.put("READYTALK_ACCESS_CODE", "aaaaa");
		//customProperties.put("READYTALK_PASSCODE", "xxxx");
		//customProperties.put("WEBEX_ID", "xxxx");
		//response = connector.userSvc().update("john", "Jacob", "Doe", "test1234@dd.com", RolesEnum.ADMIN, customProperties);
		
		
		
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
		
		
		System.out.println(response);
		
		/*
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
	}	

}
