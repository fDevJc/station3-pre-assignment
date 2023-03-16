package com.jc.station3assignment.exception.authentication;

import org.springframework.http.HttpStatus;

import com.jc.station3assignment.exception.ApplicationException;

public class InvalidTokenException extends ApplicationException {
	private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
	private static final String MESSAGE = "토큰 인증 실패";

	public InvalidTokenException() {
		super(HTTP_STATUS, MESSAGE);
	}
}
