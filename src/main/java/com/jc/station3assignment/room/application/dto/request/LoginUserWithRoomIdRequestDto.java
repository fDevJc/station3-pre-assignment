package com.jc.station3assignment.room.application.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginUserWithRoomIdRequestDto {
	private Long userId;
	private String userEmail;

	private Long roomId;
}
