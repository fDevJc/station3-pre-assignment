package com.jc.station3assignment.unit.room;

import java.util.List;

import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.DealRequestDto;
import com.jc.station3assignment.room.application.dto.request.ModifyRoomRequestDto;
import com.jc.station3assignment.room.application.dto.response.DealResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithDealsResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithMainDealResponseDto;
import com.jc.station3assignment.room.domain.RoomType;
import com.jc.station3assignment.room.domain.deal.DealType;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.request.DealRequest;
import com.jc.station3assignment.room.presentation.dto.request.ModifyRoomRequest;
import com.jc.station3assignment.room.presentation.dto.response.DealResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomWithDealsResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomWithMainDealResponse;

public class RoomDtoFactory {
	private static final String DEFAULT_ROOM_TITLE = "좋은집";
	private static final int DEFAULT_LONG_TERM_DEPOSIT = 10000;
	private static final int DEFAULT_MONTHLY_DEPOSIT = 1000;

	public static final DealRequest DEAL_REQUEST_LONG_TERM = DealRequest.builder()
		.dealType(DealType.LONG_TERM_RENT.name())
		.deposit(DEFAULT_LONG_TERM_DEPOSIT)
		.orderNumber(1)
		.build();

	public static final DealRequest DEAL_REQUEST_MONTHLY = DealRequest.builder()
		.dealType(DealType.MONTHLY_RENT.name())
		.deposit(DEFAULT_MONTHLY_DEPOSIT)
		.rent(50)
		.orderNumber(2)
		.build();

	public static final AddRoomRequest ADD_ROOM_REQUEST = AddRoomRequest.builder()
		.title(DEFAULT_ROOM_TITLE)
		.roomType(RoomType.ONE_ROOM.name())
		.deals(List.of(DEAL_REQUEST_LONG_TERM, DEAL_REQUEST_MONTHLY))
		.build();

	public static final DealResponseDto DEAL_RESPONSE_DTO = DealResponseDto.builder()
		.dealType(DealType.MONTHLY_RENT.name())
		.build();

	public static final RoomWithDealsResponseDto ROOM_WITH_DEALS_RESPONSE_DTO = RoomWithDealsResponseDto.builder()
		.id(1L)
		.title(DEFAULT_ROOM_TITLE)
		.roomType(RoomType.ONE_ROOM.name())
		.deals(List.of(DEAL_RESPONSE_DTO))
		.build();

	public static final ModifyRoomRequest MODIFY_ROOM_REQUEST = ModifyRoomRequest.builder()
		.title(DEFAULT_ROOM_TITLE)
		.roomType(RoomType.ONE_ROOM.name())
		.deals(List.of(DEAL_REQUEST_LONG_TERM, DEAL_REQUEST_MONTHLY))
		.build();

	private static final DealRequestDto DEAL_REQUEST_DTO_LONG_TERM = DealRequestDto.builder()
		.dealType(DealType.LONG_TERM_RENT.name())
		.deposit(DEFAULT_LONG_TERM_DEPOSIT)
		.orderNumber(1)
		.build();

	private static final DealRequestDto DEAL_REQUEST_DTO_MONTHLY = DealRequestDto.builder()
		.dealType(DealType.MONTHLY_RENT.name())
		.deposit(DEFAULT_MONTHLY_DEPOSIT)
		.rent(50)
		.orderNumber(2)
		.build();

	public static final AddRoomRequestDto ADD_ROOM_REQUEST_DTO = AddRoomRequestDto.builder()
		.title(DEFAULT_ROOM_TITLE)
		.roomType(RoomType.ONE_ROOM.name())
		.deals(List.of(DEAL_REQUEST_DTO_LONG_TERM, DEAL_REQUEST_DTO_MONTHLY))
		.build();

	public static final ModifyRoomRequestDto MODIFY_ROOM_REQUEST_DTO = ModifyRoomRequestDto.builder()
		.userId(1L)
		.roomId(1L)
		.title(DEFAULT_ROOM_TITLE)
		.roomType(RoomType.ONE_ROOM.name())
		.deals(List.of(DEAL_REQUEST_DTO_LONG_TERM, DEAL_REQUEST_DTO_MONTHLY))
		.build();

	public static final RoomWithMainDealResponse ROOM_RESPONSE_1 = RoomWithMainDealResponse.builder()
		.id(1L)
		.title(DEFAULT_ROOM_TITLE)
		.roomType(RoomType.ONE_ROOM.name())
		.roomTypeValue(RoomType.ONE_ROOM.value())
		.mainDealType(DealType.LONG_TERM_RENT.name())
		.mainDealTypeValue(DealType.LONG_TERM_RENT.value())
		.deposit(DEFAULT_LONG_TERM_DEPOSIT)
		.build();
	public static final RoomWithMainDealResponse ROOM_RESPONSE_2 = RoomWithMainDealResponse.builder()
		.id(2L)
		.title(DEFAULT_ROOM_TITLE)
		.roomType(RoomType.TWO_ROOM.name())
		.roomTypeValue(RoomType.TWO_ROOM.value())
		.mainDealType(DealType.MONTHLY_RENT.name())
		.mainDealTypeValue(DealType.MONTHLY_RENT.value())
		.deposit(DEFAULT_MONTHLY_DEPOSIT)
		.rent(50)
		.build();

	public static final RoomWithMainDealResponseDto ROOM_RESPONSE_DTO_1 = RoomWithMainDealResponseDto.builder()
		.id(1L)
		.title(DEFAULT_ROOM_TITLE)
		.roomType(RoomType.ONE_ROOM.name())
		.roomTypeValue(RoomType.ONE_ROOM.value())
		.mainDealType(DealType.LONG_TERM_RENT.name())
		.mainDealTypeValue(DealType.LONG_TERM_RENT.value())
		.deposit(DEFAULT_LONG_TERM_DEPOSIT)
		.build();
	public static final RoomWithMainDealResponseDto ROOM_RESPONSE_DTO_2 = RoomWithMainDealResponseDto.builder()
		.id(2L)
		.title(DEFAULT_ROOM_TITLE)
		.roomType(RoomType.TWO_ROOM.name())
		.roomTypeValue(RoomType.TWO_ROOM.value())
		.mainDealType(DealType.MONTHLY_RENT.name())
		.mainDealTypeValue(DealType.MONTHLY_RENT.value())
		.deposit(DEFAULT_MONTHLY_DEPOSIT)
		.rent(50)
		.build();
	public static final DealResponse DEAL_RESPONSE = DealResponse.builder()
		.dealType(DealType.MONTHLY_RENT.name())
		.build();

	public static final RoomWithDealsResponse ROOM_WITH_DEALS_RESPONSE = RoomWithDealsResponse.builder()
		.id(1L)
		.title(DEFAULT_ROOM_TITLE)
		.roomType(RoomType.ONE_ROOM.name())
		.deals(List.of(DEAL_RESPONSE))
		.build();

}
