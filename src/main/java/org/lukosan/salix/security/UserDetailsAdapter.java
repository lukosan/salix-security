package org.lukosan.salix.security;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsAdapter implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private SalixUser salixUser;
	
	public UserDetailsAdapter(SalixUser salixUser) {
		this.salixUser = salixUser;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String[] roles = Arrays.stream(salixUser.getRoles()).map(r -> "ROLE_" + r).toArray(size -> new String[size]);
		return AuthorityUtils.createAuthorityList(roles);
	}

	@Override
	public String getPassword() {
		return salixUser.getPassword();
	}

	@Override
	public String getUsername() {
		return salixUser.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static SalixUser userFromAuthentication(Authentication authentication) {
		return null != authentication && UserDetailsAdapter.class.isAssignableFrom(authentication.getPrincipal().getClass())
				? ((UserDetailsAdapter) authentication.getPrincipal()).salixUser : null;
	}
	
	public static SalixUser userFromPrincipal(Principal principal) {
		return null != principal && UserDetailsAdapter.class.isAssignableFrom(principal.getClass())
				? ((UserDetailsAdapter) principal).salixUser : null;
	}

}
