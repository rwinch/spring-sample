package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = ServletWebSecurityAutoConfiguration.class)
@Import(SecurityDsl.class)
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
