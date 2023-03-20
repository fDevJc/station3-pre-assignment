package com.jc.station3assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final String LOG_FORMAT = "Class : {}, Message : {}";

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		logWarn(e);
		return errorResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ErrorResponse> applicationException(ApplicationException e) {
		logWarn(e);
		return errorResponseEntity(e.getHttpStatus(), e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exception(Exception e) {
		logWarn(e);
		return errorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}

	private static void logWarn(Exception e) {
		log.warn(LOG_FORMAT, e.getClass(), e.getMessage());
	}

	private static ResponseEntity<ErrorResponse> errorResponseEntity(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(new ErrorResponse(message));
	}
}
