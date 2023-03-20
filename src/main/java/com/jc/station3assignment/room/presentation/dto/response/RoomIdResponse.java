package com.jc.station3assignment.room.presentation.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RoomIdResponse {
	private Long id;

	public RoomIdResponse(Long id) {
		this.id = id;
	}
}
