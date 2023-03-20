package com.jc.station3assignment.authentication.application;

import static com.jc.station3assignment.config.authentication.JwtTokenProvider.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.station3assignment.authentication.application.dto.request.SigninRequestDto;
import com.jc.station3assignment.authentication.application.dto.request.SignupRequestDto;
import com.jc.station3assignment.authentication.application.dto.response.SigninResponseDto;
import com.jc.station3assignment.authentication.application.dto.response.SignupResponseDto;
import com.jc.station3assignment.authentication.domain.LoginUser;
import com.jc.station3assignment.config.authentication.JwtTokenProvider;
import com.jc.station3assignment.config.authentication.PasswordEncoder;
import com.jc.station3assignment.exception.authentication.AuthenticationUserException;
import com.jc.station3assignment.user.domain.Email;
import com.jc.station3assignment.user.domain.User;
import com.jc.station3assignment.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	private final UserRepository userRepository;

	private static final String BEARER = "Bearer";

	@Transactional
	public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
		String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
		signupRequestDto.modifyWithEncodedPassword(encodedPassword);

		User user = signupRequestDto.toEntity();

		return SignupResponseDto.of(userRepository.save(user));
	}

	public SigninResponseDto signin(SigninRequestDto signinRequestDto) {
		User user = findUserByEmail(new Email(signinRequestDto.getEmail()));
		String encodedPassword = passwordEncoder.encode(signinRequestDto.getPassword());
		user.checkPassword(encodedPassword);

		String jwtToken = jwtTokenProvider.createToken(user.getId(), user.getEmail().get());

		return SigninResponseDto.builder()
			.tokenType(BEARER)
			.tokenValue(jwtToken)
			.build();
	}

	private User findUserByEmail(Email email) {
		return userRepository.findByEmail(email)
			.orElseThrow(AuthenticationUserException::new);
	}

	@Transactional(propagation = Propagation.NEVER)
	public boolean validateToken(String authentication) {
		return jwtTokenProvider.validateToken(authentication);
	}

	@Transactional(propagation = Propagation.NEVER)
	public LoginUser getAuthenticatedLoginUser(String token) {
		String id = jwtTokenProvider.getPayload(token, CLAIM_USER_ID);
		String email = jwtTokenProvider.getPayload(token, CLAIM_USER_EMAIL);

		return new LoginUser(Long.parseLong(id), email);
	}
}
