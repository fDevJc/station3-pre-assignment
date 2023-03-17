package com.jc.station3assignment.room.presentation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.ModifyRoomRequestDto;
import com.jc.station3assignment.room.application.dto.response.ModifyRoomResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomIdResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomResponseDto;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.request.ModifyRoomRequest;
import com.jc.station3assignment.room.presentation.dto.response.ModifyRoomResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomIdResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomResponse;

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
		AddRoomRequestDto addRoomRequestDto = DtoFactory.addRoomRequestDto(loginUser, addRoomRequest);
		RoomIdResponseDto roomIdResponseDto = roomService.addRoom(addRoomRequestDto);
		RoomIdResponse roomIdResponse = DtoFactory.roomIdResponse(roomIdResponseDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(roomIdResponse);
	}

	@ForOnlyLoginUser
	@PutMapping("/rooms/{roomId}")
	public ResponseEntity<ModifyRoomResponse> modifyRoom(
		@Authenticated LoginUser loginUser,
		@PathVariable Long roomId,
		@Valid @RequestBody ModifyRoomRequest modifyRoomRequest
	) {
		ModifyRoomRequestDto modifyRoomRequestDto = DtoFactory.modifyRoomRequestDto(loginUser, roomId, modifyRoomRequest);
		ModifyRoomResponseDto modifyRoomResponseDto = roomService.modifyRoom(modifyRoomRequestDto);
		ModifyRoomResponse modifyRoomResponse = DtoFactory.modifyRoomResponseDto(modifyRoomResponseDto);

		return ResponseEntity.status(HttpStatus.OK).body(modifyRoomResponse);
	}

	@ForOnlyLoginUser
	@DeleteMapping("/rooms/{roomId}")
	public ResponseEntity deleteRoom(
		@Authenticated LoginUser loginUser,
		@PathVariable Long roomId
	) {
		roomService.deleteRoom(DtoFactory.deleteRoomRequestDto(loginUser, roomId));

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@ForOnlyLoginUser
	@GetMapping("/rooms/me")
	public ResponseEntity<List<RoomResponse>> findMyRooms(
		@Authenticated LoginUser loginUser,
		@PageableDefault Pageable pageable
	) {
		List<RoomResponseDto> roomResponseDtos = roomService.findMyRooms(DtoFactory.findMyRoomsRequestDto(loginUser, pageable));
		return ResponseEntity.status(HttpStatus.OK).body(DtoFactory.listRoomResponse(roomResponseDtos));
	}
}
