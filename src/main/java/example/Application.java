package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@SpringBootApplication
public class Application {

    @Bean
    Customizer<HttpSecurity> security() throws Exception {
        return http -> http
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/**").permitAll()
            );
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
