package demo;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
@Profile("dev")
public class DevConfiguration {
    @Bean
    public AuthenticationFilter authenticationFilter() {
        AuthenticationFilter result = new AuthenticationFilter((Authentication authentication) -> authentication,
            r -> new TestingAuthenticationToken("user", "password", "ROLE_USER")
        );
        result.setSuccessHandler((request,response,authentication) -> {});
        return result;
    }

    @Bean
    FilterRegistrationBean<AuthenticationFilter> authenticationFilterFilterRegistrationBean(AuthenticationFilter authenticationFilter) {
        FilterRegistrationBean<AuthenticationFilter> result = new FilterRegistrationBean<>(authenticationFilter);
        result.setEnabled(false);
        return result;
    }
}
