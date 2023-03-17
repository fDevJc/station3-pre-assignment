package com.jc.station3assignment.room.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DealRequest {
	private String dealType;
	private int deposit;
	private int rent;
	private int orderNumber;
}
