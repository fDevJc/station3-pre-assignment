package com.jc.station3assignment.unit.room.domain.repository;

import static com.jc.station3assignment.room.domain.RoomType.*;
import static com.jc.station3assignment.room.domain.deal.DealType.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.jc.station3assignment.config.querydsl.QueryDslConfig;
import com.jc.station3assignment.room.application.dto.request.SearchRoomDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithMainDealResponseDto;
import com.jc.station3assignment.room.domain.Room;
import com.jc.station3assignment.room.domain.deal.Deal;
import com.jc.station3assignment.room.domain.repository.RoomRepository;
import com.jc.station3assignment.user.domain.Email;
import com.jc.station3assignment.user.domain.User;
import com.jc.station3assignment.user.domain.repository.UserRepository;

@Import(QueryDslConfig.class)
@Transactional
@DataJpaTest
class RoomRepositoryTest {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoomRepository roomRepository;

	@BeforeEach
	void init() {
		User user = User.builder()
			.email(new Email("fdevjc@gmail.com"))
			.build();

		Deal longTermDeal = Deal.builder()
			.dealType(LONG_TERM_RENT)
			.deposit(1000)
			.orderNumber(1)
			.build();
		Deal monthlyDeal = Deal.builder()
			.dealType(MONTHLY_RENT)
			.deposit(500)
			.rent(50)
			.orderNumber(2)
			.build();
		Deal monthlyDeal2 = Deal.builder()
			.dealType(MONTHLY_RENT)
			.deposit(700)
			.rent(30)
			.orderNumber(3)
			.build();
		Room room = Room.builder()
			.title("원룸")
			.roomType(ONE_ROOM)
			.user(user)
			.build();
		room.addDeals(List.of(longTermDeal, monthlyDeal, monthlyDeal2));

		userRepository.save(user);
		roomRepository.save(room);
	}

	@Test
	void 원룸_검색조건으로_조회한다() throws Exception {
		//given
		SearchRoomDto searchRoomDto = SearchRoomDto.builder()
			.pageable(PageRequest.of(0, 10))
			.roomType(List.of(ONE_ROOM.name()))
			.build();

		//when
		List<RoomWithMainDealResponseDto> ret = roomRepository.findAllBySearchCondition(searchRoomDto);

		//then: 우선순위(거래의 정렬순서)에 따라 방(룸타입:원룸, 거래타입:전세, 보증금:1000)이 조회된다
		assertExpectedRoom(ret, ONE_ROOM.name(), LONG_TERM_RENT.name(), 1000, 0);
	}

	@Test
	void 원룸_월세_검색조건으로_조회한다() throws Exception {
		//given
		SearchRoomDto searchRoomDto = SearchRoomDto.builder()
			.pageable(PageRequest.of(0, 10))
			.roomType(List.of(ONE_ROOM.name()))
			.dealType(List.of(MONTHLY_RENT.name()))
			.build();

		//when
		List<RoomWithMainDealResponseDto> ret = roomRepository.findAllBySearchCondition(searchRoomDto);

		//then: 우선순위(거래의 정렬순서)에 따라 방(룸타입:원룸, 거래타입:월세, 보증금:500, 임차료:50)이 조회된다
		assertExpectedRoom(ret, ONE_ROOM.name(), MONTHLY_RENT.name(), 500, 50);
	}

	@Test
	void 원룸_월세_보증금_검색조건으로_조회한다() throws Exception {
		//given
		SearchRoomDto searchRoomDto = SearchRoomDto.builder()
			.pageable(PageRequest.of(0, 10))
			.roomType(List.of(ONE_ROOM.name()))
			.dealType(List.of(MONTHLY_RENT.name()))
			.depositRange(List.of(0, 700))
			.build();

		//when
		List<RoomWithMainDealResponseDto> ret = roomRepository.findAllBySearchCondition(searchRoomDto);

		//then: 우선순위(거래의 정렬순서)에 따라 방(룸타입:원룸, 거래타입:월세, 보증금:500, 임차료:50)이 조회된다
		assertExpectedRoom(ret, ONE_ROOM.name(), MONTHLY_RENT.name(), 500, 50);
	}

	@Test
	void 원룸_월세_보증금_임차료_검색조건으로_조회한다() throws Exception {
		//given
		SearchRoomDto searchRoomDto = SearchRoomDto.builder()
			.pageable(PageRequest.of(0, 10))
			.roomType(List.of(ONE_ROOM.name()))
			.dealType(List.of(MONTHLY_RENT.name()))
			.depositRange(List.of(0, 700))
			.rentRange(List.of(0, 30))
			.build();

		//when
		List<RoomWithMainDealResponseDto> ret = roomRepository.findAllBySearchCondition(searchRoomDto);

		//then: 우선순위(거래의 정렬순서)에 따라 방(룸타입:원룸, 거래타입:월세, 보증금:700, 임차료:30)이 조회된다
		assertExpectedRoom(ret, ONE_ROOM.name(), MONTHLY_RENT.name(), 700, 30);
	}

	private static void assertExpectedRoom(List<RoomWithMainDealResponseDto> ret, String roomTypeName, String dealTypeName, int deposit, int rent) {
		assertThat(ret.size()).isEqualTo(1);
		assertThat(ret.get(0).getRoomType()).isEqualTo(roomTypeName);
		assertThat(ret.get(0).getMainDealType()).isEqualTo(dealTypeName);
		assertThat(ret.get(0).getDeposit()).isEqualTo(deposit);
		assertThat(ret.get(0).getRent()).isEqualTo(rent);
	}
}