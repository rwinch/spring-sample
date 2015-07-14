package org.springframework.security.sample;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");
	}

	@Bean
	public RequestDataValueProcessor requestDataValueProcessor() {
		return new RequestDataValueProcessor() {

			public String processAction(HttpServletRequest request, String action) {
				return action+"?processed";
			}

			public String processAction(HttpServletRequest request, String action, String method) {

				return action+"?processed";
			}

			public String processFormFieldValue(HttpServletRequest request, String name,
					String value, String type) {
				return value;
			}

			public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
				return Collections.emptyMap();
			}

			public String processUrl(HttpServletRequest request, String url) {
				return url;
			}
		};
	}
}
