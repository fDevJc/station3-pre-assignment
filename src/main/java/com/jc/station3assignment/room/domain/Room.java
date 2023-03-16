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

import lombok.Builder;
import lombok.Getter;

@Getter
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
	public Room(User user, String title, RoomType roomType) {
		this.user = user;
		this.title = title;
		this.roomType = roomType;
	}

	//==연관관계 매핑==
	public void addDeals(List<Deal> deals) {
		this.deals.addAll(this, deals);
	}
}
