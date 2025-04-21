package com.app.excel.security;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.app.excel.constants.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.app.excel.constants.Constants.AUTHORITIES_KEY;
import static com.app.excel.constants.Constants.SIGNING_KEY;

@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expirationMs}")
	private long expirationMs;

	public String generateToken(Authentication authentication) {
		final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_SECONDS);

		return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities).setIssuedAt(now)
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, SIGNING_KEY).compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody().getSubject();
			return true;
		} catch (SecurityException | MalformedJwtException ex) {
			// System.out.println("Invalid JWT signature.");
		} catch (ExpiredJwtException ex) {
			// System.out.println("Expired JWT token.");
		} catch (UnsupportedJwtException ex) {
			// System.out.println("Unsupported JWT token.");
		} catch (IllegalArgumentException ex) {
			// System.out.println("JWT claims string is empty.");
		}
		return false;
}

	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public String getRoleFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
		return claims.get("role", String.class);
	}
}
