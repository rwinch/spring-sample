package example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
	@Autowired
	TestRestTemplate rest;

	@Test
	void indexWhenAnonymous() throws Exception{
		ResponseEntity<String> result = rest.exchange(RequestEntity.get(URI.create("/")).build(), String.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).doesNotContain("user");
	}

	@Test
	void indexWhenAuthenticated() throws Exception{
		ResponseEntity<String> result = rest.exchange(RequestEntity.get(URI.create("/")).headers(h -> h.setBasicAuth("user", "password")).build(), String.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).contains("user");
	}
}
