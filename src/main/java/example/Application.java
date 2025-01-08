package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableWebSecurity
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	SecurityFilterChain springSecurity(HttpSecurity http, OAuth2AuthorizationRequestResolver resolver) throws Exception {
		http
			.authorizeHttpRequests( r -> r
				.anyRequest().authenticated()
			)
			.oauth2Login(l -> l
				.authorizationEndpoint(a -> a
					.authorizationRequestResolver(resolver)
				)
			);
		return http.build();
	}

	@Bean
	OAuth2AuthorizationRequestResolver pkceResolver(ClientRegistrationRepository clientRegistrationRepository) {
		DefaultOAuth2AuthorizationRequestResolver resolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository,"/oauth2/authorization");
		resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());
		return resolver;
	}

	@Bean
	DefaultOAuth2UserService userService() {
		DefaultOAuth2UserService service = new DefaultOAuth2UserService();
		service.setAttributesConverter((
				request) -> (attributes) -> (java.util.Map<String, Object>) attributes.get("data"));
		return service;
	}
}
