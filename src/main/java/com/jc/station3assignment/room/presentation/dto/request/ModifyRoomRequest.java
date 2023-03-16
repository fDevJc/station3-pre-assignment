package com.jc.station3assignment.room.presentation.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ModifyRoomRequest {
	private String title;

	@NotBlank
	private String roomType;

	private List<ModifyRoomRequest.ModifyRoomDealRequest> deals;

	@Builder
	@Getter
	public static class ModifyRoomDealRequest {
		private String dealType;
		private int deposit;
		private int rent;
		private int orderNumber;
	}

}
