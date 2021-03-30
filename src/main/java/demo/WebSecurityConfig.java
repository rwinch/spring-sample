package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public DefaultSecurityFilterChain configure(AuthenticationFilter authenticationFilter, HttpSecurity http) throws Exception {
		if (authenticationFilter != null) {
			http.addFilter(authenticationFilter);
		}
		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.httpBasic();
		return http.build();
	}

	@Bean
	public InMemoryUserDetailsManager userDetails() throws Exception {
		UserDetails user = User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.build();
		return new InMemoryUserDetailsManager(user);
	}

}
