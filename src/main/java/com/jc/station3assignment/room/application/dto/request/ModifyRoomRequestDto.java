package com.jc.station3assignment.room.application.dto.request;

import java.util.List;

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

	private List<DealRequestDto> deals;
}
