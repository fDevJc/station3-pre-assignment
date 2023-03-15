package com.jc.station3assignment.user.presentation;

import org.springframework.web.bind.annotation.RestController;

import com.jc.station3assignment.user.application.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {
	private final UserService userService;
}
