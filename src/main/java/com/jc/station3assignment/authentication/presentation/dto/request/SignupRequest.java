package com.jc.station3assignment.authentication.presentation.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.jc.station3assignment.exception.ValidErrorMessageConstant;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignupRequest {
	@Email(message = ValidErrorMessageConstant.EMAIL)
	@NotBlank(message = ValidErrorMessageConstant.EMAIL_NOT_BLANK)
	private String email;

	@NotBlank(message = ValidErrorMessageConstant.PASSWORD_NOT_BLANK)
	private String password;

	@NotBlank
	private String nickname;

	private String name;
	private String phoneNumber;
}
