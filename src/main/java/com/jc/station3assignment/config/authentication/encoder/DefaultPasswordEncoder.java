package com.jc.station3assignment.config.authentication.encoder;

import com.jc.station3assignment.config.authentication.PasswordEncoder;

public class DefaultPasswordEncoder implements PasswordEncoder {
	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword.toString().equals(encodedPassword);
	}
}
