package com.jc.station3assignment.room.application.dto.response;

import lombok.Getter;

@Getter
public class AddRoomResponseDto {
	private Long id;

	public AddRoomResponseDto(Long id) {
		this.id = id;
	}
}
