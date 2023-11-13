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
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

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
	public DefaultSecurityFilterChain springSecurityFilter(HttpSecurity http, HandlerMappingIntrospector hmi) throws Exception {
		http
			.authorizeHttpRequests(exchange -> exchange
				.requestMatchers("/foo").permitAll()
				.requestMatchers("/bar").permitAll()
				.requestMatchers("/public/**").permitAll()
				.requestMatchers("/admin/**", "/admin").denyAll()
				.anyRequest().authenticated()
			)
//			.addFilterBefore(hmi.createCacheFilter(), ChannelProcessingFilter.class)
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
}
