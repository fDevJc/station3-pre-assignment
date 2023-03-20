package com.jc.station3assignment.common.docs;

import static com.jc.station3assignment.common.docs.RestDocsUtils.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

public class AuthRestDocument {
	public static void postSignupDocument(ResultActions resultActions) throws Exception {
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

	public static void postSigninDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(document("post-signin",
				getRestDocRequest(),
				getRestDocResponse(),
				requestFields(
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드")
				),
				responseFields(
					fieldWithPath("tokenType").type(JsonFieldType.STRING).description("토큰 타입"),
					fieldWithPath("tokenValue").type(JsonFieldType.STRING).description("토큰 값")
				)));
	}
}
