package com.jc.station3assignment.room.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jc.station3assignment.room.domain.Room;
import com.jc.station3assignment.user.domain.User;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	List<Room> findAllByUser(Pageable pageable, User user);
}
