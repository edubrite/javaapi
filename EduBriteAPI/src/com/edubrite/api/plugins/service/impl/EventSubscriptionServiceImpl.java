package com.edubrite.api.plugins.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.edubrite.api.plugins.common.StringUtils;
import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;
import com.edubrite.api.plugins.service.EventSubscriptionService;

public class EventSubscriptionServiceImpl extends AbstractApiService implements EventSubscriptionService {

	public EventSubscriptionServiceImpl(EduBriteRemoteConnector eduBriteRemoteConnector) {
		connector = eduBriteRemoteConnector;
	}
	
	@Override
	public String enrollInEvent(String eventId, String userIds, String usernames, String openIds, Map<String, String> customProperties) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		if(StringUtils.isBlankNull(userIds) && StringUtils.isBlankNull(usernames) &&
				StringUtils.isBlankNull(openIds)) {
			//atleast one of userIds / usernames / openIds : needs to be passed
			return null;
		}
		
		if(!StringUtils.isBlankNull(userIds)) {
			parameters.put("ids", userIds);
		}
		if(!StringUtils.isBlankNull(usernames)) {
			parameters.put("usernames", usernames);
		}
		if(!StringUtils.isBlankNull(openIds)) {
			parameters.put("openIds", openIds);
		}
		
		parameters.put("dispatch", "enrollUsersInEvent");
		parameters.put("xml", String.valueOf(true));
	    parameters.put("eventId", eventId );
	    
	    if (customProperties != null && !customProperties.isEmpty()) {
			for (Map.Entry<String, String> entry : customProperties.entrySet()) {
				parameters.put("customUpdPropMap['" + entry.getKey() + "']", entry.getValue());
			}
		}

		String response = connector.invokeApi("enrollmentService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
		
	}

	@Override
	public String createExamTestInstance(String subscriptionId, String testId, boolean isPass, float score, long timeStamp) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "createExamInstance");
		parameters.put("xml", String.valueOf(true));
	    parameters.put("passed", isPass+"");
	    parameters.put("score", score+"");
	    parameters.put("startDate", timeStamp+"");
	    
	    parameters.put("subscriptionId", subscriptionId);
	    parameters.put("testId", testId);


		String response = connector.invokeApi("enrollmentService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}
	
	public String updateEventSubscription(String subscriptionId, Map<String, String> customProperties, boolean markCancel) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "updateEventSubscription");
		parameters.put("xml", String.valueOf(true));
		parameters.put("subscriptionId", subscriptionId);
		if(markCancel) {
			// only "CANCELLED" supported , anything else ignored
			parameters.put("subscriptionStatus", "CANCELLED");
		}
		
	    if (customProperties != null && !customProperties.isEmpty()) {
			for (Map.Entry<String, String> entry : customProperties.entrySet()) {
				parameters.put("customUpdPropMap['" + entry.getKey() + "']", entry.getValue());
			}
		}
		
		String response = connector.invokeApi("enrollmentService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
		
	}

}
