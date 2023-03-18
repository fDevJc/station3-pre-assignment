package com.jc.station3assignment.room.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.station3assignment.exception.room.PermissionDeniedRoomException;
import com.jc.station3assignment.exception.room.RoomNotFoundException;
import com.jc.station3assignment.exception.user.UserNotFoundException;
import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.DealRequestDto;
import com.jc.station3assignment.room.application.dto.request.FindMyRoomsRequestDto;
import com.jc.station3assignment.room.application.dto.request.LoginUserWithRoomIdRequestDto;
import com.jc.station3assignment.room.application.dto.request.ModifyRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.SearchRoomDto;
import com.jc.station3assignment.room.application.dto.response.RoomIdResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithDealsResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithMainDealResponseDto;
import com.jc.station3assignment.room.domain.Room;
import com.jc.station3assignment.room.domain.RoomType;
import com.jc.station3assignment.room.domain.deal.Deal;
import com.jc.station3assignment.room.domain.repository.RoomRepository;
import com.jc.station3assignment.user.domain.User;
import com.jc.station3assignment.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomService {
	private final RoomRepository roomRepository;
	private final UserRepository userRepository;

	@Transactional
	public RoomIdResponseDto addRoom(AddRoomRequestDto requestDto) {
		User user = userRepository.findById(requestDto.getUserId())
			.orElseThrow(() -> new UserNotFoundException(requestDto.getUserEmail()));

		List<Deal> deals = requestDto.getDeals().stream()
			.map(DealRequestDto::toEntity)
			.collect(Collectors.toList());

		Room room = Room.builder()
			.title(requestDto.getTitle())
			.user(user)
			.roomType(RoomType.valueOf(requestDto.getRoomType()))
			.build();

		room.addDeals(deals);

		return new RoomIdResponseDto(roomRepository.save(room).getId());
	}

	//TODO update 좋은 방법 고민
	@Transactional
	public RoomWithDealsResponseDto modifyRoom(ModifyRoomRequestDto requestDto) {
		User user = userRepository.findById(requestDto.getUserId())
			.orElseThrow(() -> new UserNotFoundException(requestDto.getUserEmail()));

		Room room = roomRepository.findById(requestDto.getRoomId())
			.orElseThrow(() -> new RoomNotFoundException(requestDto.getRoomId()));

		if (!room.isOwner(user)) {
			throw new PermissionDeniedRoomException(room.getId());
		}

		// room.changeTitle(requestDto.getTitle());
		// room.changeRoomType(RoomType.valueOf(requestDto.getRoomType()));
		//
		// List<Deal> deals = requestDto.getDeals().stream()
		// 	.map(modifyRoomDealRequestDto -> modifyRoomDealRequestDto.toEntity()).collect(Collectors.toList());

		// room.changeDeals(deals);

		Room room1 = Room.builder()
			.id(room.getId())
			.user(user)
			.build();

		room1.changeTitle(requestDto.getTitle());
		room1.changeRoomType(RoomType.valueOf(requestDto.getRoomType()));

		List<Deal> deals = requestDto.getDeals().stream()
			.map(DealRequestDto::toEntity).collect(Collectors.toList());

		room1.addDeals(deals);

		// room1.changeDeals(deals);

		Room save = roomRepository.save(room1);

		return RoomWithDealsResponseDto.of(save);
	}

	@Transactional
	public void deleteRoom(LoginUserWithRoomIdRequestDto requestDto) {
		User user = userRepository.findById(requestDto.getUserId())
			.orElseThrow(() -> new UserNotFoundException(requestDto.getUserEmail()));

		Room room = roomRepository.findById(requestDto.getRoomId())
			.orElseThrow(() -> new RoomNotFoundException(requestDto.getRoomId()));

		if (!room.isOwner(user)) {
			throw new PermissionDeniedRoomException(room.getId());
		}

		roomRepository.delete(room);
	}

	public List<RoomWithMainDealResponseDto> findMyRooms(FindMyRoomsRequestDto requestDto) {
		User user = userRepository.findById(requestDto.getUserId())
			.orElseThrow(() -> new UserNotFoundException(requestDto.getUserEmail()));

		List<Room> rooms = roomRepository.findAllByUser(requestDto.getPageable(), user);

		return rooms.stream()
			.map(RoomWithMainDealResponseDto::of)
			.collect(Collectors.toList());
	}

	public RoomWithDealsResponseDto findMyRoom(LoginUserWithRoomIdRequestDto requestDto) {
		User user = userRepository.findById(requestDto.getUserId())
			.orElseThrow(() -> new UserNotFoundException(requestDto.getUserEmail()));

		Room room = roomRepository.findById(requestDto.getRoomId())
			.orElseThrow(() -> new RoomNotFoundException(requestDto.getRoomId()));

		if (!room.isOwner(user)) {
			throw new PermissionDeniedRoomException(room.getId());
		}

		return RoomWithDealsResponseDto.of(room);
	}

	public List<RoomWithMainDealResponseDto> findAllRooms(SearchRoomDto searchRoomDto) {
		return roomRepository.findAllBySearchCondition(searchRoomDto);
	}
}
