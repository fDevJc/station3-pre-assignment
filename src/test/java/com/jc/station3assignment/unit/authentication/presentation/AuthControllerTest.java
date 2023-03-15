package com.jc.station3assignment.unit.authentication.presentation;

import static com.jc.station3assignment.config.docs.RestDocsUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jc.station3assignment.authentication.application.AuthService;
import com.jc.station3assignment.authentication.application.dto.request.SignupRequestDto;
import com.jc.station3assignment.authentication.application.dto.response.SignupResponseDto;
import com.jc.station3assignment.authentication.presentation.AuthController;
import com.jc.station3assignment.authentication.presentation.dto.request.SignupRequest;

@AutoConfigureRestDocs
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

		SignupResponseDto serviceResult = SignupResponseDto.builder()
			.id(1L)
			.email("fdevjc@gmail.com")
			.nickname("nickname")
			.build();

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

		//doc
		resultActions
			.andDo(document("post-signup",
				getRestDocRequest(),
				getRestDocResponse(),
				requestFields(
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
					fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
					fieldWithPath("name").type(JsonFieldType.STRING).optional().description("이름"),
					fieldWithPath("phoneNumber").type(JsonFieldType.STRING).optional().description("휴대폰")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("사용자 아이디(내부)"),
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
				)));
	}
}
