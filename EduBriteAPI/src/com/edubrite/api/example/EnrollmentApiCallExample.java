package com.edubrite.api.example;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import com.edubrite.api.plugins.common.OltPrivateKey;
import com.edubrite.api.plugins.common.OltPublicKey;
import com.edubrite.api.plugins.common.OltSymmetricKeyHandler;
import com.edubrite.api.plugins.common.Pair;
import com.edubrite.api.plugins.common.PluginConfig;
import com.edubrite.api.plugins.common.PluginConfigManager;
import com.edubrite.api.plugins.connector.EduBriteConnector;
import com.edubrite.api.plugins.connector.EduBriteConnectorFactory;
import com.edubrite.api.plugins.staticdata.ResponseType;
import com.edubrite.api.plugins.vo.User;

public class EnrollmentApiCallExample {

	public static void main(String []args)throws Exception{
		PluginConfig config = PluginConfigManager.getConfig();
		config.setEnableEncryption(false);
		Pair<OltPublicKey, OltPrivateKey> keys = OltSymmetricKeyHandler.generateBaseKeyPair();
		Key secretKey = OltSymmetricKeyHandler.generateSecretKey();
		config.setKeys(keys);
		config.setSecretKey64(OltSymmetricKeyHandler.secretKeyToBase64String(secretKey));
		EduBriteConnectorFactory.getInstance().getConnector().disconnect();
		
		EduBriteConnector connector = EduBriteConnectorFactory.getInstance().getConnector();
		boolean x = connector.connect();
		System.out.println("connected = " + x);
		PluginConfigManager.getInstance().setApplicationUser(new User("agaonker"));
		
		//For xml response
		connector.setResponseType(ResponseType.XML);
		//For json response
		//connector.setResponseType(ResponseType.JSON);
		String response = null;
		
		// code till here is common. Setting up authentication & createing secure connection
		
		// ****************************************************************************************** //

		
		//1. Update event subscription
		
		Map<String, String> customProperties = new HashMap<String, String>();
		customProperties.put("CUSTOM_EV", "YES");
		
		response = connector.evSubSvc().updateEventSubscription("fee0f334-ec9c-11ea-83bb-cd5e6f131324",customProperties,true);
		
		//2. Enroll in event
		String userNames = "learner1";
		Map<String, String> customProperties1 = new HashMap<String, String>();
		customProperties1.put("CUSTOM_EV", "P123");
		//response = connector.evSubSvc().enrollInEvent("cd1cb308-ed73-11ea-977f-0cc47a35250a", null, userNames, null, customProperties1);
		
		//3. ExamTestInstance
		
		//response = connector.evSubSvc().createExamInstance("fee0f334-ec9c-11ea-83bb-cd5e6f131324", "061afbe0-7443-11ea-8609-4a56cf0bcbd7", true, 75.0f,1598952645000L);
		

		
		System.out.println(response);
	}	

}
