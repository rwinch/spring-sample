package example;

import java.util.function.Predicate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagerFactories;
import org.springframework.security.authorization.AuthorizationManagerFactories.AdditionalRequiredFactorsBuilder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;

@SpringBootApplication
@EnableMultiFactorAuthentication(authorities = { FactorGrantedAuthority.OTT_AUTHORITY, FactorGrantedAuthority.PASSWORD_AUTHORITY})
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
    Customizer<AdditionalRequiredFactorsBuilder<Object>> mfa() {
        AuthorityAuthorizationManager<Object> webauthn = hasAuthority(FactorGrantedAuthority.WEBAUTHN_AUTHORITY);
        Predicate<Authentication> notWebauthn = (a) -> !webauthn.authorize(() -> a, "").isGranted();
        return (mfa) -> mfa
            .when(notWebauthn);
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
