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
import com.jc.station3assignment.room.application.dto.response.AddRoomResponseDto;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.response.AddRoomResponse;

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

	public static AddRoomResponse addRoomResponse(AddRoomResponseDto addRoomResponseDto) {
		return new AddRoomResponse(1L);
	}
}
