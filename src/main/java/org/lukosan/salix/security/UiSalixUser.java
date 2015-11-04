package org.lukosan.salix.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UiSalixUser implements SalixUser {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String privileges;
	private Map<String, List<String>> map = new HashMap<String, List<String>>();
	
	@Override
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPrivileges() {
		return privileges;
	}
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
		for(String perScope : privileges.split(";")) {
			String[] ab = perScope.split(":");
			if(ab.length == 2) {
				if(! map.containsKey(ab[0]))
					map.put(ab[0], new ArrayList<String>());
				map.get(ab[0]).addAll(Arrays.stream(ab[1].split(",")).collect(Collectors.toList()));
			}
		}
	}
	@Override
	public String[] getRoles() {
		Set<String> roles = new HashSet<String>();
		map.values().forEach(a -> roles.addAll(a));
		return roles.toArray(new String[0]);
	}
	@Override
	public boolean hasRole(String scope, String role) {
		return map.containsKey(scope) && map.get(scope).contains(role);
	}
	@Override
	public Map<String, Object> getMap() {
		return null;
	}
}