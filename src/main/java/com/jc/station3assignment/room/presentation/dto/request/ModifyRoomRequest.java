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

	private List<DealRequest> deals;
}
