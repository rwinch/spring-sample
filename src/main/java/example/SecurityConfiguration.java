package example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Bean
	DefaultSecurityFilterChain springSecurity(HttpSecurity http, OAuth2AuthorizationRequestResolver authorizationResolver) throws Exception {
		http
			.authorizeHttpRequests(requests -> requests
					.anyRequest().authenticated()
			)
			.oauth2Login(oauth2 -> oauth2
					.authorizationEndpoint(authorization -> authorization
							.authorizationRequestResolver(authorizationResolver)
					)
			);
		return http.build();
	}

	@Bean
	static OAuth2AuthorizationRequestResolver authorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
		DefaultOAuth2AuthorizationRequestResolver authorizationRequestResolver =
				new DefaultOAuth2AuthorizationRequestResolver(
						clientRegistrationRepository, "/oauth2/authorization");
		authorizationRequestResolver.setAuthorizationRequestCustomizer(
				OAuth2AuthorizationRequestCustomizers.withPkce());

		return  authorizationRequestResolver;
	}

	@Bean
	static WebClient webClient(WebClient.Builder webClient) {
		return webClient.build();
	}
}
