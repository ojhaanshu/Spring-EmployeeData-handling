package com.employeeManagement.emp.Interface;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;

import com.employeeManagement.emp.pojo.Users;

import io.jsonwebtoken.Claims;

public interface JWTTokenInterface {
	public String doGenerateToken(  String username);
	 public SecretKey getKey();
	 public String verify(Users user);
		public <T> T extractClaim(String token ,Function<Claims,T> claimResolver);
		public Claims extractAllClaims(String token);
		public String extractUserName(String token);
		public boolean validateToken(String token, UserDetails userDetails);
		public boolean isTokenExpired(String token) ;
		public Date extractExpiration(String token) ;
}
