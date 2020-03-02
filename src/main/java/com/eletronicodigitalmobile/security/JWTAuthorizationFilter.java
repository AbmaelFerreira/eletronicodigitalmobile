package com.eletronicodigitalmobile.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	
	private JWTUtil jwtutil;
	
	UserDetailsService userdetailservice;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtutil, UserDetailsService userdetailservice) {
		super(authenticationManager);
		this.jwtutil = jwtutil;
		this.userdetailservice = userdetailservice;
	}
	
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain) throws IOException, ServletException{
		String header = request.getHeader("Authorization");
		
		if(header != null && header.startsWith("Bearer " )) {
			UsernamePasswordAuthenticationToken auth = getAutheticantion(header.substring(7));
			
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAutheticantion(String token) {
		if(jwtutil.tokenValido(token)) {
			String username = jwtutil.getUsername(token);
			 UserDetails user = userdetailservice.loadUserByUsername(username);
			 return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
}
