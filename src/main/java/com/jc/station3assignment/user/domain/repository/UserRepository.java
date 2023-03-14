package com.jc.station3assignment.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jc.station3assignment.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
