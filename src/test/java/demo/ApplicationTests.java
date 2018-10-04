package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {
	@Autowired
	WebTestClient http;

	@Test
	public void rxWillFail() throws Exception {
		this.http.get()
				.uri("/rx")
				.headers(user())
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("Hello user");
	}

	@Test
	public void monoWorks() throws Exception {
		this.http.get()
				.uri("/mono")
				.headers(user())
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("Hello user");
	}

	private Consumer<HttpHeaders> user() {
		return h -> h.setBasicAuth("user", "password");
	}
}
