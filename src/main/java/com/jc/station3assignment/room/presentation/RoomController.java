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
import com.jc.station3assignment.config.search.annotation.SearchCondition;
import com.jc.station3assignment.room.application.RoomService;
import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.FindMyRoomsRequestDto;
import com.jc.station3assignment.room.application.dto.request.LoginUserWithRoomIdRequestDto;
import com.jc.station3assignment.room.application.dto.request.ModifyRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.SearchRoomDto;
import com.jc.station3assignment.room.application.dto.response.RoomIdResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithDealsResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithMainDealResponseDto;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.request.ModifyRoomRequest;
import com.jc.station3assignment.room.presentation.dto.response.RoomIdResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomWithDealsResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomWithMainDealResponse;

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
		AddRoomRequestDto requestDto = DtoFactory.addRoomRequestDto(loginUser, addRoomRequest);
		RoomIdResponseDto responseDto = roomService.addRoom(requestDto);
		RoomIdResponse response = DtoFactory.roomIdResponse(responseDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@ForOnlyLoginUser
	@PutMapping("/rooms/{roomId}")
	public ResponseEntity<RoomWithDealsResponse> modifyRoom(
		@Authenticated LoginUser loginUser,
		@PathVariable Long roomId,
		@Valid @RequestBody ModifyRoomRequest modifyRoomRequest
	) {
		ModifyRoomRequestDto requestDto = DtoFactory.modifyRoomRequestDto(loginUser, roomId, modifyRoomRequest);
		RoomWithDealsResponseDto responseDto = roomService.modifyRoom(requestDto);
		RoomWithDealsResponse response = DtoFactory.roomWithDealsResponse(responseDto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ForOnlyLoginUser
	@DeleteMapping("/rooms/{roomId}")
	public ResponseEntity deleteRoom(
		@Authenticated LoginUser loginUser,
		@PathVariable Long roomId
	) {
		LoginUserWithRoomIdRequestDto requestDto = DtoFactory.loginUserWithRoomIdRequestDto(loginUser, roomId);
		roomService.deleteRoom(requestDto);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@ForOnlyLoginUser
	@GetMapping("/rooms/me")
	public ResponseEntity<List<RoomWithMainDealResponse>> findMyRooms(
		@Authenticated LoginUser loginUser,
		@PageableDefault Pageable pageable
	) {
		FindMyRoomsRequestDto requestDto = DtoFactory.findMyRoomsRequestDto(loginUser, pageable);
		List<RoomWithMainDealResponseDto> responseDtoList = roomService.findMyRooms(requestDto);
		List<RoomWithMainDealResponse> responseList = DtoFactory.roomWithMainDealResponseList(responseDtoList);

		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}

	@ForOnlyLoginUser
	@GetMapping("/rooms/{roomId}/me")
	public ResponseEntity<RoomWithDealsResponse> findMyRoom(
		@Authenticated LoginUser loginUser,
		@PathVariable Long roomId
	) {
		LoginUserWithRoomIdRequestDto requestDto = DtoFactory.loginUserWithRoomIdRequestDto(loginUser, roomId);
		RoomWithDealsResponseDto responseDto = roomService.findMyRoom(requestDto);
		RoomWithDealsResponse response = DtoFactory.roomWithDealsResponse(responseDto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@ForOnlyLoginUser
	@GetMapping("/rooms")
	public ResponseEntity<List<RoomWithMainDealResponse>> findAllRooms(
		@PageableDefault Pageable pageable,
		@SearchCondition SearchRoomDto searchRoomDto
	) {
		searchRoomDto.addPageable(pageable);
		List<RoomWithMainDealResponseDto> responseDtoList = roomService.findAllRooms(searchRoomDto);
		List<RoomWithMainDealResponse> responseList = DtoFactory.roomWithMainDealResponseList(responseDtoList);

		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}
}
