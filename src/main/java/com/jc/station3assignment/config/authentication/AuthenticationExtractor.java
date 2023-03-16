package com.jc.station3assignment.config.authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationExtractor {
	String extract(HttpServletRequest request);
}
