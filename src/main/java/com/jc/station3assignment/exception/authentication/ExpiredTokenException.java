package com.jc.station3assignment.exception.authentication;

import org.springframework.http.HttpStatus;

import com.jc.station3assignment.exception.ApplicationException;

public class ExpiredTokenException extends ApplicationException {
	private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
	private static final String MESSAGE = "Token is expired: (%s)";

	public ExpiredTokenException(String message) {
		super(HTTP_STATUS, String.format(MESSAGE, message));
	}
}
