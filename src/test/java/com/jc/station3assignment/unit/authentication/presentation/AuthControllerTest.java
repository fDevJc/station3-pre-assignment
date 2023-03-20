package com.jc.station3assignment.unit.authentication.presentation;

import static com.jc.station3assignment.common.docs.AuthRestDocument.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.jc.station3assignment.authentication.application.dto.request.SigninRequestDto;
import com.jc.station3assignment.authentication.application.dto.request.SignupRequestDto;
import com.jc.station3assignment.authentication.application.dto.response.SigninResponseDto;
import com.jc.station3assignment.authentication.application.dto.response.SignupResponseDto;
import com.jc.station3assignment.authentication.presentation.dto.request.SigninRequest;
import com.jc.station3assignment.authentication.presentation.dto.request.SignupRequest;
import com.jc.station3assignment.unit.ControllerTest;

public class AuthControllerTest extends ControllerTest {

	@DisplayName("사용자는 회원가입을 할 수 있다")
	@Test
	void signup() throws Exception {
		//given
		SignupRequest signupRequest = SignupRequest.builder()
			.email("fdevjc@gmail.com")
			.password("password")
			.nickname("nickname")
			.build();

		SignupResponseDto signupResponseDto = SignupResponseDto.builder()
			.id(1L)
			.email("fdevjc@gmail.com")
			.nickname("nickname")
			.build();

		given(authService.signup(any(SignupRequestDto.class)))
			.willReturn(signupResponseDto);

		//when
		ResultActions resultActions = mockMvc.perform(post("/api/v1/signup")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(signupRequest))
			.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(signupResponseDto)));

		//doc
		postSignupDocument(resultActions);
	}

	@DisplayName("사용자는 로그인을 할 수 있다.")
	@Test
	void signin() throws Exception {
		//given
		SigninRequest signinRequest = SigninRequest.builder()
			.email("fdevjc@gmail.com")
			.password("password")
			.build();

		SigninResponseDto signinResponseDto = SigninResponseDto.builder()
			.tokenType("Bearer")
			.tokenValue("AccessToken")
			.build();

		given(authService.signin(any(SigninRequestDto.class)))
			.willReturn(signinResponseDto);

		//when
		ResultActions resultActions = mockMvc.perform(post("/api/v1/signin")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(signinRequest))
			.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(signinResponseDto)));

		//doc
		postSigninDocument(resultActions);
	}
}
