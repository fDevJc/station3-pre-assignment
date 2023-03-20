package com.jc.station3assignment.config.authentication.provider;

import java.util.Date;

import com.jc.station3assignment.config.authentication.JwtTokenProvider;
import com.jc.station3assignment.exception.authentication.ExpiredTokenException;
import com.jc.station3assignment.exception.authentication.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class DefaultJwtTokenProvider implements JwtTokenProvider {
	private final String secretKey;
	private final long expiration;

	public DefaultJwtTokenProvider(String secretKey, long expiration) {
		this.secretKey = secretKey;
		this.expiration = expiration;
	}

	@Override
	public String createToken(Long id, String email) {
		Date now = new Date();
		Date expirationTime = new Date(now.getTime() + expiration);

		return Jwts.builder()
			.claim(CLAIM_USER_ID, id.toString())
			.claim(CLAIM_USER_EMAIL, email)
			.setIssuedAt(now)
			.setExpiration(expirationTime)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	@Override
	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			throw new ExpiredTokenException(e.getMessage());
		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidTokenException(e.getMessage());
		}
	}

	@Override
	public String getPayload(String token, String key) {
		try {
			return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody()
				.get(key, String.class);
		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidTokenException(e.getMessage());
		}
	}
}
