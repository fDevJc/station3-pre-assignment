package com.jc.station3assignment.unit.room.presentation;

import static com.jc.station3assignment.config.docs.RestDocsUtils.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

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
import com.jc.station3assignment.room.domain.RoomType;
import com.jc.station3assignment.room.domain.deal.DealType;
import com.jc.station3assignment.room.presentation.RoomController;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.request.ModifyRoomRequest;

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

	@Test
	void 로그인사용자는_방을_등록할_수_있다() throws Exception {
		//given
		AddRoomRequest.AddRoomDealRequest longTermRentDealRequest = AddRoomRequest.AddRoomDealRequest.builder()
			.dealType(DealType.LONG_TERM_RENT.name())
			.deposit(1000)
			.orderNumber(1)
			.build();

		AddRoomRequest.AddRoomDealRequest monthlyRentDealRequest = AddRoomRequest.AddRoomDealRequest.builder()
			.dealType(DealType.MONTHLY_RENT.name())
			.deposit(500)
			.rent(50)
			.orderNumber(2)
			.build();

		AddRoomRequest addRoomRequest = AddRoomRequest.builder()
			.title("좋은집 입니다")
			.roomType(RoomType.ONE_ROOM.name())
			.deals(List.of(longTermRentDealRequest, monthlyRentDealRequest))
			.build();

		RoomIdResponseDto expectedResponse = new RoomIdResponseDto(1L);

		given(authService.validateToken(any()))
			.willReturn(true);
		given(authService.getAuthenticatedLoginUser(any()))
			.willReturn(new LoginUser(1L, "test@email.com"));
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
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("등록 방 아이디(내부)")
				)));
	}

	@Test
	void 로그인사용자는_자신의_방을_수정할_수_있다() throws Exception {
		//given
		ModifyRoomRequest.ModifyRoomDealRequest longTermRentDealRequest = ModifyRoomRequest.ModifyRoomDealRequest.builder()
			.dealType(DealType.LONG_TERM_RENT.name())
			.deposit(1200)
			.orderNumber(2)
			.build();

		ModifyRoomRequest.ModifyRoomDealRequest monthlyRentDealRequest = ModifyRoomRequest.ModifyRoomDealRequest.builder()
			.dealType(DealType.MONTHLY_RENT.name())
			.deposit(700)
			.rent(70)
			.orderNumber(1)
			.build();

		ModifyRoomRequest modifyRoomRequest = ModifyRoomRequest.builder()
			.title("더 좋은집 입니다")
			.roomType(RoomType.ONE_ROOM.name())
			.deals(List.of(longTermRentDealRequest, monthlyRentDealRequest))
			.build();

		ModifyRoomResponseDto expectedResponse = ModifyRoomResponseDto.builder()
			.id(1L)
			.title("111")
			.roomType(RoomType.ONE_ROOM.name())
			.deals(List.of(ModifyRoomResponseDto.ModifyRoomDealResponseDto.builder().dealType(DealType.MONTHLY_RENT.name()).build()))
			.build();

		given(authService.validateToken(any()))
			.willReturn(true);
		given(authService.getAuthenticatedLoginUser(any()))
			.willReturn(new LoginUser(1L, "test@email.com"));

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
		resultActions
			.andDo(document("put-rooms",
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
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("방 아이디(내부)"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("방소개 제목"),
					fieldWithPath("roomType").type(JsonFieldType.STRING).description("방 타입"),
					fieldWithPath("deals").type(JsonFieldType.ARRAY).description("거래 정보"),
					fieldWithPath("deals[].dealType").type(JsonFieldType.STRING).description("거래 타입(월세, 전세)"),
					fieldWithPath("deals[].deposit").type(JsonFieldType.NUMBER).description("보증금(만원)"),
					fieldWithPath("deals[].rent").type(JsonFieldType.NUMBER).description("집세(만원)"),
					fieldWithPath("deals[].orderNumber").type(JsonFieldType.NUMBER).description("정렬 순서")
				)));
	}
}
