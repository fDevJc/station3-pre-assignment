package com.jc.station3assignment.config.authentication.extractor;

import javax.servlet.http.HttpServletRequest;

import com.jc.station3assignment.config.authentication.AuthenticationExtractor;

public class BearerJwtAuthenticationExtractor implements AuthenticationExtractor {
	@Override
	public String extract(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}
}
