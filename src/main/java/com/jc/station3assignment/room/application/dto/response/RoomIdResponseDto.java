package com.jc.station3assignment.room.application.dto.response;

import lombok.Getter;

@Getter
public class RoomIdResponseDto {
	private Long id;

	public RoomIdResponseDto(Long id) {
		this.id = id;
	}
}
