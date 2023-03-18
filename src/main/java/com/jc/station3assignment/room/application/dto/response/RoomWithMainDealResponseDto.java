package com.jc.station3assignment.room.application.dto.response;

import com.jc.station3assignment.room.domain.Room;
import com.jc.station3assignment.room.domain.RoomType;
import com.jc.station3assignment.room.domain.deal.DealType;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomWithMainDealResponseDto {
	private Long id;
	private String title;
	private String roomType;
	private String roomTypeValue;
	private String mainDealType;
	private String mainDealTypeValue;
	private int deposit;
	private int rent;

	@Builder
	public RoomWithMainDealResponseDto(Long id, String title, String roomType, String roomTypeValue, String mainDealType, String mainDealTypeValue, int deposit, int rent) {
		this.id = id;
		this.title = title;
		this.roomType = roomType;
		this.roomTypeValue = roomTypeValue;
		this.mainDealType = mainDealType;
		this.mainDealTypeValue = mainDealTypeValue;
		this.deposit = deposit;
		this.rent = rent;
	}

	@QueryProjection
	public RoomWithMainDealResponseDto(Long id, String title, RoomType roomType, DealType mainDealType, int deposit, int rent) {
		this.id = id;
		this.title = title;
		this.roomType = roomType.name();
		this.roomTypeValue = roomType.value();
		this.mainDealType = mainDealType.name();
		this.mainDealTypeValue = mainDealType.value();
		this.deposit = deposit;
		this.rent = rent;
	}

	public static RoomWithMainDealResponseDto of(Room room) {
		return RoomWithMainDealResponseDto.builder()
			.id(room.getId())
			.title(room.getTitle())
			.roomType(room.getRoomType().name())
			.roomTypeValue(room.getRoomType().value())
			.mainDealType(room.getDeals().getMainDeal().getDealType().name())
			.mainDealTypeValue(room.getDeals().getMainDeal().getDealType().name())
			.deposit(room.getDeals().getMainDeal().getDeposit())
			.rent(room.getDeals().getMainDeal().getRent())
			.build();
	}
}
