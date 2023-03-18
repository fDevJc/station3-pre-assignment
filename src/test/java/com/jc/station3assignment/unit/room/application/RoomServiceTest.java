package com.jc.station3assignment.unit.room.application;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.jc.station3assignment.room.application.RoomService;
import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.FindMyRoomsRequestDto;
import com.jc.station3assignment.room.application.dto.request.ModifyRoomRequestDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithDealsResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomIdResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithMainDealResponseDto;
import com.jc.station3assignment.room.domain.Room;
import com.jc.station3assignment.room.domain.RoomType;
import com.jc.station3assignment.room.domain.deal.Deal;
import com.jc.station3assignment.room.domain.deal.DealType;
import com.jc.station3assignment.room.domain.repository.RoomRepository;
import com.jc.station3assignment.unit.room.RoomDtoFactory;
import com.jc.station3assignment.user.domain.User;
import com.jc.station3assignment.user.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
	@InjectMocks
	private RoomService roomService;

	@Mock
	private RoomRepository roomRepository;

	@Mock
	private UserRepository userRepository;

	private User mockUser = User.builder()
		.id(1L)
		.build();

	private Room mockRoom = Room.builder()
		.id(1L)
		.user(mockUser)
		.title("좋은집1")
		.roomType(RoomType.ONE_ROOM)
		.build();

	private Room mockChangedRoom = Room.builder()
		.id(1L)
		.user(mockUser)
		.roomType(RoomType.TWO_ROOM)
		.build();

	private Deal expectedRoomMainDeal = Deal.builder()
		.id(1L)
		.room(mockRoom)
		.dealType(DealType.MONTHLY_RENT)
		.deposit(500)
		.rent(50)
		.orderNumber(1)
		.build();

	@Test
	void 사용자가_방을_등록한다() throws Exception {
		//given
		AddRoomRequestDto addRoomRequestDto = RoomDtoFactory.ADD_ROOM_REQUEST_DTO;

		given(userRepository.findById(any()))
			.willReturn(Optional.of(mockUser));
		given(roomRepository.save(any(Room.class)))
			.willReturn(mockRoom);

		//when
		RoomIdResponseDto ret = roomService.addRoom(addRoomRequestDto);

		//then
		Assertions.assertThat(ret.getId()).isEqualTo(1L);
	}

	@Test
	void 사용자가_방의_타입을_수정한다() throws Exception {
		//given
		ModifyRoomRequestDto modifyRoomRequestDto = RoomDtoFactory.MODIFY_ROOM_REQUEST_DTO;

		given(userRepository.findById(anyLong()))
			.willReturn(Optional.of(mockUser));
		given(roomRepository.findById(anyLong()))
			.willReturn(Optional.of(mockRoom));
		given(roomRepository.save(any()))
			.willReturn(mockChangedRoom);

		//when
		RoomWithDealsResponseDto ret = roomService.modifyRoom(modifyRoomRequestDto);

		//then
		Assertions.assertThat(ret.getRoomType()).isEqualTo(mockChangedRoom.getRoomType().name());
	}

	@Test
	void 사용자가_내방을_조회한다() throws Exception {
		//given

		RoomWithMainDealResponseDto room1 = RoomWithMainDealResponseDto.builder()
			.id(1L)
			.title("좋은집1")
			.roomType(RoomType.ONE_ROOM.name())
			.roomTypeValue(RoomType.ONE_ROOM.value())
			.mainDealType(DealType.LONG_TERM_RENT.name())
			.mainDealTypeValue(DealType.LONG_TERM_RENT.value())
			.deposit(10000)
			.build();

		FindMyRoomsRequestDto findMyRoomsRequestDto = FindMyRoomsRequestDto.builder()
			.userId(1L)
			.userEmail("fdevjc@gmail.com")
			.pageable(PageRequest.of(0, 2))
			.build();

		mockRoom.addDeals(List.of(expectedRoomMainDeal));

		given(userRepository.findById(anyLong()))
			.willReturn(Optional.of(mockUser));

		given(roomRepository.findAllByUser(findMyRoomsRequestDto.getPageable(), mockUser))
			.willReturn(List.of(mockRoom));

		//when
		List<RoomWithMainDealResponseDto> ret = roomService.findMyRooms(findMyRoomsRequestDto);

		//then
		Assertions.assertThat(ret.get(0).getId()).isEqualTo(room1.getId());
		Assertions.assertThat(ret.get(0).getTitle()).isEqualTo(room1.getTitle());
		Assertions.assertThat(ret.get(0).getRoomType()).isEqualTo(room1.getRoomType());

	}
}
