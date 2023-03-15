package com.jc.station3assignment.authentication.presentation;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jc.station3assignment.authentication.application.AuthService;
import com.jc.station3assignment.authentication.application.dto.request.SigninRequestDto;
import com.jc.station3assignment.authentication.application.dto.request.SignupRequestDto;
import com.jc.station3assignment.authentication.application.dto.response.SigninResponseDto;
import com.jc.station3assignment.authentication.application.dto.response.SignupResponseDto;
import com.jc.station3assignment.authentication.presentation.dto.request.SigninRequest;
import com.jc.station3assignment.authentication.presentation.dto.request.SignupRequest;
import com.jc.station3assignment.authentication.presentation.dto.response.SigninResponse;
import com.jc.station3assignment.authentication.presentation.dto.response.SignupResponse;
import com.jc.station3assignment.common.DtoFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {

		SignupRequestDto signupRequestDto = DtoFactory.signupRequestDto(signupRequest);
		SignupResponseDto signupResponseDto = authService.signup(signupRequestDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(DtoFactory.signupResponse(signupResponseDto));
	}

	@PostMapping("/signin")
	public ResponseEntity<SigninResponse> signin(@Valid @RequestBody SigninRequest signinRequest) {

		SigninRequestDto signinRequestDto = DtoFactory.signinRequestDto(signinRequest);
		SigninResponseDto signinResponseDto = authService.signin(signinRequestDto);

		return ResponseEntity.status(HttpStatus.OK).body(DtoFactory.signinResponseDto(signinResponseDto));
	}
}
