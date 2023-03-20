package com.jc.station3assignment.acceptance;

import static com.jc.station3assignment.acceptance.RoomAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.jc.station3assignment.authentication.presentation.dto.response.SigninResponse;
import com.jc.station3assignment.authentication.presentation.dto.response.SignupResponse;
import com.jc.station3assignment.exception.ErrorResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomIdResponse;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthAcceptanceTest extends AcceptanceTest {
	private static final String EMAIL = "fdevjc@gmail.com";
	private static final String PASSWORD = "password";
	private static final String NICKNAME = "nickname";
	private SigninResponse dummyToken = SigninResponse.builder()
		.tokenType(null)
		.tokenValue(null)
		.build();
	private RoomIdResponse dummyRoomId = new RoomIdResponse(1L);

	@Test
	void 사용자는_회원가입을_할_수_있다() throws Exception {
		SignupResponse ret = 회원가입요청()
			.then()
			.log().all()
			.statusCode(HttpStatus.CREATED.value())
			.extract()
			.as(SignupResponse.class);

		assertThat(ret.getEmail()).isEqualTo("fdevjc@gmail.com");
		assertThat(ret.getNickname()).isEqualTo("nickname");
	}

	@Test
	void 사용자는_회원가입_로그인_후_JWT_토큰을_받아온다() throws Exception {
		//회원가입
		회원가입요청();

		//로그인
		SigninResponse ret = 로그인요청()
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.as(SigninResponse.class);

		assertThat(ret.getTokenType()).isEqualTo("Bearer");
		assertThat(ret.getTokenValue()).isNotNull();
	}

	@Test
	void 토큰이존재하지않는_사용자는_기능을_사용할_수_없다() throws Exception {
		List<Response> allFunctions = List.of(
			내방등록요청(dummyToken),
			내방수정요청(dummyToken, dummyRoomId),
			내방삭제요청(dummyToken, dummyRoomId),
			내방조회요청(dummyToken, dummyRoomId),
			내방목록요청(dummyToken),
			전체방목록요청(dummyToken)
		);

		allFunctions.forEach(response -> response
			.then()
			.log().all()
			.statusCode(HttpStatus.UNAUTHORIZED.value())
			.extract()
			.as(ErrorResponse.class));

	}

	public static Response 회원가입요청() {
		Map<String, String> params = new HashMap<>();
		params.put("email", EMAIL);
		params.put("password", PASSWORD);
		params.put("nickname", NICKNAME);

		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(params)
			.when()
			.post("/api/v1/signup");
	}

	public static Response 로그인요청() {
		Map<String, String> params = new HashMap<>();
		params.put("email", EMAIL);
		params.put("password", PASSWORD);
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(params)
			.when()
			.post("/api/v1/signin");
	}

}
