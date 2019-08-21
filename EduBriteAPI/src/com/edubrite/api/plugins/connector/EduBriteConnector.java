package com.edubrite.api.plugins.connector;

import com.edubrite.api.plugins.common.Pair;
import com.edubrite.api.plugins.service.CourseApiService;
import com.edubrite.api.plugins.service.GroupApiService;
import com.edubrite.api.plugins.service.MetadataApiService;
import com.edubrite.api.plugins.service.OpenLearnApiService;
import com.edubrite.api.plugins.service.ReportApiService;
import com.edubrite.api.plugins.service.TestApiService;
import com.edubrite.api.plugins.service.UserApiService;
import com.edubrite.api.plugins.staticdata.ResponseType;

public interface EduBriteConnector {
	public String getEduBriteConnectUrl(String action);
	public boolean connect();
	public void disconnect();
	public void reset();
	public boolean isConnected();
	public void shutdown();
	public Pair<Integer, String> getLastCommunicationError();
	public ResponseType getResponseType();
	public void setResponseType(ResponseType responseType);
	
	public UserApiService userSvc();
	public GroupApiService groupSvc();
	public TestApiService testSvc();
	public CourseApiService courseSvc();
	public ReportApiService reportSvc(String dateFormat);
	public OpenLearnApiService openlearnSvc();
	public MetadataApiService metadataSvc();
	
}
