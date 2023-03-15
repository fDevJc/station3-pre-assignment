package com.jc.station3assignment.user.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Nickname {
	private String nickname;

	public Nickname(String nickname) {
		this.nickname = nickname;
	}

	public String get() {
		return nickname;
	}
}
