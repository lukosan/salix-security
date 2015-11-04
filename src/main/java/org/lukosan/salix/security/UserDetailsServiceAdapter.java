package org.lukosan.salix.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceAdapter implements UserDetailsService {

	@Autowired
	private SalixSecurityService salixSecurityService;
	
	@Autowired(required=false)
	private SalixSecurityProperties properties;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(null != properties)
			for(SalixUser user : properties.getUsers())
				if(user.getUsername().equalsIgnoreCase(username))
					return new UserDetailsAdapter(user);
		
		SalixUser user = salixSecurityService.user(username);
		
		if(null == user)
			throw new UsernameNotFoundException("Username [" + username + "] not found");
		
		return new UserDetailsAdapter(user);
		
	}

}