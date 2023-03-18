package com.jc.station3assignment.config.authentication.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jc.station3assignment.authentication.application.AuthService;
import com.jc.station3assignment.config.authentication.AuthenticationExtractor;
import com.jc.station3assignment.config.authentication.annotation.ForOnlyLoginUser;
import com.jc.station3assignment.exception.authentication.InvalidTokenException;

public class AuthenticationInterceptor implements HandlerInterceptor {
	private final AuthenticationExtractor authenticationExtractor;
	private final AuthService authService;

	public AuthenticationInterceptor(AuthenticationExtractor authenticationExtractor, AuthService authService) {
		this.authenticationExtractor = authenticationExtractor;
		this.authService = authService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		ForOnlyLoginUser forOnlyLoginUser = ((HandlerMethod)handler).getMethodAnnotation(ForOnlyLoginUser.class);
		if (forOnlyLoginUser == null) {
			return true;
		}

		String token = authenticationExtractor.extract(request);
		if (!authService.validateToken(token)) {
			throw new InvalidTokenException();
		}

		return true;
	}
}
