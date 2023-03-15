package com.jc.station3assignment.authentication.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponse {
	private Long id;
	private String email;
	private String nickname;
}
