package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.experimental.boot.server.exec.CommonsExecWebServerFactoryBean;

import static org.springframework.experimental.boot.server.exec.MavenClasspathEntry.springBootStarter;

@TestConfiguration(proxyBeanMethods = false)
public class TestApplication {

	@Bean
//	@OAuth2ClientProviderIssuerUri
	static CommonsExecWebServerFactoryBean authorizationServer() {
		// @formatter:off
		return CommonsExecWebServerFactoryBean.builder()
				.defaultSpringBootApplicationMain()
				.classpath((classpath) -> classpath
						.entries(springBootStarter("oauth2-authorization-server"))
				);
		// @formatter:on
	}


	public static void main(String[] args) {

		SpringApplication.from(Application::main)
				.with(TestApplication.class)
				.run(args);
	}
}
