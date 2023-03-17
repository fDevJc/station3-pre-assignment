package com.jc.station3assignment.room.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomResponse {
	private Long id;
	private String title;
	private String roomType;
	private String roomTypeValue;
	private String mainDealType;
	private String mainDealTypeValue;
	private int deposit;
	private int rent;
}
