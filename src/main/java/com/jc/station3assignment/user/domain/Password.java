package com.jc.station3assignment.user.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {
	private String password;

	public Password(String password) {
		this.password = password;
	}
}
