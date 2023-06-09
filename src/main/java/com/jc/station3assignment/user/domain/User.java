package com.jc.station3assignment.user.domain;

import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jc.station3assignment.exception.authentication.AuthenticationUserException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

	public void checkPassword(String encodedPassword) {
		if (!password.isEqualTo(encodedPassword)) {
			throw new AuthenticationUserException();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User)o;
		return id.equals(user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
