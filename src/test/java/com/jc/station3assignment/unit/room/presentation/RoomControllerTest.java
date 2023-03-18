package com.jc.station3assignment.unit.room.presentation;

import static com.jc.station3assignment.config.docs.RestDocsUtils.*;
import static com.jc.station3assignment.unit.room.RoomDtoFactory.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.jc.station3assignment.authentication.domain.LoginUser;
import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.ModifyRoomRequestDto;
import com.jc.station3assignment.room.application.dto.response.RoomIdResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithDealsResponseDto;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.request.ModifyRoomRequest;
import com.jc.station3assignment.room.presentation.dto.response.RoomWithDealsResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomWithMainDealResponse;
import com.jc.station3assignment.unit.ControllerTest;

public class RoomControllerTest extends ControllerTest {
	private static final LoginUser LOGIN_USER = new LoginUser(1L, "test@email.com");

	@Test
	void 로그인사용자는_방을_등록할_수_있다() throws Exception {
		//given
		AddRoomRequest addRoomRequest = ADD_ROOM_REQUEST;
		RoomIdResponseDto expectedResponse = new RoomIdResponseDto(1L);

		given(authService.validateToken(any()))
			.willReturn(true);
		given(authService.getAuthenticatedLoginUser(any()))
			.willReturn(LOGIN_USER);
		given(roomService.addRoom(any(AddRoomRequestDto.class)))
			.willReturn(expectedResponse);

		//when
		ResultActions resultActions = mockMvc.perform(post("/api/v1/rooms")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(addRoomRequest))
			.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));

		//doc
		setPostRoomsDocument(resultActions);
	}

	@Test
	void 로그인사용자는_자신의_방을_수정할_수_있다() throws Exception {
		//given
		ModifyRoomRequest modifyRoomRequest = MODIFY_ROOM_REQUEST;
		RoomWithDealsResponseDto expectedResponse = ROOM_WITH_DEALS_RESPONSE_DTO;

		given(authService.validateToken(any()))
			.willReturn(true);
		given(authService.getAuthenticatedLoginUser(any()))
			.willReturn(LOGIN_USER);
		given(roomService.modifyRoom(any(ModifyRoomRequestDto.class)))
			.willReturn(expectedResponse);

		//when
		ResultActions resultActions = mockMvc.perform(put("/api/v1/rooms/{roomId}", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(modifyRoomRequest))
			.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));

		//doc
		setPutRoomsDocument(resultActions);
	}

	@Test
	void 로그인사용자는_자신의_방을_삭제할_수_있다() throws Exception {
		//given
		given(authService.validateToken(any()))
			.willReturn(true);
		given(authService.getAuthenticatedLoginUser(any()))
			.willReturn(LOGIN_USER);

		//when
		ResultActions resultActions = mockMvc.perform(delete("/api/v1/rooms/{roomId}", 1L));

		//then
		resultActions
			.andExpect(status().isOk());

		//doc
		setDeleteRoomsDocument(resultActions);
	}

	@Test
	void 로그인사용자는_자신의_방의_목록을_조회할_수_있다() throws Exception {
		//given
		List<RoomWithMainDealResponse> expectedResponse = List.of(ROOM_RESPONSE_1, ROOM_RESPONSE_2);

		given(authService.validateToken(any()))
			.willReturn(true);
		given(authService.getAuthenticatedLoginUser(any()))
			.willReturn(LOGIN_USER);
		given(roomService.findMyRooms(any()))
			.willReturn(List.of(ROOM_RESPONSE_DTO_1, ROOM_RESPONSE_DTO_2));

		//when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/rooms/me"));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));

		//doc
		setGetRoomsMeDocument(resultActions);
	}

	@Test
	void 로그인사용자는_자신의_방의_상세를_조회할_수_있다() throws Exception {
		//given
		RoomWithDealsResponse expectedResponse = ROOM_WITH_DEALS_RESPONSE;

		given(authService.validateToken(any()))
			.willReturn(true);
		given(authService.getAuthenticatedLoginUser(any()))
			.willReturn(LOGIN_USER);
		given(roomService.findMyRoom(any()))
			.willReturn(ROOM_WITH_DEALS_RESPONSE_DTO);

		//when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/rooms/{roomId}/me", 1L));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));

		//doc
		setGetRoomMeDocument(resultActions);
	}

	@Test
	void 로그인사용자는_모든_방의_목록을_조회할_수_있다() throws Exception {
		//given
		List<RoomWithMainDealResponse> expectedResponse = List.of(ROOM_RESPONSE_1, ROOM_RESPONSE_2);

		given(authService.validateToken(any()))
			.willReturn(true);
		given(authService.getAuthenticatedLoginUser(any()))
			.willReturn(LOGIN_USER);
		given(roomService.findAllRooms(any()))
			.willReturn(List.of(ROOM_RESPONSE_DTO_1, ROOM_RESPONSE_DTO_2));

		//when
		ResultActions resultActions = mockMvc.perform(get("/api/v1/rooms"));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));

		//doc
		setGetRoomsMeDocument(resultActions);
	}

	//Setting RestDocs Document
	private static void setDeleteRoomsDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(document("delete-rooms",
				getRestDocRequest(),
				getRestDocResponse(),
				pathParameters(
					parameterWithName("roomId").description("방 아이디"))));
	}

	private static void setGetRoomMeDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(document("put-rooms",
				getRestDocRequest(),
				getRestDocResponse(),
				pathParameters(
					parameterWithName("roomId").description("방 아이디")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("방 아이디(내부)"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("방소개 제목"),
					fieldWithPath("roomType").type(JsonFieldType.STRING).description("방 타입"),
					fieldWithPath("deals").type(JsonFieldType.ARRAY).description("거래 정보"),
					fieldWithPath("deals[].dealType").type(JsonFieldType.STRING).description("거래 타입(월세, 전세)"),
					fieldWithPath("deals[].deposit").type(JsonFieldType.NUMBER).description("보증금(만원)"),
					fieldWithPath("deals[].rent").type(JsonFieldType.NUMBER).description("집세(만원)"),
					fieldWithPath("deals[].orderNumber").type(JsonFieldType.NUMBER).description("정렬 순서"))));
	}

	private static void setGetRoomsMeDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(document("get-rooms-me",
				getRestDocRequest(),
				getRestDocResponse(),
				responseFields(
					fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("방 아이디"),
					fieldWithPath("[].title").type(JsonFieldType.STRING).description("방 제목"),
					fieldWithPath("[].roomType").type(JsonFieldType.STRING).description("방 타입"),
					fieldWithPath("[].roomTypeValue").type(JsonFieldType.STRING).description("방 타입 값"),
					fieldWithPath("[].mainDealType").type(JsonFieldType.STRING).description("메인 거래타입"),
					fieldWithPath("[].mainDealTypeValue").type(JsonFieldType.STRING).description("메인 거래타입 값"),
					fieldWithPath("[].deposit").type(JsonFieldType.NUMBER).description("보증금(만원)"),
					fieldWithPath("[].rent").type(JsonFieldType.NUMBER).description("집세(만원)"))));
	}

	private static void setPostRoomsDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(document("post-rooms",
				getRestDocRequest(),
				getRestDocResponse(),
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("방소개 제목"),
					fieldWithPath("roomType").type(JsonFieldType.STRING).description("방 타입"),
					fieldWithPath("deals").type(JsonFieldType.ARRAY).description("거래 정보"),
					fieldWithPath("deals[].dealType").type(JsonFieldType.STRING).description("거래 타입(월세, 전세)"),
					fieldWithPath("deals[].deposit").type(JsonFieldType.NUMBER).description("보증금(만원)"),
					fieldWithPath("deals[].rent").type(JsonFieldType.NUMBER).description("집세(만원)"),
					fieldWithPath("deals[].orderNumber").type(JsonFieldType.NUMBER).description("정렬 순서")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("등록 방 아이디(내부)"))));
	}

	private static void setPutRoomsDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(document("put-rooms",
				getRestDocRequest(),
				getRestDocResponse(),
				pathParameters(
					parameterWithName("roomId").description("방 아이디")
				),
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("방소개 제목"),
					fieldWithPath("roomType").type(JsonFieldType.STRING).description("방 타입"),
					fieldWithPath("deals").type(JsonFieldType.ARRAY).description("거래 정보"),
					fieldWithPath("deals[].dealType").type(JsonFieldType.STRING).description("거래 타입(월세, 전세)"),
					fieldWithPath("deals[].deposit").type(JsonFieldType.NUMBER).description("보증금(만원)"),
					fieldWithPath("deals[].rent").type(JsonFieldType.NUMBER).description("집세(만원)"),
					fieldWithPath("deals[].orderNumber").type(JsonFieldType.NUMBER).description("정렬 순서")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("방 아이디(내부)"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("방소개 제목"),
					fieldWithPath("roomType").type(JsonFieldType.STRING).description("방 타입"),
					fieldWithPath("deals").type(JsonFieldType.ARRAY).description("거래 정보"),
					fieldWithPath("deals[].dealType").type(JsonFieldType.STRING).description("거래 타입(월세, 전세)"),
					fieldWithPath("deals[].deposit").type(JsonFieldType.NUMBER).description("보증금(만원)"),
					fieldWithPath("deals[].rent").type(JsonFieldType.NUMBER).description("집세(만원)"),
					fieldWithPath("deals[].orderNumber").type(JsonFieldType.NUMBER).description("정렬 순서"))));
	}
}
