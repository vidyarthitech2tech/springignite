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
package com.nvrignite.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nvrignite.entities.RolesPermission;
import com.nvrignite.entities.User;
import com.nvrignite.repository.RolePermissionRepository;
import com.nvrignite.repository.UserRepository;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Override
	public User getCurrentUser() {
		final String userName = (String) SecurityUtils.getSubject().getPrincipal();
		if (userName != null) {
			return getUser(userName);
		} else {
			return null;
		}
	}

	@Override
	public void createAdminUser(String userName, String email, String password) {
		User user = new User();
		user.setUserName(userName);
		user.setEmail(email);
		user.setPassword(password);
		Set<String> roles = new HashSet<>();
		roles.add("admin");
		user.setUserRoles(roles);
		userRepository.save(userName, user);
	}

	@Override
	public User getUser(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public List<User> getAllUsers() {
		Iterable<User> user = userRepository.findAll();
		List<User> users = new ArrayList<>();
		user.forEach(users::add);
		return users;
	}

	@Override
	public void deleteUser(String userName) {
		userRepository.delete(userName);
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user.getUserName(), user);
	}

	@Override
	public void createUser(User user) {
		userRepository.save(user.getUserName(), user);
	}

	@Override
	public Set<String> getAllRoles() {
		Set<String> roleSet = new HashSet<>();
		Iterable<RolesPermission> roles = rolePermissionRepository.findAll();
		for (RolesPermission role : roles) {
			roleSet.add(role.getRoleName());
		}
		return roleSet;
	}

	@Override
	public void createRoleAndPermission(RolesPermission rolesPermission) {
		rolePermissionRepository.save(rolesPermission.getRoleName(), rolesPermission);
	}

}
