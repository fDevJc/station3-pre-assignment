package com.jc.station3assignment.unit.room.presentation;

import static com.jc.station3assignment.config.docs.RestDocsUtils.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
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
import com.jc.station3assignment.authentication.domain.LoginUser;
import com.jc.station3assignment.room.application.RoomService;
import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.ModifyRoomRequestDto;
import com.jc.station3assignment.room.application.dto.response.ModifyRoomResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomIdResponseDto;
import com.jc.station3assignment.room.presentation.RoomController;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.request.ModifyRoomRequest;
import com.jc.station3assignment.unit.room.RoomTestSampleDto;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@AutoConfigureRestDocs
@WebMvcTest({RoomController.class})
public class RoomControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private RoomService roomService;

	@MockBean
	private AuthService authService;

	private static final LoginUser LOGIN_USER = new LoginUser(1L, "test@email.com");

	@Test
	void 로그인사용자는_방을_등록할_수_있다() throws Exception {
		//given
		AddRoomRequest addRoomRequest = RoomTestSampleDto.ADD_ROOM_REQUEST;
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
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));

		//doc
		setPostRoomsDocument(resultActions);
	}

	@Test
	void 로그인사용자는_자신의_방을_수정할_수_있다() throws Exception {
		//given
		ModifyRoomRequest modifyRoomRequest = RoomTestSampleDto.MODIFY_ROOM_REQUEST;
		ModifyRoomResponseDto expectedResponse = RoomTestSampleDto.MODIFY_ROOM_RESPONSE_DTO;

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
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
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

	//Sample Dto

	//Setting RestDocs Document
	private static void setDeleteRoomsDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(document("delete-rooms",
				getRestDocRequest(),
				getRestDocResponse(),
				pathParameters(
					parameterWithName("roomId").description("방 아이디"))));
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
