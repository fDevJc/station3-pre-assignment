package com.jc.station3assignment.room.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.station3assignment.exception.user.UserNotFoundException;
import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.response.AddRoomResponseDto;
import com.jc.station3assignment.room.domain.Room;
import com.jc.station3assignment.room.domain.RoomType;
import com.jc.station3assignment.room.domain.deal.Deal;
import com.jc.station3assignment.room.domain.repository.RoomRepository;
import com.jc.station3assignment.user.domain.User;
import com.jc.station3assignment.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomService {
	private final RoomRepository roomRepository;
	private final UserRepository userRepository;

	@Transactional
	public AddRoomResponseDto addRoom(AddRoomRequestDto addRoomRequestDto) {
		User user = userRepository.findById(addRoomRequestDto.getUserId())
			.orElseThrow(() -> new UserNotFoundException(addRoomRequestDto.getUserEmail()));

		List<Deal> deals = addRoomRequestDto.getDeals().stream()
			.map(addRoomDealRequestDto -> addRoomDealRequestDto.toEntity()).collect(Collectors.toList());

		Room room = Room.builder()
			.title(addRoomRequestDto.getTitle())
			.user(user)
			.roomType(RoomType.valueOf(addRoomRequestDto.getRoomType()))
			.build();

		room.addDeals(deals);

		return new AddRoomResponseDto(roomRepository.save(room).getId());
	}
}
