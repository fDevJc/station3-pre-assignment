package com.jc.station3assignment.authentication.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SigninResponse {
	private String tokenType;
	private String tokenValue;
}
