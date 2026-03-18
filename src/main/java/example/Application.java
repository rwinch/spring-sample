package example;

import java.util.function.Supplier;

import org.jspecify.annotations.Nullable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authorization.AllRequiredFactorsAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.DefaultAuthorizationManagerFactory;
import org.springframework.security.authorization.RequiredFactor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialUserEntity;
import org.springframework.security.web.webauthn.management.MapPublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.MapUserCredentialRepository;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;

@SpringBootApplication
@EnableMultiFactorAuthentication(authorities = {})
public class Application {

    @Bean
    SecurityFilterChain security(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(requests -> requests
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults())
            .oneTimeTokenLogin(ott -> ott
                .tokenGenerationSuccessHandler((request,response, token) -> {
                    response.setHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
                    response.getWriter().write("Check the console");
                    System.out.println("Use the token http://localhost:" + request.getServerPort() + "/login/ott?token=" + token.getTokenValue());
                })
            )
            .webAuthn(webauthn -> webauthn
                .rpId("localhost")
                .allowedOrigins("http://localhost:8080")
            )
            .build();
    }

    @Bean
    public PublicKeyCredentialUserEntityRepository userEntityRepository() {
        return new MapPublicKeyCredentialUserEntityRepository();
    }

    @Bean
    DefaultAuthorizationManagerFactory<Object> mfa(PublicKeyCredentialUserEntityRepository userEntities, UserCredentialRepository userCreds) {
        DefaultAuthorizationManagerFactory<Object> mfa = new DefaultAuthorizationManagerFactory<>();
        mfa.setAdditionalAuthorization(new WebauthnOrMfaAuthorizationManager<>(userEntities, userCreds));
        return mfa;
    }

    private static class WebauthnOrMfaAuthorizationManager<T> implements AuthorizationManager<T> {

        @Override
        public @Nullable AuthorizationResult authorize(Supplier<? extends @Nullable Authentication> authn, T object) {
            AuthorizationResult webauthnResult = this.requiresWebauthn.authorize(authn, object);
            if (webauthnResult.isGranted()) {
                return webauthnResult;
            }
            if (webauthnRegistered(authn.get())) {
                return this.requiresPasswordOttWebauthnFactors.authorize(authn, object);
            }
            return this.requiresPasswordOttFactors.authorize(authn, object);
        }

        public boolean webauthnRegistered(Authentication authentication) {
            if (authentication == null || authentication.getName() == null) {
                return false;
            }
            PublicKeyCredentialUserEntity userEntity = this.userEntities
                    .findByUsername(authentication.getName());
            if (userEntity == null) {
                return false;
            }
            return !this.userCredentials.findByUserId(userEntity.getId()).isEmpty();
        }

        private final AuthorizationManager<T> requiresPasswordOttFactors =  AllRequiredFactorsAuthorizationManager.<T>builder()
                .requireFactor(RequiredFactor.Builder::passwordAuthority)
                .requireFactor(RequiredFactor.Builder::ottAuthority)
                .build();

        private final AuthorizationManager<T> requiresWebauthn = AllRequiredFactorsAuthorizationManager.<T>builder()
                        .requireFactor(RequiredFactor.Builder::webauthnAuthority)
                        .build();

        public final AuthorizationManager<T> requiresPasswordOttWebauthnFactors = AllRequiredFactorsAuthorizationManager.<T>builder()
                .requireFactor(RequiredFactor.Builder::passwordAuthority)
                .requireFactor(RequiredFactor.Builder::ottAuthority)
                .requireFactor(RequiredFactor.Builder::webauthnAuthority)
                .build();

        final PublicKeyCredentialUserEntityRepository userEntities;

        final UserCredentialRepository userCredentials;

        private WebauthnOrMfaAuthorizationManager(PublicKeyCredentialUserEntityRepository userEntities, UserCredentialRepository userCredentials) {
            this.userEntities = userEntities;
            this.userCredentials = userCredentials;
        }
    }

    @Bean
    public UserCredentialRepository userCredentialRepository() {
        return new MapUserCredentialRepository();
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
