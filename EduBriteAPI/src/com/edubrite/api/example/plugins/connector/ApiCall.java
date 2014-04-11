package com.edubrite.api.example.plugins.connector;

import java.util.Date;

import com.edubrite.api.example.plugins.common.StringUtils;


public class ApiCall {
	private String uri;
	private String dispatch;
	private int appTime;
	private final Date timestamp = new Date();
	
	public String getDispatch() {
		return dispatch;
	}

	public String getUri() {
		return uri;
	}
	
	public ApiCall(String uri, String dispatch){
		this.uri = uri;
		if(!StringUtils.isBlankNull(dispatch)){
			this.dispatch = dispatch;
		}
	}
	public int getAppTime() {
		return appTime;
	}

	public void setAppTime(int appTime) {
		this.appTime = appTime;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dispatch == null) ? 0 : dispatch.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApiCall other = (ApiCall) obj;
		if (dispatch == null) {
			if (other.dispatch != null)
				return false;
		} else if (!dispatch.equals(other.dispatch))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
	
	
}
