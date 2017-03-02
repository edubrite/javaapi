package com.edubrite.api.plugins.staticdata;

public enum GroupMembershipTypeEnum {
	OPEN("Open to all"),
	REQUEST("By request"),
	INVITATION("By invitation");

	private final String value;

	private GroupMembershipTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
