package com.eletronicodigitalmobile.security;



import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	
	public  String generateToken(String username) {
		//LocalDateTime dataHoraAgora = LocalDateTime.now();
		return Jwts.builder()
				
				.setSubject(username)
				.setExpiration( new Date(System.currentTimeMillis()  + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	//.setExpiration(new Date(System.currentTimeMillis() + expiration))
}
