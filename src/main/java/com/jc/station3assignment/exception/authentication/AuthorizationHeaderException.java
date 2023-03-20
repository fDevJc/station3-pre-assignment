package com.jc.station3assignment.exception.authentication;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.jc.station3assignment.exception.ApplicationException;

public class AuthorizationHeaderException extends ApplicationException {
	private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
	private static final String MESSAGE = HttpHeaders.AUTHORIZATION + " 헤더 정보가 올바르지 않습니다.";

	public AuthorizationHeaderException() {
		super(HTTP_STATUS, MESSAGE);
	}
}
