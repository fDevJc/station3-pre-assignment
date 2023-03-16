package com.jc.station3assignment.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {
	private final HttpStatus httpStatus;

	protected ApplicationException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}
}
