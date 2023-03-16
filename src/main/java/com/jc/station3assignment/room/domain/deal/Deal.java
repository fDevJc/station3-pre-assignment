package com.jc.station3assignment.room.domain.deal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jc.station3assignment.room.domain.Room;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Deal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	@Enumerated(EnumType.STRING)
	private DealType dealType;

	private Integer deposit;
	private Integer rent;
	private Integer orderNumber;

	@Builder
	public Deal(Long id, Room room, DealType dealType, Integer deposit, Integer rent, Integer orderNumber) {
		this.id = id;
		this.room = room;
		this.dealType = dealType;
		this.deposit = deposit;
		this.rent = rent;
		this.orderNumber = orderNumber;
	}

	public void addRoom(Room room) {
		this.room = room;
	}
}
