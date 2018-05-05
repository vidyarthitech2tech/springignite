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

import org.apache.shiro.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * Validator for the new user form.
 */
public class UserCreateValidator implements Validator {

    private static final String SIMPLE_EMAIL_REGEX = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}";

    public boolean supports(Class aClass) {
        return UserCommand.class.isAssignableFrom(aClass);
    }

    public void validate(Object o, Errors errors) {
        UserCommand command = (UserCommand)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "error.userName.empty", "Please specify a userName.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email.empty", "Please specify an email address.");
        if( StringUtils.hasText( command.getEmail() ) && !Pattern.matches( SIMPLE_EMAIL_REGEX, command.getEmail().toUpperCase() ) ) {
            errors.rejectValue( "email", "error.email.invalid", "Please enter a valid email address." );
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.empty", "Please specify a password.");
        if(command.getRoles().length < 1)
        	errors.rejectValue( "roles", "error.roles.empty", "Please specify a role for this user.");
        
    }
}
