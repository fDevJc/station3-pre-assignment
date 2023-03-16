package com.jc.station3assignment.room.presentation;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jc.station3assignment.authentication.domain.LoginUser;
import com.jc.station3assignment.common.DtoFactory;
import com.jc.station3assignment.config.authentication.annotation.Authenticated;
import com.jc.station3assignment.config.authentication.annotation.ForOnlyLoginUser;
import com.jc.station3assignment.room.application.RoomService;
import com.jc.station3assignment.room.application.dto.response.ModifyRoomResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomIdResponseDto;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.request.ModifyRoomRequest;
import com.jc.station3assignment.room.presentation.dto.response.ModifyRoomResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomIdResponse;

import lombok.RequiredArgsConstructor;

/*	TODO
 *	- Location, Redirect
 */

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RoomController {
	private final RoomService roomService;

	@ForOnlyLoginUser
	@PostMapping("/rooms")
	public ResponseEntity<RoomIdResponse> addRoom(
		@Authenticated LoginUser loginUser,
		@Valid @RequestBody AddRoomRequest addRoomRequest
	) {
		RoomIdResponseDto roomIdResponseDto = roomService.addRoom(DtoFactory.addRoomRequestDto(loginUser, addRoomRequest));
		return ResponseEntity.status(HttpStatus.CREATED).body(DtoFactory.roomIdResponse(roomIdResponseDto));
	}

	@ForOnlyLoginUser
	@PutMapping("/rooms/{roomId}")
	public ResponseEntity<ModifyRoomResponse> modifyRoom(
		@Authenticated LoginUser loginUser,
		@PathVariable Long roomId,
		@Valid @RequestBody ModifyRoomRequest modifyRoomRequest
	) {
		ModifyRoomResponseDto modifyRoomResponseDto = roomService.modifyRoom(DtoFactory.modifyRoomRequestDto(loginUser, roomId, modifyRoomRequest));
		return ResponseEntity.status(HttpStatus.OK).body(DtoFactory.modifyRoomResponseDto(modifyRoomResponseDto));
	}
}
