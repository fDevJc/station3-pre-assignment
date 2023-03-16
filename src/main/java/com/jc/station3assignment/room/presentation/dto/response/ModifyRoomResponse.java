package com.jc.station3assignment.room.presentation.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModifyRoomResponse {
	private Long id;
	private String title;
	private String roomType;
	private List<ModifyRoomDealResponse> deals;

	@Builder
	@Getter
	public static class ModifyRoomDealResponse {
		private String dealType;
		private int deposit;
		private int rent;
		private int orderNumber;
	}
}
