package com.jc.station3assignment.acceptance;

import static com.jc.station3assignment.acceptance.AuthAcceptanceTest.*;
import static com.jc.station3assignment.common.factory.RoomDtoFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.jc.station3assignment.authentication.presentation.dto.response.SigninResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomIdResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomWithDealsResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomWithMainDealResponse;

import io.restassured.RestAssured;
import io.restassured.mapper.TypeRef;
import io.restassured.response.Response;

public class RoomAcceptanceTest extends AcceptanceTest {
	@Test
	void 로그인된_사용자는_내방을_등록할_수_있다() throws Exception {
		회원가입요청();

		SigninResponse token = 로그인요청().then().extract().as(SigninResponse.class);

		RoomIdResponse ret = 내방등록요청(token)
			.then()
			.log().all()
			.statusCode(HttpStatus.CREATED.value())
			.extract()
			.as(RoomIdResponse.class);

		assertThat(ret.getId()).isNotNull();
	}

	public static Response 내방등록요청(SigninResponse token) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", "Bearer " + token.getTokenValue())
			.body(ADD_ROOM_REQUEST)
			.when()
			.post("/api/v1/rooms");
	}

	@Test
	void 로그인된_사용자는_내방을_수정할_수_있다() throws Exception {
		회원가입요청();

		SigninResponse token = 로그인요청().then().extract().as(SigninResponse.class);

		RoomIdResponse roomId = 내방등록요청(token).then().extract().as(RoomIdResponse.class);

		RoomWithDealsResponse ret = 내방수정요청(token, roomId)
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.as(RoomWithDealsResponse.class);

		assertThat(ret.getId()).isNotNull();
		assertThat(ret.getTitle()).isNotNull();
		assertThat(ret.getRoomType()).isNotNull();
		assertThat(ret.getDeals()).isNotNull();
	}

	public static Response 내방수정요청(SigninResponse token, RoomIdResponse roomId) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", "Bearer " + token.getTokenValue())
			.body(MODIFY_ROOM_REQUEST)
			.when()
			.put("/api/v1/rooms/{roomId}", roomId.getId());
	}

	@Test
	void 로그인된_사용자는_내방을_삭제할_수_있다() throws Exception {
		회원가입요청();

		SigninResponse token = 로그인요청().then().extract().as(SigninResponse.class);

		RoomIdResponse roomId = 내방등록요청(token).then().extract().as(RoomIdResponse.class);

		내방삭제요청(token, roomId)
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value());
	}

	public static Response 내방삭제요청(SigninResponse token, RoomIdResponse roomId) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", "Bearer " + token.getTokenValue())
			.when()
			.delete("/api/v1/rooms/{roomId}", roomId.getId());
	}

	@Test
	void 로그인된_사용자는_내방을_하나_조회할_수_있다() throws Exception {
		회원가입요청();

		SigninResponse token = 로그인요청().then().extract().as(SigninResponse.class);

		RoomIdResponse roomId = 내방등록요청(token).then().extract().as(RoomIdResponse.class);

		RoomWithDealsResponse ret = 내방조회요청(token, roomId)
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.as(RoomWithDealsResponse.class);

		assertThat(ret.getId()).isNotNull();
		assertThat(ret.getTitle()).isNotNull();
		assertThat(ret.getRoomType()).isNotNull();
		assertThat(ret.getDeals()).isNotNull();
	}

	public static Response 내방조회요청(SigninResponse token, RoomIdResponse roomId) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", "Bearer " + token.getTokenValue())
			.when()
			.get("/api/v1/rooms/{roomId}/me", roomId.getId());
	}

	@Test
	void 로그인된_사용자는_내방_목록을_조회할_수_있다() throws Exception {
		회원가입요청();

		SigninResponse token = 로그인요청().then().extract().as(SigninResponse.class);

		내방등록요청(token).then().extract().as(RoomIdResponse.class);

		List<RoomWithMainDealResponse> ret = 내방목록요청(token)
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.as(new TypeRef<>() {
			});

		assertThat(ret.size()).isEqualTo(1);
	}

	public static Response 내방목록요청(SigninResponse token) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", "Bearer " + token.getTokenValue())
			.when()
			.get("/api/v1/rooms/me");
	}

	@Test
	void 로그인된_사용자는_전체방_목록을_조회할_수_있다() throws Exception {
		회원가입요청();

		SigninResponse token = 로그인요청().then().extract().as(SigninResponse.class);

		내방등록요청(token).then().extract().as(RoomIdResponse.class);

		List<RoomWithMainDealResponse> ret = 전체방목록요청(token)
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.as(new TypeRef<>() {
			});

		assertThat(ret.size()).isEqualTo(1);
	}

	public static Response 전체방목록요청(SigninResponse token) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", "Bearer " + token.getTokenValue())
			.when()
			.get("/api/v1/rooms");
	}

}
