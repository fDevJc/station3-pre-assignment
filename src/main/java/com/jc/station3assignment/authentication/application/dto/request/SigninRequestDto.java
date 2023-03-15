package com.jc.station3assignment.authentication.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SigninRequestDto {
	private String email;
	private String password;
}
