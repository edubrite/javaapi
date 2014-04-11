package com.edubrite.api.example.plugins.staticdata;

public enum RolesEnum {
	STUDENT(1, "Learner"),
	ADMIN(2, "Administrator");
	
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
