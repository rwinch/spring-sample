package demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

import java.time.Instant;
import java.util.Arrays;


@WebMvcTest(controllers = OkController.class)
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
public class WebTestClientTest {
	WebTestClient client;

	@MockBean
	// mock the JwtDecoder so that the jwks is not resolved since no AuthZ Server Setup
	JwtDecoder jwtDecoder;

	@Autowired
	void setMockMvc(MockMvc mockMvc) {
		this.client = MockMvcWebTestClient.bindTo(mockMvc)
				.build();
	}

	@Test
	void getWhenAuthenticated() {
		TestSecurityContextHolder.setAuthentication(jwtAuthenticationToken());

		client
				.get()
				.uri("/")
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	void getWhenNotAuthenticated() {
		client
				.get()
				.uri("/")
				.exchange()
				.expectStatus().is4xxClientError();
	}

	private static JwtAuthenticationToken jwtAuthenticationToken() {
		return new JwtAuthenticationToken(jwt().build(), AuthorityUtils.createAuthorityList("SCOPE_message:read"));
	}

	public static Jwt.Builder jwt() {
		// @formatter:off
		return Jwt.withTokenValue("token")
				.header("alg", "none")
				.audience(Arrays.asList("https://audience.example.org"))
				.expiresAt(Instant.MAX)
				.issuedAt(Instant.MIN)
				.issuer("https://issuer.example.org")
				.jti("jti")
				.notBefore(Instant.MIN)
				.subject("mock-test-subject");
		// @formatter:on
	}
}
