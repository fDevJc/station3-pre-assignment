package com.jc.station3assignment.room.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.station3assignment.exception.room.PermissionDeniedRoomException;
import com.jc.station3assignment.exception.room.RoomNotFoundException;
import com.jc.station3assignment.exception.user.UserNotFoundException;
import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.DeleteRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.ModifyRoomRequestDto;
import com.jc.station3assignment.room.application.dto.response.ModifyRoomResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomIdResponseDto;
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
	public RoomIdResponseDto addRoom(AddRoomRequestDto addRoomRequestDto) {
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

		return new RoomIdResponseDto(roomRepository.save(room).getId());
	}

	//TODO update 좋은 방법 고민
	@Transactional
	public ModifyRoomResponseDto modifyRoom(ModifyRoomRequestDto modifyRoomRequestDto) {
		User user = userRepository.findById(modifyRoomRequestDto.getUserId())
			.orElseThrow(() -> new UserNotFoundException(modifyRoomRequestDto.getUserEmail()));

		Room room = roomRepository.findById(modifyRoomRequestDto.getRoomId())
			.orElseThrow(() -> new RoomNotFoundException(modifyRoomRequestDto.getRoomId()));

		if (!room.isOwner(user)) {
			throw new PermissionDeniedRoomException(room.getId());
		}

		// room.changeTitle(modifyRoomRequestDto.getTitle());
		// room.changeRoomType(RoomType.valueOf(modifyRoomRequestDto.getRoomType()));
		//
		// List<Deal> deals = modifyRoomRequestDto.getDeals().stream()
		// 	.map(modifyRoomDealRequestDto -> modifyRoomDealRequestDto.toEntity()).collect(Collectors.toList());

		// room.changeDeals(deals);

		Room room1 = Room.builder()
			.id(room.getId())
			.user(user)
			.build();

		room1.changeTitle(modifyRoomRequestDto.getTitle());
		room1.changeRoomType(RoomType.valueOf(modifyRoomRequestDto.getRoomType()));

		List<Deal> deals = modifyRoomRequestDto.getDeals().stream()
			.map(modifyRoomDealRequestDto -> modifyRoomDealRequestDto.toEntity()).collect(Collectors.toList());

		room1.addDeals(deals);

		// room1.changeDeals(deals);

		Room save = roomRepository.save(room1);

		return ModifyRoomResponseDto.of(save);
	}

	@Transactional
	public void deleteRoom(DeleteRoomRequestDto deleteRoomRequestDto) {
		User user = userRepository.findById(deleteRoomRequestDto.getUserId())
			.orElseThrow(() -> new UserNotFoundException(deleteRoomRequestDto.getUserEmail()));

		Room room = roomRepository.findById(deleteRoomRequestDto.getRoomId())
			.orElseThrow(() -> new RoomNotFoundException(deleteRoomRequestDto.getRoomId()));

		if (!room.isOwner(user)) {
			throw new PermissionDeniedRoomException(room.getId());
		}

		roomRepository.delete(room);
	}
}
