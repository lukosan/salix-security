package org.lukosan.salix.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("salix.security")
public class SalixSecurityProperties {

	private List<UiSalixUser> users = new ArrayList<>();

	public List<UiSalixUser> getUsers() {
		return users;
	}

	public void setUsers(List<UiSalixUser> users) {
		this.users = users;
	}
}