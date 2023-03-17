package com.jc.station3assignment.room.application.dto.response;

import com.jc.station3assignment.room.domain.deal.Deal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DealResponseDto {

	private String dealType;
	private int deposit;
	private int rent;
	private int orderNumber;

	public static DealResponseDto of(Deal deal) {
		return DealResponseDto.builder()
			.dealType(deal.getDealType().name())
			.deposit(deal.getDeposit())
			.rent(deal.getRent())
			.orderNumber(deal.getOrderNumber())
			.build();
	}
}

