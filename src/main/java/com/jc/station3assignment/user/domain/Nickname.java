package com.jc.station3assignment.user.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Nickname {
	private static final String DEFAULT_NICKNAME = "닉네임";

	private String nickname;

	public Nickname(String nickname) {
		this.nickname = nickname == null ? DEFAULT_NICKNAME : nickname;
	}

	public String get() {
		return nickname;
	}
}
