package com.edubrite.api.plugins.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;
import com.edubrite.api.plugins.service.MetadataApiService;

public class MetadataApiServiceImpl extends AbstractApiService implements MetadataApiService {

	public MetadataApiServiceImpl(EduBriteRemoteConnector eduBriteRemoteConnector) {
		connector = eduBriteRemoteConnector;
	}
	
	@Override
	public String getSiteMetadata() {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "getSiteMetadata");
		
		String response = connector.invokeApi("metadataService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

}
