package example.spring;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static example.spring.SslInfoWebTestClientConfigurer.x509;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class MutateWithSslInfoIntegrationTests {

	@Autowired
	WebTestClient client;

	@Test
	void sslInfoNullThenUnauthorized() {
		this.client.get()
			.uri("/")
			.exchange()
			.expectStatus().isUnauthorized();
	}

	@Test
	void sslInfoThenOkAndBodyIsSubjectDn() {
		this.client.mutateWith(x509(TestCertificates.rod()))
			.get()
			.uri("/")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("CN=rod,OU=Spring Security,O=Spring Framework");
	}
}
