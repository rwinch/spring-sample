package example;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

@SpringBootApplication
public class Application {

    @Bean
    void security(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                .anyRequest().access((AuthorizationManager<RequestAuthorizationContext>) (authentication, context) -> {
                    HttpServletRequest request = context.getRequest();
                    return null;
                })
            );
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
