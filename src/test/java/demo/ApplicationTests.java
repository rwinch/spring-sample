package demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTests {
	@Autowired
	ApplicationContext context;

	WebTestClient http;

	@Before
	public void setup() {
		this.http = WebTestClient.bindToApplicationContext(this.context)
				.apply(springSecurity())
				.configureClient()
				.build();
	}

	@Test
	public void loginHasCsrfToken() {
		this.http.get().uri("/login")
				.exchange()
				.expectBody(String.class)
				.value(c -> assertThat(c).contains("<input type=\"hidden\" name=\"_csrf\" value=\""));
	}
}
