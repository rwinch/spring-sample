package example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;

@Configuration(proxyBeanMethods = false)
public class SecurityConfig {
	@Bean
	SecurityWebFilterChain springSecurityFilter(ServerHttpSecurity http) {
		RedirectServerAuthenticationEntryPoint redirect = new RedirectServerAuthenticationEntryPoint("/oauth2/authorization/spring");
		http
			.authorizeExchange(exchange -> exchange
				.anyExchange().authenticated()
			)
			.oauth2Login(Customizer.withDefaults())
			.exceptionHandling(handle -> handle
				.authenticationEntryPoint(redirect)
			);
		return http.build();
	}
}
