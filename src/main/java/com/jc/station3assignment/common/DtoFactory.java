package com.jc.station3assignment.common;

import com.jc.station3assignment.authentication.application.dto.request.SignupRequestDto;
import com.jc.station3assignment.authentication.application.dto.response.SignupResponseDto;
import com.jc.station3assignment.authentication.presentation.dto.request.SignupRequest;
import com.jc.station3assignment.authentication.presentation.dto.response.SignupResponse;

public class DtoFactory {
	public static SignupRequestDto signupRequestDto(SignupRequest signupRequest) {
		return SignupRequestDto.builder()
			.email(signupRequest.getEmail())
			.password(signupRequest.getPassword())
			.nickname(signupRequest.getNickname())
			.name(signupRequest.getName())
			.phoneNumber(signupRequest.getPhoneNumber())
			.build();
	}

	public static SignupResponse signupResponse(SignupResponseDto signupResponseDto) {
		return SignupResponse.builder()
			.id(signupResponseDto.getId())
			.email(signupResponseDto.getEmail())
			.nickname(signupResponseDto.getNickname())
			.build();
	}
}
