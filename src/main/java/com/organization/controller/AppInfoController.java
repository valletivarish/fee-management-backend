package com.organization.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organization.dto.AppInfoResponse;

@RestController
@RequestMapping("/api/config")
public class AppInfoController {

	private static final String DEMO_MODE = "DEMO MODE";
	private static final String PRODUCTION_MODE = "PRODUCTION MODE";

	private final String environmentMode;

	public AppInfoController(
			@Value("${app.environment-mode}") String environmentMode) {
		this.environmentMode = environmentMode == null ? "" : environmentMode.trim();
	}

	@GetMapping("/app-info")
	public AppInfoResponse getAppInfo() {
		String normalized = environmentMode.toUpperCase();
		boolean demoMode = DEMO_MODE.equals(normalized);
		String effectiveMode = DEMO_MODE.equals(normalized) || PRODUCTION_MODE.equals(normalized)
				? normalized
				: "UNKNOWN MODE";
		return new AppInfoResponse(effectiveMode, demoMode);
	}
}

