package com.jc.station3assignment.common.docs;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

public class RestDocument {
	private static final String AUTHORIZATION_HEADER_VALUE = "Bearer Jwt Token";

	public static void deleteRoomsDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(MockMvcRestDocumentation.document("delete-rooms",
				RestDocsUtils.getRestDocRequest(),
				RestDocsUtils.getRestDocResponse(),
				requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description(AUTHORIZATION_HEADER_VALUE)
				),
				pathParameters(
					parameterWithName("roomId").description("방 아이디"))));
	}

	public static void getRoomMeDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(MockMvcRestDocumentation.document("get-room-me",
				RestDocsUtils.getRestDocRequest(),
				RestDocsUtils.getRestDocResponse(),
				requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description(AUTHORIZATION_HEADER_VALUE)
				),
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

	public static void getRoomsMeDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(MockMvcRestDocumentation.document("get-rooms-me",
				RestDocsUtils.getRestDocRequest(),
				RestDocsUtils.getRestDocResponse(),
				requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description(AUTHORIZATION_HEADER_VALUE)
				),
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

	public static void postRoomsDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(MockMvcRestDocumentation.document("post-rooms",
				RestDocsUtils.getRestDocRequest(),
				RestDocsUtils.getRestDocResponse(),
				requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description(AUTHORIZATION_HEADER_VALUE)
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
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("등록 방 아이디(내부)"))));
	}

	public static void putRoomsDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(MockMvcRestDocumentation.document("put-rooms",
				RestDocsUtils.getRestDocRequest(),
				RestDocsUtils.getRestDocResponse(),
				pathParameters(
					parameterWithName("roomId").description("방 아이디")
				),
				requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description(AUTHORIZATION_HEADER_VALUE)
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

	public static void getRoomsDocument(ResultActions resultActions) throws Exception {
		resultActions
			.andDo(MockMvcRestDocumentation.document("get-rooms",
				RestDocsUtils.getRestDocRequest(),
				RestDocsUtils.getRestDocResponse(),
				requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description(AUTHORIZATION_HEADER_VALUE)
				),
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
}
