package com.edubrite.api.plugins.staticdata;

public enum ProgramStatus {
	COMPLETED("COMPLETED"), ENROLLED("ENROLLED");
	
	private String status;
	
	private ProgramStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return this.status;
	}
}
