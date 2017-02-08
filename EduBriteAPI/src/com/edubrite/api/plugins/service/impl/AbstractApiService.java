package com.edubrite.api.plugins.service.impl;

import java.util.Map;

import com.edubrite.api.plugins.common.StringUtils;
import com.edubrite.api.plugins.connector.impl.EduBriteRemoteConnector;
import com.edubrite.api.plugins.vo.PagedList;

public abstract class AbstractApiService {
	protected EduBriteRemoteConnector connector;
	private static final int PAGE_SIZE = 20;

	protected void addPagination(PagedList pagination,
			Map<String, String> parameters) {
		if (pagination != null) {
			parameters
					.put("pageSize", String.valueOf(pagination.getPageSize()));
			parameters
					.put("currPage", String.valueOf(pagination.getCurrPage()));
			parameters
					.put("numPages", String.valueOf(pagination.getNumPages()));
			parameters.put("numItems", String.valueOf(pagination
					.getNumItems()));
			if(!StringUtils.isBlankNull(pagination.getSortColumn())){
				parameters.put("sortColumn", String.valueOf(pagination
					.getSortColumn()));
				parameters.put("sortAsc", String.valueOf(pagination
						.isSortAsc()));
			}
		}
	}
}
