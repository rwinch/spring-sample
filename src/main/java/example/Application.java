package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer.authorizationServer;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	SecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
		http
			.httpBasic(Customizer.withDefaults())
			.authorizeHttpRequests(requests -> requests
				.anyRequest().authenticated()
			)
			.with(authorizationServer(), authz -> authz
				.oidc(Customizer.withDefaults())
			);
		return http.build();
	}
}
