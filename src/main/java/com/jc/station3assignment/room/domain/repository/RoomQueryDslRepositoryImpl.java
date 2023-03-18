package com.jc.station3assignment.room.domain.repository;

import static com.jc.station3assignment.room.domain.QRoom.*;
import static com.jc.station3assignment.room.domain.deal.QDeal.*;

import java.util.List;
import java.util.stream.Collectors;

import com.jc.station3assignment.room.application.dto.request.SearchRoomDto;
import com.jc.station3assignment.room.application.dto.response.QRoomWithMainDealResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithMainDealResponseDto;
import com.jc.station3assignment.room.domain.RoomType;
import com.jc.station3assignment.room.domain.deal.DealType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class RoomQueryDslRepositoryImpl implements RoomQueryDslRepository {
	private final JPAQueryFactory queryFactory;

	public RoomQueryDslRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<RoomWithMainDealResponseDto> findAllBySearchCondition(SearchRoomDto searchCondition) {
		return queryFactory
			.select(
				new QRoomWithMainDealResponseDto(
					room.id,
					room.title,
					room.roomType,
					deal.dealType,
					deal.deposit,
					deal.rent))
			.from(room)
			.join(deal).on(deal.room.eq(room))
			.where(inRoomType(searchCondition.getRoomType()))
			.where(Expressions.list(deal.room.id, deal.orderNumber).in(
				JPAExpressions
					.select(deal.room.id, deal.orderNumber.min())
					.from(deal)
					.where(inDealType(searchCondition.getDealType()))
					.where(betweenDeposit(searchCondition.getDepositRange()))
					.where(betweenRent(searchCondition.getRentRange()))
					.groupBy(deal.room.id)))
			.offset(searchCondition.getPageable().getOffset())
			.limit(searchCondition.getPageable().getPageSize())
			.fetch();
	}

	private static BooleanExpression betweenRent(List<Integer> range) {
		if (range == null) {
			return null;
		}
		return deal.rent.between(range.get(0), range.get(1));
	}

	private static BooleanExpression betweenDeposit(List<Integer> range) {
		if (range == null) {
			return null;
		}
		return deal.deposit.between(range.get(0), range.get(1));
	}

	private static BooleanExpression inDealType(List<String> dealTypeList) {
		if (dealTypeList == null) {
			return null;
		}
		return deal.dealType.in(dealTypeList.stream().map(DealType::valueOf).collect(Collectors.toList()));
	}

	private static BooleanExpression inRoomType(List<String> roomTypeList) {
		if (roomTypeList == null) {
			return null;
		}
		return room.roomType.in(roomTypeList.stream().map(RoomType::valueOf).collect(Collectors.toList()));
	}
}
