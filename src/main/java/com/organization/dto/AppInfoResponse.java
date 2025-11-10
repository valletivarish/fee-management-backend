package com.organization.dto;

import java.util.List;

public class AppInfoResponse {

	private final String environmentMode;
	private final boolean demoMode;

	public AppInfoResponse(String environmentMode, boolean demoMode) {
		this.environmentMode = environmentMode;
		this.demoMode = demoMode;
	}

	public String getEnvironmentMode() {
		return environmentMode;
	}

	public boolean isDemoMode() {
		return demoMode;
	}
}

