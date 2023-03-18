package com.jc.station3assignment.room.application.dto.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SearchRoomDto {
	private List<String> roomType = new ArrayList<>();
	private List<String> dealType = new ArrayList<>();
	private List<Integer> depositRange = new ArrayList<>();
	private List<Integer> rentRange = new ArrayList<>();

	private Pageable pageable;

	@Builder
	public SearchRoomDto(List<String> roomType, List<String> dealType, List<Integer> depositRange, List<Integer> rentRange, Pageable pageable) {
		this.roomType = roomType;
		this.dealType = dealType;
		this.depositRange = depositRange;
		this.rentRange = rentRange;
		this.pageable = pageable;
	}

	public void addPageable(Pageable pageable) {
		this.pageable = pageable;
	}

}
