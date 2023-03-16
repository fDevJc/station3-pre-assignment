package com.jc.station3assignment.config.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.jc.station3assignment.config.authentication.encoder.DefaultPasswordEncoder;
import com.jc.station3assignment.config.authentication.provider.DefaultJwtTokenProvider;

@Configuration
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new DefaultPasswordEncoder();
	}

	@Bean
	public JwtTokenProvider jwtTokenProvider(Environment env) {
		return new DefaultJwtTokenProvider(env.getProperty("security.jwt.secret-key"), Long.parseLong(env.getProperty("security.jwt.expiration")));
	}
}
