package com.jc.station3assignment.room.domain.deal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.jc.station3assignment.room.domain.Room;

@Embeddable
public class Deals {
	@OneToMany(
		mappedBy = "room",
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<Deal> deals = new ArrayList<>();

	public void addAll(Room room, List<Deal> deals) {
		this.deals = deals;
		deals.forEach(deal -> deal.addRoom(room));
	}

	/*
		TODO
		더티체킹을 활용할지 세이브를 활용할지 고민
	 */
	public void removeRoom() {
		// deals.forEach(deal -> deal.removeRoom());
		deals.clear();
	}

	public List<Deal> getDeals() {
		return Collections.unmodifiableList(deals);
	}
}
