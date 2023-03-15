package com.jc.station3assignment.authentication.application.dto.response;

import com.jc.station3assignment.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponseDto {
	private Long id;
	private String email;
	private String nickname;

	public static SignupResponseDto of(User user) {
		return SignupResponseDto.builder()
			.id(user.getId())
			.email(user.getEmail().get())
			.nickname(user.getNickname().get())
			.build();
	}
}
