package com.jc.station3assignment.room.domain.repository;

import java.util.List;

import com.jc.station3assignment.room.application.dto.request.SearchRoomDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithMainDealResponseDto;

public interface RoomQueryDslRepository {
	List<RoomWithMainDealResponseDto> findAllBySearchCondition(SearchRoomDto searchRoomDto);
}
