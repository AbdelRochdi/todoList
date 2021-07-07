//package com.pragmatic.todoList.mysql.dto;
//
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//public class MyUserDetails implements UserDetails {
//
//	private static final long serialVersionUID = -3904204175892736139L;
//
//	private final List<? extends GrantedAuthority> grantedAuthorithies;
//	private final String password;
//	private final String username;
//	private final boolean isAccountNonExpired;
//	private final boolean isAccountNonLocked;
//	private final boolean isCredentialsNonExpired;
//	private final boolean isEnabled;
//
//	public MyUserDetails(List<? extends GrantedAuthority> grantedAuthorithies, String password, String username,
//			boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired,
//			boolean isEnabled) {
//		this.grantedAuthorithies = grantedAuthorithies;
//		this.password = password;
//		this.username = username;
//		this.isAccountNonExpired = isAccountNonExpired;
//		this.isAccountNonLocked = isAccountNonLocked;
//		this.isCredentialsNonExpired = isCredentialsNonExpired;
//		this.isEnabled = isEnabled;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return grantedAuthorithies;
//	}
//
//	@Override
//	public String getPassword() {
//		// TODO Auto-generated method stub
//		return password;
//	}
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return username;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return isAccountNonExpired;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return isAccountNonLocked;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return isCredentialsNonExpired;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return isEnabled;
//	}
//
//}
