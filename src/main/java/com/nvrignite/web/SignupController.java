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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nvrignite.entities.User;
import com.nvrignite.service.UserService;

/**
 * Web MVC controller that handles signup requests.
 */
@Controller
public class SignupController {

	private SignupValidator signupValidator = new SignupValidator();
	private UserCreateValidator userCreateValidator = new UserCreateValidator();

	private UserService userService;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String showSignupForm(Model model, @ModelAttribute SignupCommand command) {
		return "signup";
	}

	@RequestMapping(value = "/createnewuser", method = RequestMethod.GET)
	public String showCreateNewUserForm(Model model, @ModelAttribute UserCommand command) {
		model.addAttribute("roles", userService.getAllRoles());
		return "createnewuser";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String showSignupForm(Model model, @ModelAttribute SignupCommand command, BindingResult errors) {
		signupValidator.validate(command, errors);

		if (errors.hasErrors()) {
			return showSignupForm(model, command);
		}
		userService.createAdminUser(command.getUserName(), command.getEmail(), command.getPassword());
		// Login the newly created user
		SecurityUtils.getSubject().login(new UsernamePasswordToken(command.getUserName(), command.getPassword()));

		return "redirect:/home";
	}

	@RequestMapping(value = "/createnewuser", method = RequestMethod.POST)
	public String createNewUser(Model model, @ModelAttribute UserCommand userCommand, BindingResult errors) {
		userCreateValidator.validate(userCommand, errors);

		if (errors.hasErrors()) {
			return showCreateNewUserForm(model, userCommand);
		}

		if (userService.getUser(userCommand.getUserName()) != null) {
			errors.rejectValue("userName", "error.userName.invalid", "UserName already in use.");
			return showCreateNewUserForm(model, userCommand);
		}

		User user = new User();
		user.setEmail(userCommand.getEmail());
		user.setPassword(userCommand.getPassword());
		user.setUserName(userCommand.getUserName());
		Set<String> roles = new HashSet<>();
		for (String role : userCommand.getRoles()) {
			roles.add(role);
		}
		user.setUserRoles(roles);

		userService.createUser(user);
		model.addAttribute("users", userService.getAllUsers());
		return "redirect:/manageUsers";
	}
	
	//setters
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
