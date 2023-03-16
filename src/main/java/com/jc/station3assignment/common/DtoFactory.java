package com.jc.station3assignment.common;

import java.util.stream.Collectors;

import com.jc.station3assignment.authentication.application.dto.request.SigninRequestDto;
import com.jc.station3assignment.authentication.application.dto.request.SignupRequestDto;
import com.jc.station3assignment.authentication.application.dto.response.SigninResponseDto;
import com.jc.station3assignment.authentication.application.dto.response.SignupResponseDto;
import com.jc.station3assignment.authentication.domain.LoginUser;
import com.jc.station3assignment.authentication.presentation.dto.request.SigninRequest;
import com.jc.station3assignment.authentication.presentation.dto.request.SignupRequest;
import com.jc.station3assignment.authentication.presentation.dto.response.SigninResponse;
import com.jc.station3assignment.authentication.presentation.dto.response.SignupResponse;
import com.jc.station3assignment.room.application.dto.request.AddRoomRequestDto;
import com.jc.station3assignment.room.application.dto.request.ModifyRoomRequestDto;
import com.jc.station3assignment.room.application.dto.response.ModifyRoomResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomIdResponseDto;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.request.ModifyRoomRequest;
import com.jc.station3assignment.room.presentation.dto.response.ModifyRoomResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomIdResponse;

public class DtoFactory {
	public static SignupRequestDto signupRequestDto(SignupRequest signupRequest) {
		return SignupRequestDto.builder()
			.email(signupRequest.getEmail())
			.password(signupRequest.getPassword())
			.nickname(signupRequest.getNickname())
			.name(signupRequest.getName())
			.phoneNumber(signupRequest.getPhoneNumber())
			.build();
	}

	public static SignupResponse signupResponse(SignupResponseDto signupResponseDto) {
		return SignupResponse.builder()
			.id(signupResponseDto.getId())
			.email(signupResponseDto.getEmail())
			.nickname(signupResponseDto.getNickname())
			.build();
	}

	public static SigninRequestDto signinRequestDto(SigninRequest signinRequest) {
		return SigninRequestDto.builder()
			.email(signinRequest.getEmail())
			.password(signinRequest.getPassword())
			.build();
	}

	public static SigninResponse signinResponseDto(SigninResponseDto signinResponseDto) {
		return SigninResponse.builder()
			.tokenType(signinResponseDto.getTokenType())
			.tokenValue(signinResponseDto.getTokenValue())
			.build();
	}

	public static AddRoomRequestDto addRoomRequestDto(LoginUser loginUser, AddRoomRequest addRoomRequest) {
		return AddRoomRequestDto.builder()
			.userId(loginUser.getId())
			.userEmail(loginUser.getEmail())
			.title(addRoomRequest.getTitle())
			.roomType(addRoomRequest.getRoomType())
			.deals(addRoomRequest.getDeals()
				.stream()
				.map(addRoomDealRequest -> addRoomDealRequestDto(addRoomDealRequest))
				.collect(Collectors.toList()))
			.build();
	}

	private static AddRoomRequestDto.AddRoomDealRequestDto addRoomDealRequestDto(AddRoomRequest.AddRoomDealRequest addRoomDealRequest) {
		return AddRoomRequestDto.AddRoomDealRequestDto.builder()
			.dealType(addRoomDealRequest.getDealType())
			.deposit(addRoomDealRequest.getDeposit())
			.rent(addRoomDealRequest.getRent())
			.orderNumber(addRoomDealRequest.getOrderNumber())
			.build();
	}

	public static RoomIdResponse roomIdResponse(RoomIdResponseDto roomIdResponseDto) {
		return new RoomIdResponse(roomIdResponseDto.getId());
	}

	public static ModifyRoomRequestDto modifyRoomRequestDto(LoginUser loginUser, Long roomId, ModifyRoomRequest modifyRoomRequest) {
		return ModifyRoomRequestDto.builder()
			.userId(loginUser.getId())
			.userEmail(loginUser.getEmail())
			.roomId(roomId)
			.title(modifyRoomRequest.getTitle())
			.roomType(modifyRoomRequest.getRoomType())
			.deals(modifyRoomRequest.getDeals()
				.stream()
				.map(modifyRoomDealRequest -> modifyRoomDealRequestDto(modifyRoomDealRequest))
				.collect(Collectors.toList()))
			.build();
	}

	private static ModifyRoomRequestDto.ModifyRoomDealRequestDto modifyRoomDealRequestDto(ModifyRoomRequest.ModifyRoomDealRequest modifyRoomDealRequest) {
		return ModifyRoomRequestDto.ModifyRoomDealRequestDto.builder()
			.dealType(modifyRoomDealRequest.getDealType())
			.deposit(modifyRoomDealRequest.getDeposit())
			.rent(modifyRoomDealRequest.getRent())
			.orderNumber(modifyRoomDealRequest.getOrderNumber())
			.build();
	}

	public static ModifyRoomResponse modifyRoomResponseDto(ModifyRoomResponseDto modifyRoomResponseDto) {
		return ModifyRoomResponse.builder()
			.id(modifyRoomResponseDto.getId())
			.title(modifyRoomResponseDto.getTitle())
			.roomType(modifyRoomResponseDto.getRoomType())
			.deals(modifyRoomResponseDto.getDeals()
				.stream()
				.map(modifyRoomDealResponseDto -> modifyRoomDealResponse(modifyRoomDealResponseDto))
				.collect(Collectors.toList()))
			.build();
	}

	private static ModifyRoomResponse.ModifyRoomDealResponse modifyRoomDealResponse(ModifyRoomResponseDto.ModifyRoomDealResponseDto modifyRoomDealResponseDto) {
		return ModifyRoomResponse.ModifyRoomDealResponse.builder()
			.dealType(modifyRoomDealResponseDto.getDealType())
			.deposit(modifyRoomDealResponseDto.getDeposit())
			.rent(modifyRoomDealResponseDto.getRent())
			.orderNumber(modifyRoomDealResponseDto.getOrderNumber())
			.build();
	}
}
