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

import com.jc.station3assignment.room.application.RoomService;
import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.response.AddRoomResponseDto;
import com.jc.station3assignment.room.domain.Room;
import com.jc.station3assignment.room.domain.RoomType;
import com.jc.station3assignment.room.domain.deal.DealType;
import com.jc.station3assignment.room.domain.repository.RoomRepository;
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

	@Test
	void 사용자가_방을_등록한다() throws Exception {
		//given
		AddRoomRequestDto.AddRoomDealRequestDto longTermRent = AddRoomRequestDto.AddRoomDealRequestDto.builder()
			.dealType(DealType.LONG_TERM_RENT.name())
			.deposit(1000)
			.orderNumber(1)
			.build();

		AddRoomRequestDto.AddRoomDealRequestDto monthlyRent = AddRoomRequestDto.AddRoomDealRequestDto.builder()
			.dealType(DealType.LONG_TERM_RENT.name())
			.deposit(500)
			.rent(50)
			.orderNumber(2)
			.build();

		AddRoomRequestDto addRoomRequestDto = AddRoomRequestDto.builder()
			.title("좋은 집입니다")
			.roomType(RoomType.ONE_ROOM.name())
			.deals(List.of(longTermRent, monthlyRent))
			.build();

		Room room = Room.builder()
			.id(1L)
			.build();

		given(roomRepository.save(any(Room.class)))
			.willReturn(room);

		given(userRepository.findById(any()))
			.willReturn(Optional.of(User.builder().id(1L).build()));

		//when
		AddRoomResponseDto ret = roomService.addRoom(addRoomRequestDto);

		//then
		Assertions.assertThat(ret.getId()).isEqualTo(1L);
	}
}
