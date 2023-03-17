package com.jc.station3assignment.room.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DealResponse {
	private String dealType;
	private int deposit;
	private int rent;
	private int orderNumber;
}

