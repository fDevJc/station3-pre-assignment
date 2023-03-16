package com.jc.station3assignment.exception.user;

import org.springframework.http.HttpStatus;

import com.jc.station3assignment.exception.ApplicationException;

public class UserNotFoundException extends ApplicationException {
	private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
	private static final String MESSAGE = "사용자를 찾을 수 없습니다.(email: %s)";

	public UserNotFoundException(String userEmail) {
		super(HTTP_STATUS, String.format(MESSAGE, userEmail));
	}
}
