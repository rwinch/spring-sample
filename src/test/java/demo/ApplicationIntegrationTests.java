package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTests {
	@Autowired
	TestRestTemplate rest;

	@Test
	public void entityExplicitHeaders() {
		ResponseEntity<String> get = this.rest.getForEntity("/entity/explicit", String.class);

		HttpHeaders headers = get.getHeaders();
		assertThat(headers.getCacheControl()).isEqualTo("private, max-age=1800, must-revalidate");
		assertThat(headers.getPragma()).isNull();
		assertThat(headers.getExpires()).isEqualTo(-1);
	}

	@Test
	public void entitySecurityHeaders() {
		ResponseEntity<String> get = this.rest.getForEntity("/entity/security", String.class);

		HttpHeaders headers = get.getHeaders();
		assertThat(headers.getCacheControl()).isEqualTo("no-cache, no-store, max-age=0, must-revalidate");
		assertThat(headers.getPragma()).isEqualTo("no-cache");
		assertThat(headers.getFirst(HttpHeaders.EXPIRES)).isEqualTo("0");
	}
}
