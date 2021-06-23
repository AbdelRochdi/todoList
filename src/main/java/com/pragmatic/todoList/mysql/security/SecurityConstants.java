package com.pragmatic.todoList.mysql.security;

public class SecurityConstants {

	public static final long EXPIRATION_TIME = 10000000;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/api/users";
	public static final String TOKEN_SECRET = "myTokenSecret";
	
}
