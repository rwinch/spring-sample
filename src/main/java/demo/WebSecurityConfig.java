package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequests -> authorizeRequests
					.anyRequest().hasAuthority("SCOPE_message:read")
			)
			.oauth2ResourceServer(r -> r.jwt());
		return http.build();
	}

}
