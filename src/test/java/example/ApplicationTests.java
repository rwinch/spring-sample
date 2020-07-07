package example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWebTestClient
public class ApplicationTests {
	@Autowired
	WebTestClient rest;

	@WithUserDetails
	@Test
	public void authenticatedWorks() throws Exception {
		this.rest.get().uri("/").exchange()
			.expectStatus()
				.is2xxSuccessful()
			.expectBody(String.class)
				.value(b -> assertThat(b).contains("Hello Boot!"));
	}
}
