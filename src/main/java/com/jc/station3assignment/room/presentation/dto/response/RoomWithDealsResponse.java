package com.jc.station3assignment.room.presentation.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomWithDealsResponse {
	private Long id;
	private String title;
	private String roomType;
	private List<DealResponse> deals;
}
