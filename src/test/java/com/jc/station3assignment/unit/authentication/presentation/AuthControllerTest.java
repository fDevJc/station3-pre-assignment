package com.jc.station3assignment.unit.authentication.presentation;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jc.station3assignment.authentication.application.AuthService;
import com.jc.station3assignment.authentication.application.dto.request.SignupRequestDto;
import com.jc.station3assignment.authentication.application.dto.response.SignupResponseDto;
import com.jc.station3assignment.authentication.presentation.AuthController;
import com.jc.station3assignment.authentication.presentation.dto.request.SignupRequest;

@WebMvcTest({AuthController.class})
public class AuthControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AuthService authService;

	@DisplayName("사용자는 회원가입을 할 수 있다")
	@Test
	void join() throws Exception {
		//given
		SignupRequest signupRequest = SignupRequest.builder()
			.email("fdevjc@gmail.com")
			.password("password")
			.nickname("nickname")
			.build();

		SignupResponseDto serviceResult = SignupResponseDto.builder().id(1L).build();
		given(authService.signup(any(SignupRequestDto.class)))
			.willReturn(serviceResult);

		//when
		ResultActions resultActions = mockMvc.perform(post("/api/v1/signup")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(signupRequest))
			.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(serviceResult)));

		resultActions.andDo(print());
	}
}
