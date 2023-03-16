package com.jc.station3assignment.user.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
// @Builder(access = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Email email;

	@Embedded
	private Password password;

	@Embedded
	private Nickname nickname;

	@Embedded
	private Name name;

	@Embedded
	private PhoneNumber phoneNumber;

	public static User createSignupUser(String email, String password, String nickname, String name, String phoneNumber) {
		return User.builder()
			.email(new Email(email))
			.password(new Password(password))
			.nickname(new Nickname(nickname))
			.name(new Name(name))
			.phoneNumber(new PhoneNumber(phoneNumber))
			.build();
	}
}
