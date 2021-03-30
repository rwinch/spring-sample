package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public DefaultSecurityFilterChain configure(Optional<AuthenticationFilter> authenticationFilter, HttpSecurity http) throws Exception {
		authenticationFilter.map(f -> http.addFilterAfter(f, BasicAuthenticationFilter.class));
		http
			.authorizeRequests(requests -> requests
				.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	public InMemoryUserDetailsManager userDetails() throws Exception {
		UserDetails user = User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.roles("USER")
			.build();
		return new InMemoryUserDetailsManager(user);
	}

}
