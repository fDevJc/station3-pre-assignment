package com.jc.station3assignment.room.domain;

public enum RoomType {
	ONE_ROOM("원룸"), TWO_ROOM("투룸"), THREE_ROOM("쓰리룸"), OFFICE_HOTEL("오피스텔"), APARTMENT("아파트");

	private final String value;

	RoomType(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
