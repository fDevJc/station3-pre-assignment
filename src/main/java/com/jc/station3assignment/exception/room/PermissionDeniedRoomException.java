package com.jc.station3assignment.exception.room;

import org.springframework.http.HttpStatus;

import com.jc.station3assignment.exception.ApplicationException;

public class PermissionDeniedRoomException extends ApplicationException {
	private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
	private static final String MESSAGE = "방의 접근 권한이 없습니다.(Room Id: %s)";

	public PermissionDeniedRoomException(Long roomId) {
		super(HTTP_STATUS, String.format(MESSAGE, roomId));
	}
}
