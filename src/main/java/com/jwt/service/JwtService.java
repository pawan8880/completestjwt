package com.jwt.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	private static final String SECRET = "hfjhussdhdhgftrdfgyjkosjnchsijjkjhjhhijuhgfj";
	//private static final String SECRET = System.getenv("JWT_SECRET");

	public String generateToken(String userName) {
		
		Map<String,Object> claims = new HashMap<>();
		
	return 	Jwts.builder().setClaims(claims)
		.setSubject(userName)
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
		.signWith(getSignKey(),SignatureAlgorithm.HS256)
		.compact();
		
		 
		
	}
	
	private Key getSignKey() {
		byte[] keyByte = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	public String extractUserName(String token) {
		return extractClaims(token, Claims::getSubject);
		
	}
	
	
	private <T> T extractClaims(String token, Function<Claims,T> claimResolver){
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	} 
	
	public Date extractExpiration(String token) {
		return extractClaims(token,Claims::getExpiration);
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
		
	} 
	
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
