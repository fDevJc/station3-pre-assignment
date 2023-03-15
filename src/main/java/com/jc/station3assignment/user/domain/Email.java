package com.jc.station3assignment.user.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {
	private String email;

	public Email(String email) {
		this.email = email;
	}

	public String get() {
		return email;
	}
}
