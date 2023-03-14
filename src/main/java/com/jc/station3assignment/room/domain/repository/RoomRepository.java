package com.jc.station3assignment.room.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jc.station3assignment.room.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
