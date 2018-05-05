package com.nvrignite.entities;

import java.io.Serializable;
import java.util.Set;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4746333924452133573L;

	@QuerySqlField(index = true)
	private String userName;

	private String email;

	private String password;
	
	private Set<String> userRoles;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<String> userRoles) {
		this.userRoles = userRoles;
	}

}
