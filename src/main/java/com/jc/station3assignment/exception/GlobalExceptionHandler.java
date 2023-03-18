package com.jc.station3assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jc.station3assignment.exception.authentication.AuthenticationUserException;
import com.jc.station3assignment.exception.authentication.InvalidTokenException;
import com.jc.station3assignment.exception.room.PermissionDeniedRoomException;
import com.jc.station3assignment.exception.room.RoomNotFoundException;
import com.jc.station3assignment.exception.user.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final String LOG_FORMAT = "Class : {}, Message : {}";

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
	}

	@ExceptionHandler(PermissionDeniedRoomException.class)
	public ResponseEntity<ErrorResponse> permissionDeniedRoomException(PermissionDeniedRoomException e) {
		log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
		return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
	}

	@ExceptionHandler(RoomNotFoundException.class)
	public ResponseEntity<ErrorResponse> roomNotFoundException(RoomNotFoundException e) {
		log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
		return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> userNotFoundException(UserNotFoundException e) {
		log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
		return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorResponse> invalidTokenException(InvalidTokenException e) {
		log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
		return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
	}

	@ExceptionHandler(AuthenticationUserException.class)
	public ResponseEntity<ErrorResponse> authenticationUserException(AuthenticationUserException e) {
		log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
		return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exception(Exception e) {
		log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
	}

}
