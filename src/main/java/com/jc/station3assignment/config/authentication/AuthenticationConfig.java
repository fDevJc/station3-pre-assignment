package com.jc.station3assignment.config.authentication;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jc.station3assignment.authentication.application.AuthService;
import com.jc.station3assignment.config.authentication.extractor.BearerJwtAuthenticationExtractor;
import com.jc.station3assignment.config.authentication.interceptor.AuthenticationInterceptor;
import com.jc.station3assignment.config.authentication.resolver.AuthenticationPrincipalArgumentResolver;

@Configuration
public class AuthenticationConfig implements WebMvcConfigurer {
	private final AuthService authService;

	public AuthenticationConfig(AuthService authService) {
		this.authService = authService;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor());
	}

	private AuthenticationInterceptor authenticationInterceptor() {
		return new AuthenticationInterceptor(authenticationExtractor(), authService);
	}

	private AuthenticationExtractor authenticationExtractor() {
		return new BearerJwtAuthenticationExtractor();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new AuthenticationPrincipalArgumentResolver(authenticationExtractor(), authService));
	}

}
