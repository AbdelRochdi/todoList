package com.pragmatic.todoList.mysql.security;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(SecurityConstants.HEADER_STRING);

		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);

	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		String token = request.getHeader(SecurityConstants.HEADER_STRING);

		if (token != null) {

			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET).parseClaimsJws(token);

			Claims body = claimsJws.getBody();

			String userName = body.getSubject();
			
			@SuppressWarnings("unchecked")
			List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
			
			Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
					.map(m -> new SimpleGrantedAuthority(m.get("authority")))
					.collect(Collectors.toSet());

			if (userName != null) {
				return new UsernamePasswordAuthenticationToken(userName, null, simpleGrantedAuthorities);
			}

			return null;

		}
		return null;

	}
}
