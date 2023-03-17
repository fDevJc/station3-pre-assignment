package com.jc.station3assignment.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;

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
import com.jc.station3assignment.room.application.dto.request.DealRequestDto;
import com.jc.station3assignment.room.application.dto.request.FindMyRoomsRequestDto;
import com.jc.station3assignment.room.application.dto.request.LoginUserWithRoomIdRequestDto;
import com.jc.station3assignment.room.application.dto.request.ModifyRoomRequestDto;
import com.jc.station3assignment.room.application.dto.response.DealResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomIdResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithDealsResponseDto;
import com.jc.station3assignment.room.application.dto.response.RoomWithMainDealResponseDto;
import com.jc.station3assignment.room.presentation.dto.request.AddRoomRequest;
import com.jc.station3assignment.room.presentation.dto.request.DealRequest;
import com.jc.station3assignment.room.presentation.dto.request.ModifyRoomRequest;
import com.jc.station3assignment.room.presentation.dto.response.DealResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomIdResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomWithDealsResponse;
import com.jc.station3assignment.room.presentation.dto.response.RoomWithMainDealResponse;

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
				.map(DtoFactory::dealRequestDto)
				.collect(Collectors.toList()))
			.build();
	}

	private static DealRequestDto dealRequestDto(DealRequest dealRequest) {
		return DealRequestDto.builder()
			.dealType(dealRequest.getDealType())
			.deposit(dealRequest.getDeposit())
			.rent(dealRequest.getRent())
			.orderNumber(dealRequest.getOrderNumber())
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
				.map(DtoFactory::dealRequestDto)
				.collect(Collectors.toList()))
			.build();
	}

	public static RoomWithDealsResponse roomWithDealsResponse(RoomWithDealsResponseDto roomWithDealsResponseDto) {
		return RoomWithDealsResponse.builder()
			.id(roomWithDealsResponseDto.getId())
			.title(roomWithDealsResponseDto.getTitle())
			.roomType(roomWithDealsResponseDto.getRoomType())
			.deals(roomWithDealsResponseDto.getDeals()
				.stream()
				.map(DtoFactory::dealResponse)
				.collect(Collectors.toList()))
			.build();
	}

	private static DealResponse dealResponse(DealResponseDto dealResponseDto) {
		return DealResponse.builder()
			.dealType(dealResponseDto.getDealType())
			.deposit(dealResponseDto.getDeposit())
			.rent(dealResponseDto.getRent())
			.orderNumber(dealResponseDto.getOrderNumber())
			.build();
	}

	public static LoginUserWithRoomIdRequestDto loginUserWithRoomIdRequestDto(LoginUser loginUser, Long roomId) {
		return LoginUserWithRoomIdRequestDto.builder()
			.userId(loginUser.getId())
			.userEmail(loginUser.getEmail())
			.roomId(roomId)
			.build();
	}

	public static FindMyRoomsRequestDto findMyRoomsRequestDto(LoginUser loginUser, Pageable pageable) {
		return FindMyRoomsRequestDto.builder()
			.userId(loginUser.getId())
			.userEmail(loginUser.getEmail())
			.pageable(pageable)
			.build();
	}

	public static List<RoomWithMainDealResponse> roomWithMainDealResponseList(List<RoomWithMainDealResponseDto> roomWithMainDealResponseDtos) {
		return roomWithMainDealResponseDtos.stream()
			.map(DtoFactory::roomWithMainDealResponse)
			.collect(Collectors.toList());
	}

	public static RoomWithMainDealResponse roomWithMainDealResponse(RoomWithMainDealResponseDto roomWithMainDealResponseDto) {
		return RoomWithMainDealResponse.builder()
			.id(roomWithMainDealResponseDto.getId())
			.title(roomWithMainDealResponseDto.getTitle())
			.roomType(roomWithMainDealResponseDto.getRoomType())
			.roomTypeValue(roomWithMainDealResponseDto.getRoomTypeValue())
			.mainDealType(roomWithMainDealResponseDto.getMainDealType())
			.mainDealTypeValue(roomWithMainDealResponseDto.getMainDealTypeValue())
			.deposit(roomWithMainDealResponseDto.getDeposit())
			.rent(roomWithMainDealResponseDto.getRent())
			.build();
	}

}
