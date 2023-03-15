package com.jc.station3assignment.authentication.presentation.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.jc.station3assignment.exception.ErrorMessageConstant;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SigninRequest {

	@Email(message = ErrorMessageConstant.EMAIL)
	@NotBlank(message = ErrorMessageConstant.EMAIL_NOT_BLANK)
	private String email;

	@NotBlank(message = ErrorMessageConstant.PASSWORD_NOT_BLANK)
	private String password;

}
