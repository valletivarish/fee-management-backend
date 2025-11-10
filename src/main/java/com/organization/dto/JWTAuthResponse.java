package com.organization.dto;

public class JWTAuthResponse {
	private String accessToken;
	private String tokenType = "Bearer";

	public JWTAuthResponse() {
		super();
	}

	public JWTAuthResponse(String accessToken, String tokenType) {
		super();
		this.accessToken = accessToken;
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

}
