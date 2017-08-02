/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/password")
public class PasswordController {
	private final UserDetailsManager users;

	private final PasswordEncoder passwordEncoder;

	public PasswordController(UserDetailsManager users, PasswordEncoder passwordEncoder) {
		this.users = users;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/forgot")
	public String passwordForgot(@ModelAttribute PasswordForm passwordForm) {
		// default the fields for easier demo
		passwordForm.setSecret("secret");
		passwordForm.setUsername("user");
		return "password/forgot";
	}

	@PostMapping("/forgot")
	public String passwordChange(@ModelAttribute PasswordForm passwordForm) {
		// FIXME no validation of the secret
		UserDetails user = this.users
				.loadUserByUsername(passwordForm.getUsername());
		String password = this.passwordEncoder.encode(passwordForm.getPassword());
		UserDetails newPasswordUser = User.withUsername(user.getUsername())
				.password(password)
				.authorities(new ArrayList<>(user.getAuthorities()))
				.build();
		this.users.updateUser(newPasswordUser);
		return "redirect:/";
	}
}
