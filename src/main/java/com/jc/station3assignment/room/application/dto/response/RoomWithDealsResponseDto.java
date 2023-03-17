package com.jc.station3assignment.room.application.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.jc.station3assignment.room.domain.Room;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomWithDealsResponseDto {
	private Long id;
	private String title;
	private String roomType;

	private List<DealResponseDto> deals;

	public static RoomWithDealsResponseDto of(Room room) {
		return RoomWithDealsResponseDto.builder()
			.id(room.getId())
			.title(room.getTitle())
			.roomType(room.getRoomType().name())
			.deals(room.getDeals().getDeals()
				.stream()
				.map(DealResponseDto::of)
				.collect(Collectors.toList()))
			.build();
	}
}
