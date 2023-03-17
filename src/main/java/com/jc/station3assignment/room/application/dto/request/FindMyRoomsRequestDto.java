package com.jc.station3assignment.room.application.dto.request;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindMyRoomsRequestDto {
	private Long userId;
	private String userEmail;

	private Pageable pageable;
}
