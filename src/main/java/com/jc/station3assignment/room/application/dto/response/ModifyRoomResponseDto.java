package com.jc.station3assignment.room.application.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.jc.station3assignment.room.domain.Room;
import com.jc.station3assignment.room.domain.deal.Deal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModifyRoomResponseDto {
	private Long id;
	private String title;
	private String roomType;

	private List<ModifyRoomDealResponseDto> deals;

	@Getter
	@Builder
	public static class ModifyRoomDealResponseDto {
		private String dealType;
		private int deposit;
		private int rent;
		private int orderNumber;

		public static ModifyRoomDealResponseDto of(Deal deal) {
			return ModifyRoomDealResponseDto.builder()
				.dealType(deal.getDealType().name())
				.deposit(deal.getDeposit())
				.rent(deal.getRent())
				.orderNumber(deal.getOrderNumber())
				.build();
		}
	}

	public static ModifyRoomResponseDto of(Room room) {
		return ModifyRoomResponseDto.builder()
			.id(room.getId())
			.title(room.getTitle())
			.roomType(room.getRoomType().name())
			.deals(room.getDeals().getDeals()
				.stream()
				.map(ModifyRoomDealResponseDto::of)
				.collect(Collectors.toList()))
			.build();
	}
}
