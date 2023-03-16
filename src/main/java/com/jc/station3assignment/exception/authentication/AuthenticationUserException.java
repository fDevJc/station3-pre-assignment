package com.jc.station3assignment.exception.authentication;

import org.springframework.http.HttpStatus;

import com.jc.station3assignment.exception.ApplicationException;

public class AuthenticationUserException extends ApplicationException {
	private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
	private static final String MESSAGE = "아이디 또는 패스워드가 일치하지 않습니다";

	public AuthenticationUserException() {
		super(HTTP_STATUS, MESSAGE);
	}
}
