package com.jc.station3assignment.authentication.application;

import org.springframework.stereotype.Service;

import com.jc.station3assignment.authentication.application.dto.request.SigninRequestDto;
import com.jc.station3assignment.authentication.application.dto.request.SignupRequestDto;
import com.jc.station3assignment.authentication.application.dto.response.SigninResponseDto;
import com.jc.station3assignment.authentication.application.dto.response.SignupResponseDto;
import com.jc.station3assignment.user.domain.User;
import com.jc.station3assignment.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
		signupRequestDto.modifyWithEncodedPassword(passwordEncoder.encode(signupRequestDto.getPassword()));

		User user = signupRequestDto.toEntity();
		return SignupResponseDto.of(userRepository.save(user));
	}

	public SigninResponseDto signin(SigninRequestDto signinRequestDto) {
		User user = userRepository.findByEmail(signinRequestDto.getEmail())
			.orElseThrow();

		if (!passwordEncoder.matches(signinRequestDto.getPassword(), user.getPassword().get())) {
			throw new RuntimeException();
		}

		//TODO JWT Token 생성

		return SigninResponseDto.builder()
			.tokenType("Bearer")
			.tokenValue("test")
			.build();
	}
}
