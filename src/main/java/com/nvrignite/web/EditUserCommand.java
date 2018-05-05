/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.nvrignite.web;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.nvrignite.entities.User;

/**
 * Command binding object for editing a user.
 */
public class EditUserCommand {

	private String userName;
	private String email;
	private String password;
	private String[] userRoles;

	public String[] getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(String[] userRoles) {
		this.userRoles = userRoles;
	}
	
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

	public void updateUser(User user) {
		Assert.isTrue(userName.equalsIgnoreCase(user.getUserName()), "User ID of command must match the user being updated.");
		user.setUserName(getUserName());
		user.setEmail(getEmail());
		if (StringUtils.hasText(getPassword())) {
			//user.setPassword(new Sha256Hash(getPassword()).toHex());
			user.setPassword(getPassword());
		} 
		Set<String> roles = new HashSet<>();
		for(String role : getUserRoles()){
			roles.add(role);
		}
		user.setUserRoles(roles);
	}
}
