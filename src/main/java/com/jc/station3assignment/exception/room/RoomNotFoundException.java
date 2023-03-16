package com.jc.station3assignment.exception.room;

import org.springframework.http.HttpStatus;

import com.jc.station3assignment.exception.ApplicationException;

public class RoomNotFoundException extends ApplicationException {
	private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
	private static final String MESSAGE = "방을 찾을 수 없습니다.(Room Id: %d)";

	public RoomNotFoundException(Long roomId) {
		super(HTTP_STATUS, String.format(MESSAGE, roomId));
	}
}
