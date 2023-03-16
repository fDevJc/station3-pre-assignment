package com.jc.station3assignment.room.application.dto.request;

import java.util.List;

import com.jc.station3assignment.room.domain.deal.Deal;
import com.jc.station3assignment.room.domain.deal.DealType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModifyRoomRequestDto {
	private Long userId;
	private String userEmail;

	private Long roomId;

	private String title;

	private String roomType;

	private List<ModifyRoomRequestDto.ModifyRoomDealRequestDto> deals;

	@Builder
	@Getter
	public static class ModifyRoomDealRequestDto {
		private String dealType;
		private int deposit;
		private int rent;
		private int orderNumber;

		public Deal toEntity() {
			return Deal.builder()
				.dealType(DealType.valueOf(dealType))
				.deposit(deposit)
				.rent(rent)
				.orderNumber(orderNumber)
				.build();
		}
	}
}
