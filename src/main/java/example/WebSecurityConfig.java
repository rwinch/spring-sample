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
package example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Rob Winch
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	// @formatter:off
@Bean
public DefaultSecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
	http
		.securityContext(s -> s
			.requireExplicitSave(true)
		)
		.authorizeHttpRequests(exchange -> exchange
			.mvcMatchers("/secure/**").authenticated()
			.anyRequest().permitAll()
		)
		.formLogin(withDefaults());
	return http.build();
}
	// @formatter:on

	@Bean
	public static InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.roles("USER")
			.build();
		return new InMemoryUserDetailsManager(user);
	}

	public static class AuthorizationFilter extends GenericFilterBean {

		@Override
		public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		}

		@Override
		protected void initFilterBean() throws ServletException {
			throw new RuntimeException("");
		}
	}
}
