package com.jc.station3assignment.exception.authentication;

import org.springframework.http.HttpStatus;

import com.jc.station3assignment.exception.ApplicationException;

public class InvalidTokenException extends ApplicationException {
	private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
	private static final String DEFAULT_MESSAGE = "토큰 인증 실패";
	private static final String MESSAGE = "토큰 인증 실패: (%s)";

	public InvalidTokenException(String message) {
		super(HTTP_STATUS, String.format(MESSAGE, message));
	}

	public InvalidTokenException() {
		super(HTTP_STATUS, DEFAULT_MESSAGE);
	}
}
