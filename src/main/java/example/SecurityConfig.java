package example;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.webauthn.api.AuthenticatorAttachment;
import org.springframework.security.web.webauthn.api.AuthenticatorSelectionCriteria;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialRpEntity;
import org.springframework.security.web.webauthn.api.ResidentKeyRequirement;
import org.springframework.security.web.webauthn.api.UserVerificationRequirement;
import org.springframework.security.web.webauthn.management.MapPublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.MapUserCredentialRepository;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.security.web.webauthn.management.WebAuthnRelyingPartyOperations;
import org.springframework.security.web.webauthn.management.Webauthn4JRelyingPartyOperations;

@Configuration(proxyBeanMethods = false)
class SecurityConfig {
	@Bean
	DefaultSecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(e -> e
					.anyRequest().authenticated()
			)
			.formLogin(Customizer.withDefaults())
			.webAuthn((webAuthn) -> webAuthn
				.rpName("Spring Security Relying Party")
				.rpId("localhost")
				.allowedOrigins("http://localhost:8080")
			);
		return http.build();
	}

	@Bean
	MapPublicKeyCredentialUserEntityRepository userEntityRepository() {
		return new MapPublicKeyCredentialUserEntityRepository();
	}

	@Bean
	MapUserCredentialRepository userCredentialRepository() {
		return new MapUserCredentialRepository();
	}

	@Bean
	WebAuthnRelyingPartyOperations relyingPartyOperations(PublicKeyCredentialUserEntityRepository userEntities, UserCredentialRepository userCredentials) {
		Webauthn4JRelyingPartyOperations result = new Webauthn4JRelyingPartyOperations(userEntities, userCredentials,
				PublicKeyCredentialRpEntity.builder().id("localhost").name("Spring Security Relying Party").build(), Set.of("http://localhost:8080"));
		result.setCustomizeCreationOptions(c -> c.authenticatorSelection(
				AuthenticatorSelectionCriteria.builder()
						.userVerification(UserVerificationRequirement.PREFERRED)
						.residentKey(ResidentKeyRequirement.REQUIRED)
						.authenticatorAttachment(AuthenticatorAttachment.PLATFORM)
						.build()));
		return result;

	}

	@Bean
	UserDetailsService userDetailsService() {
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
		return new InMemoryUserDetailsManager(user, baz);
	}
}
