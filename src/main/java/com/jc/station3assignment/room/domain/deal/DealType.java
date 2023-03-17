package com.jc.station3assignment.room.domain.deal;

public enum DealType {
	LONG_TERM_RENT("전세"), MONTHLY_RENT("월세");

	private final String value;

	DealType(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
