package com.jc.station3assignment.unit.room.presentation;

import static com.jc.station3assignment.common.docs.RestDocument.*;
import static com.jc.station3assignment.common.factory.RoomDtoFactory.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
	private static final String AUTHORIZATION_HEADER_VALUE = "Bearer Jwt Token";

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
			.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_VALUE)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(addRoomRequest))
			.accept(MediaType.APPLICATION_JSON));

		//then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));

		//doc
		postRoomsDocument(resultActions);
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
			.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_VALUE)
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
		putRoomsDocument(resultActions);
	}

	@Test
	void 로그인사용자는_자신의_방을_삭제할_수_있다() throws Exception {
		//given
		given(authService.validateToken(any()))
			.willReturn(true);
		given(authService.getAuthenticatedLoginUser(any()))
			.willReturn(LOGIN_USER);

		//when
		ResultActions resultActions = mockMvc.perform(delete("/api/v1/rooms/{roomId}", 1L)
			.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_VALUE));

		//then
		resultActions
			.andExpect(status().isOk());

		//doc
		deleteRoomsDocument(resultActions);
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
		ResultActions resultActions = mockMvc.perform(get("/api/v1/rooms/{roomId}/me", 1L)
			.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_VALUE));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));

		//doc
		getRoomMeDocument(resultActions);
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
		ResultActions resultActions = mockMvc.perform(get("/api/v1/rooms/me")
			.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_VALUE));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));

		//doc
		getRoomsMeDocument(resultActions);
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
		ResultActions resultActions = mockMvc.perform(get("/api/v1/rooms")
			.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_VALUE));

		//then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));

		//doc
		getRoomsDocument(resultActions);
	}
}
