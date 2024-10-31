package example;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	@Autowired
	WebTestClient http;

	@Test
	void fooWhenAnonymousThenOk() {
		this.http.get().uri("/api/v1/foo").exchange().expectStatus().is2xxSuccessful();
	}

	@Test
	void barWhenAnonymousThenUnauthorized() {
		this.http.get().uri("/api/v1/bar").exchange().expectStatus().isUnauthorized();
	}

	@Test
	@WithMockUser
	void barWhenUserThenOk() {
		this.http.get().uri("/api/v1/bar").exchange().expectStatus().isOk();
	}

	@Test
	void bazWhenAnonymousThenUnauthorized() {
		this.http.get().uri("/api/v1/baz").exchange().expectStatus().isUnauthorized();
	}

	@Test
	@WithMockUser
	void bazWhenUserThenForbidden() {
		this.http.get().uri("/api/v1/baz").exchange().expectStatus().isForbidden();
	}

	@Test
	@WithMockUser(roles = "BAZ")
	void bazWhenBazThenOk() {
		this.http.get().uri("/api/v1/baz").exchange().expectStatus().isOk();
	}

}