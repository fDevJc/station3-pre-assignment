package com.jc.station3assignment.unit.room;

import java.util.List;

import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.ModifyRoomRequestDto;
import com.jc.station3assignment.room.application.dto.response.ModifyRoomResponseDto;
import com.jc.station3assignment.room.domain.RoomType;
import com.jc.station3assignment.room.domain.deal.DealType;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.request.ModifyRoomRequest;

public class RoomTestSampleDto {
	private static final AddRoomRequest.AddRoomDealRequest ADD_ROOM_LONG_TERM_RENT_DEAL_REQUEST = AddRoomRequest.AddRoomDealRequest.builder()
		.dealType(DealType.LONG_TERM_RENT.name())
		.deposit(1000)
		.orderNumber(1)
		.build();

	private static final AddRoomRequest.AddRoomDealRequest ADD_ROOM_MONTHLY_RENT_DEAL_REQUEST = AddRoomRequest.AddRoomDealRequest.builder()
		.dealType(DealType.MONTHLY_RENT.name())
		.deposit(500)
		.rent(50)
		.orderNumber(2)
		.build();

	public static final AddRoomRequest ADD_ROOM_REQUEST = AddRoomRequest.builder()
		.title("좋은집 입니다")
		.roomType(RoomType.ONE_ROOM.name())
		.deals(List.of(ADD_ROOM_LONG_TERM_RENT_DEAL_REQUEST, ADD_ROOM_MONTHLY_RENT_DEAL_REQUEST))
		.build();

	public static final ModifyRoomResponseDto MODIFY_ROOM_RESPONSE_DTO = ModifyRoomResponseDto.builder()
		.id(1L)
		.title("changedTitle")
		.roomType(RoomType.ONE_ROOM.name())
		.deals(List.of(ModifyRoomResponseDto.ModifyRoomDealResponseDto.builder().dealType(DealType.MONTHLY_RENT.name()).build()))
		.build();

	private static final ModifyRoomRequest.ModifyRoomDealRequest MODIFY_ROOM_LONG_TERM_RENT_DEAL_REQUEST = ModifyRoomRequest.ModifyRoomDealRequest.builder()
		.dealType(DealType.LONG_TERM_RENT.name())
		.deposit(1200)
		.orderNumber(2)
		.build();

	private static final ModifyRoomRequest.ModifyRoomDealRequest MODIFY_ROOM_MONTHLY_RENT_DEAL_REQUEST = ModifyRoomRequest.ModifyRoomDealRequest.builder()
		.dealType(DealType.MONTHLY_RENT.name())
		.deposit(700)
		.rent(70)
		.orderNumber(1)
		.build();

	public static final ModifyRoomRequest MODIFY_ROOM_REQUEST = ModifyRoomRequest.builder()
		.title("changedTitle")
		.roomType(RoomType.ONE_ROOM.name())
		.deals(List.of(MODIFY_ROOM_LONG_TERM_RENT_DEAL_REQUEST, MODIFY_ROOM_MONTHLY_RENT_DEAL_REQUEST))
		.build();

	private static final AddRoomRequestDto.AddRoomDealRequestDto ADD_ROOM_LONG_TERM_RENT_DEAL_REQUEST_DTO = AddRoomRequestDto.AddRoomDealRequestDto.builder()
		.dealType(DealType.LONG_TERM_RENT.name())
		.deposit(1000)
		.orderNumber(1)
		.build();

	private static final AddRoomRequestDto.AddRoomDealRequestDto ADD_ROOM_MONTHLY_RENT_DEAL_REQUEST_DTO = AddRoomRequestDto.AddRoomDealRequestDto.builder()
		.dealType(DealType.MONTHLY_RENT.name())
		.deposit(500)
		.rent(50)
		.orderNumber(2)
		.build();

	public static final AddRoomRequestDto ADD_ROOM_REQUEST_DTO = AddRoomRequestDto.builder()
		.title("좋은 집입니다")
		.roomType(RoomType.ONE_ROOM.name())
		.deals(List.of(ADD_ROOM_LONG_TERM_RENT_DEAL_REQUEST_DTO, ADD_ROOM_MONTHLY_RENT_DEAL_REQUEST_DTO))
		.build();

	private static final ModifyRoomRequestDto.ModifyRoomDealRequestDto MODIFY_ROOM_LONG_TERM_RENT_DEAL_REQUEST_DTO = ModifyRoomRequestDto.ModifyRoomDealRequestDto.builder()
		.dealType(DealType.LONG_TERM_RENT.name())
		.deposit(1000)
		.orderNumber(1)
		.build();

	private static final ModifyRoomRequestDto.ModifyRoomDealRequestDto MODIFY_ROOM_MONTHLY_RENT_DEAL_REQUEST_DTO = ModifyRoomRequestDto.ModifyRoomDealRequestDto.builder()
		.dealType(DealType.LONG_TERM_RENT.name())
		.deposit(500)
		.rent(50)
		.orderNumber(2)
		.build();

	public static final ModifyRoomRequestDto MODIFY_ROOM_REQUEST_DTO = ModifyRoomRequestDto.builder()
		.userId(1L)
		.roomId(1L)
		.title("더 좋은 집입니다")
		.roomType(RoomType.ONE_ROOM.name())
		.deals(List.of(MODIFY_ROOM_LONG_TERM_RENT_DEAL_REQUEST_DTO, MODIFY_ROOM_MONTHLY_RENT_DEAL_REQUEST_DTO))
		.build();
}
