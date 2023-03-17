package com.jc.station3assignment.exception.room;

import org.springframework.http.HttpStatus;

import com.jc.station3assignment.exception.ApplicationException;

public class DealNotFoundException extends ApplicationException {
	private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
	private static final String MESSAGE = "방의 메인 거래유형을 찾을 수 없습니다";

	public DealNotFoundException() {
		super(HTTP_STATUS, MESSAGE);
	}
}
