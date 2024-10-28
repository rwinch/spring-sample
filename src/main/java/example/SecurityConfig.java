package example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

	@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
		http
			.authorizeExchange((e) -> e
				.anyExchange().permitAll()
			)
			.csrf(c -> c.disable());
		return http.build();
	}
}
