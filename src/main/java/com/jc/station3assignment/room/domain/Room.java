package com.jc.station3assignment.room.domain;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jc.station3assignment.room.domain.deal.Deal;
import com.jc.station3assignment.room.domain.deal.Deals;
import com.jc.station3assignment.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String title;

	@Enumerated(EnumType.STRING)
	private RoomType roomType;

	@Embedded
	private Deals deals = new Deals();

	@Builder
	public Room(Long id, User user, String title, RoomType roomType) {
		this.id = id;
		this.user = user;
		this.title = title;
		this.roomType = roomType;
	}

	public void addDeals(List<Deal> deals) {
		this.deals.addAll(this, deals);
	}

	public boolean isOwner(User user) {
		return this.user.equals(user);
	}

	public void changeTitle(String title) {
		this.title = title;
	}

	public void changeRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public Room getClone() {
		return Room.builder()
			.id(id)
			.user(user)
			.build();
	}
}
