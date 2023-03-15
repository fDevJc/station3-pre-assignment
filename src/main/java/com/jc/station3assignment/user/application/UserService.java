package com.jc.station3assignment.user.application;

import org.springframework.stereotype.Service;

import com.jc.station3assignment.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
}
