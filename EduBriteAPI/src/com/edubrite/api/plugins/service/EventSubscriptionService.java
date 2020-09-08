package com.edubrite.api.plugins.service;

import java.util.Map;

public interface EventSubscriptionService {

	public String enrollInEvent(String eventId, String userIds, String usernames, String openIds, Map<String, String> customProperties);
	public String createExamTestInstance(String subscriptionId, String testId, boolean isPass, float score, long timeStamp);
	public String updateEventSubscription(String subscriptionId,Map<String, String> customProperties, boolean markCancel);
}
