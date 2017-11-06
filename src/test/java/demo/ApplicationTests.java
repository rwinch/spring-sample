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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.Credentials.basicAuthenticationCredentials;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTests {
	@Autowired
	ApplicationContext context;

	WebTestClient http;

	@Before
	public void setup() {
		this.http = WebTestClient.bindToApplicationContext(this.context)
				.apply(springSecurity())
				.configureClient()
				.filter(basicAuthentication())
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	@Test
	public void requiresLogin() throws Exception {
		this.http.get()
				.uri("/")
				.exchange()
				.expectStatus().isUnauthorized();
	}

	@Test
	public void httpBasicWorks() throws Exception {
		this.http.get()
			.uri("/")
			.attributes(basicAuthenticationCredentials("user", "password"))
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("Hello Boot!");
	}

	@WithMockUser
	@Test
	public void authenticatedWorks() throws Exception {
		this.http.get()
				.uri("/")
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("Hello Boot!");
	}
}
