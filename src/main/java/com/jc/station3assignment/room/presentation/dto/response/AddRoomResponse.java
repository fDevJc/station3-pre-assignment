package com.jc.station3assignment.room.presentation.dto.response;

import lombok.Getter;

@Getter
public class AddRoomResponse {
	private Long id;

	public AddRoomResponse(Long id) {
		this.id = id;
	}
}
