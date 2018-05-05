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

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nvrignite.entities.RolesPermission;
import com.nvrignite.entities.User;
import com.nvrignite.service.UserService;

/**
 * Web MVC controller that handles operations related to managing users, such as editing them and deleting them. 
 */
@Controller
public class ManageUsersController {

    private EditUserValidator editUserValidator = new EditUserValidator();

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/manageUsers")
    @RequiresPermissions("user:manage")
    public void manageUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
    }

    @RequestMapping(value="/editUser",method= RequestMethod.GET)
    @RequiresPermissions("user:edit")
    public String showEditUserForm(Model model, @RequestParam String name, @ModelAttribute EditUserCommand command) {

        User user = userService.getUser(name);
        command.setUserName(user.getUserName());
        command.setEmail(user.getEmail());
        
        String[] roles = new String[user.getUserRoles().size()];
        int index=0;
        for(String role : user.getUserRoles()){
        	roles[index]=role;
        	index++;
        }
        command.setUserRoles(roles);
        model.addAttribute("mappedRoles",roles);
        model.addAttribute("roles",userService.getAllRoles());
        return "editUser";
    }

    @RequestMapping(value="/editUser",method= RequestMethod.POST)
    @RequiresPermissions("user:edit")
    public String editUser(Model model, @RequestParam String name, @ModelAttribute EditUserCommand command, BindingResult errors) {
        editUserValidator.validate( command, errors );

        if( errors.hasErrors() ) {
            return "editUser";
        }
        User user = userService.getUser(name);
        command.updateUser(user);
        userService.updateUser(user);        
        return "redirect:/manageUsers";
    }

    @RequestMapping("/deleteUser")
    @RequiresPermissions("user:delete")
    public String deleteUser(@RequestParam String userName) {
        Assert.isTrue(!userName.equalsIgnoreCase("admin"), "Cannot delete admin user" );
        userService.deleteUser(userName);
        return "redirect:/manageUsers";
    }

    
    @RequestMapping(value="/addroles",method= RequestMethod.GET)
    @RequiresPermissions("role:manage")
    public String addRoles(Model model, @ModelAttribute RolePermissionCommand rolePermissionCommand) {
    	return "addroles";
    }

    
    @RequestMapping(value="/addroles",method= RequestMethod.POST)
    @RequiresPermissions("role:manage")
    public String addRoless(Model model, @ModelAttribute RolePermissionCommand rolePermissionCommand) {
    	RolesPermission roles = new RolesPermission();
    	roles.setPermission(rolePermissionCommand.getPermission());
    	roles.setRoleName(rolePermissionCommand.getRoleName());
    	userService.createRoleAndPermission(roles);
        model.addAttribute( "users", userService.getAllUsers() );
        return "redirect:/home";
    }

    
}
