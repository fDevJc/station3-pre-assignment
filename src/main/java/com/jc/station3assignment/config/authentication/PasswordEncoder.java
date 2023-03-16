package com.jc.station3assignment.config.authentication;

public interface PasswordEncoder {
	String encode(CharSequence rawPassword);

	boolean matches(CharSequence rawPassword, String encodedPassword);
}
