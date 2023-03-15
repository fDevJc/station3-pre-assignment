package com.jc.station3assignment.authentication.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SigninResponseDto {
	private String tokenType;
	private String tokenValue;
}
