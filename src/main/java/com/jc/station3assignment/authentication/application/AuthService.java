package com.jc.station3assignment.authentication.application;

import org.springframework.stereotype.Service;

import com.jc.station3assignment.authentication.application.dto.request.SignupRequestDto;
import com.jc.station3assignment.authentication.application.dto.response.SignupResponseDto;
import com.jc.station3assignment.user.domain.User;
import com.jc.station3assignment.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	private final UserRepository userRepository;

	public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
		User user = signupRequestDto.toEntity();

		return SignupResponseDto.of(userRepository.save(user));
	}
}
