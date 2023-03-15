package com.jc.station3assignment.authentication.application.dto.request;

import com.jc.station3assignment.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequestDto {
	private String email;
	private String password;
	private String nickname;
	private String name;
	private String phoneNumber;

	public User toEntity() {
		return User.createSignupUser(
			this.email,
			this.password,
			this.nickname,
			this.name,
			this.phoneNumber
		);
	}
}
