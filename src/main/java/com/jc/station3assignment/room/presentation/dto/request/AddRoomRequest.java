package com.jc.station3assignment.room.presentation.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddRoomRequest {
	private String title;

	@NotBlank
	private String roomType;

	private List<AddRoomDealRequest> deals;

	@Builder
	@Getter
	public static class AddRoomDealRequest {
		private String dealType;
		private int deposit;
		private int rent;
		private int orderNumber;
	}
}

