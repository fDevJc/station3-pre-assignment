package com.jc.station3assignment.room.presentation.dto.response;

import lombok.Getter;

@Getter
public class RoomIdResponse {
	private Long id;

	public RoomIdResponse(Long id) {
		this.id = id;
	}
}
