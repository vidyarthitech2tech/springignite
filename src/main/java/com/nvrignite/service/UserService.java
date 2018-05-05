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

import java.util.List;
import java.util.Set;

import com.nvrignite.entities.RolesPermission;
import com.nvrignite.entities.User;

/**
 * A service interface for accessing and modifying user data in the system.
 */
public interface UserService {

    User getCurrentUser();

    void createAdminUser(String userName, String email, String password);

    void createUser(User user);

    void updateUser(User user);

    List<User> getAllUsers();

    User getUser(String userName);

    void deleteUser(String userName);

	Set<String> getAllRoles();

	void createRoleAndPermission(RolesPermission rolePerm);
}
