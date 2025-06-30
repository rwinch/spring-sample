package example.spring;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.server.reactive.SslInfo;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 *
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MockServerSpecSslInfoTests {

	@Autowired
	WebTestClient.MockServerSpec<?> server;

	/**
	 * If {@link #sslInfoNullThenUnauthorized()} is invoked after this method,
	 * it will fail unless we mark as DirtiesContext or ensure to use prototype bean
	 */
	@Test
	@Order(1) // force this method first to demo need for prototype
	void sslInfoThenOkAndBodyIsSubjectDn() {
		this.server
			.sslInfo(SslInfo.from("id", TestCertificates.rod()))
			// 2 extra methods necessary
			.configureClient()
			.build()
			.get()
			.uri("/")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("CN=rod,OU=Spring Security,O=Spring Framework");
	}

	@Test
	@Order(2)
	void sslInfoNullThenUnauthorized() {
		this.server
			// 2 extra (from using WebTestClient directly) methods necessary even when not adding x509
			.configureClient()
			.build()
			.get()
			.uri("/")
			.exchange()
			.expectStatus().isUnauthorized();
	}

	@TestConfiguration
	static class MockServerSpecConfiguration {
		@Bean
		// prototype necessary because MockServerSpec.sslInfo mutates the MockServerSpec in the ApplicationContext
		// We could also use DirtiesContext, but that makes the whole ApplicationContext reload impacting performance
		@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
		WebTestClient.MockServerSpec<?> mockServerSpec(ApplicationContext context) {
			return WebTestClient.bindToApplicationContext(context);
		}
	}
}
