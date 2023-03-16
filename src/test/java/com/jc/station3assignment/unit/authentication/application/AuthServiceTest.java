package com.jc.station3assignment.unit.authentication.application;

import static org.mockito.BDDMockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jc.station3assignment.authentication.application.AuthService;
import com.jc.station3assignment.authentication.application.dto.request.SignupRequestDto;
import com.jc.station3assignment.authentication.application.dto.response.SignupResponseDto;
import com.jc.station3assignment.config.authentication.PasswordEncoder;
import com.jc.station3assignment.user.domain.User;
import com.jc.station3assignment.user.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@InjectMocks
	private AuthService authService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@DisplayName("사용자가 정상 등록된다.")
	@Test
	void signup() throws Exception {
		//given
		SignupRequestDto signupRequestDto = SignupRequestDto.builder()
			.email("test")
			.nickname("password")
			.build();

		User user = User.createSignupUser("test", "password", "nickname", "name", "phoneNumber");

		given(userRepository.save(any(User.class)))
			.willReturn(user);

		given(passwordEncoder.encode(any()))
			.willReturn("password");

		//when
		SignupResponseDto signup = authService.signup(signupRequestDto);

		//then
		Assertions.assertThat(signup.getEmail()).isEqualTo("test");
	}
}
