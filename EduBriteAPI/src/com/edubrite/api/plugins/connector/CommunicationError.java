package com.edubrite.api.plugins.connector;

public enum CommunicationError {
	NO_ERROR(0, "Success"),
	BAD_URL(1, "Bad URL"),
	SERVER_NOT_REACHABLE(2, "Server not reachable"),
	BAD_USERNAME_PASSWORD(3, "Bad Username and/or password"),
	UNKNOWN_ERROR(4, "Unknown Error"),
	ENC_DEC_ERROR(5, "Encryption Decryption Error");
	
	private final int code;
	private final String desc;
	
	private CommunicationError(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
