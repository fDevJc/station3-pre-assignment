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
		User userEntity = findUserEntity(requestDto.getUserId(), requestDto.getUserEmail());

		List<Deal> deals = requestDto.getDeals()
			.stream()
			.map(DealRequestDto::toEntity)
			.collect(Collectors.toList());

		Room room = Room.builder()
			.title(requestDto.getTitle())
			.user(userEntity)
			.roomType(RoomType.valueOf(requestDto.getRoomType()))
			.build();

		room.addDeals(deals);

		return new RoomIdResponseDto(roomRepository.save(room).getId());
	}

	@Transactional
	public RoomWithDealsResponseDto modifyRoom(ModifyRoomRequestDto requestDto) {
		User userEntity = findUserEntity(requestDto.getUserId(), requestDto.getUserEmail());

		Room roomEntity = findRoomEntity(requestDto.getRoomId());

		checkOwner(userEntity, roomEntity);

		Room room = roomEntity.getClone();
		room.changeTitle(requestDto.getTitle());
		room.changeRoomType(RoomType.valueOf(requestDto.getRoomType()));
		List<Deal> deals = requestDto.getDeals()
			.stream()
			.map(DealRequestDto::toEntity)
			.collect(Collectors.toList());
		room.addDeals(deals);

		return RoomWithDealsResponseDto.of(roomRepository.save(room));
	}

	@Transactional
	public void deleteRoom(LoginUserWithRoomIdRequestDto requestDto) {
		User userEntity = findUserEntity(requestDto.getUserId(), requestDto.getUserEmail());

		Room roomEntity = findRoomEntity(requestDto.getRoomId());

		checkOwner(userEntity, roomEntity);

		roomRepository.delete(roomEntity);
	}

	public List<RoomWithMainDealResponseDto> findMyRooms(FindMyRoomsRequestDto requestDto) {
		User userEntity = findUserEntity(requestDto.getUserId(), requestDto.getUserEmail());

		List<Room> rooms = roomRepository.findAllByUser(requestDto.getPageable(), userEntity);

		return rooms.stream()
			.map(RoomWithMainDealResponseDto::of)
			.collect(Collectors.toList());
	}

	public RoomWithDealsResponseDto findMyRoom(LoginUserWithRoomIdRequestDto requestDto) {
		User user = findUserEntity(requestDto.getUserId(), requestDto.getUserEmail());

		Room room = findRoomEntity(requestDto.getRoomId());

		checkOwner(user, room);

		return RoomWithDealsResponseDto.of(room);
	}

	public List<RoomWithMainDealResponseDto> findAllRooms(SearchRoomDto searchRoomDto) {
		return roomRepository.findAllBySearchCondition(searchRoomDto);
	}

	private User findUserEntity(Long requestDto, String requestDto1) {
		return userRepository.findById(requestDto)
			.orElseThrow(() -> new UserNotFoundException(requestDto1));
	}

	private Room findRoomEntity(Long roomId) {
		return roomRepository.findById(roomId)
			.orElseThrow(() -> new RoomNotFoundException(roomId));
	}

	private static void checkOwner(User userEntity, Room roomEntity) {
		if (!roomEntity.isOwner(userEntity)) {
			throw new PermissionDeniedRoomException(roomEntity.getId());
		}
	}
}
