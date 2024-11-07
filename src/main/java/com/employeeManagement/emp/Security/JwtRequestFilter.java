package com.employeeManagement.emp.Security;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.employeeManagement.emp.Interface.JWTTokenInterface;
import com.employeeManagement.emp.service.JWTTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	ApplicationContext context;
	
	private JWTTokenInterface jwtService;
	
	public JwtRequestFilter(JWTTokenService jwtService) {
		this.jwtService = jwtService;
	}
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	
    	try {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
           username = jwtService.extractUserName( token);
        	   
           }
           //SecurityContextHolder.getContext().getAuthentication() ==null-----checks if its already authenticated
           if(username != null && SecurityContextHolder.getContext().getAuthentication() ==null) {
        	   UserDetails userDetails =context.getBean(UserDetailsService.class).loadUserByUsername(username);
        	   if(jwtService.validateToken(token,userDetails)) {
        		   UsernamePasswordAuthenticationToken authToken = new  UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        		   authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        		   SecurityContextHolder.getContext().setAuthentication(authToken);
        	   }
        	   
        }
        // Handle token validation here
        chain.doFilter(request, response);
    }catch(Exception e) {
    	handleException(response, "Access Token Expired or Invalid", HttpServletResponse.SC_UNAUTHORIZED);
        
    }
    	
    }
    private void handleException(HttpServletResponse response, String message, int status) throws IOException {
        // Set the response status and content type
        response.setStatus(status);
        response.setContentType("application/json");

        // Write a custom error message to the response
        String jsonResponse = message;
        response.getWriter().write(jsonResponse);
    }
}