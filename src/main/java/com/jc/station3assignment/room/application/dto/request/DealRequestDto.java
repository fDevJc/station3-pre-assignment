package com.jc.station3assignment.room.application.dto.request;

import com.jc.station3assignment.room.domain.deal.Deal;
import com.jc.station3assignment.room.domain.deal.DealType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DealRequestDto {

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
