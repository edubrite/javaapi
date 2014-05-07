package com.edubrite.api.plugins.staticdata;

public enum RolesEnum {
	STUDENT(1, "Learner"),
	ADMIN(2, "Administrator"),
	INSTRUCTOR(3, "INSTRUCTOR");
	
	private final int id;
	private final String value;
	
	private RolesEnum(int id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

	public int getId() {
		return id;
	}
}
