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
import com.edubrite.api.plugins.connector.EduBriteConnectorFactory;
import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;
import com.edubrite.api.plugins.staticdata.RolesEnum;
import com.edubrite.api.plugins.vo.PagedList;
import com.edubrite.api.plugins.vo.User;

public class ApiCallExample {

	public static void main(String []args)throws Exception{
		PluginConfig config = PluginConfigManager.getConfig();
		config.setEnableEncryption(true);
		Pair<OltPublicKey, OltPrivateKey> keys = OltSymmetricKeyHandler
				.generateBaseKeyPair();
		Key secretKey = OltSymmetricKeyHandler.generateSecretKey();
		config.setKeys(keys);
		config.setSecretKey64(OltSymmetricKeyHandler.secretKeyToBase64String(secretKey));
		EduBriteConnectorFactory.getInstance().getConnector().disconnect();
		
		EduBriteRemoteConnector connector = new EduBriteRemoteConnector();
		connector.connect();
		System.out.println("Connected " +connector.isConnected());
		
		User user = new User();
		user.setName("yourusername");
		PluginConfigManager.getInstance().setApplicationUser(user);
		String response = null;
		/*
		PagedList pagination = new PagedList();
		response = connector.getSiteGroupList("Group Name", pagination);
		System.out.println(response);
		
		
		String response = connector.getUserDetails("ab11");
		System.out.println(response);
		
		
		response = connector.updateUserDetails("ab11", "John", "Doe", "aa1@333.com");
		System.out.println(response);
		
		PagedList pagination = new PagedList();
		pagination.setPageSize(10);
		response = connector.getUserList(null, null, false, null, pagination);
		System.out.println(response);
		
		response = connector.deactivateUser("ab11");
		System.out.println(response);
		
		response = connector.activateUser("ab11");
		System.out.println(response);
		
		Map<String, List<String>> groupUsersMap = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("user1|user1@ee.com|Fname1 Lname1");
		list.add("user2|user2@ee.com|Fname2 Lname2");
		list.add("user3|user3@ee.com|Fname3 Lname3");
		
		groupUsersMap.put("Grp1", list);
		groupUsersMap.put("Grp2", list);
		boolean result = connector.addRemoveGroupAndMembers(groupUsersMap, RolesEnum.STUDENT, "add");
		System.out.println(result);
		
		Map<String, List<String>> groupUsersMap = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("conf1");
		list.add("conf2");
		groupUsersMap.put("Grp1", list);
		boolean result = connector.addRemoveGroupAndMembers(groupUsersMap, RolesEnum.STUDENT, "remove");
		System.out.println(result);
		*/
	}	

}
