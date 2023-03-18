package com.jc.station3assignment.config.authentication.extractor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

import com.jc.station3assignment.config.authentication.AuthenticationExtractor;

public class BearerJwtAuthenticationExtractor implements AuthenticationExtractor {
	private static final String BEARER = "Bearer";

	@Override
	public String extract(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		return authorization.replace(BEARER, "").trim();
	}
}
