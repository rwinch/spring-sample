package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Bean
	public DefaultSecurityFilterChain configure(HttpSecurity http, CsrfTokenRepository csrfTokenRepository) throws Exception {
		http
			.csrf(csrf -> csrf
				.csrfTokenRepository(csrfTokenRepository)
			)
			.authorizeRequests(authorizeRequests -> authorizeRequests
					.anyRequest().hasAuthority("SCOPE_message:read")
			)
			.oauth2ResourceServer(r -> r.jwt());
		return http.build();
	}

	@Bean
	static CsrfTokenRepository csrfTokenRepository() {
		return new HttpSessionCsrfTokenRepository();
	}
}
