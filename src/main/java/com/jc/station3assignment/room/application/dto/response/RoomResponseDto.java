package com.jc.station3assignment.room.application.dto.response;

import com.jc.station3assignment.room.domain.Room;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomResponseDto {
	private Long id;
	private String title;
	private String roomType;
	private String roomTypeValue;
	private String mainDealType;
	private String mainDealTypeValue;
	private int deposit;
	private int rent;

	public static RoomResponseDto of(Room room) {
		return RoomResponseDto.builder()
			.id(room.getId())
			.title(room.getTitle())
			.roomType(room.getRoomType().name())
			.roomTypeValue(room.getRoomType().value())
			.mainDealType(room.getDeals().getMainDeal().getDealType().name())
			.mainDealTypeValue(room.getDeals().getMainDeal().getDealType().name())
			.deposit(builder().deposit)
			.rent(builder().rent)
			.build();
	}
}
