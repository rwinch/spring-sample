package example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration(proxyBeanMethods = false)
class SecurityConfig {
	@Bean
	SecurityWebFilterChain springSecurity(ServerHttpSecurity http) {
		http
			.httpBasic(Customizer.withDefaults())
			.authorizeExchange(e -> e
					.pathMatchers("/api/v1/foo").permitAll()
					.pathMatchers("/api/v1/bar").authenticated()
					.pathMatchers("/api/v1/baz").hasRole("BAZ")
			);
		return http.build();
	}

	@Bean
	ReactiveUserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();
		UserDetails baz = User.withDefaultPasswordEncoder()
				.username("baz")
				.password("password")
				.roles("BAZ")
				.build();
		return new MapReactiveUserDetailsService(user, baz);
	}
}
