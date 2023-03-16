package com.jc.station3assignment.room.presentation;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jc.station3assignment.authentication.domain.LoginUser;
import com.jc.station3assignment.common.DtoFactory;
import com.jc.station3assignment.room.application.RoomService;
import com.jc.station3assignment.room.application.dto.response.AddRoomResponseDto;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.response.AddRoomResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RoomController {
	private final RoomService roomService;

	@PostMapping("/rooms")
	public ResponseEntity<AddRoomResponse> addRoom(@Valid @RequestBody AddRoomRequest addRoomRequest) {
		//TODO 인가인증 처리 후 수정
		LoginUser loginUser = new LoginUser(1L, "fdevjc@gmail.com");

		AddRoomResponseDto addRoomResponseDto = roomService.addRoom(DtoFactory.addRoomRequestDto(loginUser, addRoomRequest));
		return ResponseEntity.status(HttpStatus.CREATED).body(DtoFactory.addRoomResponse(addRoomResponseDto));
	}
}
