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

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Rob Winch
 *
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Map all usernames extracted from the X509 to a valid user. You can customize this if you want.
	 * @return
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> User.withUsername(username).password("NOT-USED").roles("USER").build();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.x509()
				.subjectPrincipalRegex("OU=app\\:(.*?)(?:,|$)")
				.and()
			.authorizeRequests()
				.anyRequest().authenticated();
	}
}
