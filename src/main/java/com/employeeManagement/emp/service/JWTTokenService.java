package com.employeeManagement.emp.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.employeeManagement.emp.Interface.JWTTokenInterface;
import com.employeeManagement.emp.pojo.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTTokenService implements JWTTokenInterface{
@Lazy
	@Autowired
 public AuthenticationManager authenticationManager;
 
 
	
	public static final long TOKEN_VALIDITY_PERIOD = 5*60*60;
	private String secretKey ="";
	 private Key key;  // Use a Key instead of String for the secret

	    public JWTTokenService() {
	    	
	       try {
			KeyGenerator keyGen =KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			
			throw new RuntimeException(e);
		}
	    }
	    
	    public SecretKey getKey() {
	    	byte[] keyBytes = Decoders.BASE64.decode(secretKey);
	    	return Keys.hmacShaKeyFor(keyBytes);
	    }
	public String doGenerateToken(  String username) {
		Map<String,Object> claims = new HashMap<>();
		return Jwts.builder().claims()
				             .add(claims)
							 .subject(username)
							 .issuedAt(new Date(System.currentTimeMillis()))
							 .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_PERIOD *1000))
							 .and()
							 .signWith(getKey())
							 .compact();
							 	
	}
	public String verify(Users user) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		if (authentication.isAuthenticated()) {
			return doGenerateToken(user.getUsername());
		}
		return "User not valid";
	}
	
	public <T> T extractClaim(String token ,Function<Claims,T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build().parseSignedClaims(token).getPayload();
	}

	public String extractUserName(String token) {
		
		return extractClaim(token,Claims::getSubject);
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
		
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) {
		
		return extractClaim(token,Claims::getExpiration);
	}

}
