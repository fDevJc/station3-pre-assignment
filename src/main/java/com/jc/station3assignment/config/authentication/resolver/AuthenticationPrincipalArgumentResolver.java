package com.jc.station3assignment.config.authentication.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.jc.station3assignment.authentication.application.AuthService;
import com.jc.station3assignment.config.authentication.AuthenticationExtractor;
import com.jc.station3assignment.config.authentication.annotation.Authenticated;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
	private final AuthenticationExtractor authenticationExtractor;
	private final AuthService authService;

	public AuthenticationPrincipalArgumentResolver(AuthenticationExtractor authenticationExtractor, AuthService authService) {
		this.authenticationExtractor = authenticationExtractor;
		this.authService = authService;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(Authenticated.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String token = authenticationExtractor.extract(request);
		return authService.getAuthenticatedLoginUser(token);
	}
}
