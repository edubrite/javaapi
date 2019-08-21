package com.edubrite.api.plugins.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.edubrite.api.plugins.common.StringUtils;
import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;
import com.edubrite.api.plugins.service.OpenLearnApiService;
import com.edubrite.api.plugins.vo.Metadata;
import com.edubrite.api.plugins.vo.PagedList;

public class OpenLearnApiServiceImpl extends AbstractApiService implements OpenLearnApiService {

	public OpenLearnApiServiceImpl(EduBriteRemoteConnector eduBriteRemoteConnector) {
		connector = eduBriteRemoteConnector;
	}
	
	@Override
	public String getLessonsSummary(String searchName, Boolean video, Boolean file, Boolean youtube, Boolean vimeo, Metadata metadata, PagedList pagination) {
		connector.ensureConnection();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dispatch", "getLessonsSummary");
		if(!StringUtils.isBlankNull(searchName)) {
			parameters.put("searchName", searchName);
		}
		if(video != null) {
			parameters.put("video", String.valueOf(video));
		}
		if(file != null) {
			parameters.put("file", String.valueOf(file));
		}
		if(youtube != null) {
			parameters.put("utube", String.valueOf(youtube));
		}
		if(vimeo != null) {
			parameters.put("vimeo", String.valueOf(vimeo));
		}
		if(metadata!=null) {
			if(!StringUtils.isBlankNull(metadata.getSubject1())) {
				parameters.put("subject1", metadata.getSubject1());
			}
			if(!StringUtils.isBlankNull(metadata.getSubject2())) {
				parameters.put("subject2", metadata.getSubject2());
			}
			if(!StringUtils.isBlankNull(metadata.getSubject3())) {
				parameters.put("subject3", metadata.getSubject3());
			}
			if(!StringUtils.isBlankNull(metadata.getRegion1())) {
				parameters.put("region1", metadata.getRegion1());
			}
			if(!StringUtils.isBlankNull(metadata.getRegion2())) {
				parameters.put("region2", metadata.getRegion2());
			}
			if(!StringUtils.isBlankNull(metadata.getRegion3())) {
				parameters.put("region3", metadata.getRegion3());
			}
			if(!StringUtils.isBlankNull(metadata.getSkillLevel())) {
				parameters.put("skillLevel", metadata.getSkillLevel());
			}
			if(!StringUtils.isBlankNull(metadata.getExam())) {
				parameters.put("exam", metadata.getExam());
			}
			if(!StringUtils.isBlankNull(metadata.getComplexity())) {
				parameters.put("complexity", metadata.getComplexity());
			}
		}
		addPagination(pagination, parameters);
		
		String response = connector.invokeApi("openlearnService.do", parameters);
		if (!connector.hasError()) {
			return response;
		} else {
			return null;
		}
	}

}
