package com.jc.station3assignment.config.authentication;

public interface JwtTokenProvider {
	String createToken(Long id, String email);

	boolean validateToken(String token);

	String getPayload(String token, String key);
}
