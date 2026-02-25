package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class Application {

    @Bean
    SecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/hasAnyRole").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/permitAll").permitAll()
                .requestMatchers("/user").hasRole("USER")
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    UserDetailsManager userDetails() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .roles("USER")
            .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
